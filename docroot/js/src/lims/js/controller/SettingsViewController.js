/**
 * Settings View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SettingsViewController = Y.Base.create('settingsViewController', Y.LIMS.Core.ViewController, [], {

    /**
     *  The initializer runs when a Group View Controller instance is created.
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
        // Vars
        var soundCheckbox = this.get('soundCheckbox');

        // Local events
        soundCheckbox.on('click', this._onSoundCheckboxUpdated, this);
    },

    /**
     * Sound checkbox changed
     *
     * @private
     */
    _onSoundCheckboxUpdated: function () {
        var model = this.get('model'),
            isMute = this.get('soundCheckbox').get('checked') ? false : true;
        // Update model
        model.set('isMute', isMute).save();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Id of the controller
        controllerId: {
            value: "settings"
        },

        // Container Node
        container: {
            valueFn: function () {
                return Y.one('#chatBar .chat-settings');
            }
        },

        // Y.LIMS.Model.SettingsModel
        model: {
            value: null // to be set
        },

        // Sound checkbox element
        soundCheckbox: {
            valueFn: function () {
                return this.get('container').one("#playSound");
            }
        }
    }
});
