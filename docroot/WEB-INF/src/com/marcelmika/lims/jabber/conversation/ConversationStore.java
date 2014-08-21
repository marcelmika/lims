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
 * @deprecated
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
