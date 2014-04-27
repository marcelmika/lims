/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.MainController = Y.Base.create('mainController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controller
    initializer: function () {
        var buddyDetails = this.get('buddyDetails'),
            settings = this.get('settings');

        // Attach events
        this._attachEvents();

        // Group
        new Y.LIMS.Controller.GroupViewController();
        // Presence
        new Y.LIMS.Controller.PresenceViewController({
            buddyDetails: buddyDetails
        });
        // Settings
        new Y.LIMS.Controller.SettingsViewController({
            settings: settings
        });
        // Conversation
        new Y.LIMS.Controller.ConversationsController();
    },

    /**
     * Attach local functions to events
     *
     * @private
     */
    _attachEvents: function () {
        // Panel events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
    },

    /**
     * Called when any panel is shown
     *
     * @param panel
     * @private
     */
    _onPanelShown: function (panel) {
        var panelId = panel.get('panelId');
        // Store current active panel id
        this.set('activePanelId', panelId);
        // Update settings
        this.get('settings').set('activePanelId', panelId).save({
            action: 'updateActivePanel'
        });
    },

    /**
     * Called when any panel is hidden
     *
     * @param panel
     * @private
     */
    _onPanelHidden: function (panel) {
        // If the hidden panel is currently active panel it means that no panel is currently active
        if (this.get('activePanelId') === panel.get('panelId')) {
            // Update settings
            this.get('settings').set('activePanelId', '').save({
                action: 'updateActivePanel'
            });
        }
    }

}, {

    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        buddyDetails: {
            value: null // default value
        },

        settings: {
            valueFn: function () {
                if (this.get('settings') === undefined) {
                    this.set('settings', new Y.LIMS.Model.SettingsModel({
                        buddy: this.get('buddyDetails')
                    }));
                }
                return this.get('settings');
            }
        },

        activePanelId: {
            value: null // default value
        }
    }
});
