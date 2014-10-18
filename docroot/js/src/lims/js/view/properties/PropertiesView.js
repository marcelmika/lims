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
     * Returns true if the properties view is opened
     *
     * {boolean}
     */
    isOpened: function () {
        return this.get('settingsContainer').hasClass('opened');
    },

    /**
     * Attach event to elements
     * @private
     */
    _attachEvents: function () {
        // Vars
        var openButton = this.get('openButton'),
            buddyListStrategy = this.get('buddyListStrategy'),
            buddyListSocialRelations = this.get('buddyListSocialRelations'),
            buddyListIgnoreDefaultUser = this.get('buddyListIgnoreDefaultUser'),
            buddyListIgnoreDeactivatedUser = this.get('buddyListIgnoreDeactivatedUser'),
            buddyListMaxBuddies = this.get('buddyListMaxBuddies'),
            buddyListMaxSearch = this.get('buddyListMaxSearch'),
            conversationListMaxMessages = this.get('conversationListMaxMessages'),
            buddyListSiteExcludes = this.get('buddyListSiteExcludes'),
            buddyListGroupExcludes = this.get('buddyListGroupExcludes');

        // Local events
        openButton.on('click', this._onOpenButtonClick, this);
        buddyListStrategy.on('choiceClick', this._onBuddyListStrategySelected, this);
        buddyListSocialRelations.on('choiceClick', this._onBuddyListSocialRelationsSelected, this);
        buddyListIgnoreDefaultUser.on('switchClick', this._onBuddyListIgnoreDefaultUserClick, this);
        buddyListIgnoreDeactivatedUser.on('switchClick', this._onBuddyListIgnoreDeactivatedUserClick, this);
        buddyListMaxBuddies.on('sliderUpdate', this._onBuddyListMaxBuddiesUpdate, this);
        buddyListMaxSearch.on('sliderUpdate', this._onBuddyListMaxSearchUpdate, this);
        conversationListMaxMessages.on('sliderUpdate', this._onConversationListMaxMessagesUpdate, this);
        buddyListSiteExcludes.on('inputUpdate', this._onBuddyListSiteExcludesUpdate, this);
        buddyListGroupExcludes.on('inputUpdate', this._onBuddyListGroupExcludesUpdate, this);
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
                buddyListSocialRelations.reset();
                buddyListSocialRelations.selectChoices(preSelectedChoices);
            }
            // Re-enable the view so the user can interact with it again
            buddyListSocialRelations.enable();
        });
    },

    /**
     * Called when the user click on the buddy list ignore default user switch
     *
     * @private
     */
    _onBuddyListIgnoreDefaultUserClick: function () {
        // Vars
        var switchView = this.get('buddyListIgnoreDefaultUser'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListIgnoreDefaultUser: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user click on the buddy list ignore deactivated user switch
     *
     * @private
     */
    _onBuddyListIgnoreDeactivatedUserClick: function () {
        // Vars
        var switchView = this.get('buddyListIgnoreDeactivatedUser'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListIgnoreDeactivatedUser: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user updates buddy list max buddies property
     *
     * @param event
     * @private
     */
    _onBuddyListMaxBuddiesUpdate: function (event) {
        // Vars
        var buddyListMaxBuddies = this.get('buddyListMaxBuddies'),
            preValue = event.preValue,          // Previously selected value
            postValue = event.postValue,        // Current value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListMaxBuddies: postValue
        });

        // Disable view
        buddyListMaxBuddies.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListMaxBuddies.setValue(preValue);
            }
            // Re-enable the view so the user can interact with it again
            buddyListMaxBuddies.enable();
        });
    },

    /**
     * Called when the user updates buddy list max search property
     *
     * @param event
     * @private
     */
    _onBuddyListMaxSearchUpdate: function (event) {
        // Vars
        var buddyListMaxSearch = this.get('buddyListMaxSearch'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Current value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListMaxSearch: postValue
        });

        // Disable view
        buddyListMaxSearch.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListMaxSearch.setValue(preValue);
            }
            // Re-enable the view so the user can interact with it again
            buddyListMaxSearch.enable();
        });
    },


    /**
     * Called when the user updates conversation list max messages property
     *
     * @param event
     * @private
     */
    _onConversationListMaxMessagesUpdate: function (event) {
        // Vars
        var conversationListMaxMessages = this.get('conversationListMaxMessages'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Current value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            conversationListMaxMessages: postValue
        });

        // Disable view
        conversationListMaxMessages.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                conversationListMaxMessages.setValue(preValue);
            }
            // Re-enable the view so the user can interact with it again
            conversationListMaxMessages.enable();
        });
    },

    /**
     * Called when the user updates buddy list sites excludes property
     *
     * @param event
     * @private
     */
    _onBuddyListSiteExcludesUpdate: function (event) {
        // Vars
        var buddyListSiteExcludes = this.get('buddyListSiteExcludes'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListSiteExcludes: postValue
        });

        // Disable view
        buddyListSiteExcludes.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListSiteExcludes.setValues(preValue);
            }
            // Re-enable the view so the user can interact with it again
            buddyListSiteExcludes.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            buddyListSiteExcludes.focus();
        });
    },

    /**
     * Called when the user updates buddy list group excludes property
     *
     * @param event
     * @private
     */
    _onBuddyListGroupExcludesUpdate: function (event) {
        // Vars
        var buddyListGroupExcludes = this.get('buddyListGroupExcludes'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListGroupExcludes: postValue
        });

        // Disable view
        buddyListGroupExcludes.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListGroupExcludes.setValues(preValue);
            }
            // Re-enable the view so the user can interact with it again
            buddyListGroupExcludes.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            buddyListGroupExcludes.focus();
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
         * Help button node
         *
         * {Node}
         */
        helpButton: {
            valueFn: function () {
                return this.get('container').one('.help');
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
                    helpButton = this.get('helpButton'),
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
                        width: 440,
                        height: 300,
                        opacity: 1
                    },
                    easing: 'backOut'
                });

                // Check if the animation should be reversed
                if (settingsContainer.hasClass('opened')) {
                    // Reverse the animation
                    animation.set('reverse', true);
                }

                // On animation start
                animation.before('start', function () {
                    // Hide the open button, we don't want the user to interact now
                    openButton.setStyle('opacity', 0);
                    // Hide buttons
                    openButton.hide();
                    helpButton.hide();

                }, this);

                // On animation end
                animation.after('end', function () {

                    // Closing
                    if (animation.get('reverse')) {
                        // Update open button text
                        openButton.set('innerHTML', Y.LIMS.Core.i18n.values.adminAreaOpen);
                        // Settings container doesn't need the closed class anymore
                        settingsContainer.removeClass('opened');
                        settingsContainer.addClass('closed');
                        // Fire the event
                        this.fire('propertiesClosed');
                    }
                    // Opening
                    else {
                        // Update open button text
                        openButton.set('innerHTML', Y.LIMS.Core.i18n.values.adminAreaClose);
                        // Settings container doesn't need the closed class anymore
                        settingsContainer.removeClass('closed');
                        settingsContainer.addClass('opened');
                        // Hide help button
                        helpButton.show();
                        // Fire the event
                        this.fire('propertiesOpened');
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
         * View for buddy list ignore default user
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        buddyListIgnoreDefaultUser: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-ignore-default-user');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list ignore deactivated user
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        buddyListIgnoreDeactivatedUser: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-ignore-deactivated-user');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
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
                    value: valueContainer.get('innerHTML')
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
                    value: valueContainer.get('innerHTML')
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
                    max: 200,
                    value: valueContainer.get('innerHTML')
                });
            }
        },

        /**
         * View for excluded sites
         *
         * {Y.LIMS.View.TokenInputElementView}
         */
        buddyListSiteExcludes: {
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
         * {Y.LIMS.View.TokenInputElementView}
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
