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
            // Hide badge
            this._hideBadge();
        },

        /**
         * Maps all events on Y object to private internal functions
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var model = this.get('model');

            // Local events
            model.after('conversationUpdated', this._onConversationUpdated, this);

            // Global events
            Y.on('panelClosed', this._onPanelClosed, this);
            // TODO: Remove
            Y.on('buddySelected', this._onBuddySelected, this); // Whenever the user click on buddy in group
        },

        /**
         * Called whenever the conversation model is updated
         *
         * @private
         */
        _onConversationUpdated: function () {
            var unreadMessages = this.get('model').get('unreadMessages'),
                badge = this.get('badge');
            if (unreadMessages === 0) {
                badge.hide();
            } else {
                badge.show();
            }

            badge.set('innerHTML', unreadMessages);
        },

        /**
         * Called whenever the buddy from group is selected
         *
         * @param buddy
         * @private
         * // TODO: This will be in conversation controller
         */
        _onBuddySelected: function (buddy) {
            var model = this.get('model'),
                participant = model.get('participants')[0];

            // TODO: This is just temporal fix
            if (participant === undefined) {
                return;
            }

            if (participant.get('screenName') === buddy.get('screenName')) {
                this.getPanel().show();
            }
        },

        _onPanelClosed: function () {
            this.fire('conversationClosed');
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

            // Y.LIMS.Model.ConversationModel
            model: {
                value: null // default value
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
            }
        }
    });
