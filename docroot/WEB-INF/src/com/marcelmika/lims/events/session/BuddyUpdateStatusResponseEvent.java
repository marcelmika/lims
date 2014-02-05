package com.marcelmika.lims.events.session;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class BuddyUpdateStatusResponseEvent {

    private String result;
    private boolean success;

    public static BuddyUpdateStatusResponseEvent updateStatusFailure(String result) {
        BuddyUpdateStatusResponseEvent event = new BuddyUpdateStatusResponseEvent();
        event.result = result;
        event.success = false;

        return event;
    }

    public static BuddyUpdateStatusResponseEvent updateStatusSuccess(String result) {
        BuddyUpdateStatusResponseEvent event = new BuddyUpdateStatusResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

}
