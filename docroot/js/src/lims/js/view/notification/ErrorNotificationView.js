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
 * Error notification view
 *
 * The class extends Y.View. It represents a view that holds error message and a resend button
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ErrorNotificationView = Y.Base.create('errorNotificationView', Y.View, [], {

    // The template property holds the contents of the #lims-error-notification-template
    // element, which will be used as the HTML template for error notifications
    // Check the templates.jspf to see all templates
    errorTemplate: Y.one('#lims-error-notification-template').get('innerHTML'),

    /**
     * Shows create error message
     *
     * @param animated true if the event should be animated
     */
    showErrorMessage: function (animated) {
        // Vars
        var errorContainer = this.get('errorContainer'),
            resendButton = this.get('resendButton'),
            container = this.get('container'),
            animation,
            instance = this;

        // If the error container is already in the document do nothing
        if (!errorContainer.inDoc()) {

            // Attach click on resend button event
            errorContainer.one('.resend-button').on('click', function (event) {
                event.preventDefault();

                // Prevent user to click on already activated button more than once
                if (!resendButton.hasClass('activated')) {
                    // Add activated class to the resend button
                    resendButton.addClass('activated');
                    // Fire click event
                    instance.fire('resendButtonClick');
                }
            });

            // Remove event should be animated
            if (animated) {
                // Create an instance of animation
                animation = new Y.Anim({
                    node: errorContainer,
                    duration: 0.3,
                    from: {opacity: 0},
                    to: {opacity: 1}
                });

                // Opacity needs to be set to zero otherwise there will
                // be a weird blink effect
                errorContainer.setStyle('opacity', 0);

                // Add error to the container
                container.append(errorContainer);

                // Run the animation
                animation.run();
            }
            // Don't animate the remove event
            else {
                // Simply add it to the container
                container.append(errorContainer);
            }
        }

        // It is possible that resend button was clicked thus it was transformed to the activated state.
        // Remove the activated class so it can be the resend button again.
        errorContainer.one('.resend-button').removeClass('activated');
    },

    /**
     * Hides create error message
     *
     * @param animated true if the event should be animated
     */
    hideErrorMessage: function (animated) {
        // Vars
        var errorContainer = this.get('errorContainer'),
            animation;

        // Hide the error message only if the error container is in DOM
        if (errorContainer.inDoc()) {

            // Animate the event
            if (animated) {

                // Create an instance of animation
                animation = new Y.Anim({
                    node: errorContainer,
                    duration: 0.3,
                    from: {opacity: 1},
                    to: {opacity: 0}
                });

                // Listen to the end of the animation
                animation.on('end', function () {
                    // Remove the error node from DOM
                    animation.get('node').remove();
                });

                // Run the animation
                animation.run();
            }
            // Don't animate the event
            else {
                // Simply remove the container from DOM
                errorContainer.remove();
            }
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container attached to error message
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Error container that holds error content
         *
         * {Node}
         */
        errorContainer: {
            valueFn: function () {
                // Vars
                var errorMessage = this.get('errorMessage');
                // Create from template
                return Y.Node.create(Y.Lang.sub(this.errorTemplate, {
                    errorMessage: errorMessage
                }));
            }
        },

        /**
         * Resend button node
         *
         * {Node}
         */
        resendButton: {
            valueFn: function () {
                return this.get('errorContainer').one('.resend-button');
            }
        },

        /**
         * Error message
         *
         * {string}
         */
        errorMessage: {
            value: null // to be set
        }
    }
});


