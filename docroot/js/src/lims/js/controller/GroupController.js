/**
 * Created by marcelmika on 4/12/14.
 */

var GroupController = Y.Base.create('groupController', Y.Base, [], {

    // The initializer runs when a GroupController instance is created, and gives
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
        list.after(['add', 'reset', 'remove', 'todoModel:doneChange'],
            this.render, this);

        // Load saved items from localStorage, if available.
        list.load();
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    add: function (e) {

        var view = new Y.LIMS.View.GroupView({model: e.model}), cont;
        view.render();

        cont = view.container !== null ? view.container : view.get('container');

        console.log(cont);

        Y.one('#chatBar .buddy-list .panel-content .online-users').append(
            cont.get('innerHTML')
        );
    }

}, {});

Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupController = GroupController;
