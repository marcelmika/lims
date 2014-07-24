/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationsController = Y.Base.create('conversationsController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controllers
    initializer: function () {
        // Bind to already rendered conversations
        this._bindConversations();
        // Attach events
        this._attachEvents();
    },

    /**
     * Attach events
     *
     * @private
     */
    _attachEvents: function () {
        // Buddy selected in group
        Y.on('buddySelected', function (buddy) {
            this._onBuddySelected(buddy);
        }, this);
    },

    /**
     * There may exist opened conversations which are rendered. This function will bind to such
     * conversations and create appropriate javascript objects based on that.
     *
     * @private
     * // TODO: Refactor
     */
    _bindConversations: function () {
        // Find all already rendered conversations
        var conversationNodes = this.get('parentContainer').all('.conversation'),
            list = this.get('conversationMap'),
            buddy = this.get('buddyDetails'),
            listID,
            conversation,
            controller;
        // Create js objects for each node
        conversationNodes.each(function (conversationNode) {
            listID = conversationNode.attr('data-conversationId');
            // Check if the conversation is already there
            if (!list.hasOwnProperty(listID)) {
                // Create new conversation
                conversation = new Y.LIMS.Model.ConversationModel({
                    conversationId: listID,
                    title: "foo",
                    creator: buddy
                });
                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    container: conversationNode,
                    model: conversation
                });
                // Add to list
                list[listID] = controller;
            } else {
                controller = list[listID];
            }
        });
    },

    /**
     * Called whenever the buddy is selected
     *
     * @param buddy
     * @private
     * // TODO: Refactor
     */
    _onBuddySelected: function (buddy) {
        if (buddy !== undefined) {
            var controller,
                conversation,
                list = this.get('conversationMap'),
            // TODO: Refactor
                listID = this.get('buddyDetails').get('screenName') + "_" + buddy.get('screenName'); // Id is a screenName of the selected buddy
            // Check if the conversation is already there
            if (!list.hasOwnProperty(listID)) {
                // Create new conversation
                conversation = new Y.LIMS.Model.ConversationModel({
                    conversationId: listID,
                    creator: this.get('buddyDetails'),
                    participants: [buddy]
                });
                conversation.save();
                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    model: conversation
                });
                // Add to list
                list[listID] = controller;
            } else {
                controller = list[listID];
            }

            controller.show();
        }
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        conversationMap: {
            value: {} // default value
        },

        // Currently logged user
        buddyDetails: {
            value: null
        },

        // Main container
        parentContainer: {
            valueFn: function () {
                return Y.one('#chatBar .chat-tabs');
            }
        }
    }
});
