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

    /**
     * Updates timestamp in each conversations
     */
    updateTimestamps: function () {
        // Vars
        var index,
            conversationItemViews = this.get('conversationItemViews'),
            conversationItem;

        for (index = 0; index < conversationItemViews.length; index++) {
            conversationItem = conversationItemViews[index];
            conversationItem.updateTimestamp();
        }
    },

    // Init model attached to the view
    _initModel: function () {
        var model = this.get('model');
        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        model.after('messageAdded', this._onMessageAdded, this);
        model.after('messagesUpdated', this._onMessagesUpdated, this);

        // Load saved items from server or local storage
        model.load();
    },

    _attachEvents: function () {
        // Vars
        var messageTextField = this.get('messageTextField');

        // Attach event to text field
        if (messageTextField !== null) {
            messageTextField.on('keydown', this._onMessageTextFieldUpdated, this);
            messageTextField.on('focus', this._onMessageTextFieldUpdated, this);
        }
    },

    _onMessageAdded: function (message) {
        this._addMessage(message);
        // Scroll to the last message
        this._scrollToBottom();
    },

    _onMessagesUpdated: function (messageList) {
        this._renderMessagesList(messageList);
        // Scroll to the last message
        this._scrollToBottom();
    },

    _addMessage: function (message) {

        // Vars
        var conversation,                                           // Newly created conversation
            panelContentList = this.get('panelContentList'),        // The place where are messages will be rendered to
            conversationList = this.get('conversationItemViews');   // List of conversation views

        // New conversation item
        conversation = new Y.LIMS.View.ConversationItemView({model: message});
        conversationList.push(conversation);
        // Render it
        conversation.render();
        // Append to list
        panelContentList.append(conversation.get('container'));
    },

    /**
     * Renders message list
     *
     * @param messageList
     * @private
     */
    _renderMessagesList: function (messageList) {

        // Vars
        var instance = this,
            panelContentList = this.get('panelContentList'); // The place where are messages will be rendered to

        // Hide indicator if it wasn't already hidden
        this.get('activityIndicator').hide();

        // TODO: Reimplement
        // This will reset the content of conversation item view
        this.set('conversationItemViews', []);
        panelContentList.set('innerHTML', '');

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
            value = textField.get('value').replace(/\n|\r/gim, ''); // Get rid of new line characters

        // Send message on enter
        if (event.keyCode === 13 && !event.shiftKey && value.length) {
            event.preventDefault();
            // Empty text field
            textField.set('value', '');
            // Fire an event that message was submitted
            this.fire('messageSubmitted', {
                message: value
            });
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
            value: null // Y.LIMS.Model.ConversationModel
        },

        // Holds all conversation item views
        conversationItemViews: {
            value: []
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

