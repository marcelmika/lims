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
 * Message Item Model
 *
 * Class represents a model for a single message
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.MessageItemModel = Y.Base.create('messageItemModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Custom sync layer
     *
     * @param action  [create|read|update|delete]
     * @param options extra parameters
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var content,            // Content of the request
            parameters,         // Request parameters
            instance = this;    // Save scope

        switch (action) {

            // Create action, called on MessageItemModel.save() method
            case 'create':

                // Notify about a beginning of the process
                instance.fire('messageBegin');

                // Parameters
                parameters = {
                    conversationId: this.get('conversationId')
                };

                // Serialize
                parameters = Y.JSON.stringify(parameters);
                content = Y.JSON.stringify(this.toJSON());

                instance.set('acknowledged', false);

                // Send request
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "CreateMessage",
                        parameters: parameters,
                        content: content
                    },
                    on: {
                        success: function () {

                            // Message was acknowledged. Thanks to that the message can be overwritten by
                            // a copy of the message received from the server.
                            instance.set('acknowledged', true);
                            instance.set('error', false);
                            // Notify about success
                            instance.fire('messageSent');

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

                            // An error occurred, save this to model
                            instance.set('acknowledged', false);
                            instance.set('error', true);

                            // Notify about failure
                            instance.fire('messageError');

                            if (callback) {
                                callback("Cannot send message", o.response);
                            }
                        }
                    }
                });
                break;

            case 'read':
            case 'update':
            case 'delete':
                break;

            default:
                callback('Invalid action');
                break;
        }
    }

}, {
    ATTRS: {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        /**
         * Unique string id of the conversation.
         *
         * {string}
         */
        conversationId: {
            value: ""
        },

        /**
         * Sender of the message
         *
         * {BuddyModelItem}
         */
        from: {
            /**
             * Setter
             *
             * @param value String or BuddyModelItem
             * @returns {BuddyModelItem}
             */
            setter: function (value) {
                // Create buddy from object
                if (value.name !== "buddyModelItem") {
                    return new Y.LIMS.Model.BuddyModelItem(value);
                }
                // Value is already an instance of Y.LIMS.Model.BuddyModelItem
                return value;
            }
        },

        /**
         * Body of the message
         *
         * {string}
         */
        body: {
            value: "" // default value
        },

        /**
         * Time of creation
         *
         * {timestamp}
         */
        createdAt: {
            value: null // default value
        },

        /**
         * Set to true if the message was acknowledged. In other words the message
         * was successfully sent to server and server responded with success.
         *
         * {boolean}
         */
        acknowledged: {
            value: true // default value
        },

        /**
         * If an error during the message delivery occurs this flag is set to true
         *
         * {boolean}
         */
        error: {
            value: false // default value
        }
    }
});
