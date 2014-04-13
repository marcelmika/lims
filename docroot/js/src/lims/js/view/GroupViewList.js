/**
 * Group View List
 *
 * The class extends Y.View. It represent a view for a list of groups
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewList = Y.Base.create('groupViewList', Y.View, [], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.GroupModelList,

    // The initializer runs when the instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {
        // Create a new GroupModelList instance to hold the GroupModel items.
        var list = this.groupList = new Y.LIMS.Model.GroupModelList();

        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        list.after('add', this.add, this);
        list.after('reset', this.reset, this);

        // Re-render the stats in the footer whenever an item is added, removed
        // or changed, or when the entire list is reset.
//        list.after(['add', 'reset', 'remove', 'todoModel:doneChange'], this.render, this);

        // Load saved items from localStorage, if available.
        list.load();
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    add: function (e) {
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
            }
        }
    }
});
