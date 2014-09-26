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
 * Single User Conversation View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SingleUserConversationViewController = Y.Base.create('singleUserConversationController',
    Y.LIMS.Core.ViewController, [], {

        // The template property holds the contents of the #lims-conversation-error-template
        // element, which will be used as the HTML template for conversation error
        // Check the templates.jspf to see all templates
        errorTemplate: Y.one('#lims-conversation-error-template').get('innerHTML'),

        /**
         *  The initializer runs when a Single User Conversation View Controller instance is created.
         */
        initializer: function () {
            // This needs to be called in each view controller
            this.setup(this.get('container'), this.get('controllerId'));
        },

        /**
         * Panel Did Load is called when the panel is attached to the controller
         */
        onPanelDidLoad: function () {
            // Events
            this._attachEvents();
        },

        /**
         * Panel Did Appear is called when the panel did appear on the screen
         */
        onPanelDidAppear: function () {
            // Vars
            var model = this.get('model'),
                listView = this.get('listView'),
                notification = this.get('notification');

            // Messages are read so suppress the count
            notification.suppress(model.get('unreadMessagesCount'));
            // Reset counter of unread messages
            model.resetUnreadMessagesCounter();
            // Always scroll to the last message when user opens the window
            listView.scrollToBottom();
            // Add focus on textarea
            listView.setTextFieldFocus();
            // Hide badge since it's not needed anymore
            this._hideBadge();
            // Start timer that periodically updates timestamps of messages
            this._startTimer();
        },

        /**
         * Panel Did Disappear is called when the panel disappeared from the screen
         */
        onPanelDidDisappear: function () {
            this._pauseTimer();
        },

        /**
         * Panel Did Unload is called whenever the panel completely disappears from the screen. This happens when
         * the user closes the whole panel.
         */
        onPanelDidUnload: function () {
            // Vars
            var model = this.get('model');

            // Close conversation
            model.closeConversation();
        },

        /**
         * Takes new model and updates local model based on it's contents
         *
         * @param model
         */
        updateModel: function (model) {
            // Vars
            var conversationModel = this.get('model'),      // Current conversation model
                currentUnreadMessagesCount,                 // Current unread message count
                updatedUnreadMessageCount,                  // Unread message count of updated model
                newMessagesCount,                           // Number of newly received messages
                notification = this.get('notification'),    // Notification handler
                instance = this;                            // Saved instance

            // There is no need to update conversation which hasn't been changed
            if (conversationModel.get('etag') !== model.get('etag')) {
                // Remember the message count
                currentUnreadMessagesCount = conversationModel.get('unreadMessagesCount');
                // Update model
                conversationModel.load(function (err, conversation) {
                    if (!err) {
                        // New message count
                        updatedUnreadMessageCount = conversation.get('unreadMessagesCount');
                        // If the unread message count has been increased notify user
                        if (updatedUnreadMessageCount > currentUnreadMessagesCount) {
                            // Actual count of newly received messages
                            newMessagesCount = updatedUnreadMessageCount - currentUnreadMessagesCount;
                            // Notify about new message
                            notification.notify(newMessagesCount);
                        }
                        // Callback
                        instance._onConversationUpdated();
                    }
                });
            }
        },

        /**
         * Maps all events on Y object to private internal functions
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var listView = this.get('listView'),
                model = this.get('model');

            // Local events
            listView.on('messageSubmitted', this._onMessageSubmitted, this);
            model.on('createBegin', this._onConversationCreateBegin, this);
            model.on('createSuccess', this._onConversationCreateSuccess, this);
            model.on('createError', this._onConversationCreateError, this);
            model.on('readSuccess', this._onConversationReadSuccess, this);

            // Remote events
            Y.on('connectionError', this._onConnectionError, this);
            Y.on('connectionOK', this._onConnectionOK, this);
        },

        /**
         * Starts timer which periodically refreshes group list
         *
         * @private
         */
        _startTimer: function () {
            // Vars
            var properties = this.get('properties'),
                listView = this.get('listView'),
                timerInterval = this.get('timerInterval');

            // Start only if the chat is enabled
            if (properties.isChatEnabled()) {
                // Update all timestamps
                listView.updateTimestamps();
                // Start periodical update
                this.set('timer', setInterval(function () {
                    // Load model
                    listView.updateTimestamps();
                }, timerInterval));
            }
        },

        /**
         * Pauses timer which periodically refreshes group list
         *
         * @private
         */
        _pauseTimer: function () {
            // Vars
            var timer = this.get('timer');
            // Pause
            clearTimeout(timer);
        },

        /**
         * Called when the conversation is being created
         *
         * @private
         */
        _onConversationCreateBegin: function () {
            // Hide the panel input. We don't want users to post any messages now
            this._hidePanelInput();
        },

        /**
         * Called when the conversation was successfully created
         *
         * @private
         */
        _onConversationCreateSuccess: function () {
            // Vars
            var model = this.get('model');
            // Load the conversation
            model.load();
        },

        /**
         * Called when the creation of conversation failed
         *
         * @private
         */
        _onConversationCreateError: function () {
            // Hide preloader
            this._hideActivityIndicator();
            // Show error message
            this._showErrorMessage();
        },

        /**
         * Called when the conversation is successfully read
         *
         * @private
         */
        _onConversationReadSuccess: function () {
            // Show the panel input so the user can post messages
            this._showPanelInput();
            // Hide error message if there was any
            this._hideErrorMessage();
        },

        /**
         * Called whenever the conversation model is updated
         *
         * @private
         */
        _onConversationUpdated: function () {
            // Vars
            var unreadMessagesCount = this.get('model').get('unreadMessagesCount');

            // No unread messages
            if (unreadMessagesCount === 0) {
                this._hideBadge();
            } else {
                // Show badge
                this._showBadge();
            }

            // Update badge count
            this._updateBadge(unreadMessagesCount);

            // Show the panel input so the users can post messages
            this._showPanelInput();
        },

        /**
         * Called when user submits message from text area
         *
         * @param event
         * @private
         */
        _onMessageSubmitted: function (event) {
            // Vars
            var model = this.get('model'),                              // Conversation model
                message = event.message,                                // Received message
                offset = this.get('properties').getServerTimeOffset(),  // Server time offset
                now = new Date().getTime(),                             // Current client time
                createdAt,                                              // Created at timestamp
                buddyDetails = this.get('buddyDetails');                // Currently logged user


            // Add the offset to the created at timestamp
            createdAt = now - offset;

            // Add new message to the conversation
            model.addMessage(new Y.LIMS.Model.MessageItemModel({
                from: buddyDetails,
                body: message,
                createdAt: createdAt
            }));
        },

        /**
         * Called when the conversation creation fails and the user click on
         * the try again button
         *
         * @private
         */
        _onTryAgainButtonClick: function () {
            // Vars
            var model = this.get('model'),
                tryAgainButtonNode = this.get('errorContainer').one('.resend-button');

            // Prevent user to click on preloader more than once
            if (!tryAgainButtonNode.hasClass('preloader')) {
                // Add preloader to the resend button
                tryAgainButtonNode.addClass('preloader');
                // Try to create the conversation again
                model.save();
            }
        },

        /**
         * Called whenever an error with connection occurred
         *
         * @private
         */
        _onConnectionError: function () {
            this.showError(Y.LIMS.Core.i18n.values.connectionErrorMessage);
        },

        /**
         * Called when there are no more connection errors
         *
         * @private
         */
        _onConnectionOK: function () {
            this.hideError();
        },

        /**
         * Updates badge value
         *
         * @param value
         * @private
         */
        _updateBadge: function (value) {
            // Vars
            var badge = this.get('badge');

            // Update value
            if (badge) {
                badge.set('innerHTML', value);
            }
        },

        /**
         * Shows badge
         *
         * @private
         */
        _showBadge: function () {
            // Vars
            var badge = this.get('badge');

            // Show badge
            if (badge) {
                badge.show();
            }
        },

        /**
         * Hides badge
         *
         * @private
         */
        _hideBadge: function () {
            // Vars
            var badge = this.get('badge');

            // Hide badge
            if (badge) {
                badge.hide();
            }
        },

        /**
         * Shows activity indicator
         *
         * @private
         */
        _showActivityIndicator: function () {
            // Vars
            var activityIndicator = this.get('activityIndicator');

            // Show preloader
            activityIndicator.show();
        },

        /**
         * Hides activity indicator
         *
         * @private
         */
        _hideActivityIndicator: function () {
            // Vars
            var activityIndicator = this.get('activityIndicator');

            // Hide preloader
            activityIndicator.hide();
        },

        /**
         * Shows panel input
         *
         * @private
         */
        _showPanelInput: function () {
            var panelInput = this.get('panelInput');
            // Set the opacity only. We don't want to show/hide the panel by calling
            // the show() or hide() method since this will remove it from the visible
            // are and brake the panel size. Thus we only manipulate the opacity
            panelInput.setStyle('opacity', 1);
        },

        /**
         * Hides panel input
         *
         * @private
         */
        _hidePanelInput: function () {
            var panelInput = this.get('panelInput');
            // Set the opacity only. We don't want to show/hide the panel by calling
            // the show() or hide() method since this will remove it from the visible
            // are and brake the panel size. Thus we only manipulate the opacity
            panelInput.setStyle('opacity', 0);
        },

        /**
         * Shows error message
         *
         * @private
         */
        _showErrorMessage: function () {
            // Vars
            var errorContainer = this.get('errorContainer'),
                panelContent = this.get('panelContent'),
                animation,
                instance = this;

            // If the error container is already in the document do nothing
            if (!errorContainer.inDoc()) {

                // Create an instance of animation
                animation = new Y.Anim({
                    node: errorContainer,
                    duration: 0.3,
                    from: {opacity: 0},
                    to: {opacity: 1}
                });

                // Opacity needs to be set to zero otherwise there wil
                // be a weird blink effect
                errorContainer.setStyle('opacity', 0);

                // Attach click on resend button event
                errorContainer.one('.resend-button').on('click', function (event) {
                    event.preventDefault();
                    instance._onTryAgainButtonClick();
                });

                // Add error to the container
                panelContent.append(errorContainer);

                // Run the effect animation
                animation.run();
            }

            // It is possible that resend button was clicked thus it was transformed to the preloader.
            // Remove the preloader class so it can be the resend button again.
            errorContainer.one('.resend-button').removeClass('preloader');
        },

        /**
         * Hides error message
         *
         * @private
         */
        _hideErrorMessage: function () {
            // Vars
            var errorContainer = this.get('errorContainer'),
                animation;

            // Run the animation only if the error container is in DOM
            if (errorContainer.inDoc()) {

                // Create an instance of animation
                animation = new Y.Anim({
                    node: errorContainer,
                    duration: 0.3,
                    from: {opacity: 1},
                    to: {opacity: 0}
                });

                // Listen to the end of the animation
                animation.on('end', function () {
                    // Remove the error node from DOM
                    animation.get('node').remove();
                });

                // Run the animation
                animation.run();
            }
        }

    }, {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        ATTRS: {

            /**
             * Id of the controller. Each view controller must have one.
             *
             * {string}
             */
            controllerId: {
                value: null // To be set
            },

            /**
             * Container node
             *
             * {Node}
             */
            container: {
                value: null // To be set
            },

            /**
             * Model attached to controller
             *
             * {Y.LIMS.Model.ConversationModel}
             */
            model: {
                value: null // to be set
            },

            /**
             * Badge that shows number of unread messages node
             *
             * {Node}
             */
            badge: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container');
                    // Find badge
                    return container.one('.unread');
                }
            },

            /**
             * List view node that holds all message views
             *
             * {Node}
             */
            listView: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container'),
                        model = this.get('model');
                    // Create new view
                    return new Y.LIMS.View.ConversationListView({
                        container: container.one('.panel-window'),
                        model: model
                    });
                }
            },

            /**
             * Panel content node that holds nodes like activity indicator, list view, error message, etc.
             *
             * {Node}
             */
            panelContent: {
                valueFn: function () {
                    return this.get('container').one('.panel-content');
                }
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
             * Activity indicator node
             *
             * {Node}
             */
            activityIndicator: {
                valueFn: function () {
                    return this.get('container').one('.preloader');
                }
            },

            /**
             * Error container node
             *
             * {Node}
             */
            errorContainer: {
                valueFn: function () {
                    return Y.Node.create(this.errorTemplate);
                }
            },

            /**
             * Timer that is used to refresh date nodes periodically
             *
             * {timer}
             */
            timer: {
                value: null // to be set
            },

            /**
             * Length of the timer period that is used to refresh date nodes periodically
             *
             * {integer}
             */
            timerInterval: {
                value: 60000 // one minute
            },

            /**
             * An instance of notification object which is responsible for
             * notifying user about incoming messages
             *
             * {Y.LIMS.Core.Notification}
             */
            notification: {
                value: null // to be set
            },

            /**
             * An instance of buddy details related to the currently logged user
             *
             * {Y.LIMS.Model.BuddyModelItem}
             */
            buddyDetails: {
                value: null
            },

            /**
             * An instance of the portlet properties object
             *
             * {Y.LIMS.Core.Properties}
             */
            properties: {
                value: null // to be set
            },

            /**
             * An instance of settings related to the currently logged user
             *
             * {Y.LIMS.Model.SettingsModel}
             */
            settings: {
                value: null // to be set
            }
        }
    });
