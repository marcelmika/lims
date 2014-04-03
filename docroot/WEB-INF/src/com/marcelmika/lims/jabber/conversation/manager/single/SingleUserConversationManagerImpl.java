package com.marcelmika.lims.jabber.conversation.manager.single;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;

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
        // Create new conversation
        SingleUserConversation conversation = SingleUserConversation.fromChat(chat);
        // Add chat pointer to chat map, otherwise it will be garbage collected;
        chatMap.put(chat.getThreadID(), chat);
        // Add conversation to the map
        conversationMap.put(chat.getThreadID(), conversation);
    }
}
