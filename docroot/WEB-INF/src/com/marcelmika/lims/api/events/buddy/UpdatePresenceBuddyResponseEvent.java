package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class UpdatePresenceBuddyResponseEvent extends ResponseEvent {

    public static UpdatePresenceBuddyResponseEvent updateStatusSuccess(String result) {
        UpdatePresenceBuddyResponseEvent event = new UpdatePresenceBuddyResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static UpdatePresenceBuddyResponseEvent updateStatusFailure(String result, Throwable exception) {
        UpdatePresenceBuddyResponseEvent event = new UpdatePresenceBuddyResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
