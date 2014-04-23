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
            model = new Y.LIMS.Model.ConversationListModel(),
            participant = this.get('participant');

        model.after('conversationUpdated', this._onConversationUpdated, this);

        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                conversationTitle: participant.get('fullName'),
                triggerTitle: participant.get('fullName'),
                unreadMessages: 0
            })
        );

        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: this.get('container'),
            panelId: "conversation"
        }));

        this.get('parentContainer').append(container);

        this.set('listView', new Y.LIMS.View.ConversationListView({
            container: this.get('container').one('.panel-window'),
            model: model
        }));

        // Events
        this._attachEvents();
    },

    /**
     * Shows the conversation panel
     */
    show: function() {
        this.get('panel').show();
    },

    /**
     * Maps all events on Y object to private internal functions
     *
     * @private
     */
    _attachEvents: function () {
        // Panel events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
        Y.on('panelClosed', this._onPanelClosed, this);
        // Buddy events
        Y.on('buddySelected', this._onBuddySelected, this); // Whenever the user click on buddy in group
    },

    _onConversationUpdated: function () {

    },

    _onBuddySelected: function(buddy) {
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

    /**
     * Panel hidden event handler. Closes own panel if some other panel was shown.
     * Thanks to that only one panel can be open at one time.
     *
     * @param panel
     * @private
     */
    _onPanelHidden: function () {
        // Todo: send active panel ajax
    },

    _onPanelClosed: function() {
        this.fire('conversationClosed');
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

        // Buddy screenName of the receiver
        participant: {
            value: "" // default value
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
