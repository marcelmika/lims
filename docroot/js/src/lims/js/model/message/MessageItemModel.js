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
//                        success: function (id, o) {
//
//                        },
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

        messageHash: {
            // TODO: Generate
            value: "asfasdfasdf" // default value
        },

        createdAt: {
            value: null // default value
        }
    }
});
