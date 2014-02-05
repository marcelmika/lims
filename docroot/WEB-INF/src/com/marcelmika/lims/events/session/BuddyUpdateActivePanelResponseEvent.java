package com.marcelmika.lims.events.session;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:30 PM
 */
public class BuddyUpdateActivePanelResponseEvent {

    private String result;
    private boolean success;

    public static BuddyUpdateActivePanelResponseEvent changeActivePanelFailure(String result) {
        BuddyUpdateActivePanelResponseEvent event = new BuddyUpdateActivePanelResponseEvent();
        event.result = result;
        event.success = false;

        return event;
    }

    public static BuddyUpdateActivePanelResponseEvent changeActivePanelSuccess(String result) {
        BuddyUpdateActivePanelResponseEvent event = new BuddyUpdateActivePanelResponseEvent();
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
