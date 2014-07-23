package com.marcelmika.lims.portal.domain;

import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Conversation {

    // Properties
    private String conversationId;
    private ConversationType conversationType;
    private Integer unreadMessagesCount;
    private Buddy buddy;
    private List<Buddy> participants;
    private List<Message> messages;


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
        // Create new conversation
        Conversation conversation = new Conversation();

        // Parameters
        conversation.conversationId = details.getConversationId();
        conversation.unreadMessagesCount = details.getUnreadMessagesCount();

        // Relations
        if (details.getParticipants() != null) {
            conversation.participants = Buddy.fromBuddyDetailsList(details.getParticipants());
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetailsList(details.getMessages());
        }

        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        if (details.getBuddy() != null) {
            conversation.buddy = Buddy.fromBuddyDetails(details.getBuddy());
        }

        return conversation;
    }

    /**
     * Factory method which creates new list of Conversations from the list of ConversationDetails
     *
     * @param details list of conversation details
     * @return List<Conversation> of conversations
     */
    public static List<Conversation> fromConversationDetailsList(List<ConversationDetails> details) {
        // Create new list of conversations
        List<Conversation> conversations = new LinkedList<Conversation>();

        // Iterate through details and create conversation based on that
        for (ConversationDetails conversationDetails : details) {
            conversations.add(fromConversationDetails(conversationDetails));
        }

        return conversations;
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

        if (buddy != null) {
            details.setBuddy(buddy.toBuddyDetails());
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Computed values
    // -------------------------------------------------------------------------------------------

    /**
     * Returns conversation title
     *
     * @return String title
     */
    public String getTitle() {
        String title = ""; // Default

        // Single user conversation title
        if (conversationType == ConversationType.SINGLE_USER) {
            for (Buddy participant : participants) {
                // Single user conversation has two participants. Title is "the other" participant than myself.
                if (!participant.getBuddyId().equals(buddy.getBuddyId())) {
                    title = PortalUtil.getUserName(participant.getBuddyId(), title);
                }
            }
        }
        // TODO: Implement MULTI_USER

        return title;
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

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId='" + conversationId + '\'' +
                ", conversationType=" + conversationType +
                ", participants=" + participants +
                ", title='" + getTitle() + '\'' +
                ", messages=" + messages +
                '}';
    }
}
