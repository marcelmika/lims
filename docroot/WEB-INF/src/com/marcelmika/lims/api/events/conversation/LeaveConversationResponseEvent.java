package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:16 AM
 */
public class LeaveConversationResponseEvent extends ResponseEvent {

    public static LeaveConversationResponseEvent leaveConversationSuccess(String result) {
        LeaveConversationResponseEvent event = new LeaveConversationResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static LeaveConversationResponseEvent leaveConversationFailure(String result, Throwable exception) {
        LeaveConversationResponseEvent event = new LeaveConversationResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }


}
