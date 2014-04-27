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
            case 'update':
                if (options.action === "updateActivePanel") {
                    this._updateActivePanel(data, callback);
                }
                break;

            case 'read':
                console.log("read buddy");
                // Look for an item in localStorage with this model's id.
//                data = localStorage.getItem(this.get('id'));
//
//                if (data) {
//                    callback(null, data);
//                } else {
//                    callback('Model not found.');
//                }
                return;

            case 'delete':
                console.log("delete buddy");
                return;

            default:
                callback('Invalid action');
        }
    },

    /**
     * Updates active panel id
     *
     * @param data
     * @param callback
     * @private
     */
    _updateActivePanel: function(data, callback) {
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
                    callback("Cannot update active panel", o.response);
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
            value: "" // default value
        }
    }
});
