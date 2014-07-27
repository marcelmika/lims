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
     * This is quite important method. Each conversation is represented by an unique string ID.
     * Such ID is composed by participants screenNames separated by "_" sign. First, we need to sort
     * both participants names alphabetically and then separate them with "_". If we don't do that we
     * will have two conversations e.g. A_B and B_A, however both conversations are between the same people.
     * So if we first sort by the screenName we will always have A_B id for conversation even though B created
     * the conversation.
     *
     * @param firstScreenName
     * @param secondScreenName
     * @returns {string} Conversation ID
     * @private
     */
    _generateConversationId: function (firstScreenName, secondScreenName) {
        // Vars
        var screenNames = [firstScreenName, secondScreenName];

        // Sort screen names alphabetically
        screenNames.sort();

        // Return new conversation Id
        return screenNames[0] + "_" + screenNames[1];
    },

    /**
     * There may exist opened conversations which are already rendered in jsp and not via javascript.
     * This function will bind to such conversations and create appropriate javascript objects.
     *
     * @private
     */
    _bindConversations: function () {
        // Find all already rendered conversations
        var conversationNodes = this.get('conversationNodes'),  // List of already rendered conversation nodes
            map = this.get('conversationMap'),                  // Map that holds all conversation controllers
            buddyDetails = this.get('buddyDetails'),            // Currently logged user
            conversationId,                                     // Id of the conversation
            conversationModel,                                  // Model which will be attached to controller
            controller;                                         // Bind controller

        // Create js objects for each node
        conversationNodes.each(function (conversationNode) {
            // Since the conversation was already rendered it contains a conversation ID which we need get
            // from the attribute on conversation node
            conversationId = conversationNode.attr('data-conversationId');

            // Bind controller only if it's not in the map
            if (!map.hasOwnProperty(conversationId)) {
                // Create new conversation model
                conversationModel = new Y.LIMS.Model.ConversationModel({
                    conversationId: conversationId,
                    creator: buddyDetails
                });
                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    // Id of controller is the id of conversation. Thanks to this we can
                    // set the currently opened panel.
                    controllerId: conversationId,
                    container: conversationNode,
                    model: conversationModel,
                    buddyDetails: buddyDetails
                });
                // Add to map
                map[conversationId] = controller;
            }
        });
    },

    /**
     * Called whenever the buddy is selected from conversations.
     * If a conversation with the particular buddy already exists we
     * just open the conversation for the user. If not, we first create
     * new conversation and then present it to the user.
     *
     * @param buddy
     * @private
     */
    _onBuddySelected: function (buddy) {

        // Vars
        var map = this.get('conversationMap'),              // Map that holds all conversation controllers
            buddyDetails = this.get('buddyDetails'),        // Currently logged user
            conversationId,                                 // Id of the conversation passed to controller
            conversationModel,                              // Model passed to controller
            conversationContainer,                          // Container node passed to controller
            controller;                                     // Controller (selected or newly created)

        // Generate conversation id
        conversationId = this._generateConversationId(buddyDetails.get('screenName'), buddy.get('screenName'));

        // Such conversation is already in the map
        if (map.hasOwnProperty(conversationId)) {
            // Find it, later on we will present it to the user
            controller = map[conversationId];
        }
        // No such conversation
        else {
            // Create new model
            conversationModel = new Y.LIMS.Model.ConversationModel({
                conversationId: conversationId,
                creator: buddyDetails,
                participants: [buddy],
                title: buddy.get('fullName')
            });

            // Create new container node
            conversationContainer = Y.Node.create(this.containerTemplate);
            // Set from template
            conversationContainer.set('innerHTML',
                Y.Lang.sub(this.template, {
                    conversationTitle: conversationModel.get('title'),
                    triggerTitle: conversationModel.get('title'),
                    unreadMessages: conversationModel.get('unreadMessages')
                })
            );
            // Add panel container to parent container
            this.get('container').append(conversationContainer);

            // Create new single user conversation controller
            controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                buddyDetails: buddyDetails,
                container: conversationContainer,
                controllerId: conversationId,
                model: conversationModel
            });

            // Save the model, thanks to that the conversation will be created on server too.
            conversationModel.save();

            // Add to list
            map[conversationId] = controller;
        }

        // At the end show the controller to the user
        controller.presentViewController();
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Map holds all currently opened conversation controllers
        conversationMap: {
            value: {} // default value
        },

        // Currently logged user
        buddyDetails: {
            value: null
        },

        // Main container
        container: {
            valueFn: function () {
                return Y.one('#chatBar .chat-tabs');
            }
        },

        // List of already rendered conversation nodes
        conversationNodes: {
            valueFn: function () {
                return this.get('container').all('.conversation');
            }
        }
    }
});
