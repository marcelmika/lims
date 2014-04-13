/**
 * Portrait
 *
 * Represent buddy's portrait
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Portrait = Y.Base.create('portrait', Y.Base, [], {

    getPortraitUrl: function(screenName) {
        var companyId = Y.LIMS.Core.Settings.companyId,
            pathImage = Y.LIMS.Core.Settings.pathImage;

        return pathImage + '/user_portrait?screenName=' + screenName + '&' + 'companyId=' + companyId;
    }

}, {});
