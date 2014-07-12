/**
 * Group Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single group item.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationModel = Y.Base.create('conversationModel', Y.Model, [], {

    addMessage: function(message) {
        var messageList = this.get('messageList');
        messageList.add(message);

        // This will send the message on server
        message.save();

        this.fire('messageAdded', message);
    },

    // Custom sync layer.
    sync: function (action, options, callback) {
        var data, url = Y.one('#chatPortletURL').get('value');

        switch (action) {
            case 'create':
                data = this.toJSON();
                data = Y.JSON.stringify(data);
                Y.io(url, {
                    method: "POST",
                    data: {
                        query: "CreateSingleUserConversation",
                        data: data
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

        participants: {
            value: "" // default value
        },

        unreadMessages: {
            value: 10 // default value
        },

        messageList: {
            valueFn: function() {
                return new Y.LIMS.Model.MessageListModel();
            }
        }
    }
});
