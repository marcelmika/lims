/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.api.entity.MessageDetails;
import org.jivesoftware.smackx.packet.DelayInformation;

import java.util.ArrayList;
import java.util.Calendar;
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
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();

        } catch (Exception e) {
            // Extension isn't provided so return empty date
            return new Date(0);
        }
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
