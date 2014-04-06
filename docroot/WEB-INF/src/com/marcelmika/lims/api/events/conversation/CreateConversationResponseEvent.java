package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:08 PM
 */
public class CreateConversationResponseEvent extends ResponseEvent {

    public static CreateConversationResponseEvent createConversationSuccess(String result) {
        CreateConversationResponseEvent event = new CreateConversationResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static CreateConversationResponseEvent createConversationFailure(String result, Throwable exception) {
        CreateConversationResponseEvent event = new CreateConversationResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
