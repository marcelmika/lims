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
 * Group View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupViewController = Y.Base.create('groupViewController', Y.LIMS.Core.ViewController, [], {

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
     * Panel Did Appear is called when the panel did appear on the screen
     */
    onPanelDidAppear: function () {
        this._startTimer();
    },

    /**
     * Panel Did Disappear is called when the panel disappeared from the screen
     */
    onPanelDidDisappear: function () {
        this._stopTimer();
    },

    /**
     * Session Expired is called whenever the user session has expired. Provide all necessary cleaning like
     * invalidation of timers, etc. At the end of the method the controller will be automatically hidden from
     * the screen.
     */
    onSessionExpired: function () {
        this._stopTimer();
    },

    /**
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {

        // Global events
        Y.on('buddySelected', this._onBuddySelected, this);
        Y.on('connectionError', this._onConnectionError, this);
        Y.on('connectionOK', this._onConnectionOK, this);
    },

    /**
     * Starts timer which periodically refreshes group list
     *
     * @private
     */
    _startTimer: function () {

        // Vars
        var model = this.get('model'),
            timerInterval = this.get('timerInterval'),
            properties = this.get('properties');

        // Start only if the chat is enabled
        if (properties.isChatEnabled()) {
            // Load model
            model.load();
            // Start periodical update
            this.set('timer', setInterval(function () {
                model.load();
            }, timerInterval));
        }
    },

    /**
     * Pauses timer which periodically refreshes group list
     *
     * @private
     */
    _stopTimer: function () {

        // Vars
        var timer = this.get('timer');
        // Pause
        clearTimeout(timer);
    },

    /**
     * Buddy selected event. Called whenever the user selects one of the buddies from
     * the group list
     *
     * @private
     */
    _onBuddySelected: function () {
        this.dismissViewController();
    },

    /**
     * Called whenever an error with connection occurred
     *
     * @private
     */
    _onConnectionError: function () {
        this.showError(Y.LIMS.Core.i18n.values.connectionErrorMessage);
    },

    /**
     * Called when there are no more connection errors
     *
     * @private
     */
    _onConnectionOK: function () {
        this.hideError();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Each controller must have an id
         *
         * {string}
         */
        controllerId: {
            value: "groups"
        },

        /**
         * Controller main container
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Controller model
         *
         * {Y.LIMS.Model.GroupModelList}
         */
        model: {
            valueFn: function () {
                return new Y.LIMS.Model.GroupModelList();
            }
        },

        /**
         * Activity indicator spinner node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one(".panel-content .preloader");
            }
        },

        /**
         * Group list container node
         *
         * {Node}
         */
        panelContentContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-content');
            }
        },

        /**
         * View for the group list
         *
         * {Y.LIMS.View.GroupViewList}
         */
        groupViewList: {
            valueFn: function () {
                // Vars
                var container = this.get('panelContentContainer'),
                    model = this.get('model'),
                    activityIndicator = this.get('activityIndicator');

                // Create view
                return new Y.LIMS.View.GroupViewList({
                    container: container,
                    model: model,
                    activityIndicator: activityIndicator
                });
            }
        },

        /**
         * Timer for poller that loads periodically groups from server
         *
         * {timer}
         */
        timer: {
            value: null // to be set
        },

        /**
         * Time interval for the timer
         *
         * {integer}
         */
        timerInterval: {
            value: 10000 // 10 seconds
        },

        /**
         * Properties object
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        }
    }
});
