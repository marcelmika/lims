package com.marcelmika.lims.jabber.conversation.manager.single;

import org.jivesoftware.smack.ChatManager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:40 PM
 */
public interface SingleUserConversationManager {

    /**
     * Manage conversations from chat manager
     *
     * @param chatManager ChatManager
     */
    public void setChatManager(ChatManager chatManager);


}
