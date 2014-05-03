/**
 * Settings
 *
 * Contains all necessary settings related to the current session
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Settings = Y.Base.create('settings', Y.Base, [], {

    screenName: null,
    pathImage: null,
    companyId: null,

    /**
     * Returns url of the portrait of buddy based on the screenName
     * @param screenName
     * @returns {string}
     */
    getPortraitUrl: function (screenName) {
        var companyId = this.companyId,
            pathImage = this.pathImage;

        return pathImage + '/user_portrait?screenName=' + screenName + '&' + 'companyId=' + companyId;
    },

    /**
     * Returns request url of the server. Given url is used for and request/response conversation
     * with the server.
     *
     * @returns {string}
     */
    getServerRequestUrl: function () {
        return Y.one('#chatPortletURL').get('value');
    },

    getCurrentUserScreenName: function() {
        return Y.one('#currentChatUserScreenName').get('value');
    }

}, {

    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        pathImage: {
            valueFn: function () {
                return this.pathImage;
            }
        },

        companyId: {
            valueFn: function () {
                return this.companyId;
            }
        },

        screenName: {
            valueFn: function() {
                return this.screenName;
            }
        }
    }
});
