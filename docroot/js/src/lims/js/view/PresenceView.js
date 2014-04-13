/**
 * Presence View
 *
 * The class extends Y.View. It represents the view for buddy presence
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PresenceView = Y.Base.create('presenceView', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<div class="status-indicator"/>',

    render: function () {

        var presenceType = this.get('presenceType'),
            container = this.get("container");

        switch (presenceType) {
            case "STATE_ACTIVE":
                container.addClass("online");
                break;

            case "STATE_AWAY":
                container.addClass("busy");
                break;

            case "STATE_DND":
                container.addClass("unavailable");
                break;

            case "STATE_OFFLINE":
                container.addClass("off");
                break;

            default:
                container.addClass("off");
                break;
        }

        // Vars
//        var container, group, buddiesView;
//        // Container and model
//        container = this.get('container');
//        group = this.get('model');
//
//        // Render Group:
//        // Fill data from model to template and set it to container
//        container.set('innerHTML',
//            Y.Lang.sub(this.template, {
//                name: group.get('name')
//            })
//        );
//
//        // Render Buddies:
//        buddiesView = new Y.LIMS.View.GroupBuddyViewList({model: group.get('buddies')});
//
//
// buddiesView.render();
//        container.append(buddiesView.get("container"));
//
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

        presenceType: {
            value: "STATE_UNRECOGNIZED"
        }
    }
});

