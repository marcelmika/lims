package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:40 PM
 */
public class DeleteBuddyResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    public static DeleteBuddyResponseEvent removeFailure(String result, BuddyDetails details) {
        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public static DeleteBuddyResponseEvent removeSuccess(String result, BuddyDetails details) {
        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
