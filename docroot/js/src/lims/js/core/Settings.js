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

Y.LIMS.Core.Settings = Y.Base.create('settings', Y.Base, [], {

    userId: null,      // This is set main.js, to access it use Y.LIMS.Core.Settings.userId
    companyId: null,   // This is set main.js, to access it use Y.LIMS.Core.Settings.companyId
    pathImage: null,   // This is set main.js, to access it use Y.LIMS.Core.Settings.pathImage


    /**
     * Returns root container node
     *
     * @returns {Node}
     */
    getRootNode: function () {
        return this.get('rootNode');
    },

    /**
     * Returns url of the portrait of buddy based on the screenName
     * @param screenName
     * @returns {string}
     */
    getPortraitUrl: function (screenName) {
        var companyId = this.get('companyId'),
            pathImage = this.get('pathImage');

        return pathImage + '/user_portrait?screenName=' + screenName + '&' + 'companyId=' + companyId;
    },

    /**
     * Returns request url of the server. Given url is used for and request/response conversation
     * with the server.
     *
     * @returns {string}
     */
    getServerRequestUrl: function () {
        return Y.one('#limsPortletURL').get('value');
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
     * Returns true if the whole chat is enabled
     *
     * @returns {boolean}
     */
    isChatEnabled: function () {
        return Y.one('#limsPortletEnabled').get('value') === 'true';
    }

}, {

    ATTRS: {

        /**
         * Root container node
         */
        rootNode: {
            valueFn: function () {
                return Y.one('#lims-container');
            }
        },

        /**
         * User Id of the currently logged user
         */
        userId: {
            valueFn: function () {
                return Y.LIMS.Core.Settings.userId;
            }
        },

        /**
         * Company Id of the currently logged user
         */
        companyId: {
            valueFn: function () {
                return Y.LIMS.Core.Settings.companyId;
            }
        },

        /**
         * Screen name of the currently logged user
         */
        screenName: {
            valueFn: function () {
                // Since it cannot be accessed via Liferay.ThemeDisplay we need to
                // take it manually via value in HTML
                return Y.one('#limsCurrentUserScreenName').get('value');
            }
        },


        /**
         * Screen name of the currently logged user
         */
        fullName: {
            valueFn: function () {
                // Since it cannot be accessed via Liferay.ThemeDisplay we need to
                // take it manually via value in HTML
                return Y.one('#limsCurrentUserFullName').get('value');
            }
        },

        /**
         * Holds current server time
         */
        serverTime: {
            valueFn: function () {
                return Y.one('#limsCurrentServerTime').get('value');
            }
        },

        /**
         * Path to images
         */
        pathImage: {
            valueFn: function () {
                return Y.LIMS.Core.Settings.pathImage;
            }
        }
    }
});
