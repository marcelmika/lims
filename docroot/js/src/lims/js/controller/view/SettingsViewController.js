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
 * Settings View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SettingsViewController = Y.Base.create('settingsViewController',
    Y.LIMS.Core.ViewController, [Y.LIMS.Controller.ControllerExtension], {

        /**
         *  The initializer runs when a Group View Controller instance is created.
         */
        initializer: function () {
            // This needs to be called in each view controller
            this.setup(this.get('container'), this.get('controllerId'));
            // Check if the checkboxes need to support IE
            this._supportCheckboxes();
        },

        /**
         * Panel Did Load is called when the panel is attached to the controller
         */
        onPanelDidLoad: function () {
            // Reads current settings from rendered settings view
            this._bindSettings();
            // Events
            this._attachEvents();
        },

        /**
         * Attaches events to DOM elements from container
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var soundCheckbox = this.get('soundCheckbox'),
                adminProperties = this.get('adminProperties');

            // Local events
            soundCheckbox.on('click', this._onSoundCheckboxUpdated, this);
            adminProperties.on('propertiesOpened', this._onAdminPropertiesOpened, this);
            adminProperties.on('propertiesClosed', this._onAdminPropertiesClosed, this);
        },

        /**
         * Takes all checkboxes and checks if they are supported
         *
         * @private
         */
        _supportCheckboxes: function () {
            // Vars
            var checkboxes = this.get('checkboxes');

            // Remove switch classes from checkboxes if the portlet
            // is in the IE support mode
            // TODO: move to checkbox element view
            if (this.hasIESupport()) {
                checkboxes.each(function (checkbox) {
                    checkbox.ancestor().removeClass('switch');
                });
            }
        },

        /**
         * Reads settings from rendered dom
         *
         * @private
         */
        _bindSettings: function () {
            // Vars
            var model = this.get('model'),
                adminProperties = this.get('adminProperties'),
                isMute = this.get('soundCheckbox').get('checked') ? false : true;

            // Set to model
            model.set('isMute', isMute);
            model.set('isAdminAreaOpened', adminProperties.isOpened());
        },

        /**
         * Sound checkbox changed
         *
         * @private
         */
        _onSoundCheckboxUpdated: function () {
            var model = this.get('model'),
                isMute = this.get('soundCheckbox').get('checked') ? false : true;
            // Update model
            model.set('isMute', isMute).save();
        },

        /**
         * Called when the admin properties view is opened
         *
         * @private
         */
        _onAdminPropertiesOpened: function () {
            // Vars
            var model = this.get('model');

            // Set the model value
            model.set('isAdminAreaOpened', true);
            // And save it
            model.save();

            console.log('opened');
        },

        /**
         * Called when the admin properties view is closed
         *
         * @private
         */
        _onAdminPropertiesClosed: function () {
            // Vars
            var model = this.get('model');

            // Set the model value
            model.set('isAdminAreaOpened', false);
            // And save it
            model.save();

            console.log('closed');
        }

    }, {

        // Specify attributes and static properties for your View here.
        ATTRS: {

            // Id of the controller
            /**
             * Id of the controller
             *
             * {string}
             */
            controllerId: {
                value: "settings"
            },

            /**
             * Container attached to controller
             *
             * {Node}
             */
            container: {
                value: null // to be set
            },

            /**
             * Model attached to controller
             *
             * {Y.LIMS.Model.SettingsModel}
             */
            model: {
                value: null // to be set
            },

            /**
             * Holds a view related to the admin properties
             *
             * {Y.LIMS.View.PropertiesView}
             */
            adminProperties: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container').one('.admin-area');

                    return new Y.LIMS.View.PropertiesView({
                        container: container
                    });
                }
            },

            /**
             * Holds all checkboxes
             *
             * []
             */
            checkboxes: {
                getter: function () {
                    return this.get('container').all('input[type=checkbox]');
                }
            },


            /**
             * Sound check box node
             *
             * {Node}
             */
            soundCheckbox: {
                valueFn: function () {
                    return this.get('container').one(".play-sound-checkbox");
                }
            }
        }
    });
