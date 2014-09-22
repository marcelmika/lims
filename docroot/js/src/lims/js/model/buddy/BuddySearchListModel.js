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
        // Vars

        // Save the query
        this.set('searchQuery', searchQuery);

        this.load();
    },

    // Custom sync layer.
    sync: function (action, options, callback) {

        var parameters,
            instance = this,
            searchQuery = this.get('searchQuery');

        switch (action) {
            case 'create':
            case 'update':
            case 'read':

                // Notify about the beginning of search
                this.fire('searchStared');

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
                            console.log(o);
//                            var i, groupCollection, groups, group, buddies;
//                            // Parse groups
//                            groupCollection = Y.JSON.parse(o.response);
//                            groups = groupCollection.groups;
//
//                            if (etag.toString() !== groupCollection.etag.toString()) {
//
//                                // Empty the list
//                                instance.reset();
//
//                                instance.set('etag', groupCollection.etag);
//
//                                // Add groups to list
//                                for (i = 0; i < groups.length; i++) {
//                                    // Create new group
//                                    group = new Y.LIMS.Model.GroupModelItem(groups[i]);
//
//                                    // List of buddies
//                                    buddies = new Y.LIMS.Model.BuddyModelList();
//                                    buddies.add(groups[i].buddies);
//
//                                    // Add buddies to group
//                                    group.set('buddies', buddies);
//
//                                    // Add group to group list
//                                    instance.add(group);
//                                }
//
//                                instance.fire('groupsReadSuccess', {
//                                    groupsList: instance
//                                });
//                            }
                            instance.fire('searchSuccess');
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            instance.fire('searchError');

                            if (callback) {
                                callback("search error", o.response);
                            }
                        }
                    },
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });


                this.fire('searchFinished');

                break;
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

        searchQuery: {
            value: null // to be set
        }
    }
});

