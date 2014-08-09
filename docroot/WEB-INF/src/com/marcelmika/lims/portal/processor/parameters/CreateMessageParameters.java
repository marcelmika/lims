package com.marcelmika.lims.portal.processor.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/12/14
 * Time: 1:37 PM
 */
public class CreateMessageParameters {

    private String conversationId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "CreateMessageParameters{" +
                "conversationId='" + conversationId + '\'' +
                '}';
    }
}
