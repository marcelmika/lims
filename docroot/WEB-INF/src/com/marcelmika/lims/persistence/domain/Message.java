package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.MessageDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:59 AM
 */
public class Message {

    private Buddy to;
    private Buddy from;
    private Date createdAt;
    private String body;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    public static Message fromMessageDetails(MessageDetails details) {
        // Create new message
        Message message = new Message();
        // Map properties
        message.body = details.getBody();
        message.createdAt = details.getCreatedAt();

        // Relations:
        // To
        if (details.getTo() != null) {
            message.to = Buddy.fromBuddyDetails(details.getTo());
        }
        // From
        if (details.getFrom() != null) {
            message.from = Buddy.fromBuddyDetails(details.getFrom());
        }

        return message;
    }

    public static List<Message> fromMessageDetails(List<MessageDetails> details) {
        // Create new message list
        List<Message> messages = new ArrayList<Message>();
        // Map
        for (MessageDetails messageDetails : details) {
            messages.add(Message.fromMessageDetails(messageDetails));
        }
        return messages;
    }

    public static List<MessageDetails> toMessageDetailsList(List<Message> messages) {
        // Create new list
        List<MessageDetails> details = new ArrayList<MessageDetails>();
        // Map
        for (Message message : messages) {
            details.add(message.toMessageDetails());
        }

        return details;
    }

    public MessageDetails toMessageDetails() {
        // Create new message details
        MessageDetails details = new MessageDetails();
        // Properties
        details.setBody(body);
        details.setCreatedAt(createdAt);

        // Relations
        if (to != null) {
            details.setTo(to.toBuddyDetails());
        }

        if (from != null) {
            details.setFrom(from.toBuddyDetails());
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

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
