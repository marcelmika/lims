package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:40 PM
 */
public class BuddyDeleteResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    public static BuddyDeleteResponseEvent removeFailure(String result, BuddyDetails details) {
        BuddyDeleteResponseEvent event = new BuddyDeleteResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public static BuddyDeleteResponseEvent removeSuccess(String result, BuddyDetails details) {
        BuddyDeleteResponseEvent event = new BuddyDeleteResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
