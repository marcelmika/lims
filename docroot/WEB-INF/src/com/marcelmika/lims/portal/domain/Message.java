package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.marcelmika.lims.events.details.MessageDetails;

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
            message.setBody(GetterUtil.getString(parameterMap.get(KEY_BODY)));
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
        details.setBody(body);

        return details;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}




