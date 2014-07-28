/**
 * Conversation Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * ConversationModel instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationListModel = Y.Base.create('conversationListModel', Y.ModelList, [], {

    // This tells the list that it will hold instances of the ConversationModel class.
    model: Y.LIMS.Model.ConversationModel,


    /**
     * Returns true if the list contains conversation
     *
     * @param conversationId
     * @returns {boolean}
     */
    containsConversation: function (conversationId) {
        return this.getByConversationId(conversationId) !== null;
    },

    /**
     * Returns a conversation based on it's id
     *
     * @param conversationId
     * @returns {ConversationModel|null}
     */
    getByConversationId: function (conversationId) {

        // Vars
        var conversations;

        // Find conversation
        conversations = Y.Array.filter(this.toArray(), function (model) {
            return model.get('conversationId') === conversationId;
        });

        // Even though the conversation should be unique the filter always returns an array
        if (conversations.length > 0) {
            return conversations[0];
        }

        // Nothing was found
        return null;
    },

    /**
     * Custom sync layer
     *
     * @param action
     * @param options
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var
//            content,            // Content which will be sent as body in request
//            parameters,         // Request parameters
            instance = this,    // Save the instance so we can call its methods in diff context
            response,           // Response from the server
            settings = new Y.LIMS.Core.Settings();

        switch (action) {

            // This is called whenever the conversation is created i.e whenever
            // the user clicks on any of the buddies in the group
            case 'create':
//                // Simply take the conversation object, serialize it to json
//                // and send.
//                content = Y.JSON.stringify(this.toJSON());
//
//                // Send the request
//                Y.io(settings.getServerRequestUrl(), {
//                    method: "POST",
//                    data: {
//                        query: "CreateSingleUserConversation",
//                        content: content
//                    },
//                    on: {
//                        success: function (id, o) {
//                            // Deserialize response
//                            response = Y.JSON.parse(o.response);
//                            // Call success
//                            callback(null, response);
//                        },
//                        failure: function (x, o) {
//                            // Call failure
//                            callback("Cannot create new conversation", o);
//                        }
//                    }
//                });
                return;

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of messages related to the conversation.
            case 'read':

                console.log('reading conversations');

                // Send the request
                Y.io(settings.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadOpenedConversations"
                    },
                    on: {
                        success: function (id, o) {
                            // Deserialize response
                            response = Y.JSON.parse(o.response);
                            // Update conversation list
                            instance.updateConversationList(response);
                            // Call success
                            callback(null, response);
                        },
                        failure: function (x, o) {
                            console.log('failure');
                            // Call failure
                            callback("Cannot create new conversation", o);
                        }
                    }
                });

                return;

            default:
                callback('Invalid action');
        }
    },

    updateConversationList: function(response) {

        // Vars
        var index, conversation, conversationModels = [];

        // Create model instance from response
        for (index = 0; index < response.length; index++) {
            conversation = response[index];
            conversationModels.push(new Y.LIMS.Model.ConversationModel(conversation));
        }
        // Repopulate the list
        this.reset(conversationModels);

        this.fire('conversationsUpdated', this);
    }

}, {});