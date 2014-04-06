package com.marcelmika.lims.core.domain;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.entity.MessageDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:26 PM
 */
public class Conversation {

    private String conversationId;
    private ConversationType conversationType;
    private String title;
    private String name;
    private String type;
    private String visibility;
    private Message lastMessage;
    private List<Buddy> participants = new ArrayList<Buddy>();
    private List<Message> messages = new ArrayList<Message>();


    /**
     * Creates new Conversation and maps data from Conversation details
     *
     * @param conversationDetails ConversationDetails
     * @return Conversation
     */
    public static Conversation fromConversationDetails(ConversationDetails conversationDetails) {
        // Create new Conversation
        Conversation conversation = new Conversation();
        // Map data to conversation details
        conversation.setConversationId(conversationDetails.getConversationId());
        conversation.setTitle(conversationDetails.getTitle());
        conversation.setName(conversationDetails.getName());
        conversation.setType(conversationDetails.getType());
        conversation.setVisibility(conversationDetails.getVisibility());

        // Relations
        conversation.setLastMessage(Message.fromMessageDetails(conversationDetails.getLastMessage()));
        // Relations - participant
        for(BuddyDetails participant : conversationDetails.getParticipants()) {
            conversation.addParticipant(Buddy.fromBuddyDetails(participant));
        }
        // Relations - messages
        for(MessageDetails messageDetails : conversationDetails.getMessages()) {
            // TODO: parse messages
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

        return details;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public List<Buddy> getParticipants() {
        return participants;
    }

    public void addParticipant(Buddy participant) {
        participants.add(participant);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
