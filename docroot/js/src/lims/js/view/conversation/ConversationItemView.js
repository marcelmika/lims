/**
 * Group View Item
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationItemView = Y.Base.create('conversationViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="conversation-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.MessageItemModel,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-item-template').get('innerHTML'),


    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
        // Todo: this is just for debug
            settings = new Y.LIMS.Core.Settings();

            // Render Group:
            // Fill data from model to template and set it to container
            container.set('innerHTML',
                Y.Lang.sub(this.template, {
                    createdPrettified: "a minute ago",
                    fullName: "John Doe",
                    content: "Test",
                    portrait: this._getPortrait(settings.getCurrentUserScreenName())
                })
            );

        // Render Buddies:
//        buddiesView = new Y.LIMS.View.GroupBuddyViewList({model: group.get('buddies')});
//        buddiesView.render();
//        container.append(buddiesView.get("container"));

        return this;
    },

    // Returns user portrait
    _getPortrait: function (screenName) {
        var portraitView = new Y.LIMS.View.PortraitView({screenName: screenName});
        portraitView.render();
        return portraitView.get('container').get('outerHTML');
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
            value: null // default value
        }
    }

});

