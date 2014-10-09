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
 * Choice Element View
 *
 * Represents a view that holds button with choice
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ChoiceElementView = Y.Base.create('choiceElementView', Y.View, [], {

    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Enables view
     */
    enable: function () {
        // Vars
        var buttonGroup = this.get('buttonGroup');

        // Make buttons visible again
        buttonGroup.setStyle('opacity', 1);

        // Remove disabled class
        buttonGroup.all('.button').removeClass('disabled');
    },

    /**
     * Disables view
     */
    disable: function () {
        // Vars
        var buttonGroup = this.get('buttonGroup');

        // Dim buttons
        buttonGroup.setStyle('opacity', 0.5);

        // Add disabled class to buttons
        buttonGroup.all('.button').addClass('disabled');
    },

    /**
     * Select particular choice
     *
     * @param choice
     */
    selectChoice: function (choice) {
        // Vars
        var container = this.get('container'),
            isExclusive = this.get('isExclusive'),
            instance = this;

        // Exclusive
        if (isExclusive) {

            // Deactivate other choices if the selection is exclusive
            container.all('.button').each(function (button) {
                instance._deactivateChoice(button.getAttribute('data-choice'));
            });

            // Activate the choice
            this._activateChoice(choice);
        }
        // Inclusive
        else {
            // Already active
            if (this._isActiveChoice(choice)) {
                // Deactivate
                this._deactivateChoice(choice);
            }
            // Not active yet
            else {
                // Activate
                this._activateChoice(choice);
            }
        }
    },

    /**
     * Selects choices given in an array
     *
     * @param choices []
     */
    selectChoices: function (choices) {
        // Vars
        var index;

        // Select all choices given in the array
        for (index = 0; index < choices.length; index++) {
            this.selectChoice(choices[index]);
        }
    },

    /**
     * Deselects particular choice
     *
     * @param choice {string}
     */
    deselectChoice: function (choice) {
        this._deactivateChoice(choice);
    },

    /**
     * Attach events to dom elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var container = this.get('container');

        // Local events
        container.delegate('click', this._onChoiceClick, 'label', this);
    },

    /**
     * Activates the choice
     *
     * @param choice {string}
     * @private
     */
    _activateChoice: function (choice) {
        // Vars
        var selectedChoices = this.get('selectedChoices'),
            choiceNode = this._getChoiceNode(choice);

        if (choiceNode) {
            // Remember selected choices
            selectedChoices.push(choiceNode.getAttribute('data-choice'));

            // Add active class so the button will be active
            choiceNode.addClass('active');
        }
    },

    /**
     * Deactivates the choice
     *
     * @param choice {string}
     * @private
     */
    _deactivateChoice: function (choice) {
        // Vars
        var selectedChoices = this.get('selectedChoices'),
            choiceNode = this._getChoiceNode(choice),
            index;

        if (choiceNode) {

            // Remove deactivated choice from the selected choices list
            for (index = selectedChoices.length; index >= 0; index--) {
                if (selectedChoices[index] === choice) {
                    selectedChoices.splice(index - 1, 1);
                }
            }

            // Remove active class thus the button will no longer be active
            choiceNode.removeClass('active');
        }
    },

    /**
     * Returns true if the choice is active
     *
     * @param choice {Node}
     * @returns {boolean}
     * @private
     */
    _isActiveChoice: function (choice) {
        // Vars
        var choiceNode = this._getChoiceNode(choice);

        if (choiceNode) {
            return choice.hasClass('active');
        } else {
            return false;
        }
    },


    /**
     * Called when the user clicks on one of the choices
     *
     * @param event
     * @private
     */
    _onChoiceClick: function (event) {

        // Vars
        var choiceNode = event.currentTarget,                           // Choice a is a target in event
            selectedChoices = this.get('selectedChoices').slice(0),     // Get a copy of selected choices
            choice = choiceNode.getAttribute('data-choice'),            // Get choice from choice node
            isDisabled = choiceNode.hasClass('disabled');               // Check if node is disabled

        // Don't do anything if the choice is disabled
        if (!isDisabled) {

            // Select the choice
            this.selectChoice(choice);

            // Fire event
            this.fire('choiceClick', {
                choice: choice,
                selectedChoices: selectedChoices    // Previously selected choices
            });
        }
    },

    /**
     * Returns node related to the choice
     *
     * @param choice
     * @private
     */
    _getChoiceNode: function (choice) {
        // Vars
        var container = this.get('container'),
            choiceNode = null;

        // Search all choices
        container.all('.button').each(function (button) {

            if (button.getAttribute('data-choice') === choice) {
                choiceNode = button;
            }
        });

        return choiceNode;
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
         * Button group node
         *
         * {Node}
         */
        buttonGroup: {
            valueFn: function () {
                return this.get('container').one('.button-group');
            }
        },

        /**
         * Holds an array of selected choices IDs
         *
         * []
         */
        selectedChoices: {
            valueFn: function () {
                // Vars
                var container = this.get('container'),
                    selectedChoices = [];

                container.all('.active').each(function (choice) {
                    selectedChoices.push(choice.getAttribute('data-choice'));
                });

                return selectedChoices;
            }
        },

        /**
         * True if the choices are exclusive. In other words, if set to true only one option
         * can be selected at a time. If set to false all or nothing might be selected.
         *
         * {boolean}
         */
        isExclusive: {
            value: true // Selection is exclusive by default
        }

    }
});
