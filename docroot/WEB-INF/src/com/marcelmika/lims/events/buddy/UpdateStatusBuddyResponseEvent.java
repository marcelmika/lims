package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class UpdateStatusBuddyResponseEvent extends ResponseEvent {

    public static UpdateStatusBuddyResponseEvent updateStatusSuccess(String result) {
        UpdateStatusBuddyResponseEvent event = new UpdateStatusBuddyResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static UpdateStatusBuddyResponseEvent updateStatusFailure(String result, Throwable exception) {
        UpdateStatusBuddyResponseEvent event = new UpdateStatusBuddyResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
