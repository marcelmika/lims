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
        // Attach events
        this._attachEvents();

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

    _attachEvents: function () {
        // Attach event to text field
        var messageTextField = this.get('messageTextField');
        if (messageTextField !== null) {
            messageTextField.on('keydown', this._onMessageTextFieldUpdated, this);
            messageTextField.on('focus', this._onMessageTextFieldUpdated, this);
        }
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    _updateConversationList: function (e) {
        // Hide indicator
        this.get('activityIndicator').hide();
        // New conversation
        var conversation = new Y.LIMS.View.ConversationItemView({model: e.model});
        // Render it
        conversation.render();
        // Append to list
        this.get('panelContent').append(conversation.get('container'));
    },

    /**
     * Called whenever the message field is updated
     *
     * @param event
     * @private
     */
    _onMessageTextFieldUpdated: function (event) {
        var textField = this.get('messageTextField'),
            model = this.get('model'),
            // Get rid of new line characters
            value = textField.get('value').replace(/\n|\r/gim, '');

        // Send message on enter
        if (event.keyCode === 13 && !event.shiftKey && value.length) {
            event.preventDefault();
            // Empty text field
            textField.set('value', "");
            model.create(new Y.LIMS.Model.ConversationItemModel({message: value}));
        }
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
        },

        // Panel content container
        panelContent: {
            valueFn: function () {
                var container = this.get('container').one('.panel-content');
                if (container !== undefined) {
                    return container;
                }
            }
        },

        // Container for activity indicator
        activityIndicator: {
            valueFn: function () {
                var container = this.get('container').one('.preloader');
                if (container !== undefined) {
                    return container;
                }
            }
        },

        // Message text field container
        messageTextField: {
            valueFn: function () {
                var container = this.get('container').one('.panel-input textarea');
                if (container !== undefined) {
                    return container;
                }
            }
        }

    }

});

