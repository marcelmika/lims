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
 * Slider Element View
 *
 * Represents a view that holds slider element
 */
Y.namespace('LIMS.View');

Y.LIMS.View.SliderElementView = Y.Base.create('sliderElementView', Y.View, [], {

    /**
     * Called on initialization
     */
    initializer: function () {
        // Render
        this.render();
        // Attach events
        this._attachEvents();
    },

    /**
     * Renders view
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            slider = this.get('slider');

        // Render slider
        slider.render(container);
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var slider = this.get('slider');

        // Local events
        slider.after("valueChange", this._onSliderValueChange, this);
    },

    /**
     * Called when the slider changes value
     *
     * @param event
     * @private
     */
    _onSliderValueChange: function (event) {
        // Vars
        var valueContainer = this.get('valueContainer');

        console.log(event);

        valueContainer.set('innerHTML', event.newVal);
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
         * Container node with the values
         *
         * {Node}
         */
        valueContainer: {
            value: null // to be set
        },

        /**
         * Slider element
         *
         * {Y.Slider}
         */
        slider: {
            valueFn: function () {
                // Vars
                var min = this.get('min'),
                    max = this.get('max'),
                    value = this.get('value');

                // Create an instance of slider
                return new Y.Slider({
                    min: min,
                    max: max,
                    value: value,
                    length: '200px',
                    thumbUrl: '/lims-portlet/images/slider-thumb@2x.png'
                });
            }
        },

        /**
         * Min value
         *
         * {integer}
         */
        min: {
            value: 0 // default value
        },

        /**
         * Max value
         *
         * {integer}
         */
        max: {
            value: 10 // default value
        },

        /**
         * Starting value
         *
         * {integer}
         */
        value: {
            value: 5 // default
        }
    }

});
