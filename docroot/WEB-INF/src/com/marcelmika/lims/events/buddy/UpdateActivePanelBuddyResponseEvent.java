package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:30 PM
 */
public class UpdateActivePanelBuddyResponseEvent extends ResponseEvent {


    public static UpdateActivePanelBuddyResponseEvent updateActivePanelFailure(String result,
                                                                               Throwable exception) {
        UpdateActivePanelBuddyResponseEvent event = new UpdateActivePanelBuddyResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public static UpdateActivePanelBuddyResponseEvent updateActivePanelSuccess(String result) {
        UpdateActivePanelBuddyResponseEvent event = new UpdateActivePanelBuddyResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

}
