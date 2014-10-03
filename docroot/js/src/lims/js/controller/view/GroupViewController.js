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

    // Template for list button
    listButtonTemplate: '<button class="panel-button list">',

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
        // Start poller
        this._startPolling();
        // Subscribe to key up event
        this._subscribeKeyUp();
    },

    /**
     * Panel Did Disappear is called when the panel disappeared from the screen
     */
    onPanelDidDisappear: function () {
        // Stop poller
        this._stopPolling();
        // Detach the key up event
        this._detachKeyUp();
    },

    /**
     * Session Expired is called whenever the user session has expired. Provide all necessary cleaning like
     * invalidation of timer, etc. At the end of the method the controller will be automatically hidden from
     * the screen.
     */
    onSessionExpired: function () {
        this._stopPolling();
    },

    /**
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {

        // Vars
        var searchButton = this.get('searchButton'),
            listButton = this.get('listButton');

        // Local events
        searchButton.on('click', this._onSearchClicked, this);
        listButton.on('click', this._onSearchClosed, this);

        // Global events
        Y.on('buddySelected', this._onBuddySelected, this);
    },

    /**
     * Subscribes to the global key up event
     *
     * @private
     */
    _subscribeKeyUp: function () {
        if (Y.one('doc')) {
            // Save the subscription to the key up event
            this.set('keyUpSubscription', Y.one('doc').on('keyup', this._onKeyPress, this));
        }
    },

    /**
     * Detaches the subscription to the global key up event
     *
     * @private
     */
    _detachKeyUp: function () {
        // Vars
        var keyUpSubscription = this.get('keyUpSubscription');

        if (keyUpSubscription) {
            keyUpSubscription.detach();
        }
    },

    /**
     * Starts poller that periodically refreshes the group list
     *
     * @private
     */
    _startPolling: function () {

        // Vars
        var model = this.get('model'),
            poller = this.get('poller'),
            properties = this.get('properties');

        // Start only if the chat is enabled
        if (properties.isChatEnabled()) {

            // Register model to the poller
            poller.register('groupViewController:model', new Y.LIMS.Core.PollerEntry({
                model: model,       // Model that will be periodically refreshed
                interval: 10000     // 10 seconds period
            }));
        }
    },

    /**
     * Stops poller that periodically refreshes the group list
     *
     * @private
     */
    _stopPolling: function () {
        // Vars
        var poller = this.get('poller');
        // Pause
        poller.unregister('groupViewController:model');
    },

    /**
     * Shows the search panel
     *
     * @private
     */
    _showSearchPanel: function () {

        // Vars
        var panelTitle = this.get('panelTitle'),
            searchPanelView = this.get('searchPanelView'),
            searchPanelModel = this.get('searchPanelModel'),
            searchPanelContainer = searchPanelView.get('container'),
            animation,
            instance = this;

        // Only show the search panel if not in document already
        if (!searchPanelContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: searchPanelContainer,
                duration: 0.5,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Search button will no longer be needed so hide it at the end of the animation
            animation.on('end', function () {
                instance._hideSearchButton();
                instance._showListButton();
            }, this);

            // Opacity needs to be set to zero otherwise there will
            // be a weird blink effect
            searchPanelContainer.setStyle('opacity', 0);

            // Add search panel to the container
            panelTitle.insert(searchPanelContainer, 'after');

            // Reset the previous search
            searchPanelModel.reset();
            searchPanelView.reset();

            // Run the animation
            animation.run();
        }
    },

    /**
     * Hides the search panel
     *
     * @private
     */
    _hideSearchPanel: function () {

        // Vars
        var searchPanelView = this.get('searchPanelView'),
            searchPanelContainer = searchPanelView.get('container'),
            animation,
            instance = this;

        // Only hide the search panel if it's in the document
        if (searchPanelContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: searchPanelContainer,
                duration: 0.5,
                from: {opacity: 1},
                to: {opacity: 0}
            });

            // Listen to the end of the animation
            animation.on('end', function () {
                // Remove the search panel from DOM
                animation.get('node').remove();
                instance._hideListButton();
                // We will need the search button in the panel title again
                instance._showSearchButton();
            }, this);

            // Run the animation
            animation.run();
        }
    },

    /**
     * Shows the search button in panel title
     *
     * @private
     */
    _showSearchButton: function () {
        // Vars
        var searchButton = this.get('searchButton');
        // Show the button
        searchButton.show();
    },

    /**
     * Hides the search button in panel title
     *
     * @private
     */
    _hideSearchButton: function () {
        // Vars
        var searchButton = this.get('searchButton');
        // Hide the button
        searchButton.hide();
    },

    /**
     * Shows the list button in panel title
     *
     * @private
     */
    _showListButton: function () {
        // Vars
        var listButton = this.get('listButton'),
            minimizeButton = this.get('minimizeButton');

        if (!listButton.inDoc()) {
            minimizeButton.insert(listButton, 'after');
        }

        // Show the button
        listButton.show();
    },

    /**
     * Hides the list button in panel title
     *
     * @private
     */
    _hideListButton: function () {
        // Vars
        var listButton = this.get('listButton');
        // Hide the button
        listButton.hide();
    },

    /**
     * Buddy selected event. Called whenever the user selects one of the buddies from
     * the group list
     *
     * @private
     */
    _onBuddySelected: function () {
        // Hide the controller
        this.dismissViewController();
        // If the buddy was selected from search panel, hide it
        this._hideSearchPanel();
    },

    /**
     * Called when a search button in panel title is pressed
     *
     * @private
     */
    _onSearchClicked: function () {
        // Show the search panel
        this._showSearchPanel();
    },

    /**
     * Called when the search panel is closed
     *
     * @private
     */
    _onSearchClosed: function () {
        // Hide the search panel
        this._hideSearchPanel();
    },

    /**
     * Called whenever the user presses any kay in the browser
     *
     * @param event
     * @private
     */
    _onKeyPress: function (event) {
        // User pressed ESC key
        if (event.keyCode === 27) {
            // Hide the search panel
            this._hideSearchPanel();
        }
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
         * Panel title node
         *
         * {Node}
         */
        panelTitle: {
            valueFn: function () {
                return this.get('container').one('.panel-title');
            }
        },

        /**
         * View that takes care of the search panel
         *
         * {Y.LIMS.View.GroupSearchView}
         */
        searchPanelView: {
            valueFn: function () {
                // Vars
                var model = this.get('searchPanelModel');
                // Create new instance of the view
                return new Y.LIMS.View.GroupSearchView({
                    model: model
                });
            }
        },

        /**
         * Model related to the search panel
         *
         * {Y.LIMS.Model.BuddySearchListModel}
         */
        searchPanelModel: {
            valueFn: function () {
                return new Y.LIMS.Model.BuddySearchListModel();
            }
        },

        /**
         * Search button in panel title node
         *
         * {Node}
         */
        searchButton: {
            valueFn: function () {
                return this.get('container').one('.panel-title .panel-button.search');
            }
        },

        /**
         * Minimize button in panel title node
         *
         * {Node}
         */
        minimizeButton: {
            valueFn: function () {
                return this.get('container').one('.panel-title .panel-button.minimize');
            }
        },

        /**
         * Search button in panel title node
         *
         * {Node}
         */
        listButton: {
            valueFn: function () {
                return Y.Node.create(this.listButtonTemplate);
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
         * Properties object
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        },

        /**
         * Cached global key up subscription
         *
         * {event}
         */
        keyUpSubscription: {
            value: null // to be set
        },

        /**
         * An instance of poller that periodically refreshes models that are subscribed
         *
         * {Y.LIMS.Core.Poller}
         */
        poller: {
            value: null // to be set
        }
    }
});
