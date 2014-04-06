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

    private String conversationId;
    private ConversationType conversationType;
    private Buddy participant;
    private List<Message> messages = new ArrayList<Message>();


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method creates single user conversation from smack chat
     *
     * @param chat Conversation is created from the Chat.
     * @return SingleUserConversation
     */
    public static SingleUserConversation fromChat(Chat chat) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Map properties
        conversation.conversationId = chat.getThreadID();
        // Map relationships
        conversation.participant = Buddy.fromChat(chat);
        conversation.conversationType = ConversationType.SINGLE_USER;
        // Set conversation as a message listener. Thanks to that it
        // will be able to change inner content
        chat.addMessageListener(conversation);

        return conversation;
    }


    // -------------------------------------------------------------------------------------------
    // Message Listener
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


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
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
