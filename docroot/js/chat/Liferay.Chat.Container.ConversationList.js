AUI().use('aui-base', 'anim', 'aui-live-search', function (A) {

    Liferay.namespace('Chat.Container');

    // ------------------------------------------------------------------------------
    //    Conversation List Container
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Container.ConversationList = function () {
        var instance = this;

        // Panel
        instance.panel = new Liferay.Chat.Panel({
            fromMarkup: '.chat-tabs > .conversations',
            panelId: 'conversations'
        });

        var panel = instance.panel.getPanel();
        // Public vars
        instance.node = panel.one('.current-conversations');
        instance.privateConversations = [];
        instance.publicConversations = [];
        // Private vars                
        instance._tokenQueryURL = Liferay.Chat.Globals.chatPortletURL;
        instance._privateTriggerEl = panel.one('.private-trigger');
        instance._publicTriggerEl = panel.one('.public-trigger');
        instance._noConversationInfoEl = panel.one('.no-conversation-info');
        instance._newConversationTriggerEl = panel.one('.new-conversation-trigger');
        instance._preloader = panel.one('.preloader');
        instance._sendTriggerEl = panel.one('.send');
        instance._cancelTriggerEl = panel.one('.cancel');
        instance._switchEl = panel.one('.switch');
        instance._newConversationPanelEl = panel.one('.panel-new-conversation');
        instance._messageTextareaEl = $('.conversations .message-textarea');
        // Token input
        instance._tokenInputEl = $(".conversations .users-token-input");
        instance._tokenInputEl.tokenInput(instance._tokenQueryURL, {
            theme: "facebook",
            preventDuplicates: true,
            dropdownContainer: '.conversations .panel-window',
            onResult: function (results) {
                var r = [];
                $.each(results, function (index, value) {
                    // Remove yourself from the list
                    if (value.id != themeDisplay.getUserId()) {
                        r.push(value);
                    }
                });

                return r;
            }
        });
        instance._usersListEl = $('.panel-new-conversation ul');

        // Events                                         
        instance._newConversationTriggerEl.on('click', instance._onOpenNewConversation, instance);
        instance._publicTriggerEl.on('click', instance._onPublicConversationSelected, instance);
        instance._privateTriggerEl.on('click', instance._onPrivateConversationSelected, instance);
        instance._sendTriggerEl.on('click', instance._onCreateNewConversation, instance);
        instance._cancelTriggerEl.on('click', instance._onCancelNewConversation, instance);
        instance.node.delegate('click', instance._onSelectedConversation, 'li');
        // Remove error class if user starts to type a message
        instance._messageTextareaEl.bind('input propertychange', function () {
            if (instance._messageTextareaEl.val()) {
                instance._messageTextareaEl.removeClass('error');
            }
        });
        instance._usersListEl.bind('input propertychange', function () {
            instance._usersListEl.removeClass('error');
        });


        // Preloader
        instance.startPreloader();
    };

    Liferay.Chat.Container.ConversationList.prototype = {
        // ------------------------------------------------------------------------------
        //    Public
        // ------------------------------------------------------------------------------
        update: function (conversations) {
            var instance = this;
            // Stop preloader
            instance.stopPreloader();
            // Repair possibly corrupted parameters
            conversations = conversations || [];
            // Clear previous content
            instance.privateConversations = [];
            instance.publicConversations = [];
            // Update conversations
            for (var i = 0; i < conversations.length; i++) {
                var conversation = conversations[i];
                // Add to private conversations
                if (conversation.roomVisibility === Liferay.Chat.PollerKeys.JABBER_ROOM_TYPE_PRIVATE) {
                    instance.privateConversations[conversation.roomJID] = conversation;
                }
                // Add to public conversations
                if (conversation.roomVisibility === Liferay.Chat.PollerKeys.JABBER_ROOM_TYPE_PUBLIC) {
                    instance.publicConversations[conversation.roomJID] = conversation;
                }
            }

            // Update panel content
            // Private
            if (instance._privateTriggerEl.hasClass('selected')) {
                instance._updatePanelContent(instance.privateConversations);
            }
            // Public
            if (instance._publicTriggerEl.hasClass('selected')) {
                instance._updatePanelContent(instance.publicConversations);
            }

        },
        clearContent: function () {
            var instance = this;
            // Calling update with no params will clear list
            instance.update();
            // Hide info becuase technically there is no element in the list but we are
            // waiting for the next ping which will refresh the list
            instance._noConversationInfoEl.hide();
            // Show preloader because list is empty right now and we are wating for the next ping
            instance.startPreloader();
        },
        getConversation: function (roomJID) {
            var instance = this;

            if (instance.privateConversations[roomJID]) {
                return instance.privateConversations[roomJID];
            }
            if (instance.publicConversations[roomJID]) {
                return instance.publicConversations[roomJID];
            }

            return null;
        },
        hidePanels: function (panel) {
            var instance = this;
            if (instance.panel !== panel && instance.panel.isOpened) {
                instance.panel.hide();
            }
        },
        invisible: function (invisible) {
            var instance = this;
            instance.panel.setInvisible(invisible);
        },
        startPreloader: function () {
            var instance = this;
            instance._preloader.show();
        },
        stopPreloader: function () {
            var instance = this;
            instance._preloader.hide();
        },
        // ------------------------------------------------------------------------------
        //    Private
        // ------------------------------------------------------------------------------
        _updatePanelContent: function (conversations) {
            var instance = this;
            var buffer = [''];
            var i = 0;
            for (var key in conversations) {
                var conversation = conversations[key];
                var nodeList = instance._createNodeList(conversation);
                buffer.push(nodeList);
                i++;
            }
            instance.node.html(buffer.join(''));

            // Show no conversations info if necessary
            if (i > 0) {
                instance._noConversationInfoEl.hide();
            } else {
                instance._noConversationInfoEl.show();
            }
        },
        _clearTokens: function () {
            var instance = this;
            instance._tokenInputEl.tokenInput("clear");
        },
        _createNodeList: function (conversation) {
            if (!conversation.buddies) {
                return;
            }
            var userImagePath = Liferay.Chat.Util.getUserImagePath(conversation.buddies[0].portraitId);
            var lastMessage = conversation.lastMessage;
            var content = lastMessage ? lastMessage.content : "";
            var roomJID = conversation.roomJID;
            var title = (conversation.roomVisibility === Liferay.Chat.PollerKeys.JABBER_ROOM_TYPE_PRIVATE) ?
                Liferay.Chat.Util.getMultipleBuddiesTitle(conversation.buddies) :
                conversation.title;

            var nodeList =
                '<li data-roomJID="' + roomJID + '">' +
                    '<img class="portrait" alt="" src="' + userImagePath + '" />' +
                    '<div class="conversation-title">' + title + '</div>' +
                    '<div class="conversation-last-message">' + content + '</div>' +
                    '<div class="clear"></div>' +
                    '</li>';

            return nodeList;
        },
        _openPanelNewConversation: function () {
            var instance = this;
            var animation = new A.Anim({
                node: instance._newConversationPanelEl,
                duration: 0.2,
                to: {
                    height: 185
                }
            });

            animation.run();
            animation.on('end', function () {
                instance._newConversationPanelEl.setStyle("height", "auto");
            });
        },
        _closePanelNewConversation: function () {
            var instance = this;
            var animation = new A.Anim({
                node: instance._newConversationPanelEl,
                duration: 0.2,
                to: {
                    height: 0
                }
            });
            animation.run();
            animation.on('end', function () {
                instance._newConversationTriggerEl.removeClass('no-icon');
                instance._switchEl.show();
            });
            // Clear all tokens in the input fields
            instance._clearTokens();
            // Clear message from text area
            instance._messageTextareaEl.val('');
            // Set focus to text area (if this isn't here dropdown will
            // remain on the screen)
            instance._messageTextareaEl.focus();
            // Remove error classes if there are any
            instance._messageTextareaEl.removeClass('error');
            instance._usersListEl.removeClass('error');
        },
        // ------------------------------------------------------------------------------
        //    Events
        // ------------------------------------------------------------------------------
        _onOpenNewConversation: function () {
            var instance = this;
            // Hide private/public switch
            instance._switchEl.hide();
            // Remove icon from the new conversation element
            instance._newConversationTriggerEl.addClass('no-icon');
            // Open panel
            instance._openPanelNewConversation();
            // Set focus on the input field
            instance._tokenInputEl.focus();
        },
        _onCancelNewConversation: function () {
            var instance = this;
            // Close panel
            instance._closePanelNewConversation();
        },
        _onCreateNewConversation: function () {
            var instance = this;
            var users = instance._tokenInputEl.val();
            var message = instance._messageTextareaEl.val();

            // Check for empty inputs
            if (!users) {
                instance._usersListEl.addClass('error');
            }

            if (!message) {
                instance._messageTextareaEl.addClass('error');
            }

            if (!users || !message) {
                return;
            }

            // Close panels
            instance._closePanelNewConversation();
            instance.panel.hide();

            // Fire an event
            A.fire('createNewConversation', {users: users, message: message});
        },
        _onSelectedConversation: function (event) {
            var target = event.currentTarget;
            // Fire selected room JID
            if (target.ancestor('li')) {
                var roomJID = target.getAttribute('data-roomJID');
                A.fire('selectedConversation', roomJID);
            }
        },
        _onPublicConversationSelected: function () {
            var instance = this;
            // Switch buttons
            instance._publicTriggerEl.addClass('selected');
            instance._privateTriggerEl.removeClass('selected');
            // Update panel content
            instance._updatePanelContent(instance.publicConversations);
            // Fire event
            A.fire('publicConversationSelected');
        },
        _onPrivateConversationSelected: function () {
            var instance = this;
            // Switch buttons
            instance._publicTriggerEl.removeClass('selected');
            instance._privateTriggerEl.addClass('selected');
            // Update panel content
            instance._updatePanelContent(instance.privateConversations);
            // Fire event
            A.fire('privateConversationSelected');
        }
    };
});