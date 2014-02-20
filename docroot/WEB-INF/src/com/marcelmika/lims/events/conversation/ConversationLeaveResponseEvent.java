package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:16 AM
 */
public class ConversationLeaveResponseEvent extends ResponseEvent {

    public static ConversationLeaveResponseEvent leaveConversationSuccess(String result) {
        ConversationLeaveResponseEvent event = new ConversationLeaveResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static ConversationLeaveResponseEvent leaveConversationFailure(String result, Throwable exception) {
        ConversationLeaveResponseEvent event = new ConversationLeaveResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }


}
