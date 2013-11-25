
AUI().use('aui-base', function(A) {

    Liferay.namespace('Chat.Container');

    // ------------------------------------------------------------------------------
    //    Conversation Sessions Container
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Container.ConversationSessions = function() {
        var instance = this;

        instance.conversations = [];

        A.all(".chat-tabs .conversation").each(function(conversationNode) {
            var panelId = conversationNode.getAttribute("panelId");
            var c = new Liferay.Chat.Conversation({
                panelId: panelId,
                fromMarkup: '.chat-tabs #conversation_' + panelId
            });
            instance.conversations[panelId] = c;
        });
    };

    Liferay.Chat.Container.ConversationSessions.prototype = {
        addConversation: function(conversation) {
            var instance = this;

            // Conversation already exists
            if (instance.conversations[conversation.roomJID]) {
                instance.conversations[conversation.roomJID].show();
            }
            // Create new conversation
            else {
                var portraitId = conversation.lastMessage ?
                        conversation.lastMessage.from.portraitId : "";

                var c = new Liferay.Chat.Conversation({
                    panelId: conversation.roomJID,
                    panelTitle: conversation.title,
                    panelIcon: Liferay.Chat.Util.getUserImagePath(portraitId)
                });

                // Add to container
                instance.conversations[conversation.roomJID] = c;
            }
        },
        getConversation: function(roomJID) {
            var instance = this;
            return instance.conversations[roomJID];
        },
        removeConversation: function(roomJID) {
            var instance = this;            
            delete instance.conversations[roomJID];
        },
        updateActiveConversation: function(conversation) {
            var instance = this;
            // Find given conversation
            var activeConversation = instance.conversations[conversation.roomJID];

            if (activeConversation) {
                // Update
                activeConversation.update(conversation);
            }
        },
        updateConversations: function(conversations) {
            var instance = this;

            for (var index in conversations) {
                var c = conversations[index];
                if (c && c.roomJID) {

                    // If there is no such conversation create a new one
                    // This usually happens when other buddy sends a message
                    // to the conversation which isn't opened.
                    if (!instance.getConversation(c.roomJID)) {
                        instance.addConversation(c);
                    }

                    // Update conversation
                    var activeConversation = instance.getConversation(c.roomJID);
                    activeConversation.update(c);
                }
            }
        },
        hidePanels: function(panel) {
            var instance = this;
            for (var conversationId in instance.conversations) {
                var p = instance.conversations[conversationId];
                if (p !== panel && p.isOpened) {
                    p.hide();
                }
            }
        },
        invisible: function(invisible) {
            var instance = this;
            for (var conversationId in instance.conversations) {
                var p = instance.conversations[conversationId];
                p.setInvisible(invisible);
            }
        }
    };
});