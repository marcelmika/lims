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
 * Group Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single group item.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.MessageItemModel = Y.Base.create('messageItemModel', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        // TODO: Take from settings
        var content, parameters, url = Y.one('#limsPortletURL').get('value');

        switch (action) {

            // CREATE
            case 'create':

                // Parameters
                parameters = {
                    conversationId: this.get('conversationId')
                };
                parameters = Y.JSON.stringify(parameters);
                // Content
                content = Y.JSON.stringify(this.toJSON());

                // Send request
                Y.io(url, {
                    method: "POST",
                    data: {
                        query: "CreateMessage",
                        parameters: parameters,
                        content: content
                    },
                    on: {
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
                        }
                    }
                });
                break;

            // READ
            case 'read':
                return;

            // UPDATE
            case 'update': // Message cannot be updated
            // DELETE
            case 'delete': // Message cannot be deleted
                return;

            default:
                callback('Invalid action');
        }
    }

}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        conversationId: {
            value: ""
        },

        from: {
            /**
             * Setter
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

        body: {
            value: "" // default value
        },

        createdAt: {
            value: null // default value
        }
    }
});
