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
 * Buddy Search List Model
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * BuddyModelItem instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddySearchListModel = Y.Base.create('buddySearchListModel', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the BuddyModelItem class.
    model: Y.LIMS.Model.BuddyModelItem,

    /**
     * Starts a search for buddies based on the search query
     *
     * @param searchQuery
     */
    search: function (searchQuery) {
        // Save the query
        this.set('searchQuery', searchQuery);
        // Load the source
        this.load();
    },

    // Custom sync layer.
    sync: function (action, options, callback) {

        var parameters,
            instance = this,
            searchQuery = this.get('searchQuery');

        switch (action) {

            case 'read':

                // Notify about the beginning of search
                this.fire('searchStarted');

                // Set parameters
                parameters = Y.JSON.stringify({
                    searchQuery: searchQuery
                });

                // Read from server
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "SearchBuddies",
                        parameters: parameters
                    },
                    on: {
                        success: function (id, o) {

                            // Vars
                            var buddies,
                                index;

                            // Parse buddies from response
                            buddies = Y.JSON.parse(o.responseText);

                            // Empty the list
                            instance.reset();

                            // Deserialize buddies
                            for (index = 0; index < buddies.length; index++) {
                                // Add buddy to the list
                                instance.add(new Y.LIMS.Model.BuddyModelItem(buddies[index]));
                            }

                            // Fire success event
                            instance.fire('searchSuccess', {
                                searchList: instance
                            });
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Empty the list
                            instance.reset();
                            // Fire error event
                            instance.fire('searchError');

                            // Callback the error
                            if (callback) {
                                callback("search error", o.responseText);
                            }
                        }
                    }
                });
                break;


            case 'create':
            case 'update':
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
         * Search query string
         *
         * {string}
         */
        searchQuery: {
            value: null // to be set
        }
    }
});

