package com.marcelmika.lims.core.domain;

import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:26 PM
 */
public class Conversation {

    // Properties
    private String conversationId;
    private ConversationType conversationType;
    private Integer unreadMessagesCount;
    private Buddy buddy;
    private List<Buddy> participants;
    private List<Message> messages;
    private Date updatedAt;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new Conversation from the ConversationDetails
     *
     * @param details ConversationDetails
     * @return Conversation
     */
    public static Conversation fromConversationDetails(ConversationDetails details) {
        // Create new Conversation
        Conversation conversation = new Conversation();

        // Parameters
        conversation.conversationId = details.getConversationId();
        conversation.unreadMessagesCount = details.getUnreadMessagesCount();
        conversation.updatedAt = details.getUpdatedAt();

        // Relations
        if (details.getBuddy() != null) {
            conversation.buddy = Buddy.fromBuddyDetails(details.getBuddy());
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
        details.setUpdatedAt(updatedAt);

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

    public List<Buddy> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Buddy> participants) {
        this.participants = participants;
    }

    public void addParticipant(Buddy participant) {
        participants.add(participant);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
