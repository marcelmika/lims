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

Y.LIMS.View.SettingsView = Y.Base.create('settingsView', Y.View, [], {

    /**
     * Called before initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
        // Bind settings
        this._bindSettings();
    },

    /**
     * Attach event to elements
     * @private
     */
    _attachEvents: function () {
        // Vars
        var soundSwitch = this.get('soundSwitch');

        // Local events
        soundSwitch.on('switchClick', this._onSoundSwitchClick, this);
    },

    /**
     * Binds settings from rendered HTML
     *
     * @private
     */
    _bindSettings: function () {
        // Vars
        var model = this.get('model'),
            soundSwitch = this.get('soundSwitch');

        // Set settings
        model.set('isMute', !soundSwitch.isOn());
    },

    /**
     * Called when the user click on the switch
     *
     * @private
     */
    _onSoundSwitchClick: function () {
        var soundSwitch = this.get('soundSwitch'),
            model = this.get('model');

        // Update model
        model.set('isMute', !soundSwitch.isOn());

        // Disable view
        soundSwitch.disable();

        // Save model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                soundSwitch.toggle();
            }
            // Re-enable the view so the user can interact with it again
            soundSwitch.enable();
        });
    }

}, {

    ATTRS: {

        /**
         * Container attached to view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Model attache to view
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * View for sound switch
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        soundSwitch: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.play-sound');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        }
    }

});
