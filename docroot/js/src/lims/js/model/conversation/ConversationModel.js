/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Conversation Model
 *
 * This class is responsible for data related to the conversation. Such as conversation
 * metadata, list of participants and list of messages.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationModel = Y.Base.create('conversationModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Adds message to conversation. Sends request to server.
     *
     * @param message
     */
    addMessage: function (message) {

        // Vars
        var messageList = this.get('messageList'),  // List of messages
            offset = this.get('serverTimeOffset'),  // Server time offset
            createdAt,                              // Creation date of message
            instance = this;                        // Save scope

        // Message has a conversation id same as the conversation id stored in this model
        message.set('conversationId', this.get('conversationId'));

        // This will send the message to the server
        message.save(function (err) {
            // Trigger event if the message wasn't sent
            if (err) {
                instance.fire('messageError');
            }
        });

        // Timestamp needs to be updated because of the possible difference between
        // server time and client time
        createdAt = message.get('createdAt');
        message.set('createdAt', createdAt + offset);

        // Add it locally. We are async here. In other words we are not
        // waiting for the response and directly add the message to the list
        // and present it to the user. If the request fails we keep the message
        // in the list and let the user to resend the message. Apparently we don't do
        // this here since this is not a responsibility of the model.
        messageList.add(message);

        // Notify about the event
        this.fire('messageAdded', {
            message: message
        });
    },

    /**
     * Closes the conversation for currently logged user. The conversation however still
     * exists in the system. Only it's not visible to the user. If the counterpart of the
     * conversation sends message to closed conversation the conversation will open again.
     */
    closeConversation: function () {

        // Vars
        var parameters = Y.JSON.stringify({
            conversationId: this.get('conversationId')
        });

        // Send the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "CloseSingleUserConversation",
                parameters: parameters
            },
            on: {
                // There isn't much we can do. If the request ends with success
                // we simply don't do anything. If it fails the user will see
                // the conversation whenever the user goes to another web page
                // and try it again.
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }
                }
            }
        });
    },

    /**
     * Resets counter of unread messages. This should happen when the user opens conversation.
     * Since it's opened all messages have been read. Thus we can reset the counter.
     */
    resetUnreadMessagesCounter: function (callback) {

        // Vars
        var instance = this,
            parameters = Y.JSON.stringify({
            conversationId: this.get('conversationId')
        });

        // Send the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "ResetUnreadMessagesCounter",
                parameters: parameters
            },
            on: {
                success: function () {

                    // Update unread messages count
                    instance.set('unreadMessagesCount', 0);

                    if (callback) {
                        callback(null, instance);
                    }
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    if (callback) {
                        callback('cannot reset unread messages', instance);
                    }
                }
            }
        });
    },

    /**
     * Custom sync layer
     *
     * @param action
     * @param options
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var content,            // Content which will be sent as body in request
            parameters,         // Request parameters
            instance = this,    // Save the instance so we can call its methods in diff context
            response,           // Response from the server
            etag = this.get('etag');

        switch (action) {

            // This is called whenever the conversation is created i.e whenever
            // the user clicks on any of the buddies in the group
            case 'create':

                // Fire begin event
                instance.fire('createBegin');

                // Simply take the conversation object, serialize it to json
                // and send.
                content = Y.JSON.stringify(this.toJSON());

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "CreateSingleUserConversation",
                        content: content
                    },
                    on: {
                        success: function (id, o) {
                            // Deserialize response
                            response = Y.JSON.parse(o.responseText);

                            // Fire success event
                            instance.fire('createSuccess');

                            // Call success
                            callback(null, instance);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Fire failure event
                            instance.fire('createError');

                            // Call failure
                            callback("Cannot create new conversation", o);
                        }
                    }
                });
                return;

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of messages related to the conversation.
            case 'read':

                // Fire begin event
                instance.fire('readBegin');

                // Construct parameters
                parameters = Y.JSON.stringify({
                    conversationId: this.get('conversationId'),
                    etag: etag,
                    pagination: {
                        firstMessageId: null,
                        lastMessageId: null,
                        action: "next"
                    }
                });

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadSingleUserConversation",
                        parameters: parameters
                    },
                    on: {
                        success: function (id, o) {
                            // If nothing has change the server return 304 (not modified)
                            // As a result we don't need to refresh anything
                            if (o.status === 304) {
                                callback(null, instance);
                                return;
                            }
                            // Deserialize response
                            response = Y.JSON.parse(o.responseText);
                            // Update message list
                            instance.updateConversation(response);

                            // Fire success event
                            instance.fire('readSuccess');

                            // Call success
                            callback(null, instance);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Fire failure event
                            instance.fire('readError');

                            // Call failure
                            callback("Cannot read conversation", o);
                        }
                    }
                });

                return;

            default:
                callback('Invalid action');
        }
    },

    /**
     * Updates message list with the messages passed as a parameter.
     * Contains logic which removes duplicates and keep the correct order
     * of messages.
     *
     * @param conversation
     */
    updateConversation: function (conversation) {

        // Vars
        var messageList = this.get('messageList'),        // List of messages
            offset = this.get('serverTimeOffset'),        // Server time offset
            createdAt,                                    // Stores message time of creation
            messageModels = [],                           // Holds message models
            notAcknowledgedModels,                        // Message models from list that are not yet acknowledged
            message,                                      // Deserialized message
            index;                                        // Used for iteration

        // Update from response
        this.setAttrs({
            etag: conversation.etag,
            unreadMessagesCount: conversation.unreadMessagesCount
        });

        // Keep a copy of messages that haven't been sent to server yet
        notAcknowledgedModels = messageList.getNotAcknowledged();

        // Parse messages from conversation messages
        for (index = 0; index < conversation.messages.length; index++) {

            // Deserialize message
            message = new Y.LIMS.Model.MessageItemModel(conversation.messages[index]);

            // Timestamp needs to be updated because of the possible difference between
            // server time and client time
            createdAt = message.get('createdAt');
            message.set('createdAt', createdAt + offset);

            // Add message to message list
            messageModels.push(message);
        }

        // Add not yet acknowledged messages at the and.
        // Note: This is quite old school however the fastest possible way
        for (index = 0; index < notAcknowledgedModels.length; index++) {
            messageModels.push(notAcknowledgedModels[index]);
        }

        // Replay old models with the new ones
        messageList.reset(messageModels);

        // Notify about the event
        this.fire('messagesUpdated', {
            messageList: messageList
        });
    }

}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        conversationId: {
            value: null
        },

        title: {
            value: ""
        },

        creator: {
            value: null // to be set
        },

        participants: {
            value: "" // default value
        },

        etag: {
            value: null
        },

        unreadMessagesCount: {
            value: 0 // default value
        },

        serverTimeOffset: {
            value: null // to be set
        },

        messageList: {
            valueFn: function () {
                return new Y.LIMS.Model.MessageListModel();
            }
        }
    }
});
