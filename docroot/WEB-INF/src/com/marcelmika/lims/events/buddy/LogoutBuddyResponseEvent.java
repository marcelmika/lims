package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:46 PM
 */
public class LogoutBuddyResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    public static LogoutBuddyResponseEvent logoutFailure(String result, BuddyDetails details) {
        LogoutBuddyResponseEvent event = new LogoutBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public static LogoutBuddyResponseEvent logoutSuccess(String result, BuddyDetails details) {
        LogoutBuddyResponseEvent event = new LogoutBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
