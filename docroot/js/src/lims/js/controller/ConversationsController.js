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
        // Timer
        this._startTimer();
    },

    /**
     * Attach events
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var conversationList = this.get('conversationList');

        // Local
        conversationList.on('conversationsUpdated', this._onConversationsUpdated, this);

        // Buddy selected in group
        Y.on('buddySelected', function (buddy) {
            this._onBuddySelected(buddy);
        }, this);
        // Session expired
        Y.on('userSessionExpired', this._onSessionExpired, this);
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
            unreadMessagesCount,                                // Unread messages count
            settings = this.get('settings'),                    // Settings of logged user
            conversationModel,                                  // Model which will be attached to controller
            conversationList = this.get('conversationList'),    // List of conversations
            notification = this.get('notification'),            // Notification handler
            controller;                                         // Bind controller

        // Create js objects for each node
        conversationNodes.each(function (conversationNode) {
            // Since the conversation was already rendered it contains a conversation ID which we need get
            // from the attribute on conversation node
            conversationId = conversationNode.attr('data-conversationId');
            unreadMessagesCount = conversationNode.attr('data-unreadMessagesCount');

            // Bind controller only if it's not in the map
            if (!map.hasOwnProperty(conversationId)) {

                // Create new conversation model
                conversationModel = new Y.LIMS.Model.ConversationModel({
                    conversationId: conversationId,
                    creator: buddyDetails,
                    unreadMessagesCount: unreadMessagesCount
                });
                // Add conversation model to list
                conversationList.add(conversationModel);

                // Create new single user conversation and add it to the list
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    // Id of controller is the id of conversation. Thanks to this we can
                    // set the currently opened panel.
                    controllerId: conversationId,
                    container: conversationNode,
                    model: conversationModel,
                    buddyDetails: buddyDetails,
                    settings: settings,
                    notification: notification
                });

                // Remove controller from map if it's unloaded from the screen
                controller.on('panelDidUnload', function () {
                    delete map[conversationId];
                });

                // Add to map
                map[conversationId] = controller;

                // Silently notify about new messages
                notification.notify(unreadMessagesCount, true);
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
        var map = this.get('conversationMap'),                  // Map that holds all conversation controllers
            buddyDetails = this.get('buddyDetails'),            // Currently logged user
            conversationId,                                     // Id of the conversation passed to controller
            conversationModel,                                  // Model passed to controller
            conversationContainer,                              // Container node passed to controller
            conversationList = this.get('conversationList'),    // Holds all conversation models
            settings = this.get('settings'),                    // Settings of logged user
            notification = this.get('notification'),            // Notification handler
            controller;                                         // Controller (selected or newly created)

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
            // Add model to list
            conversationList.add(conversationModel);

            // Create new container node
            conversationContainer = Y.Node.create(this.containerTemplate);
            // Set from template
            conversationContainer.set('innerHTML',
                Y.Lang.sub(this.template, {
                    conversationTitle: conversationModel.get('title'),
                    triggerTitle: conversationModel.get('title'),
                    unreadMessages: conversationModel.get('unreadMessagesCount')
                })
            );
            // Add panel container to parent container
            this.get('container').append(conversationContainer);

            // Create new single user conversation controller
            controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                buddyDetails: buddyDetails,
                container: conversationContainer,
                controllerId: conversationId,
                model: conversationModel,
                settings: settings,
                notification: notification
            });

            // Save the model, thanks to that the conversation will be created on server too.
            conversationModel.save();

            // Remove controller from map if it's unloaded from the screen
            controller.on('panelDidUnload', function () {
                delete map[conversationId];
            });

            // Add controller to map
            map[conversationId] = controller;
        }

        // At the end show the controller to the user
        controller.presentViewController();
    },

    /**
     * Called whenever we receive a notification from long pooling that conversations have been updated
     *
     * @private
     */
    _onConversationsUpdated: function () {
        // Vars
        var map = this.get('conversationMap'),                      // Map that holds all conversation controllers
            controller,                                             // Controller from map or newly created
            instance = this,                                        // This
            buddyDetails = this.get('buddyDetails'),                // Currently logged user
            conversationList = this.get('conversationList'),        // Holds all conversation models
            conversationContainer,                                  // Container node passed to controller
            container = this.get('container'),                      // Container of all conversations
            notification = this.get('notification'),                // Notification handler
            settings = this.get('settings'),                        // Settings of logged user
            conversationId;                                         // Id of the conversation passed to controller

        // For each conversation check if new controller should be created if some
        // of the participants send a message to the user. Or if we should only update
        // existing conversation
        conversationList.each(function (conversationModel) {
            // Get the conversation id from model
            conversationId = conversationModel.get('conversationId');

            // Controller exists
            if (map.hasOwnProperty(conversationId)) {
                controller = map[conversationId];
                controller.updateModel(conversationModel);
            }
            // Create new controller from conversation model only if there is
            // a pending message. This check needs to be here because it is possible
            // that the user closed the conversation but in a mean time received
            // the long poll update. This will open the conversation again. Obviously we
            // don't want that.
            else if (conversationModel.get('unreadMessagesCount') !== 0) {
                // Add creator to model
                conversationModel.set('creator', buddyDetails);
                // etag must be send to 0 because we want the controller
                // to refresh the message list. Since we have received
                // model with already set etag we need to set it to 0 here.
                conversationModel.set('etag', 0);
                // Add it to list
                conversationList.add(conversationModel);

                // Create new container node
                conversationContainer = Y.Node.create(instance.containerTemplate);
                // Set from template
                conversationContainer.set('innerHTML',
                    Y.Lang.sub(instance.template, {
                        conversationTitle: conversationModel.get('title'),
                        triggerTitle: conversationModel.get('title'),
                        unreadMessages: conversationModel.get('unreadMessagesCount')
                    })
                );
                // Add panel container to parent container
                container.append(conversationContainer);

                // Create new single user conversation controller
                controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                    buddyDetails: buddyDetails,
                    container: conversationContainer,
                    controllerId: conversationId,
                    model: conversationModel,
                    settings: settings,
                    notification: notification
                });

                // Remove controller from map if it's unloaded from the screen
                controller.on('panelDidUnload', function () {
                    delete map[conversationId];
                });

                // Add controller to map
                map[conversationId] = controller;

                // Load conversation model (e.g. messages, etc.)
                conversationModel.load(function (err) {
                    if (!err) {
                        // Show the controller to the user
                        controller.showViewController();
                        // We have created a controller based on long polling message.
                        // We thus need to notify the user about received messages.
                        notification.notify(conversationModel.get('unreadMessagesCount'));
                    }
                });
            }
        });
    },

    /**
     * Called whenever the user session expires
     * @private
     */
    _onSessionExpired: function () {
        this._stopTimer();
    },

    /**
     * Starts timer which periodically refreshes group list
     *
     * @private
     */
    _startTimer: function () {
        // Vars
        var globals = this.get('globals'),
            conversationList = this.get('conversationList'),
            timerInterval = this.get('timerInterval');

        // Start only if the chat is enabled
        if (globals.isChatEnabled()) {
            // Update all timestamps
            conversationList.load();
            // Start periodical update
            this.set('timer', setInterval(function () {
                // Load model
                conversationList.load();
            }, timerInterval));
        }
    },

    /**
     * Pauses timer which periodically refreshes group list
     *
     * @private
     */
    _stopTimer: function () {
        // Vars
        var timer = this.get('timer');
        // Pause
        clearTimeout(timer);
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Map holds all currently opened conversation controllers
        conversationMap: {
            value: {} // default value
        },

        conversationList: {
            valueFn: function () {
                return new Y.LIMS.Model.ConversationListModel();
            }
        },

        // Currently logged user
        buddyDetails: {
            value: null
        },

        // Main container node
        container: {
            valueFn: function () {
                // Get root
                var rootNode = this.get('globals').getRootNode();
                // Return container
                return rootNode.one('.lims-tabs');
            }
        },

        // Notification
        notification: {
            value: null // to be set
        },

        // List of already rendered conversation nodes
        conversationNodes: {
            valueFn: function () {
                return this.get('container').all('.conversation');
            }
        },

        // Timer used to set async calls to server
        timer: {
            value: null // to be set
        },

        // Length of timer period
        timerInterval: {
            value: 7000 // 7 seconds
        },

        // Global settings
        globals: {
            valueFn: function () {
                return new Y.LIMS.Core.Settings();
            }
        },

        settings: {
            value: null // to be set
        }
    }
});
