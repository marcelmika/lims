package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.marcelmika.lims.events.details.MessageDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Message {

    // Constants
    private static final String KEY_BODY = "message";

    // Properties
    private Buddy from;
    private Buddy to;
    private Date createdAt;
    private String body;

    /**
     * Factory method which creates new Message object from the PollerRequest
     *
     * @param pollerRequest request
     * @return Message
     */
    public static Message fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new conversation
        Message message = new Message();
        // Conversation Id
        if (parameterMap.containsKey(KEY_BODY)) {
            message.body = GetterUtil.getString(parameterMap.get(KEY_BODY));
        }

        return message;
    }

    /**
     * Factory method which creates a list of Messages from a list of MessageDetails
     *
     * @param detailsList list of MessageDetails
     * @return Message
     */
    public static List<Message> fromMessageDetails(List<MessageDetails> detailsList) {
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
        message.from = Buddy.fromBuddyDetails(details.getFrom());
        message.to = Buddy.fromBuddyDetails(details.getTo());
        message.createdAt = details.getCreatedAt();
        message.body = details.getBody();

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




