/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.MainController = Y.Base.create('mainController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {

         new Y.LIMS.View.GroupViewList();

    }
}, {});
