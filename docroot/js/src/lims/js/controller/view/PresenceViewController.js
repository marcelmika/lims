/**
 * Presence View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.PresenceViewController = Y.Base.create('presenceViewController', Y.LIMS.Core.ViewController, [], {

    /**
     *  The initializer runs when a Presence View Controller instance is created.
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
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {
        // Attach click on panel's item
        this.getPanel().get('container').delegate('click', function (event) {
            var presence, target = event.currentTarget;
            // Element was li
            if (target.ancestor('li')) {
                // Fire event
                presence = target.getAttribute('data-status');
                Y.fire('presenceChanged', presence);
            }
        }, 'li');

        Y.on('presenceChanged', this._onPresenceChanged, this);
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

        // Disable chat if needed
        if (presence === "jabber.status.off") {
            Y.fire("chatDisabled");
            // Since if we call chatDisabled all controllers will be automatically
            // hidden. Thus we need to show our controller again.
            this.showViewController();
        } else {
            Y.fire("chatEnabled");
        }

        // Dismiss controller
        this.dismissViewController();
    },

    /**
     * Updates presence indicator based on the presence passed in param
     *
     * @param presence
     * @private
     */
    _updatePresenceIndicator: function (presence) {
        // Vars
        var buddyDetails = this.get('buddyDetails'),
            presenceClass = "";

        // Set status indicator based on the presence type
        switch (presence) {
            case "jabber.status.online":
                presenceClass = "online";
                buddyDetails.set('presence', 'STATE_ACTIVE');
                break;
            case "jabber.status.busy":
                presenceClass = "busy";
                buddyDetails.set('presence', 'STATE_AWAY');
                break;
            case "jabber.status.unavailable":
                presenceClass = "unavailable";
                buddyDetails.set('presence', 'STATE_DND');
                break;
            case "jabber.status.off":
                presenceClass = "off";
                buddyDetails.set('presence', 'STATE_OFFLINE');
                break;
            default:
                presenceClass = "off";
                buddyDetails.set('presence', 'STATE_UNRECOGNIZED');
                break;
        }

        // Save to currently logged user
        buddyDetails.save({action: "updatePresence"});

        // Update status indicator
        this.get('statusIndicator').setAttribute('class', "status-indicator " + presenceClass);
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Id of the controller
        controllerId: {
            value: "presence"
        },

        // Container Node
        container: {
            valueFn: function () {
                return Y.one('#chatBar .status-panel');
            }
        },

        // Currently logged user
        buddyDetails: {
            value: null // Y.LIMS.Model.BuddyModelItem
        },

        // Container for status indicator
        statusIndicator: {
            valueFn: function () {
                return this.get('container').one('.status-indicator');
            }
        }
    }
});
