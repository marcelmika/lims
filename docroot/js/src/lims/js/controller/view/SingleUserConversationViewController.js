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
            // Vars
            var listView = this.get('listView');

            // Events
            this._attachEvents();

            // Hide the panel input. We don't want users to post any messages until the message feed is ready
            listView.hideView();
        },

        /**
         * Panel Did Appear is called when the panel did appear on the screen
         */
        onPanelDidAppear: function () {
            // Vars
            var model = this.get('model'),
                listView = this.get('listView'),
                instance = this;

            // Reset counter of unread messages
            model.resetUnreadMessagesCounter(function (err) {
                if (!err) {
                    // Reset badge
                    instance._updateBadge(0);
                }
            });
            // Always scroll to the last message when user opens the window
            listView.scrollToBottom();
            // Add focus on textarea
            listView.setTextFieldFocus();
            // Hide badge since it's not needed anymore
            this._hideBadge();
            // Start timer that periodically updates timestamps of messages
            this._startTimer();
            // Make the badge less noticeable
            this._dimBadge();
        },

        /**
         * Panel Did Disappear is called when the panel disappeared from the screen
         */
        onPanelDidDisappear: function () {

            // Make the badge noticeable again
            this._brightBadge();

            // No need to updated message timestamps since they will be updated whenever
            // the panel appears again
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
                notification = this.get('notification'),    // Notification handler
                listView = this.get('listView'),            // List view
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

                            // If the message text field has focus, there is no need to notify the
                            // user about newly incoming messages since we assume that the user is
                            // currently chatting so he sees all the messages
                            if (listView.get('hasMessageTextFieldFocus')) {
                                conversationModel.resetUnreadMessagesCounter();
                            }
                            // Message text field does not have a focus thus we notify the user
                            else {
                                // Notify about new message
                                notification.notify(conversationModel.get('lastMessage'));
                                // Update badge count
                                instance._updateBadge(updatedUnreadMessageCount);
                            }
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
                model = this.get('model'),
                createErrorView = this.get('conversationCreateErrorView'),
                readErrorView = this.get('conversationReadErrorView');

            // Local events
            listView.on('messageSubmitted', this._onMessageSubmitted, this);
            listView.on('messageTextFieldFocus', this._onMessageTextFieldFocus, this);
            model.on('createBegin', this._onConversationCreateBegin, this);
            model.on('createSuccess', this._onConversationCreateSuccess, this);
            model.on('createError', this._onConversationCreateError, this);
            model.on('readSuccess', this._onConversationReadSuccess, this);
            model.on('readError', this._onConversationReadError, this);
            createErrorView.on('resendButtonClick', this._onConversationCreateRetry, this);
            readErrorView.on('resendButtonClick', this._onConversationReadRetry, this);

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
            // Vars
            var listView = this.get('listView');
            // Hide the panel input. We don't want users to post any messages now
            listView.hideView();
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
            // Vars
            var createErrorView = this.get('conversationCreateErrorView');

            // Show error message
            createErrorView.showErrorMessage(true);
            // Hide preloader
            this._hideActivityIndicator();
        },

        /**
         * Called when the user clicks on the retry button within the create error view
         *
         * @private
         */
        _onConversationCreateRetry: function () {
            // Vars
            var model = this.get('model');

            // Save the model again
            model.save();
        },

        /**
         * Called when the conversation is successfully read
         *
         * @private
         */
        _onConversationReadSuccess: function () {
            // Vars
            var createErrorView = this.get('conversationCreateErrorView'),
                readErrorView = this.get('conversationReadErrorView'),
                listView = this.get('listView');

            // Hide error messages if there were any
            createErrorView.hideErrorMessage(false);
            readErrorView.hideErrorMessage(false);
            // Show the panel input so the user can post messages
            listView.showView();
        },

        /**
         * Called when the conversation read fails
         * @private
         */
        _onConversationReadError: function () {
            // Vars
            var createErrorView = this.get('conversationCreateErrorView'),
                readErrorView = this.get('conversationReadErrorView'),
                listView = this.get('listView');

            // Hide preloader
            this._hideActivityIndicator();
            // Hide create error message if there is any
            createErrorView.hideErrorMessage(true);
            // Show read error message
            readErrorView.showErrorMessage(true);
            // Hide the panel input. We don't want users to post any messages now
            listView.hideView();
        },

        /**
         * Called when the user click on the retry button within the read error view
         *
         * @private
         */
        _onConversationReadRetry: function () {
            // Vars
            var model = this.get('model');

            // Reload model
            model.load();
        },

        /**
         * Called whenever the conversation model is updated
         *
         * @private
         */
        _onConversationUpdated: function () {
            // Vars
            var listView = this.get('listView');

            // Show the panel input so the users can post messages
            listView.showView();
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
         * Called when the message text field gains focus
         *
         * @private
         */
        _onMessageTextFieldFocus: function () {
            // Vars
            var model = this.get('model'),
                instance = this;

            // If the users sets focus to the text field
            // and there are still some unread messages
            // reset the counter since we assume that he
            // reads all unread messages
            if (model.get('unreadMessagesCount') > 0) {
                model.resetUnreadMessagesCounter(function (err) {
                    if (!err) {
                        // Reset badge
                        instance._updateBadge(0);
                    }
                });
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

            // No unread messages
            if (value === 0) {
                this._hideBadge();
            } else {
                // Show badge
                this._showBadge();
            }

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
                Y.LIMS.Core.Util.show(badge);
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
                Y.LIMS.Core.Util.hide(badge);
            }
        },

        /**
         * Makes the badge brighter
         *
         * @private
         */
        _brightBadge: function () {
            // Vars
            var badge = this.get('badge');

            badge.removeClass('dimmed');
        },

        /**
         * Makes the badge less noticeable
         *
         * @private
         */
        _dimBadge: function () {
            // Vars
            var badge = this.get('badge');

            badge.addClass('dimmed');
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
            Y.LIMS.Core.Util.show(activityIndicator);
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
            Y.LIMS.Core.Util.hide(activityIndicator);
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
             * Conversation create error view
             *
             * {Y.LIMS.View.ErrorNotificationView}
             */
            conversationCreateErrorView: {
                valueFn: function () {
                    // Vars
                    var container = this.get('panelContent');
                    // Create view
                    return new Y.LIMS.View.ErrorNotificationView({
                        container: container,
                        errorMessage: Y.LIMS.Core.i18n.values.conversationCreateErrorMessage
                    });
                }
            },

            /**
             * Conversation read error view
             *
             * {Y.LIMS.View.ErrorNotificationView}
             */
            conversationReadErrorView: {
                valueFn: function () {
                    // Vars
                    var container = this.get('panelContent');
                    // Create view
                    return new Y.LIMS.View.ErrorNotificationView({
                        container: container,
                        errorMessage: Y.LIMS.Core.i18n.values.conversationReadErrorMessage
                    });
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
