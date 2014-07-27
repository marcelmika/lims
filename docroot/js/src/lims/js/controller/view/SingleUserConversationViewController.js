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
            var model = this.get('model');

            // Reset counter of unread messages
            model.resetUnreadMessagesCounter();
            // Hide badge
            this._hideBadge();
            // Start timer
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
         * Maps all events on Y object to private internal functions
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var model = this.get('model'),
                listView = this.get('listView');

            // Local events
            model.after('conversationUpdated', this._onConversationUpdated, this);
            listView.on('messageSubmitted', this._onMessageSubmitted, this);
        },

        /**
         * Starts timer which periodically refreshes group list
         *
         * @private
         */
        _startTimer: function () {
            // Vars
            var settings = this.get('settings'),
                listView = this.get('listView'),
                timerInterval = this.get('timerInterval');

            // Start only if the chat is enabled
            if (settings.isChatEnabled()) {
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
            var unreadMessages = this.get('model').get('unreadMessages'),
                badge = this.get('badge');

            // Hide badge if needed
            if (unreadMessages === 0) {
                badge.hide();
            } else {
                badge.show();
            }

            badge.set('innerHTML', unreadMessages);
        },

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
         * Hides badge
         *
         * @private
         */
        _hideBadge: function () {
            // Vars
            var badge = this.get('badge');

            // Hide badge
            if (badge !== null) {
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
            if (badge !== null) {
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
                value: 10000 // 10 seconds
            },

            // Global settings
            settings: {
                valueFn: function () {
                    return new Y.LIMS.Core.Settings();
                }
            }
        }
    });
