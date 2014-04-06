package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.api.entity.MessageDetails;
import org.jivesoftware.smackx.packet.DelayInformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:16 AM
 */
public class Message {

    private Buddy to;
    private Buddy from;
    private Date createdAt;
    private String body;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    public static Message fromSmackMessage(org.jivesoftware.smack.packet.Message smackMessage) {
        // Create new message
        Message message = new Message();
        // Map properties
        message.body = smackMessage.getBody();
        message.createdAt = getMessageTimestamp(smackMessage);
        // Map relations
        message.from = Buddy.fromSmackMessage(smackMessage);

        return message;
    }

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

    public static List<Message> fromMessageDetails (List<MessageDetails> details) {
        // Create new message list
        List<Message> messages = new ArrayList<Message>();
        // Map
        for (MessageDetails messageDetails : details) {
            messages.add(Message.fromMessageDetails(messageDetails));
        }
        return messages;
    }

    /**
     * Method which calculates smack message timestamp.
     * Message creation date can be retrieved from the offline messages.
     *
     * @param message from smack
     * @return Date
     */
    private static Date getMessageTimestamp(org.jivesoftware.smack.packet.Message message) {

        try {
            // Get the timestamp from message extension
            DelayInformation inf = (DelayInformation) message.getExtension("x", "jabber:x:delay");
            // Return offline message timestamp
            if (inf != null) {
                return inf.getStamp();
            }
            // Message is not offline -> return current timestamp
            return new Date();

        } catch (Exception e) {
            // Extension isn't provided so return empty date
            return new Date(0);
        }
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
