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

Y.LIMS.Model.GroupModelItem = Y.Base.create('groupModelItem', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        var data;

        switch (action) {
            case 'create':
                data = this.toJSON();
                return;
            case 'read':
                return;
            case 'update':
                return;
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

        name: {
            value: "" // default value
        },

        buddies: {
            value: [] // default value
        },

        /**
         * Social relation type
         *
         * {Y.LIMS.Model.GroupSocialRelationType}
         */
        socialRelation: {
            /**
             * Setter
             *
             * @param object
             * @returns {Y.LIMS.Model.GroupSocialRelationType}
             */
            setter: function (object) {
                // No social relation was set
                if (!object) {
                    return null;
                }

                // Create a model instance from value object
                if (object.name !== "groupSocialRelationType") {
                    return new Y.LIMS.Model.GroupSocialRelationType({
                        socialRelationType: object
                    });
                }
                // Value is already an instance of Y.LIMS.Model.GroupSocialRelationType
                return object;
            }
        }
    }
});
