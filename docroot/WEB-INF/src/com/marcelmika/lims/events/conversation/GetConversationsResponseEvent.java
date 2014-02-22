package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.ConversationDetails;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 5:52 PM
 */
public class GetConversationsResponseEvent extends ResponseEvent {

    private List<ConversationDetails> conversations;

    public static GetConversationsResponseEvent leaveConversationSuccess(List<ConversationDetails> conversations) {
        GetConversationsResponseEvent event = new GetConversationsResponseEvent();
        event.success = true;
        event.conversations = conversations;

        return event;
    }

    public static GetConversationsResponseEvent getConversationsFailure(String result, Throwable exception) {
        GetConversationsResponseEvent event = new GetConversationsResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public List<ConversationDetails> getConversations() {
        return conversations;
    }
}
