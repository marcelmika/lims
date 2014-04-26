/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.MainController = Y.Base.create('mainController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controller
    initializer: function (options) {
        console.log(options);

        new Y.LIMS.Controller.GroupViewController();
        new Y.LIMS.Controller.PresenceViewController({buddyDetails: options.buddyDetails});
        new Y.LIMS.Controller.SettingsViewController();
        new Y.LIMS.Controller.ConversationsController();
    }
}, {});
