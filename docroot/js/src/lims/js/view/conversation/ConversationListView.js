/**
 * Conversation View
 *
 * The class extends Y.View. It represents the view for a single conversation.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationListView = Y.Base.create('conversationListView', Y.View, [], {

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.ConversationListModel,

    initializer: function () {
        // Init model
        this._initModel();

        return this;
    },

    // Init model attached to the view
    _initModel: function () {
        var model = this.get('model');
        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        model.after('add', this._updateConversationList, this);
        model.after('reset', this._updateConversationList, this);

        // Load saved items from server or local storage
        model.load();
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    _updateConversationList: function (e) {
        // New conversation
        var conversation = new Y.LIMS.View.ConversationItemView({model: e.model});
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

