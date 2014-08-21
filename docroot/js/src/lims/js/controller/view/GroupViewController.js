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
        // Vars
        var model = this.get('model');

        // Local events
        model.after('groupsLoaded', this._groupsLoaded, this);

        // Global events
        Y.on('buddySelected', this._onBuddySelected, this);
    },

    /**
     * Starts timer which periodically refreshes group list
     *
     * @private
     */
    _startTimer: function () {
        // Vars
        var settings = this.get('settings'),
            model = this.get('model'),
            timerInterval = this.get('timerInterval');

        // Start only if the chat is enabled
        if (settings.isChatEnabled()) {
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
     * Called whenever the groups model is updated
     *
     * @private
     */
    _groupsLoaded: function () {
        // Do the animation of groups
        this._fadeInGroups();
        // Hide indicator
        this.get('activityIndicator').hide();
    },

    /**
     * Runs fade in effect on groups
     *
     * @private
     */
    _fadeInGroups: function () {
        // Container
        var container = this.get('groupViewListContainer'),
            animation = new Y.Anim({
                node: container,
                duration: 0.5,
                from: {
                    opacity: 0
                },
                to: {
                    opacity: 1
                }
            });

        // Opacity needs to be set to zero otherwise there will
        // be a weird blink effect
        container.setStyle('opacity', 0);

        // Run the effect animation
        animation.run();
    },

    /**
     * Buddy selected event. Called whenever the user selects one of the buddies from
     * the group list
     *
     * @private
     */
    _onBuddySelected: function () {
        this.dismissViewController();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Id of the controller
        controllerId: {
            value: "groups"
        },

        // Container Node
        container: {
            valueFn: function () {
                // Get root
                var rootNode = this.get('settings').getRootNode();
                // Return container
                return rootNode.one('.buddy-list');
            }
        },

        // Y.LIMS.Model.GroupModelList
        model: {
            valueFn: function () {
                return new Y.LIMS.Model.GroupModelList();
            }
        },

        // Container for activity indicator
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one(".panel-content .preloader");
            }
        },

        // Container Node for group list
        groupViewListContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-content .group-list');
            }
        },

        // Group view which contains all contacts
        groupViewList: {
            valueFn: function () {
                // Vars
                var container = this.get('groupViewListContainer'),
                    model = this.get('model');
                // Create view
                return new Y.LIMS.View.GroupViewList({
                    container: container,
                    model: model
                });
            }
        },

        // Timer used to set async calls to server
        timer: {
            value: null // to be set
        },

        // Length of timer period
        timerInterval: {
            value: 10000 // 10 seconds
        },

        // Global settings
        settings: {
            valueFn: function () {
                return new Y.LIMS.Core.Settings();
            }
        }
    }
});
