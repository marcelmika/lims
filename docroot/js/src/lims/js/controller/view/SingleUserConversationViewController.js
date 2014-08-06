/**
 * Single User Conversation View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SingleUserConversationViewController = Y.Base.create('singleUserConversationController',
    Y.LIMS.Core.ViewController, [], {


        /**
         *  The initializer runs when a Single User Conversation View Controller instance is created.
         */
        initializer: function () {
            // This needs to be called in each view controller
            this.setup(this.get('container'), this.get('controllerId'));
        },

        /**
         * Panel Did Load is called when the panel is attached to the controller
         */
        onPanelDidLoad: function () {
            // Events
            this._attachEvents();
        },

        /**
         * Panel Did Appear is called when the panel did appear on the screen
         */
        onPanelDidAppear: function () {
            // Vars
            var model = this.get('model'),
                listView = this.get('listView'),
                notification = this.get('notification');

            // Messages are read so suppress the count
            notification.suppress(model.get('unreadMessagesCount'));
            // Reset counter of unread messages
            model.resetUnreadMessagesCounter();
            // Always scroll to the last message when user opens the window
            listView.scrollToBottom();
            // Hide badge since it's not needed anymore
            this._hideBadge();
            // Start timer that periodically updates timestamps of messages
            this._startTimer();
        },

        /**
         * Panel Did Disappear is called when the panel disappeared from the screen
         */
        onPanelDidDisappear: function () {
            this._pauseTimer();
        },

        /**
         * Panel Did Unload is called whenever the panel completely disappears from the screen. This happens when
         * the user closes the whole panel.
         */
        onPanelDidUnload: function () {
            // Vars
            var model = this.get('model');

            // Close conversation
            model.closeConversation();
        },

        /**
         * Takes new model and updates local model based on it's contents
         *
         * @param model
         */
        updateModel: function (model) {
            // Vars
            var conversationModel = this.get('model'),      // Current conversation model
                currentUnreadMessagesCount,                 // Current unread message count
                updatedUnreadMessageCount,                  // Unread message count of updated model
                newMessagesCount,                           // Number of newly received messages
                notification = this.get('notification'),    // Notification handler
                instance = this;                            // This

            // There is no need to update conversation which hasn't been changed
            if (conversationModel.get('etag') !== model.get('etag')) {
                // Remember the message count
                currentUnreadMessagesCount = conversationModel.get('unreadMessagesCount');
                // Update model
                conversationModel.load(function (err, conversation) {
                    if (!err) {
                        // New message count
                        updatedUnreadMessageCount = conversation.get('unreadMessagesCount');
                        // If the unread message count has been increased notify user
                        if (updatedUnreadMessageCount > currentUnreadMessagesCount) {
                            // Actual count of newly received messages
                            newMessagesCount = updatedUnreadMessageCount - currentUnreadMessagesCount;
                            // Notify about new message
                            notification.notify(newMessagesCount);
                        }
                        // Callback
                        instance._onConversationUpdated();
                    }
                });
            }
        },

        /**
         * Maps all events on Y object to private internal functions
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var listView = this.get('listView');

            // Local events
            listView.on('messageSubmitted', this._onMessageSubmitted, this);
        },

        /**
         * Starts timer which periodically refreshes group list
         *
         * @private
         */
        _startTimer: function () {
            // Vars
            var globals = this.get('globals'),
                listView = this.get('listView'),
                timerInterval = this.get('timerInterval');

            // Start only if the chat is enabled
            if (globals.isChatEnabled()) {
                // Update all timestamps
                listView.updateTimestamps();
                // Start periodical update
                this.set('timer', setInterval(function () {
                    // Load model
                    listView.updateTimestamps();
                }, timerInterval));
            }
        },

        /**
         * Pauses timer which periodically refreshes group list
         *
         * @private
         */
        _pauseTimer: function () {
            // Vars
            var timer = this.get('timer');
            // Pause
            clearTimeout(timer);
        },

        /**
         * Called whenever the conversation model is updated
         *
         * @private
         */
        _onConversationUpdated: function () {
            // Vars
            var unreadMessagesCount = this.get('model').get('unreadMessagesCount');

            // No unread messages
            if (unreadMessagesCount === 0) {
                this._hideBadge();
            } else {
                // Show badge
                this._showBadge();
            }

            // Update badge count
            this._updateBadge(unreadMessagesCount);
        },

        /**
         * Called when user submits message from text area
         *
         * @param event
         * @private
         */
        _onMessageSubmitted: function (event) {
            // Vars
            var model = this.get('model'),                  // Conversation model
                message = event.message,                    // Received message
                buddyDetails = this.get('buddyDetails');    // Currently logged user

            // Add new message to the conversation
            model.addMessage(new Y.LIMS.Model.MessageItemModel({
                from: buddyDetails,
                body: message,
                createdAt: new Date().getTime()
            }));
        },

        /**
         * Updates badge value
         *
         * @param value
         * @private
         */
        _updateBadge: function (value) {
            // Vars
            var badge = this.get('badge');

            // Update value
            if (badge) {
                badge.set('innerHTML', value);
            }
        },

        /**
         * Hides badge
         *
         * @private
         */
        _hideBadge: function () {
            // Vars
            var badge = this.get('badge');

            // Hide badge
            if (badge) {
                badge.hide();
            }
        },

        /**
         * Shows badge
         *
         * @private
         */
        _showBadge: function () {
            // Vars
            var badge = this.get('badge');

            // Show badge
            if (badge) {
                badge.show();
            }
        }

    }, {

        // Specify attributes and static properties for your View here.
        ATTRS: {

            // Id of the controller
            controllerId: {
                value: null // To be set
            },

            // Container Node
            container: {
                value: null // To be set
            },

            // Model attached to controller
            model: {
                value: null // Y.LIMS.Model.ConversationModel
            },

            // Badge Node
            badge: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container');
                    // Find badge
                    return container.one('.unread');
                }
            },

            // List view that holds all messages
            listView: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container'),
                        model = this.get('model');
                    // Create new view
                    return new Y.LIMS.View.ConversationListView({
                        container: container.one('.panel-window'),
                        model: model
                    });
                }
            },

            notification: {
                value: null // to be set
            },

            // Currently logged user
            buddyDetails: {
                value: null
            },

            // Timer used to set async calls to server
            timer: {
                value: null // to be set
            },

            // Length of timer period
            timerInterval: {
                value: 60000 // one minute
            },

            // Global values
            globals: {
                valueFn: function () {
                    return new Y.LIMS.Core.Settings();
                }
            },

            // Holds user related settings
            settings: {
                value: null // to be set
            }
        }
    });
