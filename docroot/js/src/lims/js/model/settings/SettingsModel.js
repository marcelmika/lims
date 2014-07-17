/**
 * Settings Model
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.SettingsModel = Y.Base.create('settingsModel', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        // Get JSON representation of current object
        var data = Y.JSON.stringify(this.toJSON());

        switch (action) {
            case 'create':
            case 'update': // There is no difference between create and update
                // Update Event Handler
                this._updateEventHandler(data, options, callback);
                break;

            case 'read':
                // Read Event Handler
                this._readEventHandler(data, options, callback);
                break;

            case 'delete':
                // Delete is not provided
                break;

            default:
                callback('Invalid action');
        }
    },

    /**
     * Event handler decides which update function should be called based on the
     * options.action call. Update Settings is a default fallback
     *
     * @param data
     * @param options
     * @param callback
     * @private
     */
    _updateEventHandler: function(data, options, callback) {
        if(options !== undefined) {
            // Update active panel action
            if (options.hasOwnProperty("action") && options.action === "updateActivePanel") {
                this._updateActivePanel(data, callback);
            }
        }

        // Update settings action is a default
        else {
            this._updateSettings(data, callback);
        }
    },

    /**
     * Updates active panel id
     *
     * @param data
     * @param callback
     * @private
     */
    _updateActivePanel: function (data, callback) {
        // Create settings that contains request url
        var settings = new Y.LIMS.Core.Settings();
        console.log(data);

        // Do the request
        Y.io(settings.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateActivePanel",
                data: data
            },
            on: {
                success: function (id, o) {
                    callback(null, o.response);
                },
                failure: function (x, o) {
                    callback("Cannot update active panel", o.response);
                }
            }
        });
    },

    /**
     * Updates settings
     *
     * @param data
     * @param callback
     * @private
     */
    _updateSettings: function (data, callback) {
        // Create settings that contains request url
        var settings = new Y.LIMS.Core.Settings();
        // Do the request
        Y.io(settings.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateSettings",
                data: data
            },
            on: {
                success: function (id, o) {
                    callback(null, o.response);
                },
                failure: function (x, o) {
                    callback("Cannot update settings", o.response);
                }
            }
        });
    },

    /**
     * Event handler decides which read function should be called based on the
     * options.action call. Update Settings is a default fallback
     *
     * @param callback
     * @private
     */
    _readEventHandler: function() {
        // todo: Read from dom
        // Look for an item in localStorage with this model's id.
//                data = localStorage.getItem(this.get('id'));
//
//                if (data) {
//                    callback(null, data);
//                } else {
//                    callback('Model not found.');
//                }
    }


}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        buddy: {
            value: null
        },

        activePanelId: {
            value: null // default value
        },

        isMute: {
            value: null // default value
        }
    }
});
