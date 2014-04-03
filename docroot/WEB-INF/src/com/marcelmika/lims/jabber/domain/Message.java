package com.marcelmika.lims.jabber.domain;

import org.jivesoftware.smackx.packet.DelayInformation;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:16 AM
 */
public class Message {

    private Buddy from;
    private Date createdAt;
    private String body;


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
}
