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
 * Panel View
 *
 * The class extends Y.View. It contains all necessary methods to handle panels with content
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PanelView = Y.Base.create('panelView', Y.View, [], {

    // The template property holds the contents of the #lims-conversation-item-error-template
    // element, which will be used as the HTML template for an error message
    // Check the templates.jspf to see all templates
    errorTemplate: Y.one('#lims-panel-error-template').get('innerHTML'),

    /**
     * Constructor
     *
     * @param options {container, panelId} are required
     */
    initializer: function (options) {
        this.set("container", options.container);
        this.set("panelId", options.panelId);

        // Handle already opened panel
        if (options.container.hasClass('selected')) {
            // Fire event
            Y.fire('panelShown', this);
            // Set flag
            this.set('isOpened', true);
        }

        // Attach events to DOM elements from container
        this._attachEvents();
    },

    /**
     * Shows the panel
     */
    show: function () {
        var container = this.get('container');
        // Show container
        container.removeClass('closed');
        container.addClass('selected');
        // Set flag
        this.set('isOpened', true);
        // Fire event
        Y.fire('panelShown', this);
    },

    /**
     * Hides panel
     */
    hide: function () {
        var container = this.get('container');
        // Hide container
        container.removeClass('selected');
        // Set flag
        this.set('isOpened', false);
        // Fire event
        Y.fire('panelHidden', this);
    },

    /**
     * Toggles panel. If it's shown it hides it, if it's hidden it shows it.
     */
    toggle: function () {
        var isOpened = this.get('isOpened');
        if (isOpened) {
            this.hide();
        } else {
            this.show();
        }
    },

    /**
     * Completely removes panel from tabs
     */
    close: function () {
        var container = this.get('container');
        // Remove container
        container.addClass('closed');
        // Set flag
        this.set('isOpened', false);
        // Fire event
        Y.fire('panelClosed', this);
    },

    /**
     * Shows the error message notification in the panel
     *
     * @param errorMessage
     */
    showError: function (errorMessage) {
        // Vars
        var panelTitle = this.get('panelTitle'),                        // Title of the panel node
            errorContainer = this.get('errorContainer'),                // Container with error
            errorMessageNode = errorContainer.one('.error-message'),    // Error message node
            animation;                                                  // Animation that will make it nicer


        // Set the error message
        errorMessageNode.set('innerHTML', errorMessage);

        // If the error container is already in the document don't animate it
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
            // Add it after title
            panelTitle.insert(errorContainer, 'after');

            // Run the effect animation
            animation.run();
        }
    },

    /**
     * Hides the error message notification
     */
    hideError: function () {
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
                // Remove container from DOM at the end of the animation
                animation.get('node').remove();
            });

            // Run!
            animation.run();
        }
    },

    /**
     * Attaches events to all necessary elements from the container
     *
     * @private
     */
    _attachEvents: function () {
        // Attach trigger event
        this.get('trigger').on('click', this._onTriggerClick, this);
        // Attach panel button event
        this.get('panelButtons').on('click', this._onPanelButtonsClick, this);
    },

    /**
     * Trigger click handler
     *
     * @private
     */
    _onTriggerClick: function () {
        // Toggle panel while clicked on trigger
        this.toggle();
    },

    /**
     * Panel buttons click handler
     *
     * @param event
     * @private
     */
    _onPanelButtonsClick: function (event) {
        var target = event.currentTarget;
        // Minimize button
        if (target.hasClass('minimize')) {
            this.hide();
        }
        // Close button
        else if (target.hasClass('close')) {
            this.close();
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Panel container
        container: {
            value: null // To be set in initializer
        },

        // Id of the panel
        panelId: {
            value: null // To be set in initializer
        },

        // Indicates if the panel is opened
        isOpened: {
            value: false // default value
        },

        // Panel title
        panelTitle: {
            valueFn: function () {
                return this.get('container').one('.panel-title');
            }
        },

        // Tab bar item which shows/hides panel
        trigger: {
            valueFn: function () {
                return this.get('container').one('.panel-trigger');
            }
        },

        // All buttons related to panel like e.g. minimize or close button
        panelButtons: {
            valueFn: function () {
                return this.get('container').all('.panel-button');
            }
        },

        // Search container
        searchContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-search');
            }
        },

        // Error container
        errorContainer: {
            valueFn: function () {
                return  Y.Node.create(this.errorTemplate);
            }
        }
    }

});

