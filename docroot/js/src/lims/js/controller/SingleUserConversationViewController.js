/**
 * Single User Conversation View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SingleUserConversationViewController = Y.Base.create('singleUserConversationController', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    // <li class="conversation" id="conversation_{conversationId}" panelId="{panelId}">
    containerTemplate: '<li class="conversation">',

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-template').get('innerHTML'),

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controllers
    initializer: function () {
        var container = this.get('container'),
            model = new Y.LIMS.Model.ConversationListModel();

        model.after('conversationUpdated', this._onConversationUpdated, this);

        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                conversationTitle: 'John Doe',
                triggerTitle: 'John Doe',
                unreadMessages: 5
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

        // TODO : Debug
        this.get('panel').show();

        // Events
        this._attachEvents();
    },


    /**
     * Maps all events on Y object to private internal functions
     *
     * @private
     */
    _attachEvents: function () {

        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
    },

    _onConversationUpdated: function () {

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

        listView: {
            value: null
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        }
    }
});
