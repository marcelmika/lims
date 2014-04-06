package com.marcelmika.lims.core.domain;

import com.marcelmika.lims.api.entity.MessageDetails;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:26 PM
 */
public class Message {

    private Buddy from;
    private Buddy to;
    private Date createdAt;
    private String body;

    /**
     * Creates new Message and maps data from Message details
     *
     * @param messageDetails MessageDetails
     * @return Message
     */
    public static Message fromMessageDetails(MessageDetails messageDetails) {
        // Create new Message
        Message message = new Message();
        // Map data to message details
        message.setFrom(Buddy.fromBuddyDetails(messageDetails.getFrom()));
        message.setTo(Buddy.fromBuddyDetails(messageDetails.getTo()));
        message.setCreatedAt(messageDetails.getCreatedAt());
        message.setBody(message.getBody());

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
        details.setFrom(from.toBuddyDetails());
        details.setTo(to.toBuddyDetails());
        details.setCreatedAt(createdAt);
        details.setBody(body);

        return details;
    }

    public Buddy getFrom() {
        return from;
    }

    public void setFrom(Buddy from) {
        this.from = from;
    }

    public Buddy getTo() {
        return to;
    }

    public void setTo(Buddy to) {
        this.to = to;
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
}
