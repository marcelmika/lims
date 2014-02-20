package com.marcelmika.lims.events.details;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:34 PM
 */
public class ConversationDetails {

    private String conversationId;
    private List<BuddyDetails> participants;
    private String title;
    private String name;
    private String type;
    private String visibility;
    private MessageDetails lastMessage;
    private List<MessageDetails> messages;


    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public List<BuddyDetails> getParticipants() {
        return participants;
    }

    public void setParticipants(List<BuddyDetails> participants) {
        this.participants = participants;
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

    public MessageDetails getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDetails lastMessage) {
        this.lastMessage = lastMessage;
    }

    public List<MessageDetails> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDetails> messages) {
        this.messages = messages;
    }

}
