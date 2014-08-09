package com.marcelmika.lims.jabber.conversation.manager.single;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 12:50 AM
 */
public class SingleUserConversationManagerFactory {

    public static SingleUserConversationManager buildManager() {
        return new SingleUserConversationManagerImpl();
    }
}
