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

        // Add a focus on the search input and empty the content
        searchInput.set('value', '');
        searchInput.focus();

        // Render the view
        this.render();
        // No preloader is needed at the beginning
        this._hideActivityIndicator();
        // Show info message
        this._showInfoMessage();
        // Hide errors
        this._hideErrorMessage(false);
    },

    /**
     * Renders view
     *
     * @returns {Y.LIMS.View.GroupSearchView}
     */
    render: function () {
        // Vars
        var listContainer = this.get('listContainer'),
            model = this.get('model'),
            buddy,
            buddyView,
            index;

        // Empty the list view
        listContainer.set('innerHTML', '');

        // Create view for model instances
        for (index = 0; index < model.size(); index++) {
            // Get buddy from the list
            buddy = model.item(index);
            // Build view
            buddyView = new Y.LIMS.View.GroupBuddyViewItem({model: buddy});
            // Render view
            buddyView.render();
            // Append to container
            listContainer.append(buddyView.get('container'));
        }

        if (model.size() === 0) {
            this._showNoResultsMessage();
        } else {
            this._hideNoResultsMessage();
        }

        return this;
    },

    /**
     * Attach listeners to events
     *
     * @private
     */
    _attachEvents: function () {

        // Vars
        var searchInput = this.get('searchInput'),
            model = this.get('model');

        // Local events
        searchInput.on('keyup', this._onSearchInputUpdate, this);
        model.on('searchStarted', this._onSearchStarted, this);
        model.on('searchSuccess', this._onSearchSuccess, this);
        model.on('searchError', this._onSearchError, this);
    },

    /**
     * Performs search operation
     *
     * @private
     */
    _search: function () {

        // Vars
        var timer = this.get('timer'),
            timerDelayInterval = this.get('timerDelayInterval'),
            searchInput = this.get('searchInput'),
            model = this.get('model'),
            value;

        // Get rid of new line characters
        value = searchInput.get('value').replace(/\n|\r/gim, '');
        value = Y.Lang.trim(value);

        // Clear out the timer first. Since we don't want to send a request whenever the user
        // types another letter we wait until he stops.
        clearTimeout(timer);

        // Set a new timer
        this.set('timer', setTimeout(function () {
            // If there is no content to be searched do nothing
            if (value !== "") {
                // Search buddies
                model.search(value);
            }

        }, timerDelayInterval));
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
     * Shows the info message
     *
     * @private
     */
    _showInfoMessage: function () {
        // Vars
        var infoContainer = this.get('infoContainer'),
            infoMessage = this.get('infoMessage');

        // Set the message
        infoMessage.set('innerHTML', Y.LIMS.Core.i18n.values.searchInfoMessage);

        // Show info
        infoContainer.show();
    },

    /**
     * Hides the info message
     *
     * @private
     */
    _hideInfoMessage: function () {
        // Vars
        var infoContainer = this.get('infoContainer');
        // Hide info
        infoContainer.hide();
    },

    /**
     * Shows no results info message
     *
     * @private
     */
    _showNoResultsMessage: function () {
        // Vars
        var infoContainer = this.get('infoContainer'),
            infoMessage = this.get('infoMessage');

        // Set the message
        infoMessage.set('innerHTML', ''); // This needs to be here otherwise the content is not refreshed on retina
        infoMessage.set('innerHTML', Y.LIMS.Core.i18n.values.searchNoResultsMessage);

        // Show info
        infoContainer.show();
    },

    /**
     * Hides no results info message
     *
     * @private
     */
    _hideNoResultsMessage: function () {
        // Vars
        var infoContainer = this.get('infoContainer');
        // Hide info
        infoContainer.hide();
    },

    /**
     * Shows error message
     *
     * @private
     */
    _showErrorMessage: function () {
        // Vars
        var errorContainer = this.get('errorContainer'),
            searchContent = this.get('searchContent'),
            animation,
            instance = this;

        // If the error container is already in the document do nothing
        if (!errorContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.2,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Opacity needs to be set to zero otherwise there will
            // be a weird blink effect
            errorContainer.setStyle('opacity', 0);

            // Attach click on resend button event
            errorContainer.one('.resend-button').on('click', function (event) {
                event.preventDefault();
                instance._onRefreshButtonClick();
            });

            // Add the error to the container
            searchContent.append(errorContainer);

            // Run the effect animation
            animation.run();
        }

        // It is possible that resend button was clicked thus it was transformed to the preloader.
        // Remove the preloader class so it can be the resend button again.
        errorContainer.one('.resend-button').removeClass('preloader');
    },

    /**
     * Hides error message
     *
     * @private
     */
    _hideErrorMessage: function (animated) {
        // Vars
        var errorContainer = this.get('errorContainer'),
            animation;

        // Run the animation only if the error container is in DOM
        if (errorContainer.inDoc()) {

            // Animated
            if (animated) {
                // Create an instance of animation
                animation = new Y.Anim({
                    node: errorContainer,
                    duration: 0.2,
                    from: {opacity: 1},
                    to: {opacity: 0}
                });

                // Listen to the end of the animation
                animation.on('end', function () {
                    // Remove the error node from DOM
                    animation.get('node').remove();
                });

                // Run!
                animation.run();
            }
            // Static
            else {
                // Simply remove the error node from DOM
                errorContainer.remove();
            }

        }
    },

    /**
     * Called when the search input field is updated
     *
     * @private
     */
    _onSearchInputUpdate: function () {
        // Start search
        this._search();
    },

    /**
     * Called when the search begins
     *
     * @private
     */
    _onSearchStarted: function () {
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
        // Hide info message
        this._hideNoResultsMessage();
        // Render incoming data
        this.render();
        // Hide error
        this._hideErrorMessage(true);
    },

    /**
     * Called when the search end with failure
     *
     * @private
     */
    _onSearchError: function () {
        // Hide the preloader
        this._hideActivityIndicator();
        // Render
        this.render();
        // Hide info message
        this._hideNoResultsMessage();
        // Show error
        this._showErrorMessage();
    },

    /**
     * Called when the user click on the refresh button in the error container
     *
     * @private
     */
    _onRefreshButtonClick: function () {
        // Vars
        var refreshButtonNode = this.get('errorContainer').one('.resend-button');

        // Prevent user to click on preloader more than once
        if (!refreshButtonNode.hasClass('preloader')) {
            // Add preloader to the resend button
            refreshButtonNode.addClass('preloader');
            // Search
            this._search();
        }
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
         * Content of the search results node
         *
         * {Node}
         */
        searchContent: {
            valueFn: function () {
                return this.get('container').one('.search-content');
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
         * Info message node in info container
         *
         * {Node}
         */
        infoMessage: {
            valueFn: function () {
                return this.get('infoContainer').one('.info-message');
            }
        },

        /**
         * Error container node
         *
         * {Node}
         */
        errorContainer: {
            valueFn: function () {
                return this.get('container').one('.error-container');
            }
        },

        /**
         * List container node
         *
         * {Node}
         */
        listContainer: {
            valueFn: function () {
                return this.get('container').one('.group-buddy-list');
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
         *
         * {integer}
         */
        timerDelayInterval: {
            value: 500 // half a second
        }
    }
});

