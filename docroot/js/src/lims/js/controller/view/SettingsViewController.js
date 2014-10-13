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
        },

        /**
         * Panel Did Load is called when the panel is attached to the controller
         */
        onPanelDidLoad: function () {
            // Attach Events
            this._attachEvents();
            // Bind settings
            this._bindSettings();
        },

        /**
         * Attaches events to DOM elements from container
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var adminProperties = this.get('adminProperties');

            // Local events
            if (adminProperties) {
                adminProperties.on('propertiesOpened', this._onAdminPropertiesOpened, this);
                adminProperties.on('propertiesClosed', this._onAdminPropertiesClosed, this);
            }
        },

        /**
         * Binds settings from rendered HTML
         *
         * @private
         */
        _bindSettings: function () {
            // Vars
            var model = this.get('model'),
                adminProperties = this.get('adminProperties');

            // Set settings
            if (adminProperties) {
                model.set('isAdminAreaOpened', adminProperties.isOpened());
            }
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
        }

    }, {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        ATTRS: {

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
             * Holds a view related to the admin properties. If the user
             * is not an admin null is returned.
             *
             * {Y.LIMS.View.PropertiesView|null}
             */
            adminProperties: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container').one('.admin-area');

                    if (!container) {
                        return null;
                    }

                    return new Y.LIMS.View.PropertiesView({
                        container: container
                    });
                }
            },

            /**
             * Holds a view related to the user settings
             *
             * {Y.LIMS.View.SettingsView}
             */
            userSettings: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container').one('.user-settings'),
                        model = this.get('model');

                    return new Y.LIMS.View.SettingsView({
                        container: container,
                        model: model
                    });
                }
            }
        }
    });
