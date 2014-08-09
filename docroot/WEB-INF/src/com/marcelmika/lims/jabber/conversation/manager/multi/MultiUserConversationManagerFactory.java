package com.marcelmika.lims.jabber.conversation.manager.multi;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 7:32 PM
 */
public class MultiUserConversationManagerFactory {


    public static MultiUserConversationManager buildManager() {
        return new MultiUserConversationManagerImpl();
    }

}
