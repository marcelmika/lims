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
 * Admin Properties View
 *
 * Handles properties settings
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PropertiesView = Y.Base.create('propertiesView', Y.View, [], {

    /**
     * Called before initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Opens or closes properties view
     */
    toggleView: function () {
        // Vars
        var openAnimation = this.get('openAnimation');

        // Run the animation
        openAnimation.run();
    },

    /**
     * Attach event to elements
     * @private
     */
    _attachEvents: function () {
        // Vars
        var openButton = this.get('openButton'),
            buddyListStrategy = this.get('buddyListStrategy'),
            buddyListSocialRelations = this.get('buddyListSocialRelations');

        // Local events
        openButton.on('click', this._onOpenButtonClick, this);
        buddyListStrategy.on('choiceClick', this._onBuddyListStrategySelected, this);
        buddyListSocialRelations.on('choiceClick', this._onBuddyListSocialRelationsSelected, this);
    },

    /**
     * Called when the open button is clicked
     *
     * @private
     */
    _onOpenButtonClick: function () {
        // Open properties view
        this.toggleView();
    },

    /**
     * Called when user selects one of the buddy list strategies
     *
     * @param event
     * @private
     */
    _onBuddyListStrategySelected: function (event) {
        // Vars
        var choice = event.choice,                                           // Choice id is passed in event
            buddyListStrategy = this.get('buddyListStrategy'),               // Get the buddy list strategy view
            buddyListSocialRelations = this.get('buddyListSocialRelations'), // Social relations view
            preSelectedChoices = event.preSelectedChoices,                   // Choices before selection
            model;                                                           // Properties model

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListStrategy: choice
        });

        // Disable view
        buddyListStrategy.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListStrategy.selectChoices(preSelectedChoices);
            } else {
                // If the list strategy has something to do with social relations
                // enable the social relations view
                if (choice === 'SOCIAL' || choice === 'SITES_AND_SOCIAL') {
                    buddyListSocialRelations.enable();
                } else {
                    buddyListSocialRelations.disable();
                }
            }

            // Re-enable the view so the user can interact with it again
            buddyListStrategy.enable();
        });
    },

    /**
     * Called when user selects one of the buddy list social relations
     *
     * @param event
     * @private
     */
    _onBuddyListSocialRelationsSelected: function (event) {
        // Vars
        var buddyListSocialRelations = this.get('buddyListSocialRelations'),
            preSelectedChoices = event.preSelectedChoices,
            postSelectedChoices = event.postSelectedChoices,
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListSocialRelations: postSelectedChoices
        });

        // Disable view
        buddyListSocialRelations.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListSocialRelations.selectChoices(preSelectedChoices);
            }
            // Re-enable the view so the user can interact with it again
            buddyListSocialRelations.enable();
        });
    }


}, {

    ATTRS: {

        /**
         * Container node attached to the view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Settings container
         *
         * {Node}
         */
        settingsContainer: {
            valueFn: function () {
                return this.get('container').one('.settings');
            }
        },

        /**
         * Container node with the open/close button
         *
         * {Node}
         */
        openButton: {
            valueFn: function () {
                return this.get('container').one('.open-button');
            }
        },

        /**
         * Returns animation that will open or close the whole view
         *
         * {Y.Anim}
         */
        openAnimation: {
            valueFn: function () {
                // Vars
                var openButtonAnimation = this.get('openButtonAnimation'),
                    settingsContainer = this.get('settingsContainer'),
                    openButton = this.get('openButton'),
                    animation;

                // Create the opening animation
                animation = new Y.Anim({
                    node: settingsContainer,
                    duration: 0.8,
                    from: {
                        width: 250,
                        height: 0,
                        opacity: 0
                    },
                    to: {
                        width: 420,
                        height: 300,
                        opacity: 1
                    },
                    easing: 'backOut'
                });


                // On animation start
                animation.before('start', function () {
                    // Hide the open button, we don't want the user to interact now
                    openButton.setStyle('opacity', 0);
                    openButton.hide();

                }, this);

                // On animation end
                animation.after('end', function () {

                    // Settings container doesn't need the closed class anymore
                    settingsContainer.removeClass('closed');

                    // If we are closing set text to open
                    // If we are opening set text to close
                    if (animation.get('reverse')) {
                        // Update open button text
                        openButton.set('innerHTML', 'Open'); // TODO: i18n
                    } else {
                        // Update open button text
                        openButton.set('innerHTML', 'Close'); // TODO: i18n
                    }

                    // Show the button
                    openButton.show();
                    // And run the animation
                    openButtonAnimation.run();

                    // Toggle the animation direction
                    animation.set('reverse', !animation.get('reverse'));

                }, this);

                return animation;
            }
        },

        /**
         * Return open button animation
         *
         * {Y.Anim}
         */
        openButtonAnimation: {
            valueFn: function () {
                // Vars
                var openButton = this.get('openButton');

                // Create button animation
                return new Y.Anim({
                    node: openButton,
                    duration: 0.5,
                    from: {opacity: 0},
                    to: {opacity: 1}
                });
            }
        },

        /**
         * View for buddy list strategy
         *
         * {Y.LIMS.View.ChoiceElementView}
         */
        buddyListStrategy: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-strategy');

                return new Y.LIMS.View.ChoiceElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list social relations
         *
         * {Y.LIMS.View.ChoiceElementView}
         */
        buddyListSocialRelations: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-social-relations');

                return new Y.LIMS.View.ChoiceElementView({
                    container: container,
                    isExclusive: false
                });
            }
        },

        /**
         * View for buddy list max buddies
         *
         * {Y.LIMS.View.SliderElementView}
         */
        buddyListMaxBuddies: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-max-buddies .slider'),
                    valueContainer = this.get('container').one('.buddy-list-max-buddies .value');

                return new Y.LIMS.View.SliderElementView({
                    container: container,
                    valueContainer: valueContainer,
                    min: 10,
                    max: 500,
                    value: 200 // TODO: Parse
                });
            }
        },

        /**
         * View for buddy list max search
         *
         * {Y.LIMS.View.SliderElementView}
         */
        buddyListMaxSearch: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-max-search .slider'),
                    valueContainer = this.get('container').one('.buddy-list-max-search .value');

                return new Y.LIMS.View.SliderElementView({
                    container: container,
                    valueContainer: valueContainer,
                    min: 5,
                    max: 50,
                    value: 10 // TODO: Parse
                });
            }
        },

        /**
         * View for conversation list max messages
         *
         * {Y.LIMS.View.SliderElementView}
         */
        conversationListMaxMessages: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.conversation-list-max-messages .slider'),
                    valueContainer = this.get('container').one('.conversation-list-max-messages .value');

                return new Y.LIMS.View.SliderElementView({
                    container: container,
                    valueContainer: valueContainer,
                    min: 10,
                    max: 500,
                    value: 100 // TODO: Parse
                });
            }
        },

        /**
         * View for excluded sites
         *
         * {Y.LIMS.Model.View.TokenInputElementView}
         */
        buddyListSitesExcludes: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-site-excludes');

                return new Y.LIMS.View.TokenInputElementView({
                    container: container
                });
            }
        },

        /**
         * View for excluded groups
         *
         * {Y.LIMS.Model.View.TokenInputElementView}
         */
        buddyListGroupExcludes: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-group-excludes');

                return new Y.LIMS.View.TokenInputElementView({
                    container: container
                });
            }
        }
    }
});
