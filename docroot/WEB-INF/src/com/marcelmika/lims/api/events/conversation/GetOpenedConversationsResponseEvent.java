package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/4/14
 * Time: 9:41 PM
 */
public class GetOpenedConversationsResponseEvent extends ResponseEvent {

    private List<ConversationDetails> conversations;

    public static GetOpenedConversationsResponseEvent getOpenedConversationsSuccess(
            List<ConversationDetails> conversations) {

        GetOpenedConversationsResponseEvent event = new GetOpenedConversationsResponseEvent();
        event.success = true;
        event.conversations = conversations;

        return event;
    }

    public static GetOpenedConversationsResponseEvent getOpenedConversationsFailure(String result,
                                                                                    Throwable exception) {
        GetOpenedConversationsResponseEvent event = new GetOpenedConversationsResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public List<ConversationDetails> getConversations() {
        return conversations;
    }

}
