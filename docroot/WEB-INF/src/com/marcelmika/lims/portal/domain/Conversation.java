package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Conversation {

    // Constants
    public static final String KEY_CONVERSATION_ID = "roomJID";
    public static final String KEY_CONVERSATION_TYPE = "conversationType";
    public static final String KEY_MESSAGES = "messages";

    // Properties
    private String conversationId;
    private String title;
    private ConversationType conversationType;
    private Integer unreadMessagesCount;
    private List<Buddy> participants;
    private List<Message> messages;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new Conversation from the PollerRequest
     *
     * @param pollerRequest request
     * @return Conversation
     * @deprecated
     */
    public static Conversation fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new conversation
        Conversation conversation = new Conversation();
        // Conversation Id
        if (parameterMap.containsKey(KEY_CONVERSATION_ID)) {
            conversation.conversationId = GetterUtil.getString(parameterMap.get(KEY_CONVERSATION_ID));
        }
        // Conversation type
        if (parameterMap.containsKey(KEY_CONVERSATION_TYPE)) {
            String key = GetterUtil.getString(parameterMap.get(KEY_CONVERSATION_TYPE));
            conversation.conversationType = ConversationType.fromKey(key);
        }

        return conversation;
    }

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

        return conversation;
    }

    public static List<Conversation> fromConversationDetailsList(List<ConversationDetails> details) {
        // Create new list of conversations
        List<Conversation> conversations = new LinkedList<Conversation>();
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

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                ", title='" + title + '\'' +
                ", messages=" + messages +
                '}';
    }
}
