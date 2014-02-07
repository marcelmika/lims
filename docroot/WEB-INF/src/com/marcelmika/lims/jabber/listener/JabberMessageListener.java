
package com.marcelmika.lims.jabber.listener;

import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.JabberUtil;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberMessageListener implements MessageListener {

    private Conversation conversation;

    public JabberMessageListener() {

    }

    public JabberMessageListener(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
        // Message adapter
        Message msg = new Message(message);
        // Extract  jabber name
        msg.setFrom(JabberUtil.getScreenName(msg.getFrom()));
        msg.setCompanyId(conversation.getOwner().getCompanyId());

        // Add only messages with sender
        if (msg.getFrom() != null) {
            conversation.getMessages().add(msg);
//            System.out.println("[CONVERSATION][" + conversation.getOwner().getScreenName() + "] " + msg.getFrom() + ": " + msg.getBody());

//            System.out.println("[Current Messages stack:] " + conversation.getMessages());
        } else {
//            System.out.println("[CONVERSATION][" + conversation.getOwner().getScreenName() + "] Message with no sender: " + msg.getBody());
        }
    }

}