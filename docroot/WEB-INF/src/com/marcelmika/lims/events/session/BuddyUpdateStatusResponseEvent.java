package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class BuddyUpdateStatusResponseEvent extends ResponseEvent {

    public static BuddyUpdateStatusResponseEvent updateStatusSuccess(String result) {
        BuddyUpdateStatusResponseEvent event = new BuddyUpdateStatusResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static BuddyUpdateStatusResponseEvent updateStatusFailure(String result, Throwable exception) {
        BuddyUpdateStatusResponseEvent event = new BuddyUpdateStatusResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
