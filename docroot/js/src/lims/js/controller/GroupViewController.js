/**
 * Group View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupViewController = Y.Base.create('groupViewController', Y.View, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {
//        var groupModelList;

        // Init group list
        this.groupViewList = new Y.LIMS.View.GroupViewList();
        // Update the display when a new item is added to the list, or when the
        // entire list is reset.

    }


}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.one("#chat-bar .chat-tabs .buddy-list");
            }
        },

        groupViewList: {
            value: null // default value
        }
    }
});
