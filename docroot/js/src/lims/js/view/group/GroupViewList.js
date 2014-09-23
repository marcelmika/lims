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
 * Group View List
 *
 * The class extends Y.View. It represent a view for a list of groups
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewList = Y.Base.create('groupViewList', Y.View, [], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.GroupModelList,

    // The template property holds the contents of the #lims-group-list-error-template
    // element, which will be used as the HTML template for an error message
    // Check the templates.jspf to see all templates
    errorTemplate: Y.one('#lims-group-list-error-template').get('innerHTML'),

    // The template property holds the contents of the #lims-group-list-info-template
    // element, which will be used as the HTML template for an info message
    // Check the templates.jspf to see all templates
    infoTemplate: Y.one('#lims-group-list-info-template').get('innerHTML'),

    /**
     * The initializer runs when the instance is created, and gives
     * us an opportunity to set up the view.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();

        // Group list needs to be removed from the DOM since we don't know if there
        // are any groups yet
        this.get('groupList').remove();
    },

    /**
     * Attaches listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        var model = this.get('model');

        // Local events
        model.after('add', this._onGroupAdd, this);
        model.after('reset', this._onGroupReset, this);
        model.after('groupsReadSuccess', this._onGroupsReadSuccess, this);
        model.after('groupsReadError', this._onGroupsReadError, this);
    },


    /**
     * Called when the groups model is read
     *
     * @private
     */
    _onGroupsReadSuccess: function () {

        // Vars
        var activityIndicator = this.get('activityIndicator'),
            model = this.get('model');

        // If there was any error, hide it
        this._hideError();

        if (model.isEmpty()) {
            this._showEmptyInfo();
            this._hideGroups();
        } else {
            // Show the groups
            this._showGroups();
            this._hideEmptyInfo();
        }

        // Hide indicator
        activityIndicator.hide();
    },

    /**
     * Called when there groups wasn't loaded due to an error
     *
     * @private
     */
    _onGroupsReadError: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator');

        // Hide indicator
        activityIndicator.hide();
        // Hide groups
        this._hideGroups();
        // Hide info about empty groups
        this._hideEmptyInfo();
        // Show error
        this._showError();
    },

    /**
     * Called whenever the group model is reset
     *
     * @private
     */
    _onGroupReset: function () {
        // Vars
        var groupList = this.get('groupList');

        // Empty node
        groupList.set('innerHTML', '');
    },

    /**
     * Creates a new GroupView instance and renders it into the list whenever a
     * Group item is added to the list.
     *
     * @param e event
     * @private
     */
    _onGroupAdd: function (e) {
        // Vars
        var groupView = new Y.LIMS.View.GroupViewItem({model: e.model}),
            groupList = this.get('groupList');

        // Render group
        groupView.render();
        // Add it to group list
        groupList.append(groupView.get('container'));
    },

    /**
     * Shows group list
     *
     * @private
     */
    _showGroups: function () {
        // Vars
        var groupList = this.get('groupList'),
            container = this.get('container'),
            animation;

        // If the group list is already in the document don't animate it
        if (!groupList.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: groupList,
                duration: 0.5,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Opacity needs to be set to zero otherwise there will
            // be a weird blink effect
            groupList.setStyle('opacity', 0);

            // Add group list to the container
            container.append(groupList);

            // Run the effect animation
            animation.run();
        }
    },

    /**
     * Hides group list
     *
     * @private
     */
    _hideGroups: function () {
        // Vars
        var groupList = this.get('groupList'),
            animation;

        // Run the animation only if the group list is in DOM
        if (groupList.inDoc()) {

            // Create the animation instance
            animation = new Y.Anim({
                node: groupList,
                duration: 0.5,
                from: {opacity: 1},
                to: {opacity: 0}
            });

            // Listen to the end of the animation
            animation.on('end', function () {
                animation.get('node').remove();
            });

            // Run!
            animation.run();
        }
    },

    /**
     * Shows the info message
     *
     * @private
     */
    _showEmptyInfo: function () {
        // Vars
        var container = this.get('container'),
            infoContainer = this.get('infoContainer'),
            animation;

        // If the info container is already in the document don't add it
        if (!infoContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: infoContainer,
                duration: 0.5,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Opacity needs to be set to zero otherwise there will
            // be a weird blink effect
            infoContainer.setStyle('opacity', 0);

            // Add the info to the container
            container.append(infoContainer);

            // Run the animation
            animation.run();
        }
    },

    _hideEmptyInfo: function () {
        // Vars
        var infoContainer = this.get('infoContainer'),
            animation;

        // Run the animation only if the info container is in DOM
        if (infoContainer.inDoc()) {

            // Create the animation instance
            animation = new Y.Anim({
                node: infoContainer,
                duration: 0.5,
                from: {opacity: 1},
                to: {opacity: 0}
            });

            // Listen to the end of the animation
            animation.on('end', function () {
                animation.get('node').remove();
            });

            // Run the animation
            animation.run();
        }
    },

    /**
     * Shows the error message
     *
     * @private
     */
    _showError: function () {
        // Vars
        var container = this.get('container'),
            errorContainer = this.get('errorContainer'),
            animation,
            instance = this;

        // If the error container is already in the document don't add it
        if (!errorContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.5,
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
            container.append(errorContainer);

            // Run the effect animation
            animation.run();
        }

        // It is possible that resend button was clicked thus it was transformed to the preloader.
        // Remove the preloader class so it can be the resend button again.
        errorContainer.one('.resend-button').removeClass('preloader');
    },

    /**
     * Hides the error message
     *
     * @private
     */
    _hideError: function () {
        // Vars
        var errorContainer = this.get('errorContainer'),
            animation;

        // Run the animation only if the error container is in DOM
        if (errorContainer.inDoc()) {

            // Create the animation instance
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.5,
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
    },

    /**
     * Called when the user click on the refresh button in the error container
     *
     * @private
     */
    _onRefreshButtonClick: function () {
        // Vars
        var model = this.get('model'),
            refreshButtonNode = this.get('errorContainer').one('.resend-button');

        // Prevent user to click on preloader more than once
        if (!refreshButtonNode.hasClass('preloader')) {
            // Add preloader to the resend button
            refreshButtonNode.addClass('preloader');

            // Save the model again
            model.load();
        }
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Group view container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Group list model
         *
         * {Y.LIMS.Model.GroupModelList}
         */
        model: {
            value: null // to be set
        },

        /**
         * Group list node
         *
         * {Node}
         */
        groupList: {
            valueFn: function () {
                return this.get('container').one('.group-list');
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            value: null // to be set
        },

        /**
         * Node for error container
         *
         * {Node}
         */
        errorContainer: {
            valueFn: function () {
                return Y.Node.create(this.errorTemplate);
            }
        },

        /**
         * Node for info container
         *
         * {Node}
         */
        infoContainer: {
            valueFn: function () {
                return Y.Node.create(this.infoTemplate);
            }
        }
    }
});
