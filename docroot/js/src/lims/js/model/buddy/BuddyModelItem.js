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
 * Buddy Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single Buddy.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelItem = Y.Base.create('buddyModelItem', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Updates buddy presence
     *
     * @param presence
     * @param callback
     * @private
     */
    updatePresence: function (presence, callback) {
        // Vars
        var content;

        // Update locally
        this.set('presence', presence);

        // Deserialize
        content = Y.JSON.stringify(this.toJSON());

        // Do the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateBuddyPresence",
                content: content
            },
            on: {
                success: function (id, o) {
                    if (callback) {
                        callback(null, o.responseText);
                    }
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }
                    if (callback) {
                        callback("Cannot update buddy presence", o.responseText);
                    }
                }
            }
        });
    },

    // Custom sync layer.
    sync: function (action, options, callback) {

        switch (action) {
            case 'create':
            case 'update':
            case 'read':
            case 'delete':
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

        /**
         * Id of the buddy
         *
         * {integer|null}
         */
        buddyId: {
            value: null // default value
        },

        /**
         * Screen name of the buddy
         *
         * {string}
         */
        screenName: {
            value: "" // default value
        },

        /**
         * Set to true if the buddy is male
         *
         * {boolean|null}
         */
        male: {
            value: null // default value
        },

        /**
         * Portrait id of the buddy
         *
         * {integer}
         */
        portraitId: {
            value: 0 // default value
        },

        /**
         * Portrait token of the buddy
         *
         * {string|null}
         */
        portraitToken: {
            value: null // default value
        },

        /**
         * Portrait image token of the buddy
         *
         * {string | null}
         */
        portraitImageToken: {
            value: null // default value
        },

        /**
         * Full name of the buddy
         *
         * {string}
         */
        fullName: {
            value: "" // default value
        },

        /**
         * Presence of the buddy
         *
         * {string}
         */
        presence: {
            value: null // default value
        }
    }
});
