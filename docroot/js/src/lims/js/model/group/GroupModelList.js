/**
 * Group Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * GroupModelItem instances, and to provide some convenience methods for getting
 * information about the Group items in the list.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModelList = Y.Base.create('groupModelList', Y.ModelList, [], {

    // This tells the list that it will hold instances of the GroupModelItem class.
    model: Y.LIMS.Model.GroupModelItem,

    // Returns an array of all models in this list with the `done` attribute
    // set to `true`.
//    done: function () {
//        return this.filter(function (model) {
//            return model.get('type') === 'apple';
//        });
//    },

    // Returns an array of all models in this list with the `done` attribute
    // set to `false`.
//    remaining: function () {
//        return this.filter(function (model) {
//            return !model.get('done');
//        });
//    }

    // Custom sync layer.
    sync: function (action, options, callback) {
        var data, url, etag = this.get('etag'), instance = this;

//        instance.callback = callback;

        switch (action) {
            case 'create':
                Y.log("create");
                data = this.toJSON();
                Y.log(data);
                return;
//                // Use the current timestamp as an id just to simplify the example. In a
//                // real sync layer, you'd want to generate an id that's more likely to
//                // be globally unique.
//                data.id = Y.Lang.now();
//
//                // Store the new record in localStorage, then call the callback.
//                localStorage.setItem(data.id, Y.JSON.stringify(data));
//                callback(null, data);
//                return;

            case 'read':
                // TODO: Move away
                url = Y.one('#chatPortletURL').get('value');

                Y.io(url, {
                    method: "GET",
                    data: {
                        query: "GetGroupList",
                        etag: etag
                    },
                    on: {
                        success: function (id, o) {
                            // TODO: Refactor
                            var i, groupCollection, groups, group, buddies;
                            // Parse groups
                            groupCollection = Y.JSON.parse(o.response);
                            groups = groupCollection.groups;

                            if (etag.toString() !== groupCollection.etag.toString()) {
                                // Empty the list
                                instance.reset();

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

                                if (etag === 0 ) {
                                    instance.fire("groupsLoaded");
                                }
                            }
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
                            callback("group model error", o.response);
                        }
                    },
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                return;

            // Look for an item in localStorage with this model's id.
//                data = localStorage.getItem(this.get('id'));
//
//                if (data) {
//                    callback(null, data);
//                } else {
//                    callback('Model not found.');
//                }
//
//                return;

            case 'update':
                return;
//
//                data = this.toJSON();
//
//                // Update the record in localStorage, then call the callback.
//                localStorage.setItem(this.get('id'), Y.JSON.stringify(data));
//                callback(null, data);
//                return;

            case 'delete':
                return;
//                localStorage.removeItem(this.get('id'));
//                callback();
//                return;

            default:
                callback('Invalid action');
        }
    }

}, {

    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        etag: {
            value: 0 // default value
        }
    }

});
