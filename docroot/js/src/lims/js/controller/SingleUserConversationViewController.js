/**
 * Single User Conversation View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SingleUserConversationViewController = Y.Base.create('singleUserConversationController', Y.View, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controllers
    initializer: function () {
        var view = new Y.LIMS.View.ConversationView(),
            container = this.get('container');

        container.append(view.render().get('container'));

        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: view.get('container'),
            panelId: "conversation"
        }));

        this.get('panel').show();

        // Events
        this._attachEvents();
    },


    /**
     * Maps all events on Y object to private internal functions
     *
     * @private
     */
    _attachEvents: function() {

        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
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

        // Main container
        container: {
            valueFn: function () {
                return Y.one('#chatBar .chat-tabs');
            }
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        }
    }
});
