/**
 * Single User Conversation View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SingleUserConversationViewController = Y.Base.create('singleUserConversationController', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="conversation">',

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-template').get('innerHTML'),

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controllers
    initializer: function () {
        var container = this.get('container'),
            model = this.get('model'),
            participant = model.get('participants')[0];
        // Create content of the container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                conversationTitle: participant.get('fullName'),
                triggerTitle: participant.get('fullName'),
                unreadMessages: model.get('unreadMessages')
            })
        );

        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: container,
            panelId: "conversation"
        }));

        // Add panel container to parent container
        this.get('parentContainer').append(container);

        // Set list view
        this.set('listView', new Y.LIMS.View.ConversationListView({
            container: container.one('.panel-window'),
            model: model
        }));

        // Set badge
        this.set('badge', container.one('.unread'));
        // Hide badge at the beginning
        this.get('badge').hide();

        // Events
        this._attachEvents();
    },

    /**
     * Shows the conversation panel
     */
    show: function () {
        this.get('panel').show();
    },

    /**
     * Maps all events on Y object to private internal functions
     *
     * @private
     */
    _attachEvents: function () {
        var model = this.get('model');
        // Model event
        model.after('conversationUpdated', this._onConversationUpdated, this);
        // Panel events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
        Y.on('panelClosed', this._onPanelClosed, this);
        Y.on('chatEnabled', this._onChatEnabled, this);
        Y.on('chatDisabled', this._onChatDisabled, this);
        // Buddy events
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
     */
    _onBuddySelected: function (buddy) {
        var participant = this.get('participant');
        if (participant.get('screenName') === buddy.get('screenName')) {
            this.get('panel').show();
        }
    },

    /**
     * Panel shown event handler. Closes own panel if some other panel was shown.
     * Thanks to that only one panel can be open at one time.
     *
     * @param panel
     * @private
     */
    _onPanelShown: function (panel) {
        // Don't close own panel
        if (panel !== this.get('panel')) {
            this.get('panel').hide();
        }
    },

    _onPanelClosed: function () {
        this.fire('conversationClosed');
    },

    /**
     * Called whenever the chat is enabled
     *
     * @private
     */
    _onChatEnabled: function() {
        this.get('container').show();
    },

    /**
     * Called whenever the chat is disabled
     *
     * @private
     */
    _onChatDisabled: function() {
        this.get('container').hide();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Template for a single conversation
        container: {
            valueFn: function () {
                if (this.get('container') === undefined) {
                    return Y.Node.create(this.containerTemplate);
                }
                return this.get('container');
            }
        },

        // Main container
        parentContainer: {
            valueFn: function () {
                return Y.one('#chatBar .chat-tabs');
            }
        },

        badge: {
            value: null // default value
        },

        model: {
            value: null // default value
        },

        listView: {
            value: null
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        }
    }
});
