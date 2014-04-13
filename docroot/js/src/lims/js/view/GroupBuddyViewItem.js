/**
 * Group Buddy View Item
 *
 * The class extends Y.View. It represents the view for a single group buddy item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupBuddyViewItem = Y.Base.create('groupBuddyViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-buddy-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.BuddyModelItem,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
//    template: Y.one('#lims-group-buddy-item-template').get('innerHTML'),

    render: function () {
        // Vars
        var container, model;
        // Container and model
        container = this.getContainer();
        model = this.getModel();

        container.set('innerHTML', model.get("fullName"));

        // Fill data from model to template and set it to container
//        container.set('innerHTML',
//            Y.Lang.sub(this.template, {
//                name: model.get('name')
//            })
//        );

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

