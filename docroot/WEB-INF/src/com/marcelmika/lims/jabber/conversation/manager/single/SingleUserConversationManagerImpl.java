package com.marcelmika.lims.jabber.conversation.manager.single;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import com.marcelmika.lims.jabber.utils.Jid;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPException;

import java.util.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:20 PM
 */
public class SingleUserConversationManagerImpl implements SingleUserConversationManager, ChatManagerListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(SingleUserConversationManagerImpl.class);

    // Smack Chat Manager
    private ChatManager chatManager;

    private Map<String, Chat> chatMap = Collections.synchronizedMap(new HashMap<String, Chat>());
    private Map<String, SingleUserConversation> conversationMap = Collections.synchronizedMap(
            new HashMap<String, SingleUserConversation>()
    );


    // -------------------------------------------------------------------------------------------
    // Single User Conversation Manager
    // -------------------------------------------------------------------------------------------

    /**
     * Manage conversations from chat manager
     *
     * @param chatManager ChatManager
     */
    @Override
    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;

        // Add listener
        this.chatManager.addChatListener(this);
    }

    /**
     * Returns a list of all conversations
     *
     * @return SingleUserConversation list of conversations
     */
    @Override
    public List<SingleUserConversation> getConversations() {
        return new ArrayList<SingleUserConversation>(conversationMap.values());
    }

    /**
     * Sends message to conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     */
    @Override
    public SingleUserConversation sendMessage(SingleUserConversation conversation,
                                              Message message) throws JabberException {
        // Find local conversation based on the id taken from conversation from parameter
        SingleUserConversation localConversation = conversationMap.get(conversation.getConversationId());

        // Send a message to the conversation which was already in the system
        if (localConversation != null) {
            try {
                // Conversation was already created so take the chat from map
                Chat chat = chatMap.get(localConversation.getConversationId());
                // Send message via chat
                chat.sendMessage(message.getBody());

            } catch (Exception e) {
                throw new JabberException(e.getMessage(), e);
            }
        }
        // Send a message to the conversation which is not created yet
        else {
            try {
                // Receiver
                Buddy receiver = message.getTo();
                // Receiver's Jid
                String receiverJid = Jid.getJid(receiver.getScreenName());
                // Create a new chat
                Chat chat = chatManager.createChat(receiverJid, null);
                // Create a conversation from chat
                localConversation = createConversation(chat);
                // Send message via new conversation
                chat.sendMessage(message.getBody());

            } catch (Exception e) {
                throw new JabberException(e.getMessage(), e);
            }
        }


        // Return conversation
        return localConversation;
    }


    // -------------------------------------------------------------------------------------------
    // Chat Manager Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called when the chat was created
     *
     * @param chat           the chat that was created
     * @param createdLocally true if the chat was created by the local user
     */
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        // Only if the remote user created the conversation
        if (!createdLocally) {
            // Create new conversation
            createConversation(chat);
        } else {
            // We don't need to care about it here because the chat was already
            // created in sendMessage() method called by the local user.
            log.info("Chat created locally with id: " + chat.getThreadID());
        }
    }


    // -------------------------------------------------------------------------------------------
    // Private Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Stores chat in the system
     *
     * @param chat Chat
     * @return SingleUserConversation create conversation
     */
    private SingleUserConversation createConversation(Chat chat) {
        // Create new conversation
        SingleUserConversation conversation = SingleUserConversation.fromChat(chat);
        // Add chat pointer to chat map, otherwise it will be garbage collected.
        chatMap.put(conversation.getConversationId(), chat);
        // Add conversation to the map
        conversationMap.put(conversation.getConversationId(), conversation);

        return conversation;
    }
}
