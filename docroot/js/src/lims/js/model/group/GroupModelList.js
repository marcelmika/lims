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
 * Group Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * GroupModelItem instances, and to provide some convenience methods for getting
 * information about the Group items in the list.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModelList = Y.Base.create('groupModelList', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the GroupModelItem class.
    model: Y.LIMS.Model.GroupModelItem,

    // Custom sync layer.
    sync: function (action, options, callback) {

        // Vars
        var parameters,
            etag = this.get('etag'),
            instance = this;

        switch (action) {

            case 'read':

                // Set parameters
                parameters = Y.JSON.stringify({
                    // Send etag to server so it knows if it should send groups again or we should keep
                    // the old cached values
                    etag: etag
                });

                // Read from server
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetGroupList",
                        parameters: parameters
                    },
                    on: {
                        success: function (id, o) {

                            var i, groupCollection, groups, group, buddies;
                            // Parse groups
                            groupCollection = Y.JSON.parse(o.response);
                            groups = groupCollection.groups;

                            if (etag.toString() !== groupCollection.etag.toString()) {

                                // Empty the list
                                instance.fire('groupReset');

                                instance.set('etag', groupCollection.etag);

                                // Add groups to list
                                for (i = 0; i < groups.length; i++) {
                                    // Create new group
                                    group = new Y.LIMS.Model.GroupModelItem(groups[i]);

                                    // List of buddies
                                    buddies = new Y.LIMS.Model.BuddyModelList();
                                    buddies.add(groups[i].buddies);

                                    // Add buddies to group
                                    group.set('buddies', buddies);

                                    // Add group to group list
                                    instance.add(group);
                                }

                                // Fire success event
                                instance.fire('groupsReadSuccess', {
                                    groupsList: instance
                                });
                            }

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

                            // Clear etag otherwise when we load the data again it
                            // might still be cached
                            instance.set('etag', -1);

                            // Fire error event
                            instance.fire('groupsReadError');

                            if (callback) {
                                callback("group model error", o.response);
                            }
                        }
                    },
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                break;

            case 'create':
            case 'delete':
            case 'update':
                // Do nothing
                break;


            default:
                callback('Invalid action');
        }
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Etag of the groups. This is used for caching. If the requested etag
         * is the same like the one currently cached there is no need to send
         * the data.
         */
        etag: {
            value: -1 // default value
        }
    }
});
