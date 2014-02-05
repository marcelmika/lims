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

    public static BuddyLoginResponseEvent loginFailure(String result, BuddyDetails details) {
        BuddyLoginResponseEvent event = new BuddyLoginResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

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
