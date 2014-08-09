package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.json.JSON;
import com.marcelmika.lims.api.entity.MessageDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Message {

    // Properties
    private Buddy from;
    private String conversationId;
    private String body;
    private Date createdAt;

    /**
     * Factory method which creates a list of Messages from a list of MessageDetails
     *
     * @param detailsList list of MessageDetails
     * @return Message
     */
    public static List<Message> fromMessageDetailsList(List<MessageDetails> detailsList) {
        // Create new list of messages
        List<Message> messages = new ArrayList<Message>();
        // Iterate through the list and add messages
        for (MessageDetails details : detailsList) {
            messages.add(Message.fromMessageDetails(details));
        }

        return messages;
    }

    /**
     * Factory method which creates new Message from the MessageDetails
     *
     * @param details message details
     * @return Message
     */
    public static Message fromMessageDetails(MessageDetails details) {
        // Crate new message
        Message message = new Message();
        // Map values
        message.createdAt = details.getCreatedAt();
        message.body = details.getBody();

        // Relations
        if (details.getFrom() != null) {
            message.from = Buddy.fromBuddyDetails(details.getFrom());
        }

        return message;
    }

    /**
     * Maps message to message details
     *
     * @return MessageDetails
     */
    public MessageDetails toMessageDetails() {
        // Create message details
        MessageDetails details = new MessageDetails();
        // Map data from message
        details.setCreatedAt(createdAt);
        details.setBody(body);

        // Relations
        if (from != null) {
            details.setFrom(from.toBuddyDetails());
        }

        return details;
    }


    public Buddy getFrom() {
        return from;
    }

    public void setFrom(Buddy from) {
        this.from = from;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}




