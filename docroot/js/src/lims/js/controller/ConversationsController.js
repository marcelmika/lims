/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        // Vars
        var properties = this.get('properties');

        // Bind to already rendered conversations
        this._bindConversations();
        // Attach events
        this._attachEvents();

        // Start polling only if the chat is enabled
        if (properties.isChatEnabled()) {
            this._startPolling();
        }

        // Fire an event that the initialization has been finished
        Y.fire('initializationFinished');
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

        // Chat enabled/disabled
        Y.on('chatEnabled', this._onChatEnabled, this);
        Y.on('chatDisabled', this._onChatDisabled, this);
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
            properties = this.get('properties'),                // Portlet properties
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
                    unreadMessagesCount: unreadMessagesCount,
                    serverTimeOffset: properties.getServerTimeOffset()
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
                    properties: properties,
                    notification: notification
                });

                // Remove controller from map if it's unloaded from the screen
                controller.on('panelDidUnload', function (event) {
                    delete map[event.controllerId];
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
        var map = this.get('conversationMap'),                  // Map that holds all conversation controllers
            buddyDetails = this.get('buddyDetails'),            // Currently logged user
            conversationId,                                     // Id of the conversation passed to controller
            conversationModel,                                  // Model passed to controller
            conversationContainer,                              // Container node passed to controller
            conversationList = this.get('conversationList'),    // Holds all conversation models
            settings = this.get('settings'),                    // Settings of logged user
            properties = this.get('properties'),                // Portlet properties
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
                title: buddy.get('fullName'),
                serverTimeOffset: properties.getServerTimeOffset()
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
                properties: properties,
                notification: notification
            });

            // Save the model, thanks to that the conversation will be created on server too.
            conversationModel.save();

            // Remove controller from map if it's unloaded from the screen
            controller.on('panelDidUnload', function (event) {
                delete map[event.controllerId];
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
            properties = this.get('properties'),                    // Portlet properties
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
                    properties: properties,
                    notification: notification
                });

                // Remove controller from map if it's unloaded from the screen
                controller.on('panelDidUnload', function (event) {
                    delete map[event.controllerId];
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
                        notification.notify(conversationModel.get('lastMessage'));
                    }
                });
            }
        });
    },

    /**
     * Called whenever the user session expires
     *
     * @private
     */
    _onSessionExpired: function () {
        // Stop poller
        this._stopPolling();
    },

    /**
     * Called whenever the user enables the chat
     *
     * @private
     */
    _onChatEnabled: function () {
        // Re-enable polling
        this._startPolling();
    },

    /**
     * Called whenever the user disables the chat
     *
     * @private
     */
    _onChatDisabled: function () {
        // Disable the poller
        this._stopPolling();
    },

    /**
     * Starts poller which periodically refreshes the conversation list
     *
     * @private
     */
    _startPolling: function () {
        // Vars
        var conversationList = this.get('conversationList'),
            poller = this.get('poller');

        // Register model to the poller
        poller.register('conversationController:model', new Y.LIMS.Core.PollerEntry({
            model: conversationList,        // Model that will be periodically refreshed
            interval: 7000,                 // 7 seconds period
            connectionMonitor: true         // Fires connection success/error event
        }));
    },

    /**
     * Stops poller which periodically refreshes the conversation list
     *
     * @private
     */
    _stopPolling: function () {
        // Vars
        var poller = this.get('poller');
        // Pause
        poller.unregister('conversationController:model');
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Main container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Map that holds all currently opened conversation controllers
         *
         * {object}
         */
        conversationMap: {
            value: {} // default value
        },

        /**
         * An instance of currently logged user details
         *
         * {Y.LIMS.Model.BuddyModelItem}
         */
        buddyDetails: {
            value: null
        },

        /**
         * Properties object that holds the global portlet properties
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        },

        /**
         * Settings of the currently logged user
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        settings: {
            value: null // to be set
        },

        /**
         * Conversation list model
         *
         * {Y.LIMS.Model.ConversationListModel}
         */
        conversationList: {
            valueFn: function () {
                return new Y.LIMS.Model.ConversationListModel();
            }
        },

        /**
         * Notification object responsible for the incoming message notification
         *
         * {Y.LIMS.Core.Notification}
         */
        notification: {
            value: null // to be set
        },

        /**
         * List of already rendered conversation nodes
         *
         * {array}
         */
        conversationNodes: {
            valueFn: function () {
                return this.get('container').all('.conversation');
            }
        },

        /**
         * An instance of poller that periodically refreshes models that are subscribed
         *
         * {Y.LIMS.Core.Poller}
         */
        poller: {
            value: null // to be set
        }
    }
});
