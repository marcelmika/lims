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
 * Settings
 *
 * Contains all necessary settings related to the current user session
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Properties = Y.Base.create('properties', Y.Base, [], {

    userId: null,      // This is set in main.js, to access it use Y.LIMS.Core.Properties.userId
    companyId: null,   // This is set in main.js, to access it use Y.LIMS.Core.Properties.companyId
    pathImage: null,   // This is set in main.js, to access it use Y.LIMS.Core.Properties.pathImage
    isIE: false,       // This is set in main.js, to access it use Y.LIMS.Core.Properties.isIE
    resourceURL: null,  // This is set in main.js, to access it use Y.LIMS.Core.Properties.resourceURL

    /**
     * Values are parsed from HTML in main.js.
     * Check the properties.jspf for the list of all possible properties.
     *
     * {}
     */
    values: null,

    /**
     * Called when the object is created
     */
    initializer: function () {
        // Attach to events
        this._attachEvents();
    },

    /**
     * Returns userId of the currently connected user
     *
     * @returns {int}
     */
    getCurrentUserId: function () {
        return this.get('userId');
    },

    /**
     * Returns companyId of the currently connected user
     *
     * @return {number}
     */
    getCurrentUserCompanyId: function () {
        return this.get('companyId');
    },

    /**
     * Returns true if the current user is male
     *
     * @return {boolean}
     */
    getCurrentUserMale: function () {
        return this.get('male');
    },

    /**
     * Returns portraitId of the currently connected user
     *
     * @return {number}
     */
    getCurrentUserPortraitId: function () {
        return this.get('portraitId');
    },

    /**
     * Returns portrait image token of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserPortraitImageToken: function () {
        return this.get('portraitImageToken');
    },

    /**
     * Returns portrait token of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserPortraitToken: function () {
        return this.get('portraitToken');
    },

    /**
     * Returns screenName of the currently connected user
     *
     * @returns {string}
     */
    getCurrentUserScreenName: function () {
        return this.get('screenName');
    },

    /**
     * Returns fullName of the currently connected user
     *
     * @returns {string}
     */
    getCurrentUserFullName: function () {
        return this.get('fullName');
    },


    /**
     * Returns server time offset compared to client time
     *
     * @returns {timestamp}
     */
    getServerTimeOffset: function () {
        return this.get('offset');
    },

    /**
     * Returns true if the whole chat is enabled
     *
     * @returns {boolean}
     */
    isChatEnabled: function () {
        return this.get('isChatEnabled');
    },

    /**
     * Attach dom events
     *
     * @private
     */
    _attachEvents: function () {
        // Chat enabled/disabled
        Y.on('chatEnabled', this._onChatEnabled, this);
        Y.on('chatDisabled', this._onChatDisabled, this);
    },

    /**
     * Called when the chat is enabled (turned on)
     *
     * @private
     */
    _onChatEnabled: function () {
        this.set('isChatEnabled', true);
    },

    /**
     * Called when the chat is disabled (turned off)
     *
     * @private
     */
    _onChatDisabled: function () {
        this.set('isChatEnabled', false);
    }

}, {

    ATTRS: {

        /**
         * User Id of the currently logged user
         *
         * {integer}
         */
        userId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.userId;
            }
        },

        /**
         * Company Id of the currently logged user
         *
         * {integer}
         */
        companyId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.companyId;
            }
        },

        /**
         * Set to true if the user is male
         *
         * {boolean}
         */
        male: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.male;
            }
        },

        /**
         * Portrait id of the currently logged user
         *
         * {integer}
         */
        portraitId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portraitId;
            }
        },

        /**
         * Portrait image token of the currently logged user
         *
         * {string}
         */
        portraitImageToken: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portraitImageToken;
            }
        },

        /**
         * Portrait token of the currently logged user
         *
         * {string}
         */
        portraitToken: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portraitToken;
            }
        },

        /**
         * Screen name of the currently logged user
         *
         * {string}
         */
        screenName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.screenName;
            }
        },


        /**
         * Full name of the currently logged user
         *
         * {string}
         */
        fullName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.fullName;
            }
        },

        /**
         * Holds current server time
         *
         * {number}
         */
        serverTime: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.currentServerTime;
            }
        },

        /**
         * Holds delta between current server time and client time
         */
        offset: {
            valueFn: function () {
                // Get server time
                var currentChatServerTime = this.get('serverTime');
                // Count offset
                return new Date().getTime() - currentChatServerTime;
            }
        },

        /**
         * Path to images
         */
        pathImage: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.pathImage;
            }
        },

        /**
         * Set to true if chat is enabled
         *
         * {boolean}
         */
        isChatEnabled: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portletEnabled;
            }
        }
    }
});
