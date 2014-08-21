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
 * Portrait
 *
 * Represent buddy's portrait
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PortraitView = Y.Base.create('portraitView', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<img alt="" class="portrait"/>',

    render: function () {
        // Vars
        var container = this.get("container"),
            settings = new Y.LIMS.Core.Settings();

        // Set portrait image src attribute based on the screen name
        container.set('src', settings.getPortraitUrl(this.get("screenName")));

        return this;
    }
}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        screenName: {
            value: null
        }
    }
});
