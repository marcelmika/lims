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
 * Token Input Element View
 *
 * Represents a view that holds input text field that support tokens
 */
Y.namespace('LIMS.View');

Y.LIMS.View.TokenInputElementView = Y.Base.create('tokenInputElementView', Y.View, [], {


    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
        // Bind
        this._bind();
    },

    /**
     * Sets the token input values
     *
     * @param values
     */
    setValues: function (values) {
        // Vars
        var inputNode = this.get('inputNode');

        // Set tokens to token input
        inputNode.tokenInput.set('tokens', values);
    },

    /**
     * Enables view
     */
    enable: function () {
        // Vars
        var container = this.get('container');

        // Enable input
        container.removeClass('disabled');
        container.all('input').set('disabled', null);

        // Set the flag
        this.set('isDisabled', false);
    },

    /**
     * Disables view
     */
    disable: function () {
        // Vars
        var container = this.get('container');

        // Disable input
        container.addClass('disabled');
        container.all('input').set('disabled', 'disabled');

        // Set the flag
        this.set('isDisabled', true);
    },

    /**
     * Sets focus to token input
     */
    focus: function () {
        // Vars
        var tokenInputNode = this.get('tokenInputNode');

        // Set focus to the next token
        tokenInputNode.focus();
    },

    /**
     * Takes the focus away from token input
     */
    blur: function () {
        // Vars
        var tokenInputNode = this.get('tokenInputNode');

        // Take th focus away
        tokenInputNode.blur();
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var inputNode = this.get('inputNode');

        // Local events
        inputNode.tokenInput.on('tokensChange', this._onTokensChange, this);
    },

    /**
     * Binds values from rendered HTML
     *
     * @private
     */
    _bind: function () {
        // Vars
        var container = this.get('container');

        // If the rendered token input container already has
        // a disabled class, disable the whole view
        if (container.hasClass('disabled')) {
            this.disable();
        }
    },

    /**
     * Called when tokens are changed
     *
     * @param event
     * @private
     */
    _onTokensChange: function (event) {
        // Vars
        var preValue = this.get('preValue');

        // There is no need to do anything since nothing has changed
        if (preValue === event.newVal) {
            return;
        }

        // Set the previous value
        this.set('preValue', event.prevVal);
        // Set the current value
        this.set('value', event.newVal);

        // Fire an event
        this.fire('inputUpdate', {
            preValue: this.get('preValue'),
            postValue: event.newVal
        }, this);
    }

}, {

    ATTRS: {

        /**
         * Container attached to the view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Input node
         *
         * {Node}
         */
        inputNode: {
            valueFn: function () {
                // Vars
                var inputNode = this.get('container').one('input');

                // Tokenize
                inputNode.plug(Y.Plugin.TokenInput, {
                    removeButton: true
                });

                return inputNode;
            }
        },

        /**
         * Input node used for tokens
         *
         * {Node}
         */
        tokenInputNode: {
            valueFn: function () {
                return this.get('inputNode').tokenInput.get('inputNode');
            }
        },

        /**
         * Current value
         *
         * []
         */
        value: {
            value: [] // to be set
        },

        /**
         * Previously set value
         *
         * []
         */
        preValue: {
            value: [] // to be set
        },

        /**
         * True if the token input is disabled
         *
         * {boolean}
         */
        isDisabled: {
            value: false // default value
        }
    }
});
