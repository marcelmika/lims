package com.marcelmika.lims.api.entity;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:34 PM
 */
public class ConversationDetails {

    private String conversationId;
    private ConversationTypeDetails conversationType;
    private Integer unreadMessagesCount;
    private BuddyDetails buddy;
    private List<MessageDetails> messages;
    private List<BuddyDetails> participants;
    private String title;
    private String name;
    private String type;
    private String visibility;
    private MessageDetails lastMessage;

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

    /**
     * @deprecated
     */
    public String getTitle() {
        return title;
    }

    /**
     * @deprecated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @deprecated
     */
    public String getName() {
        return name;
    }

    /**
     * @deprecated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @deprecated
     */
    public String getType() {
        return type;
    }

    /**
     * @deprecated
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @deprecated
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * @deprecated
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * @deprecated
     */
    public MessageDetails getLastMessage() {
        return lastMessage;
    }

    /**
     * @deprecated
     */
    public void setLastMessage(MessageDetails lastMessage) {
        this.lastMessage = lastMessage;
    }
}
