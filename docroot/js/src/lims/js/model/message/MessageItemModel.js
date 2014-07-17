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
        var content, parameters, url = Y.one('#chatPortletURL').get('value');

        switch (action) {

            // CREATE
            case 'create':
                console.log("Message Create");

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
                        success: function (id, o) {
                            console.log('success');
                            console.log(o.response);
                        },
                        failure: function (x, o) {
                            console.log('failure');
                            console.log(x);
                            console.log(o.status);
                        }
                    }
//                    headers: {
//                        'Content-Type': 'application/json'
//                    }
                });
                break;

            // READ
            case 'read':
                console.log("Message Read");
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
            // TODO: remove default
            value: {
                screenName: "marcel.mika"
            } // default value
        },

        body: {
            value: "" // default value
        },

        messageHash: {
            // TODO: Generate
            value: "asfasdfasdf" // default value
        },

        timestamp: {
            value: "" // default value
        }
    }
});
