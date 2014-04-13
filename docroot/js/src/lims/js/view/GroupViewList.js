/**
 * Group View List
 *
 * The class extends Y.View. It represent a view for a list of groups
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewList = Y.Base.create('groupViewList', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<ul class="group-list"/>',

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.GroupModelList,

    // The initializer runs when the instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {
        var groupModelList;

        // Init group list
        groupModelList = this.groupModelList = new Y.LIMS.Model.GroupModelList(); // todo: new this.model()
        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        groupModelList.after('add', this.updateGroupViewList, this);
        groupModelList.after('reset', this.updateGroupViewList, this);

        // Load saved items from server or local storage
        groupModelList.load();
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    updateGroupViewList: function (e) {
        var groupView;
        // Create new Group View Item
        groupView = new Y.LIMS.View.GroupViewItem({model: e.model});
        // Render it
        groupView.render();
        // Append to list
        this.get('container').append(groupView.get('container'));
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.one("#chatBar .buddy-list .panel-content .online-users");
//                return Y.Node.create(this.containerTemplate);
            }
        }
    }
});
