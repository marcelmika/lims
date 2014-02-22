package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Conversation {

    // Constants
    private static final String KEY_CONVERSATION_ID = "roomJID";

    // Properties
    private String conversationId;

    /**
     * Factory method which creates new Conversation object from the PollerRequest
     *
     * @param pollerRequest request
     * @return Conversation
     */
    public static Conversation fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new conversation
        Conversation conversation = new Conversation();
        // Conversation Id
        if (parameterMap.containsKey(KEY_CONVERSATION_ID)) {
            conversation.setConversationId(GetterUtil.getString(parameterMap.get(KEY_CONVERSATION_ID)));
        }

        return conversation;
    }


    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
