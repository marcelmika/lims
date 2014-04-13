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
    template: Y.one('#lims-group-buddy-item-template').get('innerHTML'),

    // Renders view
    render: function () {
        // Vars
        var container, model;
        // Container and model
        container = this.get("container");
        model = this.get("model");

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: model.get('fullName'),
                portrait: this._getPortrait(model.get('screenName')),
                presence: this._getPresence(model.get('presence'))
            })
        );

        return this;
    },

    // Returns user portrait URL
    _getPortrait: function (screenName) {
        var portraitView = new Y.LIMS.View.PortraitView({screenName: screenName});
        portraitView.render();
        return portraitView.get('container').get('outerHTML');
    },

    // Returns presence view
    _getPresence: function (presenceType) {
        var presenceView = new Y.LIMS.View.PresenceView({presenceType: presenceType});
        presenceView.render();
        return presenceView.get('container').get('outerHTML');
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

