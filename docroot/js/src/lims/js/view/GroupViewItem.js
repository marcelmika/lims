/**
 * Group View Item
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewItem = Y.Base.create('groupViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.GroupModelItem,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-group-item-template').get('innerHTML'),

    render: function () {
        // Vars
        var container, group, buddyIndex, buddy, buddies, buddyView;
        // Container and model
        container = this.get('container');
        group = this.get('model');

        // Render Group:
        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: group.get('name')
            })
        );

        // Render buddies:
        buddies = group.get('buddies');
        console.log(buddies);
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
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },
        // Instance of model attached to view
        model: {
            value: null
        }
    }

});

