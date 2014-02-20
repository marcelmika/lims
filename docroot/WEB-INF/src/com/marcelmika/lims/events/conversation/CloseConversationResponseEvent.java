package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:16 AM
 */
public class CloseConversationResponseEvent extends ResponseEvent {

    public static CloseConversationResponseEvent closeConversationSuccess(String result) {
        CloseConversationResponseEvent event = new CloseConversationResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static CloseConversationResponseEvent closeConversationFailure(String result, Throwable exception) {
        CloseConversationResponseEvent event = new CloseConversationResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }


}
