package com.marcelmika.lims.jabber.domain;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:07 AM
 */
public class SingleUserConversation implements MessageListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(SingleUserConversation.class);

    private Buddy participant;
    private List<Message> messages = new ArrayList<Message>();


    /**
     * Factory method creates single user conversation from smack chat
     *
     * @param chat Conversation is created from the Chat.
     * @return SingleUserConversation
     */
    public static SingleUserConversation fromChat(Chat chat) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
//        conversation.threadId = chat.getThreadID();
        // Map relationships
        // Add buddy to conversation
        conversation.participant = Buddy.fromChat(chat);
        // Set conversation as a message listener. Thanks to that it
        // will be able to change inner content
        chat.addMessageListener(conversation);

        return conversation;
    }


    // -------------------------------------------------------------------------------------------
    // Override: MessageListener
    // -------------------------------------------------------------------------------------------

    /**
     * Called whenever the buddy receives message
     *
     * @param smackMessage which was received
     */
    @Override
    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message smackMessage) {
        // Create new message from smack message
        Message message = Message.fromSmackMessage(smackMessage);

        log.info(String.format("From: %s, Body: %s", message.getFrom().getScreenName(), message.getBody()));

        messages.add(message);
    }

    public Buddy getParticipant() {
        return participant;
    }

    public void setParticipant(Buddy participant) {
        this.participant = participant;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
