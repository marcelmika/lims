/**
 * Conversation View
 *
 * The class extends Y.View. It represents the view for a single conversation.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationView = Y.Base.create('conversationView', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    // <li class="conversation" id="conversation_{conversationId}" panelId="{panelId}">
    containerTemplate: '<li class="conversation">',

    // Specify an optional model to associate with the view.
//    model: Y.LIMS.Model.GroupModelItem,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-template').get('innerHTML'),

    render: function () {
        // Vars
        var container;
        // Container and model
        container = this.get('container');
//        group = this.get('model');

        // Render Group:
        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                conversationTitle: 'John Doe',
                triggerTitle: 'John Doe',
                unreadMessages: 5
            })
        );

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
            value: null // default value
        }
    }

});

