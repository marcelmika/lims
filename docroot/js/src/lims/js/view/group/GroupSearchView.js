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
 * Group Search View
 *
 * The class extends Y.View. It represent a view for a search functionality in contacts panel
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupSearchView = Y.Base.create('groupSearchView', Y.View, [], {

    // The template property holds the contents of the #lims-panel-search-template
    // element, which will be used as the HTML template for the search panel
    // Check the templates.jspf to see all templates
    template: Y.one('#lims-panel-search-template').get('innerHTML'),

    /**
     *  The initializer runs when an instance is created.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Resets the search result
     */
    reset: function () {

        // Vars
        var searchInput = this.get('searchInput');

        // Add a focus on the search input
        searchInput.set('value', '');
        searchInput.focus();

        // No preloader is needed at the beginning
        this._hideActivityIndicator();
    },

    /**
     * Attach listeners to events
     *
     * @private
     */
    _attachEvents: function () {

        // Vars
        var closeButton = this.get('closeButton'),
            searchInput = this.get('searchInput'),
            model = this.get('model');

        // Local events
        closeButton.on('click', this._onCloseButtonClick, this);
        searchInput.on('keyup', this._onSearchInputUpdate, this);
        model.on('searchStared', this._onSearchStared, this);
        model.on('searchSuccess', this._onSearchSuccess, this);
    },

    /**
     * Shows activity indicator
     *
     * @private
     */
    _showActivityIndicator: function () {

        // Vars
        var activityIndicator = this.get('activityIndicator'),
            container = this.get('container');

        // Show only if it's not already in the document
        if (!activityIndicator.inDoc()) {
            // Add to container
            container.append(activityIndicator);
        }
    },

    /**
     * Hides activity indicator
     *
     * @private
     */
    _hideActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator');

        // Hide only if the activity indicator is in the document
        if (activityIndicator.inDoc()) {
            activityIndicator.remove();
        }
    },

    /**
     * Called when the close button is pressed
     *
     * @private
     */
    _onCloseButtonClick: function () {
        // Fire an event
        this.fire('searchClosed');
    },

    /**
     * Called when the search input field is updated
     *
     * @private
     */
    _onSearchInputUpdate: function () {

        // Vars
        var timer = this.get('timer'),
            timerDelayInterval = this.get('timerDelayInterval'),
            searchInput = this.get('searchInput'),
            model = this.get('model'),
            value;

        // Get rid of new line characters
        value = searchInput.get('value').replace(/\n|\r/gim, '');

        // Clear out the timer first. Since we don't want to send a request whenever the user
        // types another letter we wait until he stops.
        clearTimeout(timer);

        // Set a new timer
        this.set('timer', setTimeout(function () {
            // Search buddies
            model.search(value);

        }, timerDelayInterval));
    },

    /**
     * Called when the search begins
     *
     * @private
     */
    _onSearchStared: function () {
        // Show the preloader
        this._showActivityIndicator();
    },

    /**
     * Called when the search ends successfully
     *
     * @private
     */
    _onSearchSuccess: function () {
        // Hide the preloader
        this._hideActivityIndicator();
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Main container
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.template);
            }
        },

        /**
         * Model related to the view
         *
         * {Y.LIMS.Model.BuddySearchListModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * Search input field node
         *
         * {Node}
         */
        searchInput: {
            valueFn: function () {
                return this.get('container').one('.search-input-field');
            }
        },

        /**
         * Info container node
         *
         * {Node}
         */
        infoContainer: {
            valueFn: function () {
                return this.get('container').one('.info-container');
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one('.preloader');
            }
        },

        /**
         * Close button node
         *
         * {Node}
         */
        closeButton: {
            valueFn: function () {
                return this.get('container').one('.close-search-button');
            }
        },

        /**
         * Time used for the delayed key up in text field
         *
         * {timer}
         */
        timer: {
            value: null // to be set
        },

        /**
         * Delayed key up interval. Model is refreshed after the delay.
         * This is useful because we don't want to overwhelm the server with many
         * request.
         */
        timerDelayInterval: {
            value: 1000 // 1 second
        },

        /**
         * Content of the search results node
         *
         * {Node}
         */
        searchContent: {
            valueFn: function () {
                return this.get('container').one('.search-content');
            }
        }


    }
});

