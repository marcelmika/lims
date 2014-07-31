/**
 * Settings Model
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.SettingsModel = Y.Base.create('settingsModel', Y.Model, [], {


    /**
     * Returns true if the user decided that he doesn't want to hear
     * sound notifications
     *
     * @returns {boolean}
     */
    isMute: function () {
        return this.get('isMute');
    },

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
            case 'delete':
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
    _updateEventHandler: function (data, options, callback) {
        if (options !== undefined) {
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
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }
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
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }
                    callback("Cannot update settings", o.response);
                }
            }
        });
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
