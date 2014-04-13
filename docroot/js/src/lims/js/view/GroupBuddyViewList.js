/**
 * Group Buddy View List
 *
 * The class extends Y.View. It represent a view for a list of buddies within a single group
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupBuddyViewList = Y.Base.create('groupBuddyViewList', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<ul class="group-buddy-list"/>',

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.BuddyModelList,

    render: function () {
        // Vars
        var container, buddies, buddyIndex, buddy, buddyView;
        // Container and model
        container = this.getContainer();
        buddies = this.getModel();

        for (buddyIndex = 0; buddyIndex < buddies.size(); buddyIndex++) {
            // Get buddy from model list
            buddy = buddies.item(buddyIndex);
            // Build view from buddy
            buddyView = new Y.LIMS.View.GroupBuddyViewItem({model: buddy});
            // Render view
            buddyView.render();
            // Append to container
            container.append(buddyView.get('container'));
        }

        return this;
    },

    // ----------------------------------------------------------------------------------------------------------------
    // Getters/Setters
    // ----------------------------------------------------------------------------------------------------------------

    getContainer: function () {
        return this.get('container');
    },

    getModel: function () {
        return this.get('model');
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {

        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        model: {
            value: null // default value
        }
    }
});
