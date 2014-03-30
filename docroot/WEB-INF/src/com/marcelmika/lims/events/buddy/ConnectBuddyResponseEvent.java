package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 12:28 PM
 */
public class ConnectBuddyResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    /**
     * Factory method which creates new success response object
     *
     * @param result textual description of the success
     * @param details  related to the event
     * @return ConnectBuddyResponseEvent
     */
    public static ConnectBuddyResponseEvent connectFailure(String result, BuddyDetails details) {
        ConnectBuddyResponseEvent event = new ConnectBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    /**
     * Factory method which creates new failure response object
     *
     * @param result textual description of the failure
     * @param details related to the event
     * @return ConnectBuddyResponseEvent
     */
    public static ConnectBuddyResponseEvent connectSuccess(String result, BuddyDetails details) {
        ConnectBuddyResponseEvent event = new ConnectBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
