/**
 * Conversation View
 *
 * The class extends Y.View. It represents the view for a single conversation.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationListView = Y.Base.create('conversationListView', Y.View, [], {

    // Specify an optional model to associate with the view.
//    model: Y.LIMS.Model.GroupModelItem,

    initializer: function (options) {

        this.set('container', options.container);

        this._updateConversationList();

        return this;
    },


    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    _updateConversationList: function () {
        var conversation;
        // Create new Group View Item
        conversation = new Y.LIMS.View.ConversationItemView();
        // Render it
        conversation.render();
        // Append to list
        this.get('container').append(conversation.get('container'));
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            value: null
        },

        // Instance of model attached to view
        model: {
            value: null // default value
        }
    }

});

