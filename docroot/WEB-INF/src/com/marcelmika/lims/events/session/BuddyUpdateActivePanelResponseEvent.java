package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:30 PM
 */
public class BuddyUpdateActivePanelResponseEvent extends ResponseEvent {


    public static BuddyUpdateActivePanelResponseEvent updateActivePanelFailure(String result,
                                                                               Throwable exception) {
        BuddyUpdateActivePanelResponseEvent event = new BuddyUpdateActivePanelResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public static BuddyUpdateActivePanelResponseEvent updateActivePanelSuccess(String result) {
        BuddyUpdateActivePanelResponseEvent event = new BuddyUpdateActivePanelResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

}
