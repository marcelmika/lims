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

    // Custom sync layer.
    sync: function (action, options, callback) {
        var settings = new Y.LIMS.Core.Settings(),
            parameters,
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
                Y.io(settings.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetGroupList",
                        parameters: parameters
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

                                if (etag === 0) {
                                    instance.fire("groupsLoaded", {
                                        groupsList: instance
                                    });
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

    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        etag: {
            value: 0 // default value
        }
    }
});
