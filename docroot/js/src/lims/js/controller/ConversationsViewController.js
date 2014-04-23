/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationsController = Y.Base.create('conversationsController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controllers
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Attach events
     * @private
     */
    _attachEvents: function () {
        // Buddy selected in group
        Y.on('buddySelected', function (buddy) {
            this._onBuddySelected(buddy);
        }, this);
    },

    /**
     * Called whenever the buddy is selected
     *
     * @param buddy
     * @private
     */
    _onBuddySelected: function (buddy) {
        if (buddy !== undefined) {
            var controller, model,
                list = this.get('conversationMap'),
                listID = buddy.get('screenName'); // Id is a screenName of the selected buddy
            // Check if the conversation is already there
            if (!list.hasOwnProperty(listID)) {
                model = new Y.LIMS.Model.ConversationListModel({
                    participant: buddy,
                    unreadMessages: 0
                });
                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({model:model});
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
        }
    }
});
