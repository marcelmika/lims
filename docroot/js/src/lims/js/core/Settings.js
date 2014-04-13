/**
 * Settings
 *
 * Contains all necessary settings related to the current session
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Settings = Y.Base.create('settings', Y.Base, [], {

    pathImage: null,
    companyId: null,

    getPortraitUrl: function(screenName) {
        var companyId = this.companyId,
            pathImage = this.pathImage;

        return pathImage + '/user_portrait?screenName=' + screenName + '&' + 'companyId=' + companyId;
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
        }
    }
});
