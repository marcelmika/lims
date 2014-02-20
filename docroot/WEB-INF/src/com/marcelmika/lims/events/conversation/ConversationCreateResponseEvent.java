package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:08 PM
 */
public class ConversationCreateResponseEvent extends ResponseEvent {

    public static ConversationCreateResponseEvent createConversationSuccess(String result) {
        ConversationCreateResponseEvent event = new ConversationCreateResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static ConversationCreateResponseEvent createConversationFailure(String result, Throwable exception) {
        ConversationCreateResponseEvent event = new ConversationCreateResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
