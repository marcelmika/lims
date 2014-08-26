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
                Y.fire('presenceChanged', {
                    presence: presence
                });
            }
        }, 'li');

        Y.on('presenceChanged', this._onPresenceChanged, this);
    },

    /**
     * Presence changed event
     *
     * @param event
     * @private
     */
    _onPresenceChanged: function (event) {
        // Update presence indicator
        this._updatePresenceIndicator(event.presence);

        // Disable chat if needed
        if (event.presence === "OFFLINE") {
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
            case "ACTIVE":
                presenceClass = "online";
                buddyDetails.updatePresence('ACTIVE');
                break;
            case "AWAY":
                presenceClass = "busy";
                buddyDetails.updatePresence('AWAY');
                break;
            case "DND":
                presenceClass = "unavailable";
                buddyDetails.updatePresence('DND');
                break;
            case "OFFLINE":
                presenceClass = "off";
                buddyDetails.updatePresence('OFFLINE');
                break;
            default:
                presenceClass = "off";
                buddyDetails.updatePresence('UNRECOGNIZED');
                break;
        }

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
            value: null // to be set
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
