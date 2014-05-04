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
        // Init model
        this._initModel();
    },

    // Init model attached to the view
    _initModel: function () {
        var groupModelList;
        // Init group list
        groupModelList = this.get('model');
        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        groupModelList.after('add', this._onGroupAdd, this);
        groupModelList.after('reset', this._onGroupReset, this);
    },


    /**
     * Called whenever the group model is reset
     *
     * @private
     */
    _onGroupReset: function() {
        // Empty container
        this.get('container').set('innerHTML', '');
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    _onGroupAdd: function (e) {
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

        container: {
            value: null // default value
        },

        model: {
            value: null // default value
        }
    }
});
