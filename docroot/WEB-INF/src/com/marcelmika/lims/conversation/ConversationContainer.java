
package com.marcelmika.lims.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ConversationContainer {

    private HashMap<String, Conversation> conversations = new HashMap<String, Conversation>();

    /**
     * Adds conversation to the container. If the conversation is already in the
     * container nothing happens
     * @param conversationId conversation id
     * @param conversation conversation
     */
    public void addConversation(String conversationId, Conversation conversation) {
        if(conversation == null) {
            return;
        }

        if (!conversations.containsKey(conversationId)) {
            conversations.put(conversationId, conversation);
//            System.out.println("[CONTAINER][ADD] " + conversation.toJSON());
        } else {
//            System.out.println("[CONTAINER][ADD] " + conversationId + " is already in the container");
        }
    }

    /**
     * Returns conversation based on its id
     * @param conversationId conversation ID
     * @return Conversation
     */
    public Conversation getConversation(String conversationId) {
        return conversations.get(conversationId);
    }

    /**
     * Returns all conversations in the container
     * @return List<Conversation>
     */
    public List<Conversation> getAllConversations() {
        return new ArrayList<Conversation>(conversations.values());
    }

    /**
     * Removes conversation from the container
     * @param conversationId conversation ID
     */
    public void removeConversation(String conversationId) {
        conversations.remove(conversationId);
    }

    /**
     * Restarts all conversations in the container
     */
    public void restartConversations() {
        for (Conversation conversation : conversations.values()) {
            conversation.restart();
        }
    }
}
