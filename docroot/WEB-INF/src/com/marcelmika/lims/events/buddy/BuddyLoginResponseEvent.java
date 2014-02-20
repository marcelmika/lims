package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:43 PM
 */
public class BuddyLoginResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    /**
     * Factory method which creates new success response object
     *
     * @param result textual description of the success
     * @param details  related to the event
     * @return BuddyLoginResponseEvent
     */
    public static BuddyLoginResponseEvent loginFailure(String result, BuddyDetails details) {
        BuddyLoginResponseEvent event = new BuddyLoginResponseEvent();
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
     * @return BuddyLoginResponseEvent
     */
    public static BuddyLoginResponseEvent loginSuccess(String result, BuddyDetails details) {
        BuddyLoginResponseEvent event = new BuddyLoginResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
