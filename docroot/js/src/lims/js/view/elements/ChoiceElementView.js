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
 * Represents a view that holds buttons with choice
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
        buttonGroup.removeClass('disabled');
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
        buttonGroup.addClass('disabled');
    },

    /**
     * Select particular choice. Returns true if the choice was selected
     *
     * @param choice {string}
     * @return {boolean} true if the choice was selected
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
            return this._activateChoice(choice);
        }
        // Inclusive
        else {
            // Already active
            if (this._isActiveChoice(choice)) {
                // Deactivate
                return this._deactivateChoice(choice);
            }
            // Not active yet
            else {
                // Activate
                return this._activateChoice(choice);
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
     * Deselects particular choice. Returns true if the choice was deselected.
     *
     * @param choice {string}
     * @return {boolean}
     */
    deselectChoice: function (choice) {
        return this._deactivateChoice(choice);
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
     * @return {boolean} true if the choices was activated
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

            // Return success
            return true;
        }
        // No choice node was found
        else {
            // Return failure
            return false;
        }
    },

    /**
     * Deactivates the choice
     *
     * @param choice {string}
     * @return {boolean} true if the choice was deactivated
     * @private
     */
    _deactivateChoice: function (choice) {
        // Vars
        var selectedChoices = this.get('selectedChoices'),
            isExclusive = this.get('isExclusive'),
            choiceNode = this._getChoiceNode(choice),
            index;

        // No such choice node means failure
        if (!choiceNode) {
            return false;
        }

        // Choice might be exclusive, thus there is no need to validate the count
        // However, if it's not exclusive we need to validate the min threshold
        if (isExclusive || this._checkMinThreshold()) {

            // Remove deactivated choice from the selected choices list
            for (index = selectedChoices.length; index >= 0; index--) {
                if (selectedChoices[index] === choice) {
                    selectedChoices.splice(index, 1);
                }
            }

            // Remove active class thus the button will no longer be active
            choiceNode.removeClass('active');

            // Return success
            return true;
        }
        // No choice node was found or choice cannot be performed
        else {
            // Return failure
            return false;
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

        // If there is no such choice node it cannot be active
        if (!choiceNode) {
            return false;
        }

        // Active choice has active class
        return choiceNode.hasClass('active');
    },

    /**
     * Returns true if any choice can be either selected or deselected.
     * Minimal count of selected choices can be set in minChoices attribute.
     *
     * @private
     */
    _checkMinThreshold: function () {
        // Vars
        var selectedChoices = this.get('selectedChoices'),
            minChoices = this.get('minChoices');

        return selectedChoices.length > minChoices;
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
            preSelectedChoices = this.get('selectedChoices').slice(0),  // Get a copy of selected choices
            choice = choiceNode.getAttribute('data-choice'),            // Get choice from choice node
            isDisabled = this.get('buttonGroup').hasClass('disabled');  // Check if node is disabled

        // Don't do anything if the choice is disabled
        if (!isDisabled) {

            // Select the choice
            if (this.selectChoice(choice)) {

                // Fire event
                this.fire('choiceClick', {
                    choice: choice,
                    preSelectedChoices: preSelectedChoices,             // Previously selected choices
                    postSelectedChoices: this.get('selectedChoices')    // Currently selected choices
                });
            }
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
        },

        /**
         * Number of minimal choices selected at once
         */
        minChoices: {
            value: 1 // at least one choice must be selected by default
        }

    }
});
