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

Y.LIMS.Model.ConversationModel = Y.Base.create('conversationModel', Y.Model, [], {

    /**
     * Adds message to conversation. Sends request to server.
     *
     * @param message
     */
    addMessage: function (message) {

        // Vars
        var messageList = this.get('messageList');

        // This will send the message to the server
        message.set('conversationId', this.get('conversationId'));
        message.save(); // Message model has its own sync layer

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
        var settings = new Y.LIMS.Core.Settings(),
            parameters = Y.JSON.stringify({
                conversationId: this.get('conversationId')
            });

        // Send the request
        Y.io(settings.getServerRequestUrl(), {
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
    resetUnreadMessagesCounter: function () {

        // Vars
        var settings = new Y.LIMS.Core.Settings(),
            parameters = Y.JSON.stringify({
                conversationId: this.get('conversationId')
            });

        // Send the request
        Y.io(settings.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "ResetUnreadMessagesCounter",
                parameters: parameters
            },
            on: {
                // There isn't much we can do. If the request ends with success
                // the user is not going to see badge anymore (of course if there will be any
                // new messages it will appear again).
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
            etag = this.get('etag'),
            settings = new Y.LIMS.Core.Settings();

        switch (action) {

            // This is called whenever the conversation is created i.e whenever
            // the user clicks on any of the buddies in the group
            case 'create':
                // Simply take the conversation object, serialize it to json
                // and send.
                content = Y.JSON.stringify(this.toJSON());

                // Send the request
                Y.io(settings.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "CreateSingleUserConversation",
                        content: content
                    },
                    on: {
                        success: function (id, o) {
                            // Deserialize response
                            response = Y.JSON.parse(o.response);
                            // Call success
                            callback(null, instance);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
                            // Call failure
                            callback("Cannot create new conversation", o);
                        }
                    }
                });
                return;

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of messages related to the conversation.
            case 'read':
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
                Y.io(settings.getServerRequestUrl(), {
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
                            response = Y.JSON.parse(o.response);
                            // Update message list
                            instance.updateConversation(response);
                            // Call success
                            callback(null, instance);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
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
        var messageList = this.get('messageList'),
            messageModels = [],
            index;
        // Update from response
        this.setAttrs({
            etag: conversation.etag,
            unreadMessagesCount: conversation.unreadMessagesCount
        });

        for (index = 0; index < conversation.messages.length; index++) {
            // TODO: Handle messages which wasn't yet sent to server
            // Add message to message list
            messageModels.push(new Y.LIMS.Model.MessageItemModel(conversation.messages[index]));
        }

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

        messageList: {
            valueFn: function () {
                return new Y.LIMS.Model.MessageListModel();
            }
        }
    }
});
