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
     * Attach event to elements
     * @private
     */
    _attachEvents: function () {
        // Vars
        var buddyListStrategy = this.get('buddyListStrategy'),
            buddyListSocialRelations = this.get('buddyListSocialRelations');

        // Local events
        buddyListStrategy.on('choiceClick', this._onBuddyListStrategySelected, this);
        buddyListSocialRelations.on('choiceClick', this._onBuddyListSocialRelationsSelected, this);
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
         * Container node for buddy list strategy
         *
         * {Node}
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
         * Container node for buddy list social relations
         *
         * {Node}
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
        }
    }
});
