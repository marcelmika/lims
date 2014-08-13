/**
 * Buddy Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single Buddy.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelItem = Y.Base.create('buddyModelItem', Y.Model, [], {

    /**
     * Updates buddy presence
     *
     * @param presence
     * @param callback
     * @private
     */
    updatePresence: function (presence, callback) {
        // Create settings that contains request url
        var settings = new Y.LIMS.Core.Settings(),
            content;

        // Update locally
        this.set('presence', presence);

        // Deserialize
        content = Y.JSON.stringify(this.toJSON());

        // Do the request
        Y.io(settings.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateBuddyPresence",
                content: content
            },
            on: {
                success: function (id, o) {
                    if (callback) {
                        callback(null, o.response);
                    }
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }
                    if (callback) {
                        callback("Cannot update buddy presence", o.response);
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

        buddyId: {
            value: null // default value
        },

        screenName: {
            value: "" // default value
        },

        fullName: {
            value: "" // default value
        },

        presence: {
            value: null // default value
        }
    }
});
