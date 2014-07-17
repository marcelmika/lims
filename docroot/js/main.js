

AUI().use('lims-core', "lims-model", "lims-view", "lims-controller", function(A) {

    // If there is no chat bar do nothing
    if (!A.one('#chatBar')) {
        return;
    }

    // Dom ready startup
    A.on('domready', function() {

        // Set global settings
        // TODO: Remove, this doesn't work anyway
        A.LIMS.Core.Settings.pathImage = Liferay.ThemeDisplay.getPathImage();
        A.LIMS.Core.Settings.userId = Liferay.ThemeDisplay.getUserId();
        A.LIMS.Core.Settings.companyId = Liferay.ThemeDisplay.getCompanyId();

        // Start the app!
        new A.LIMS.Controller.MainController({
            userId: Liferay.ThemeDisplay.getUserId(),
            companyId: Liferay.ThemeDisplay.getCompanyId(),
            pathImage: Liferay.ThemeDisplay.getPathImage()
        });
    });
});