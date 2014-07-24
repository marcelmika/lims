/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationsController = Y.Base.create('conversationsController', Y.Base, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="conversation">',

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-template').get('innerHTML'),


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
                    creator: buddy
                });
                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    controllerId: listID,
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
                container,
                list = this.get('conversationMap'),
            // TODO: Refactor
                listID = this.get('buddyDetails').get('screenName') + "_" + buddy.get('screenName'); // Id is a screenName of the selected buddy
            // Check if the conversation is already there
            if (!list.hasOwnProperty(listID)) {
                // Create new conversation
                conversation = new Y.LIMS.Model.ConversationModel({
                    conversationId: listID,
                    creator: this.get('buddyDetails'),
                    participants: [buddy],
                    title: buddy.get('fullName')
                });

                container = Y.Node.create(this.containerTemplate);
                 // TODO: Init container
                container.set('innerHTML',
                    Y.Lang.sub(this.template, {
                        conversationTitle: conversation.get('title'),
                        triggerTitle: conversation.get('title'),
                        unreadMessages: conversation.get('unreadMessages')
                    })
                );
                // Add panel container to parent container
                this.get('parentContainer').append(container);

                conversation.save();
                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    container: container,
                    controllerId: listID,
                    model: conversation
                });
                // Add to list
                list[listID] = controller;
            } else {
                controller = list[listID];
            }

            controller.presentViewController();
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
