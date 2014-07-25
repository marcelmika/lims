package com.marcelmika.lims.portal.processor.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/25/14
 * Time: 8:41 PM
 */
public class ResetUnreadMessagesCounterParameters {

    private String conversationId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "CloseConversationParameters{" +
                "conversationId='" + conversationId + '\'' +
                '}';
    }
}
