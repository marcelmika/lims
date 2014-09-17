/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.MainController = Y.Base.create('mainController', Y.Base, [Y.LIMS.Controller.ControllerExtension], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controller
    initializer: function () {
        var buddyDetails = this.get('buddyDetails'),
            settingsModel = this.get('settingsModel'),
            notification = this.get('notification'),
            properties = this.get('properties'),
            serverTime = this.get('serverTimeModel'),
            rootNode = this.getRootNode();

        // Attach events
        this._attachEvents();

        // Load the most fresh server time to count server time offset
        serverTime.load(function (err) {

            // Update to the optimal offset that we get from the server.
            // If there is an error properties contain offset read from the
            // html as a fallback.
            if (!err) {
                properties.set('offset', new Date().getTime() - serverTime.get('time'));
            }

            // Group
            new Y.LIMS.Controller.GroupViewController({
                container: rootNode.one('.buddy-list'),
                properties: properties
            });
            // Presence
            new Y.LIMS.Controller.PresenceViewController({
                container: rootNode.one('.status-panel'),
                buddyDetails: buddyDetails
            });
            // Settings
            new Y.LIMS.Controller.SettingsViewController({
                container: rootNode.one('.chat-settings'),
                model: settingsModel
            });
            // Conversation
            new Y.LIMS.Controller.ConversationsController({
                container: rootNode.one('.lims-tabs'),
                buddyDetails: buddyDetails,
                settings: settingsModel,
                notification: notification,
                properties: properties
            });
        });
    },

    /**
     * This is called whenever the user session expires
     */
    sessionExpired: function () {
        // Fire an event so the other controllers know about the expiration
        Y.fire('userSessionExpired');
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
        Y.on('userSessionExpired', this._onSessionExpired, this);
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
        this.get('settingsModel').updateActivePanel(panelId);
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
            this.get('settingsModel').updateActivePanel(null);
        }
    },

    /**
     * Called when the user session expires
     *
     * @private
     */
    _onSessionExpired: function () {
        // Hide the whole portlet
        this.getRootNode().hide();
    }

}, {

    ATTRS: {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.
        buddyDetails: {
            valueFn: function () {
                // We need settings to determine user
                var properties = new Y.LIMS.Core.Properties();
                // Get logged user
                return new Y.LIMS.Model.BuddyModelItem({
                    buddyId: properties.getCurrentUserId(),
                    screenName: properties.getCurrentUserScreenName(),
                    fullName: properties.getCurrentUserFullName()
                });
            }
        },

        // Settings related to the logged user
        settingsModel: {
            valueFn: function () {
                return new Y.LIMS.Model.SettingsModel({
                    buddy: this.get('buddyDetails')
                });
            }
        },

        // Server time
        serverTimeModel: {
            valueFn: function () {
                return new Y.LIMS.Model.ServerTimeModel();
            }
        },

        // Notification
        notification: {
            valueFn: function () {
                return new Y.LIMS.Core.Notification({
                    settings: this.get('settingsModel'),
                    container: this.getRootNode().one('.lims-sound'),
                    properties: this.get('properties')
                });
            }
        },

        properties: {
            valueFn: function () {
                return new Y.LIMS.Core.Properties();
            }
        },

        // Current active panel
        activePanelId: {
            value: null // default value
        }
    }
});
