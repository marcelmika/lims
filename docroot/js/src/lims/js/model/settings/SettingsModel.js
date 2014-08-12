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

    /**
     * Updates active panel id
     *
     * @param activePanelId
     * @param callback
     */
    updateActivePanel: function (activePanelId, callback) {
        // Create settings that contains request url
        var settings = new Y.LIMS.Core.Settings(),
            content;

        // Save locally
        this.set('activePanelId', activePanelId);

        // Serialize
        content = Y.JSON.stringify(this.toJSON());

        // Do the request
        Y.io(settings.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateActivePanel",
                content: content
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

    // Custom sync layer.
    sync: function (action, options, callback) {
        // Get JSON representation of current object
        var content,
            settings = new Y.LIMS.Core.Settings();

        // Serialize
        content = Y.JSON.stringify(this.toJSON());

        switch (action) {
            case 'create':
            case 'update': // There is no difference between create and update

                // Do the request
                Y.io(settings.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "UpdateSettings",
                        content: content
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
                break;

            case 'read':
            case 'delete':
                break;

            default:
                callback('Invalid action');
        }
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
            value: false // default value
        }
    }
});
