/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationsController = Y.Base.create('conversationsController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controllers
    initializer: function () {

        new Y.LIMS.Controller.SingleUserConversationViewController();
    }
}, {

});
