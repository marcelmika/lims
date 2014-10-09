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
 * Properties Model
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.PropertiesModel = Y.Base.create('propertiesModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Custom sync layer
     *
     * @param action  [create|read|update|delete]
     * @param options extra parameters
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var content;

        switch (action) {

            // Read action, called on ServerTimeModel.load() method
            case 'create':

                // Deserialize
                content = Y.JSON.stringify(this.toJSON());

                // Do the request
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "PatchProperties",
                        content: content
                    },
                    on: {
                        success: function () {

                            if (callback) {
                                callback(null);
                            }
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            if (callback) {
                                callback('Cannot update property');
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
                if (callback) {
                    callback('Invalid action');
                }
                break;
        }
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Buddy list strategy
         *
         * {string}
         */
        buddyListStrategy: {
            value: null // to be set
        }
    }
});
