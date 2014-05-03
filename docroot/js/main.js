

AUI().use('lims-core', "lims-model", "lims-view", "lims-controller", function(A) {

    // If there is no chat bar do nothing
    if (!A.one('#chatBar')) {
        return;
    }

    // Dom ready startup
    A.on('domready', function() {

        // Set global settings
        A.LIMS.Core.Settings.pathImage = Liferay.ThemeDisplay.getPathImage();
        A.LIMS.Core.Settings.companyId = Liferay.ThemeDisplay.getCompanyId();

        // Get logged user
        var buddyDetails = new A.LIMS.Model.BuddyModelItem({
           buddyId: Liferay.ThemeDisplay.getUserId()
        });

        // Start the app!
        new A.LIMS.Controller.MainController({
            buddyDetails: buddyDetails
        });
    });
});