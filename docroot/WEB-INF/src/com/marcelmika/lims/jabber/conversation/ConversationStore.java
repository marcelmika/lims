package com.marcelmika.lims.jabber.conversation;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton which stores all conversation containers within the application
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ConversationStore {
    Map<Long, ConversationContainer> conversationContainers = new HashMap<Long, ConversationContainer>();

    // Private constructor prevents instantiation from other classes
    private ConversationStore() {
    }

    public static ConversationStore getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Returns conversation container which belongs to the particular user based on the userID
     *
     * @param userId Liferay ID of the user
     * @return ConversationContainer
     */
    public ConversationContainer getConversationContainer(long userId) {
        return conversationContainers.get(userId);
    }

    /**
     * Removes conversation container from the conversation store based on the userID
     *
     * @param userId Liferay ID of the user
     */
    public void removeConversationContainer(long userId) {
        conversationContainers.remove(userId);
    }

    /**
     * Puts conversation container to the conversation store
     *
     * @param userId    Liferay ID of the user
     * @param container Container which will be added to the conversation store
     */
    public void putConversationContainer(long userId, ConversationContainer container) {
        conversationContainers.put(userId, container);
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before. This is based on
     * the Bill Pugh solution for singleton
     */
    private static class SingletonHolder {
        public static final ConversationStore INSTANCE = new ConversationStore();
    }
}
