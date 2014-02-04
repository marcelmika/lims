package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:46 PM
 */
public class BuddyLogoutResponseEvent {

    private String result;
    private boolean success;
    private BuddyDetails details;

    public static BuddyLogoutResponseEvent logoutFailure(String result, BuddyDetails details) {
        BuddyLogoutResponseEvent event = new BuddyLogoutResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public static BuddyLogoutResponseEvent logoutSuccess(String result, BuddyDetails details) {
        BuddyLogoutResponseEvent event = new BuddyLogoutResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
