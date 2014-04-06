package com.marcelmika.lims.jabber.conversation.manager.single;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    // Override: SingleUserConversationManager
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

        // No conversation found
        if (localConversation == null) {
            log.info("No local conversation");
            // Receiver
            Buddy receiver = message.getTo();
            // Create a new chat, this calls chatCreated() method on both sides
            // so we don't need to take care of it here
            Chat chat = chatManager.createChat(receiver.getScreenName(), null);
            try {
                chat.sendMessage(message.getBody());
                // Create new conversation
                SingleUserConversation c = SingleUserConversation.fromChat(chat);
                // Add chat pointer to chat map, otherwise it will be garbage collected.
                chatMap.put(chat.getThreadID(), chat);
                // Add conversation to the map
                conversationMap.put(chat.getThreadID(), c);

                return c;
            } catch (XMPPException e) {
                throw new JabberException(e);
            }


        } else {
            log.info("Local conversation found");
            // Get chat from map
            Chat chat = chatMap.get(localConversation.getConversationId());
            try {
                // Send message via chat
                chat.sendMessage(message.getBody());

                return localConversation;
            } catch (XMPPException e) {
                throw new JabberException(e);
            }
        }
    }


    // -------------------------------------------------------------------------------------------
    // Override: ChatManagerListener
    // -------------------------------------------------------------------------------------------

    /**
     * Called when the chat was created
     *
     * @param chat           the chat that was created
     * @param createdLocally true if the chat was created by the local user
     */
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        if (!createdLocally) {
            // Create new conversation
            SingleUserConversation conversation = SingleUserConversation.fromChat(chat);
            // Add chat pointer to chat map, otherwise it will be garbage collected.
            chatMap.put(chat.getThreadID(), chat);
            // Add conversation to the map
            conversationMap.put(chat.getThreadID(), conversation);
            log.info("Chat created remotely with id: " + chat.getThreadID());
        } else {
            log.info("Chat created locally with id: " + chat.getThreadID());
        }
    }


    /**
     * Create a new conversation
     *
     * @param buddy Participant of the new conversation.
     * @throws JabberException return A new Conversation.
     */
//    private SingleUserConversation createConversation(Buddy buddy) throws JabberException {
//        // Create a new chat, this calls chatCreated() method
//        Chat chat = chatManager.createChat(buddy.getScreenName(), null);
//        if (chat == null) {
//            throw new JabberException("Created chat was null");
//        }
////        return cre
//    }
}
