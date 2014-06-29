package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:55 AM
 */
public class Conversation {

    private String conversationId;
    private ConversationType conversationType;
    private List<Buddy> participants = new ArrayList<Buddy>();
    private List<Message> messages = new ArrayList<Message>();

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new list of Conversations from the list of ConversationDetails
     *
     * @param detailsList list of conversation details
     * @return List<Conversation> of conversations
     */
    public static List<Conversation> fromConversationDetails(List<ConversationDetails> detailsList) {
        // Create new list of conversations
        List<Conversation> conversations = new ArrayList<Conversation>();

        // Iterate through details and create conversation based on that
        for (ConversationDetails details : detailsList) {
            conversations.add(Conversation.fromConversationDetails(details));
        }

        return conversations;
    }

    /**
     * Factory method which creates new Conversation from the ConversationDetails
     *
     * @param details conversation details
     * @return Conversation
     */
    public static Conversation fromConversationDetails(ConversationDetails details) {
        // Create new conversation
        Conversation conversation = new Conversation();
        // Map parameters
        conversation.conversationId = details.getConversationId();

        // Relations
        if (details.getParticipants() != null) {
            List<Buddy> participants = new LinkedList<Buddy>();
            for (BuddyDetails participant : details.getParticipants()) {
                participants.add(Buddy.fromBuddyDetails(participant));
            }
            conversation.participants = participants;
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetails(details.getMessages());
        }

        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        return conversation;
    }

    /**
     * Maps conversation to conversation details
     *
     * @return ConversationDetails
     */
    public ConversationDetails toConversationDetails() {
        // Create conversation details
        ConversationDetails details = new ConversationDetails();
        // Map data from conversation
        details.setConversationId(conversationId);

        // Relations
        if (conversationType != null) {
            details.setConversationType(conversationType.toConversationTypeDetails());
        }

        if (participants != null) {
            List<BuddyDetails> participantDetails = new LinkedList<BuddyDetails>();
            for (Buddy participant : participants) {
                participantDetails.add(participant.toBuddyDetails());
            }
            details.setParticipants(participantDetails);
        }

        return details;
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

    public List<Buddy> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Buddy> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
