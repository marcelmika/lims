

AUI({debug:true}).use('console', 'lims-core', "lims-model", "lims-view", "lims-controller", function(A) {

    // Dom ready startup
    A.on('domready', function() {

        // Set global settings
        A.LIMS.Core.Settings.pathImage = Liferay.ThemeDisplay.getPathImage();
        A.LIMS.Core.Settings.companyId = Liferay.ThemeDisplay.getCompanyId();

        // Start the app!
        new A.LIMS.Controller.MainController();
    });
});