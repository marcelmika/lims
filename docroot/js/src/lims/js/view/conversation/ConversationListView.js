/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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


    /**
     * Scrolls to the last message
     * @private
     */
    scrollToBottom: function () {
        var panelContent = this.get('panelContent');
        setTimeout(function () {
            panelContent.set('scrollTop', panelContent.get('scrollHeight'));
        }, 1);
    },

    /**
     * Sets focus to the text area
     */
    setTextFieldFocus: function () {
        // Vars
        var messageTextField = this.get('messageTextField');
        // Set the focus
        messageTextField.focus();
    },

    /**
     * Attaches listener to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var messageTextField = this.get('messageTextField'),
            model = this.get('model');

        // Attach events to text field
        if (messageTextField) {
            messageTextField.on('keydown', this._onMessageTextFieldUpdated, this);
            messageTextField.on('focus', this._onMessageTextFieldUpdated, this);
        }

        // Attach events to model
        model.after('messageAdded', this._onMessageAdded, this);
        model.after('messageError', this._onMessageError, this);
        model.after('messagesUpdated', this._onMessagesUpdated, this);
    },

    /**
     * Called when a single message is added to the model
     *
     * @param event
     * @private
     */
    _onMessageAdded: function (event) {
        // Add a single message to the list
        this._addMessage(event.message);
        // Scroll to bottom so the user sees the message
        this.scrollToBottom();
    },

    /**
     * Called when the whole message list is updated
     *
     * @private
     */
    _onMessagesUpdated: function () {
        // Hide indicator if it wasn't already hidden
        this.get('activityIndicator').hide();
        // Render the list
        this._renderMessagesList();
        // Since the list is already rendered there is no need to
        // animate any other addition to the list
        this.set('shouldAnimateList', false);
    },

    /**
     * Called when there is an error during message delivery
     *
     * @private
     */
    _onMessageError: function () {
        // Scroll to bottom otherwise the error wouldn't be visible
        this.scrollToBottom();
    },

    /**
     * Renders and adds a single message to the list
     *
     * @param message
     * @private
     */
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
     * Renders the whole message list
     *
     * @private
     */
    _renderMessagesList: function () {

        // Vars
        var instance = this,
            messageList = this.get('model').get('messageList'),
            animate = this.get('shouldAnimateList'); // Store the instance

        // Hide the view and show it after it's rendered
        this._hideListView();
        // Reset content
        this._resetListView();

        // Create view for each message
        messageList.each(function (message) {
            instance._addMessage(message, false);
        });

        // Show it again and animate it if needed
        this._showListView(animate);
        // Scroll to bottom so the user sees the message
        this.scrollToBottom();
    },

    /**
     * Removes the whole content from  list view
     *
     * @private
     */
    _resetListView: function () {
        // Vars
        var panelContentList = this.get('panelContentList');
        // This will reset the content of conversation item view
        panelContentList.set('innerHTML', '');
        this.set('conversationItemViews', []);
    },

    /**
     * Shows list view
     *
     * @param animated true if the action should be animated
     * @private
     */
    _showListView: function (animated) {
        // Vars
        var panelContentList = this.get('panelContentList'),
            animation = new Y.Anim({
                node: panelContentList,
                duration: 0.5,
                from: {
                    opacity: 0
                },
                to: {
                    opacity: 1
                }
            });

        // Opacity needs to be set to zero otherwise there will
        // be a weird blink effect
        if (animated) {
            panelContentList.setStyle('opacity', 0);
        }

        panelContentList.show();

        // Run the effect animation
        if (animated) {
            animation.run();
        }
    },

    /**
     * Hides list view
     *
     * @private
     */
    _hideListView: function () {
        // Vars
        var panelContentList = this.get('panelContentList');
        // Hide list view
        panelContentList.hide();
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
                if (container) {
                    return container;
                }
            }
        },

        // List that hold messages
        panelContentList: {
            valueFn: function () {
                var container = this.get('container').one('.panel-content ul');
                if (container) {
                    return container;
                }
            }
        },

        // Container for activity indicator
        activityIndicator: {
            valueFn: function () {
                var container = this.get('container').one('.preloader');
                if (container) {
                    return container;
                }
            }
        },

        shouldAnimateList: {
            value: true
        },

        // Message text field container
        messageTextField: {
            valueFn: function () {
                var container = this.get('container').one('.panel-input textarea');
                if (container) {
                    return container;
                }
            }
        }

    }

});

