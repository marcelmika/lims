package com.marcelmika.lims.api.entity;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:34 PM
 */
public class ConversationDetails {

    // Properties
    private String conversationId;
    private ConversationTypeDetails conversationType;
    private Integer unreadMessagesCount;
    private BuddyDetails buddy;
    private List<MessageDetails> messages;
    private List<BuddyDetails> participants;


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationTypeDetails getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationTypeDetails conversationType) {
        this.conversationType = conversationType;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public void setBuddy(BuddyDetails buddy) {
        this.buddy = buddy;
    }

    public List<BuddyDetails> getParticipants() {
        return participants;
    }

    public void setParticipants(List<BuddyDetails> participants) {
        this.participants = participants;
    }


    public List<MessageDetails> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDetails> messages) {
        this.messages = messages;
    }
}
