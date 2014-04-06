package com.marcelmika.lims.jabber.conversation.manager.single;

import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import org.jivesoftware.smack.ChatManager;

import java.util.List;

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


    /**
     * Returns a list of all conversations
     *
     * @return SingleUserConversation list of conversations
     */
    public List<SingleUserConversation> getConversations();

    /**
     * Sends message to conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     */
    public SingleUserConversation sendMessage(SingleUserConversation conversation,
                                              Message message) throws JabberException;

}
