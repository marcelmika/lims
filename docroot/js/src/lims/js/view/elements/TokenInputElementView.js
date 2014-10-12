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

        this.bind();
    },

    bind: function () {
        // Vars
        var inputNode = this.get('inputNode');

        // Tokenize
        inputNode.plug(Y.Plugin.TokenInput, {
            removeButton: true
        });
    },


    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {

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
        }
    }
});
