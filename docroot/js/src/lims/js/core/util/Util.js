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

Y.namespace('LIMS.Core');

/**
 * Utility functions
 */
var Util = {

    /**
     * Decodes html entities
     *
     * @param html
     * @return {string}
     */
    htmlDecode: function (html) {
        // Vars
        var div = Y.Node.create("<div/>");
        // Set the encoded html
        div.set('innerHTML', html);
        // Return decoded html
        return div.get('innerHTML');
    },

    /**
     * Returns first character of the text
     *
     * @param text
     * @return {string}
     */
    firstCharacter: function (text) {
        // Vars
        var decoded = this.htmlDecode(Y.Lang.trim(text));

        return decoded && decoded.length > 0 ? decoded.charAt(0) : '';
    },

    /**
     * Returns true if node is hidden
     *
     * @param node {Node}
     * @return {boolean}
     */
    isHidden: function (node) {
        return node.hasClass('hide');
    },

    /**
     * Shows node
     *
     * @param node {Node}
     */
    show: function (node) {
        node.removeClass('hide');
    },

    /**
     * Hides node
     *
     * @param node {Node}
     */
    hide: function (node) {
        node.addClass('hide');
    }

};

/**
 * Utility functions
 *
 * @example Y.LIMS.Core.Util.htmlDecode(text)
 */
Y.LIMS.Core.Util = Util;