

AUI().use(
        'aui-base',
        'liferay-poller',
        'stylesheet',
        function(A) {

            Liferay.namespace('Chat');

            Liferay.Chat.Manager = {
                init: function() {

                    Liferay.LIMS.Model.getGroups();

                    var instance = this;
                    // Instance Vars Private
                    instance._chatContainerEl = Liferay.Chat.Globals.chatContainerEl;
                    instance._chatBarEl = instance._chatContainerEl.one('.chat-bar');
                    instance._portletId = Liferay.Chat.Globals.chatPortletId;
                    instance._created = Liferay.Chat.Util.getCurrentTimestamp();
                    instance._chatSuspended = !(Liferay.Chat.Globals.chatPortletEnabled);
                    instance._closedConversationsBuffer = [];
                    instance._containers = {};

                    // Instance Vars Public
                    instance.notificator = new Liferay.Chat.Notification();
                    instance.errorMessage = new Liferay.Chat.Error();

                    // Instance Events
                    A.on('pollerUpdate', instance._onPollerUpdate, instance);
                    A.on('pollerSessionExpired', instance._onPollerSessionExpired, instance);
                    A.on('showPanel', instance._onPanelShow, instance);
                    A.on('hidePanel', instance._onPanelHide, instance);
                    A.on('closePanel', instance._onPanelClose, instance);
                    A.on('selectedConversation', instance._openConversation, instance);
                    A.on('selectedBuddy', instance._onCreateSingleUserConversation, instance);
                    A.on('statusChanged', instance._onStatusChanged, instance);
                    A.on('settingsChanged', instance._onSettingsChanged, instance);
                    A.on('createNewConversation', instance._onCreateNewConversation, instance);
                    A.on('publicConversationSelected', instance._onPublicConversationSelected, instance);
                    A.on('privateConversationSelected', instance._onPrivateConversationSelected, instance);
                    A.on('leaveConversationSelected', instance._onLeaveConversationSelected, instance);
                    A.on('messageSend', instance._onMessageSend, instance);
                    A.on('addToConversationSelected', instance._onAddToConversation, instance);

                    A.on('Model:GroupsChanged', instance._foo, instance);

                    // Create containers
                    instance._initContainers();

                    // Start poller
                    Liferay.Chat.Poller.init();
                    // Chat isn't enabled thus there is no need to start a poller
                    if (instance._chatSuspended) {
                        Liferay.Chat.Poller.suspend();
                    }
                },

                _foo: function(groups) {
                    var instance = this;
                  console.log(groups);
                    instance._getContainer('buddyList').update(groups);
                },

                // ------------------------------------------------------------------------------
                //    Containers
                // ------------------------------------------------------------------------------
                _initContainers: function() {
                    var instance = this;

                    // Crate
                    var statusContainer = new Liferay.Chat.Container.Status();
                    var buddyListContainer = new Liferay.Chat.Container.BuddyList();
                    var settingsContainer = new Liferay.Chat.Container.Settings();
                    var conversationListContainer = new Liferay.Chat.Container.ConversationList();
                    var conversationSessionsContainer = new Liferay.Chat.Container.ConversationSessions();

                    // Add to instance
                    instance._addContainer('status', statusContainer);
                    instance._addContainer('buddyList', buddyListContainer);
                    instance._addContainer('conversationMap', conversationListContainer);
                    instance._addContainer('settings', settingsContainer);
                    instance._addContainer('conversationSessions', conversationSessionsContainer);
                },
                _getContainer: function(containerId) {
                    var instance = this;
                    return instance._containers[containerId];
                },
                _addContainer: function(containerId, container) {
                    var instance = this;
                    instance._containers[containerId] = container;
                },
                // ------------------------------------------------------------------------------
                //    Conversation
                // ------------------------------------------------------------------------------
                _openConversation: function(roomJID) {
                    var instance = this;

                    // Get particular conversation from the conversations list
                    var conversationsListContainer = instance._getContainer('conversationMap');
                    var conversation = conversationsListContainer.getConversation(roomJID);
                    // Add it to conversation sessions container
                    var conversationSessionContainer = instance._getContainer('conversationSessions');
                    conversationSessionContainer.addConversation(conversation);

                    // Hide conversation list panel
                    conversationsListContainer.panel.hide();
                    // Open conversation panel
                    conversationSessionContainer.getConversation(roomJID).show();

                    // Send to liferay 
                    Liferay.Chat.Poller.send({
                        roomJID: roomJID
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_OPEN_CONVERSATION);
                },
                _closeConversation: function(roomJID) {
                    var instance = this;
                    // Remove from session container                    
                    instance._getContainer('conversationSessions').removeConversation(roomJID);
                    // Add to buffer
                    instance._closedConversationsBuffer.push(roomJID);
                    // Send to liferay 
                    Liferay.Chat.Poller.send({
                        roomJID: roomJID
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CLOSE_CONVERSATION);
                },
                // ------------------------------------------------------------------------------
                //    Events
                // ------------------------------------------------------------------------------         
                _onPanelClose: function(panel) {
                    var instance = this;
                    // We don't care about container's panels
                    // TODO: rewrite to for cycle and iterate throught keys in instance._containers
                    if (panel === 'buddylist' || panel === 'conversations' || panel === 'settings' || panel === 'status') {
                        return;
                    }
                    // Remove panel from conversations container
                    var conversationContainer = instance._getContainer('conversationSessions');
                    conversationContainer.removeConversation(panel._panelId);

                    // Close conversation
                    instance._closeConversation(panel._panelId);

                    // Save on server side
                    Liferay.Chat.Poller.send({
                        activePanelId: ""
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHANGE_ACTIVE_PANEL);
                },
                _onPanelHide: function(panel) {
                    // Save on server side
                    Liferay.Chat.Poller.send({
                        activePanelId: ""
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHANGE_ACTIVE_PANEL);
                },
                _onPanelShow: function(panel) {
                    var instance = this;
                    // Hide others
                    for (var containerId in instance._containers) {
                        instance._containers[containerId].hidePanels(panel);
                    }
                    // Focus conversation
                    if (panel instanceof Liferay.Chat.Conversation) {
                        panel._chatInput.focus();
                    }
                    // Save to server
                    Liferay.Chat.Poller.send({
                        activePanelId: panel._panelId
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHANGE_ACTIVE_PANEL);
                },
                _onPollerUpdate: function(response) {
                    var instance = this;
                    return;
                    // No updates while the chat is off
                    if (instance._chatSuspended) {
                        // Suspend poller
                        Liferay.Chat.Poller.suspend();
                        return;
                    }

                    // Handle error
                    instance._handleError(response);
                    // End flow on error
                    if (response.error) {
                        return;
                    }

                    console.log(response);

                    // Handle Initial request
                    instance._handleInitialRequest(response);
                    // Update conversation list
                    instance._getContainer('conversationMap').update(response.conversations);
                    // Update buddy list container
                    if(response.initialRequest) {
                        instance._getContainer('buddyList').update(response.groups);
                    }
                    // Handle Opened Conversations
                    instance._handleOpenedConversations(response.openedConversations);
                },
                _onPollerSessionExpired: function() {
                    var instance = this;
                    instance._chatContainerEl.hide();
                },
                _onSettingsChanged: function(settings) {
                    var instance = this;
                    // Set values                    
                    instance.notificator.setMute(settings.mute);
                    // Send new settings to server
                    Liferay.Chat.Poller.send({
                        mute: settings.mute
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_SAVE_SETTINGS);
                },
                _onStatusChanged: function(status) {
                    var instance = this;

                    // Switching chat off                   
                    if (status === Liferay.Chat.PollerKeys.JABBER_STATUS_OFF) {
                        instance._handleSuspend(status);
                        return;
                    }
                    // Switch chat on
                    if (status !== Liferay.Chat.PollerKeys.JABBER_STATUS_OFF && instance._chatSuspended) {
                        instance._handleResume(status);
                        return;
                    }

                    // Send new status to liferay
                    Liferay.Chat.Poller.send({
                        status: status
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHANGE_STATUS);


                },
                _onPublicConversationSelected: function() {
                    // Send new status to liferay
                    Liferay.Chat.Poller.send({
                        roomVisibility: Liferay.Chat.PollerKeys.JABBER_ROOM_TYPE_PUBLIC
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHANGE_ACTIVE_ROOM_TYPE);
                },
                _onPrivateConversationSelected: function() {
                    // Send new status to liferay
                    Liferay.Chat.Poller.send({
                        roomVisibility: Liferay.Chat.PollerKeys.JABBER_ROOM_TYPE_PRIVATE
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHANGE_ACTIVE_ROOM_TYPE);
                },
                _onLeaveConversationSelected: function(roomJID) {
                    var instance = this;
                    // Send to server                    
                    Liferay.Chat.Poller.send({
                        roomJID: roomJID
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_LEAVE_CONVERSATION);
                    // Close conversation
                    var conversation = instance._getContainer('conversationSessions').getConversation(roomJID);
                    conversation.close();
                    // Remove from conversation list
                    instance._getContainer('conversationMap').clearContent();
                },
                _onCreateSingleUserConversation: function(screenName) {
                    // Send to server
                    Liferay.Chat.Poller.send({
                        screenName: screenName
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CREATE_SINGLE_USER_CONVERSATION);
                    // todo: Create empty message thread
                },
                _onCreateNewConversation: function(data) {
                    // Send to server 
                    Liferay.Chat.Poller.send({
                        users: data.users,
                        message: data.message
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CREATE_MESSAGE);
                },
                _onMessageSend: function(data) {
                    // Send to server
                    Liferay.Chat.Poller.send({
                        roomJID: data.roomJID,
                        message: data.message
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_SEND_MESSAGE);

                },
                _onAddToConversation: function(data) {
                    // Send to server
                    Liferay.Chat.Poller.send({
                        roomJID: data.roomJID,
                        users: data.users
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_ADD_TO_CONVERSATION);
                },
                // ------------------------------------------------------------------------------
                //    Handlers
                // ------------------------------------------------------------------------------
                _handleInitialRequest: function(response) {
                    var instance = this;
                    // Dont trigger sound on initial request                        
                    var mute = instance._getContainer('settings').getMute();
                    if (response.initialRequest) {
                        Liferay.Chat.Manager.notificator.setMute(true);
                    } else {
                        // Set to real value
                        Liferay.Chat.Manager.notificator.setMute(mute);
                    }
                },
                _handleOpenedConversations: function(conversations) {
                    var instance = this;
                    if (conversations) {
                        // Check if the conversation wasn't already closed. There is a possibility
                        // that we close the conversation but before the action is taken we get 
                        // a ping from server that defines given conversation opened.
                        for (var i in conversations) {
                            var conversation = conversations[i];
                            if (Liferay.Chat.Util.contains(conversation.roomJID, instance._closedConversationsBuffer)) {
                                delete conversations[i];
                            }
                        }

                        // Add to container
                        instance._getContainer('conversationSessions').updateConversations(conversations);
                    }

                    // Clear buffer
                    instance._closedConversationsBuffer = [];
                },
                _handleError: function(response) {
                    var instance = this;
                    // Handle error
                    if (response.error) {
                        var message = "Liferay IMS error";
                        if(response.error.indexOf("not connected to Jabber") != -1) {
                            message = "Liferay IMS is not connected to the Jabber server";
                        }
                        instance.errorMessage.setMessage(message);
                        // Show error message
                        instance.errorMessage.show();
                        // Hide chat bar so the user cannot interact
                        instance._chatBarEl.hide();
                    } else {
                        // Hide error messages
                        instance.errorMessage.hide();
                        // Show the whole chat
                        instance._chatBarEl.show();
                    }
                },
                _handleSuspend: function(status) {
                    var instance = this;
                    // Hide all containers except of the status container  
                    instance._getContainer('buddyList').invisible(true);
                    instance._getContainer('conversationMap').invisible(true);
                    instance._getContainer('settings').invisible(true);
                    instance._getContainer('conversationSessions').invisible(true);
                    // Save to server                    
                    Liferay.Chat.Poller.send({
                        enabled: false,
                        status: status
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHAT_ENABLED);
                    // Save to instance
                    instance._chatSuspended = true;
                },
                _handleResume: function(status) {
                    var instance = this;
                    // Recreate containers
                    instance._getContainer('buddyList').invisible(false);
                    instance._getContainer('conversationMap').invisible(false);
                    instance._getContainer('settings').invisible(false);
                    instance._getContainer('conversationSessions').invisible(false);
                    // Resume poller
                    Liferay.Chat.Poller.resume();
                    // Save to server
                    Liferay.Chat.Poller.send({
                        enabled: true,
                        status: status
                    }, Liferay.Chat.PollerKeys.POLLER_ACTION_CHAT_ENABLED);
                    // Save to instance
                    instance._chatSuspended = false;
                },
                // ------------------------------------------------------------------------------
                //    Deprecated
                // ------------------------------------------------------------------------------                                
                _chatSessions: {},
                _addChat: function(chatName, chat) {
                    var instance = this;
                    instance._chatSessions[chatName] = chat;
                },
                _createChatSession: function(options) {
                    var instance = this;
                    // Create new conversation
                    var conversation = new Liferay.Chat.Conversation({
                        panelId: options.userId,
                        panelTitle: options.fullName,
                        panelIcon: options.portraitId
                    });

                    var userId = options.userId;

                    // TODO: Depricated?
                    instance._addChat(userId, conversation);

                    // Show window
                    if (options.open) {
                        conversation.show();
                    }

                    return conversation;
                }
            };

            A.augment(Liferay.Chat.Manager, A.Attribute, true);

        }
);