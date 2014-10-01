AUI().use('lims-core', "lims-model", "lims-view", "lims-controller", function (A) {

    // If there is no chat bar do nothing
    if (!A.one('#lims-container .lims-bar')) {
        return;
    }

    // Dom ready startup
    A.on('domready', function () {

        // Vars
        var mainController;

        // Set global settings
        A.LIMS.Core.Properties.pathImage = Liferay.ThemeDisplay.getPathImage();
        A.LIMS.Core.Properties.userId = Liferay.ThemeDisplay.getUserId();
        A.LIMS.Core.Properties.companyId = Liferay.ThemeDisplay.getCompanyId();
        A.LIMS.Core.Properties.isIE = Liferay.Browser.isIe();

        // Set i18n values
        A.LIMS.Core.i18n.values = {
            connectionErrorMessage: Liferay.Language.get('connection-error'),
            searchInfoMessage: Liferay.Language.get('panel-search-info-text'),
            searchNoResultsMessage: Liferay.Language.get('panel-search-no-results-text'),
            searchErrorMessage: Liferay.Language.get('panel-search-error-text'),
            groupListEmptyInfoMessage: Liferay.Language.get('panel-group-list-empty-info-text'),
            groupListErrorMessage: Liferay.Language.get('panel-group-list-error-text'),
            conversationCreateErrorMessage: Liferay.Language.get('panel-conversation-create-error-text'),
            conversationReadErrorMessage: Liferay.Language.get('panel-conversation-read-error-text'),
            socialRelationUnknown: Liferay.Language.get('social-relation-unknown'),
            socialRelationConnection: Liferay.Language.get('social-relation-connection'),
            socialRelationCoworker: Liferay.Language.get('social-relation-coworker'),
            socialRelationFriend: Liferay.Language.get('social-relation-friend'),
            socialRelationRomanticPartner: Liferay.Language.get('social-relation-romantic-partner'),
            socialRelationSibling: Liferay.Language.get('social-relation-sibling'),
            timeRangeJustNow: Liferay.Language.get('time-range-just-now'),
            timeRangeMinuteAgo: Liferay.Language.get('time-range-minute-ago'),
            timeRangeMinutesAgo: Liferay.Language.get('time-range-minutes-ago'),
            timeRangeHourAgo: Liferay.Language.get('time-range-hour-ago'),
            timeRangeHoursAgo: Liferay.Language.get('time-range-hours-ago'),
            timeRangeYesterday: Liferay.Language.get('time-range-yesterday'),
            timeRangeDaysAgo: Liferay.Language.get('time-range-days-ago'),
            timeRangeWeeksAgo: Liferay.Language.get('time-range-weeks-ago'),
            dateFormat: Liferay.Language.get('date-format')
        };

        // Start the app!
        mainController = new A.LIMS.Controller.MainController({
            userId: Liferay.ThemeDisplay.getUserId(),
            companyId: Liferay.ThemeDisplay.getCompanyId(),
            pathImage: Liferay.ThemeDisplay.getPathImage()
        });

        // Notify main controller if the user session expires
        Liferay.bind('sessionExpired', function () {
            mainController.sessionExpired();
        });

    });
});