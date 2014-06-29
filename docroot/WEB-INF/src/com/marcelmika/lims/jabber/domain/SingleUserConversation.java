package com.marcelmika.lims.jabber.domain;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;
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
        conversation.conversationId = chat.getParticipant();
        // Map relationships
        conversation.participant = Buddy.fromChat(chat);
        conversation.conversationType = ConversationType.SINGLE_USER;
        // Set conversation as a message listener. Thanks to that it
        // will be able to change inner content
        chat.addMessageListener(conversation);

        return conversation;
    }

    public static SingleUserConversation fromConversationDetails(ConversationDetails details) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Map properties
        conversation.conversationId = details.getConversationId();

        // Relations
        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        if (details.getParticipants() != null && details.getParticipants().size() > 0) {
            // Get first participant (in general there shouldn't ever by more than 1 participants in a
            // single user conversation
            conversation.participant = Buddy.fromBuddyDetails(details.getParticipants().get(0));
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetails(details.getMessages());
        }

        return conversation;
    }

    public static List<ConversationDetails> toConversationDetailsList(List<SingleUserConversation> conversations) {
        // Create new list
        List<ConversationDetails> details = new ArrayList<ConversationDetails>();
        // Map
        for (SingleUserConversation conversation : conversations) {
            details.add(conversation.toConversationDetails());
        }

        return details;
    }

    public ConversationDetails toConversationDetails() {
        // Create new details
        ConversationDetails details = new ConversationDetails();
        // Properties
        details.setConversationId(conversationId);

        // Relations
        if (conversationType != null) {
            details.setConversationType(conversationType.toConversationTypeDetails());
        }

        if (messages != null) {
            details.setMessages(Message.toMessageDetailsList(messages));
        }

        if (participant != null) {
            List<BuddyDetails> participants = new ArrayList<BuddyDetails>();
            participants.add(participant.toBuddyDetails());
            details.setParticipants(participants);
        }

        return details;
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
