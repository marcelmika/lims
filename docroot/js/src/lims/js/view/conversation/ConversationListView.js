/**
 * Conversation View
 *
 * The class extends Y.View. It represents the view for a single conversation.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationListView = Y.Base.create('conversationListView', Y.View, [], {

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.MessageListModel,

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
        model.after('messageAdded', this._addMessage, this);
        model.after('messagesUpdated', this._renderMessagesList, this);

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


    _addMessage: function (message) {

        // Vars
        var conversation, // Newly created conversation
            panelContentList = this.get('panelContentList'); // The place where are messages will be rendered to

        // Hide indicator if it wasn't already hidden
        this.get('activityIndicator').hide();

        // New conversation item
        conversation = new Y.LIMS.View.ConversationItemView({model: message});
        // Render it
        conversation.render();
        // Append to list
        panelContentList.append(conversation.get('container'));

        // Scroll to the last message
        this._scrollToBottom();
    },

    /**
     * Renders message list
     *
     * @param messageList
     * @private
     */
    _renderMessagesList: function (messageList) {

        // Vars
        var instance = this;

        // TODO: Clear the content of panel

        // Create view for each message
        messageList.each(function (message) {
            instance._addMessage(message, false);
        });
    },

    /**
     * Scrolls to the last message
     * @private
     */
    _scrollToBottom: function () {
        var panelContent = this.get('panelContent');
        setTimeout(function () {
            panelContent.set('scrollTop', panelContent.get('scrollHeight'));
        }, 1);
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
            textField.set('value', '');
            model.addMessage(new Y.LIMS.Model.MessageItemModel({body: value}));
        }
    }
}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            value: null
        },

        // Conversation model
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

        // List that hold messages
        panelContentList: {
            valueFn: function () {
                var container = this.get('container').one('.panel-content ul');
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

