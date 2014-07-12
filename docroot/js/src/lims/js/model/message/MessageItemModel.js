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
        var content, url = Y.one('#chatPortletURL').get('value');

        switch (action) {
            case 'create':
                console.log("Message Create");
                content = Y.JSON.stringify(this.toJSON());
                Y.io(url, {
                    method: "POST",
                    data: {
                        query: "CreateMessage",
                        parameters: {
                           conversationId : 1
                        },
                        content: content
                    },
                    on: {
//                        success: function (id, o) {
//                            console.log('success');
//                            console.log(id);
//                            console.log(o.response);
//                        },
//                        failure: function (x, o) {
//                            console.log(x);
//                            console.log(o);
//                        }
                    }
//                    headers: {
//                        'Content-Type': 'application/json'
//                    }
                });
                break;

            case 'read':
                console.log("Message Read");
//                console.log("read item");
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
                console.log("Message Update");
//                console.log("update item");
                return;
//
//                data = this.toJSON();
//
//                // Update the record in localStorage, then call the callback.
//                localStorage.setItem(this.get('id'), Y.JSON.stringify(data));
//                callback(null, data);
//                return;

            case 'delete':
                console.log("Message Delete");
//                console.log("delete item");
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
