package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:16 AM
 */
public class ConversationCloseResponseEvent extends ResponseEvent {

    public static ConversationCloseResponseEvent closeConversationSuccess(String result) {
        ConversationCloseResponseEvent event = new ConversationCloseResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static ConversationCloseResponseEvent closeConversationFailure(String result, Throwable exception) {
        ConversationCloseResponseEvent event = new ConversationCloseResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }


}
