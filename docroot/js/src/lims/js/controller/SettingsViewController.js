/**
 * Settings View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SettingsViewController = Y.Base.create('settingsViewController', Y.View, [], {

    /**
     *  The initializer runs when a Settings View Controller instance is created, and gives
     *  us an opportunity to set up the view.
     */
    initializer: function () {
        var container = this.get('container');
        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: container,
            panelId: "settings"
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

        // Local events
        this.get('soundCheckbox').on('click', this._onSoundCheckboxUpdated, this);

        // Remote events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
    },

    /**
     * Sound checkbox changed
     *
     * @private
     */
    _onSoundCheckboxUpdated: function () {
        var isChecked = this.get('soundCheckbox').get('checked') ? true : false;
        // Todo: Send to server
        console.log('checkbox updated to: ' + isChecked);
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
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Main container
        container: {
            valueFn: function () {
                return Y.one('#chatBar .chat-settings');
            }
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        },

        // Sound checkbox element
        soundCheckbox: {
            valueFn: function () {
                return this.get('container').one("#playSound");
            }
        }
    }
});
