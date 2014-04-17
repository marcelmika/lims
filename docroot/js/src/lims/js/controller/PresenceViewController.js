/**
 * Presence View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.PresenceViewController = Y.Base.create('presenceViewController', Y.View, [], {

    /**
     *  The initializer runs when a Presence View Controller instance is created, and gives
     *  us an opportunity to set up the view.
     */
    initializer: function () {
        var container = this.get('container');
        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: container,
            panelId: "presence"
        }));

        // Attach events to DOM elements from container
        this._attachEvents();
    },

    /**
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {
        // Attach click on panel's item
        this.get('panel').get('container').delegate('click', function (event) {
            var presence, target = event.currentTarget;
            // Element was li
            if (target.ancestor('li')) {
                // Fire event
                presence = target.getAttribute('data-status');
                Y.fire('presenceChanged', presence);
            }
        }, 'li');

        Y.on('presenceChanged', this._onPresenceChanged, this);
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
    },

    /**
     * Presence changed event
     *
     * @param presence
     * @private
     */
    _onPresenceChanged: function (presence) {
        // Update presence indicator
       this._updatePresenceIndicator(presence);
        // Todo: Send to server
        this.get('panel').hide();
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
     * @private
     */
    _onPanelHidden: function () {
        // Todo: send active panel ajax
    },

    /**
     * Updates presence indicator based on the presence passed in param
     *
     * @param presence
     * @private
     */
    _updatePresenceIndicator: function (presence) {
        var presenceClass = "";
        // Set status indicator based on the presence type
        switch (presence) {
            case "jabber.status.online":
                presenceClass = "online";
                break;
            case "jabber.status.busy":
                presenceClass = "busy";
                break;
            case "jabber.status.unavailable":
                presenceClass = "unavailable";
                break;
            case "jabber.status.offline":
                presenceClass = "off";
                break;
            default:
                presenceClass = "off";
                break;
        }

        this.get('statusIndicator').setAttribute('class', "status-indicator " + presenceClass);
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Main container
        container: {
            valueFn: function () {
                return Y.one('#chatBar .status-panel');
            }
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        },

        // Container for status indicator
        statusIndicator: {
            valueFn: function () {
                return this.get('container').one('.status-indicator');
            }
        }
    }
});
