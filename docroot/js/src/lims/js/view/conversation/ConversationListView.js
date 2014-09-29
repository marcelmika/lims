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

    // Template used to define height monitor
    heightMonitorTemplate: '<pre class="chat-height-monitor"/>',

    /**
     * Initializer
     *
     * @returns {Y.LIMS.View.ConversationListView}
     */
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
     * Shows view
     */
    showView: function () {
        // Show panel view
        this._showPanelInput();
        // Show list view again
        this._showListView();
    },

    /**
     * Hides view
     */
    hideView: function () {
        // Hide panel input
        this._hidePanelInput();
        // Hide list view too
        this._hideListView();
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
            messageTextField.on('keyup', this._onMessageTextFieldKeyUp, this);
            messageTextField.on('focus', this._onMessageTextFieldFocus, this);
            messageTextField.on('blur', this._onMessageTextFieldBlur, this);
        }

        // Attach events to model
        model.after('messageAdded', this._onMessageAdded, this);
        model.after('messageError', this._onMessageError, this);
        model.after('messagesUpdated', this._onMessagesUpdated, this);
    },

    /**
     * Called when the message text field gains focus
     *
     * @param event
     * @private
     */
    _onMessageTextFieldFocus: function (event) {
        // Vars
        var hasMessageTextFieldFocus = this.get('hasMessageTextFieldFocus');

        // We don't want to fire it more than once. Thus there is no
        // need to call a body of the if statement again
        if (hasMessageTextFieldFocus === false) {
            // Set the focus flag
            this.set('hasMessageTextFieldFocus', true);
            // Fire an event
            this.fire('messageTextFieldFocus');
        }

        // Message text field was also updated
        this._onMessageTextFieldUpdated(event);
    },

    /**
     * Called when the message text field loses its focus
     *
     * @private
     */
    _onMessageTextFieldBlur: function () {
        // Vars
        var hasMessageTextFieldFocus = this.get('hasMessageTextFieldFocus');

        // We don't want to fire it more than once. Thus there is  no
        // need to call a body of the if statement again
        if (hasMessageTextFieldFocus === true) {
            // Set the focus flag
            this.set('hasMessageTextFieldFocus', false);
            // Fire an event
            this.fire('messageTextFieldBlur');
        }
    },

    /**
     * Called when the users presses any key while there is a focus on message text field
     *
     * @param event
     * @private
     */
    _onMessageTextFieldKeyUp: function (event) {
        // Message text field was also updated
        this._onMessageTextFieldUpdated(event);
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
            animate = this.get('shouldAnimateList');

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
     * Shows panel input
     *
     * @private
     */
    _showPanelInput: function () {
        // Vars
        var panelInput = this.get('panelInput'),
            animation;

        // Show panel only if it's hidden
        if (panelInput.getStyle('opacity') < 1) {

            // Create animation instance
            animation = new Y.Anim({
                node: panelInput,
                duration: 0.2,
                from: {
                    opacity: 0
                },
                to: {
                    opacity: 1
                }
            });

            // Run animation
            animation.run();
        }
    },

    /**
     * Hides input panel
     *
     * @private
     */
    _hidePanelInput: function () {
        // Vars
        var panelInput = this.get('panelInput');

        // Set the opacity only. We don't want to show/hide the panel by calling
        // the show() or hide() method since this will remove it from the visible
        // are and brake the panel size. Thus we only manipulate the opacity
        panelInput.setStyle('opacity', 0);
    },

    /**
     * Called whenever the message field is updated
     *
     * @param event
     * @private
     */
    _onMessageTextFieldUpdated: function (event) {
        var textField = this.get('messageTextField'),
            value;

        // Get rid of new line characters
        value = textField.get('value').replace(/\n|\r/gim, '');

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

        // Resize
        this._resizeMessageTextField();
    },

    /**
     * Automatically counts the size of the message text field and resizes it if needed.
     * It also moves with the size of the panel content. As a result the whole window
     * has the same height only the content is resized.
     *
     * @private
     */
    _resizeMessageTextField: function () {
        // Vars
        var heightMonitor = this.get('heightMonitor').getDOM(),
            messageTextField = this.get('messageTextField').getDOM(),
            panelContent = this.get('panelContent'),
            panelContentHeightCached = this.get('panelContentHeightCached'),
            messageTextFieldHeightCached = this.get('messageTextFieldHeightCached'),
            maxHeight = this.get('maxMessageTextFieldHeight'),
            minHeight = this.get('minMessageTextFieldHeight'),
            panelInputHeight = this.get('panelInputHeight'),
            panelInputOffset = this.get('panelInputOffset'),
            messageContent = messageTextField.value,
            height,
            textNode;

        // Create a text node that has the same content like text field
        textNode = Y.config.doc.createTextNode(messageContent);

        // Empty height monitor
        heightMonitor.innerHTML = '';
        // Insert text node to the height monitor
        heightMonitor.appendChild(textNode);

        // Read the content from the height monitor
        messageContent = heightMonitor.innerHTML;

        // Add at least to spaces if the content is empty
        if (!messageContent.length) {
            messageContent = '&nbsp;&nbsp;';
        }

        // Internet Explorer uses break instead of new line
        if (Y.LIMS.Core.Properties.isIE) {
            messageContent = messageContent.replace(/\n/g, '<br />');
        }

        // Replace the updated content
        heightMonitor.innerHTML = messageContent;

        // Count the height it suppose to be something between min and max height
        height = Math.min(Math.max(heightMonitor.offsetHeight - 4, minHeight), maxHeight);

        // There is no need to do anything if the height wasn't changed
        if (height !== messageTextFieldHeightCached) {
            // Cache the new height
            this.set('messageTextFieldHeightCached', height);

            // Update message text field height
            messageTextField.style.height = height + 'px';
            // The parent node needs to be updated as well
            messageTextField.parentNode.style.height = (height + panelInputOffset) + 'px';
            // If we reached the maximum height start scrolling
            messageTextField.style.overflowY = (height === maxHeight) ? 'scroll' : 'hidden';

            // Update list height
            panelContent.setStyle('height', panelContentHeightCached + panelInputHeight - ((height + panelInputOffset)));
            // Scroll the list to bottom
            this.scrollToBottom();
        }
    }
}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container node
         *
         * {Y.Node}
         */
        container: {
            value: null
        },

        /**
         * Conversation model
         *
         * {Y.LIMS.Model.ConversationModel}
         */
        model: {
            value: null
        },

        /**
         * An array that holds all conversations
         *
         * {array}
         */
        conversationItemViews: {
            value: []
        },

        /**
         * Panel content node
         *
         * {Y.Node}
         */
        panelContent: {
            valueFn: function () {
                return this.get('container').one('.panel-content');
            }
        },

        /**
         * Cached value of panel content height
         *
         * {integer}
         */
        panelContentHeightCached: {
            value: 250
        },

        /**
         * Message list node
         *
         * {Y.Node}
         */
        panelContentList: {
            valueFn: function () {
                return this.get('container').one('.panel-content ul');
            }
        },

        /**
         * Activity indicator node
         *
         * {Y.Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one('.preloader');
            }
        },

        /**
         * Set to true if the appearance of elements in the list should be animated
         *
         * {boolean}
         */
        shouldAnimateList: {
            value: true
        },

        /**
         * Message text field node
         *
         * {Y.Node}
         */
        messageTextField: {
            valueFn: function () {
                return this.get('container').one('.panel-input textarea');
            }
        },

        /**
         * True if the message text field has focus, false if it's blurred
         *
         * {boolean}
         */
        hasMessageTextFieldFocus: {
            value: false // default value
        },

        /**
         * Panel input node that holds message text field
         *
         * {Node}
         */
        panelInput: {
            valueFn: function () {
                return this.get('container').one('.panel-input');
            }
        },

        /**
         * Height of the panel input node i.e. the parent node of message text field
         *
         * {integer}
         */
        panelInputHeight: {
            value: 34
        },

        /**
         * Offset between the panel input and message text field
         *
         * {integer}
         */
        panelInputOffset: {
            value: 12
        },

        /**
         * Cached value of message text field height
         *
         * {integer}
         */
        messageTextFieldHeightCached: {
            value: 0 // to be set
        },

        /**
         * Maximal possible height of message text field
         *
         * {integer}
         */
        maxMessageTextFieldHeight: {
            value: 64
        },

        /**
         * Minimal possible height of message text field
         *
         * {integer}
         */
        minMessageTextFieldHeight: {
            value: 14
        },

        /**
         * Height monitor is used to calculate proper size of the message text field
         *
         * {Node}
         */
        heightMonitor: {
            valueFn: function () {
                var heightMonitorNode = Y.Node.create(this.heightMonitorTemplate);
                // Set the same width as message text field
                heightMonitorNode.setStyle('width', 248);
                // Add it to container, don't worry css will take it away from the visible window
                heightMonitorNode.appendTo(this.get('container'));

                return heightMonitorNode;
            }
        }
    }
});

