package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:15 AM
 */
public class OpenConversationResponseEvent extends ResponseEvent {

    public static OpenConversationResponseEvent openConversationSuccess(String result) {
        OpenConversationResponseEvent event = new OpenConversationResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static OpenConversationResponseEvent openConversationFailure(String result, Throwable exception) {
        OpenConversationResponseEvent event = new OpenConversationResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
