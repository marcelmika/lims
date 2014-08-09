package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:30 PM
 */
public class UpdateActivePanelResponseEvent extends ResponseEvent {


    public static UpdateActivePanelResponseEvent updateActivePanelFailure(String result,
                                                                               Throwable exception) {
        UpdateActivePanelResponseEvent event = new UpdateActivePanelResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public static UpdateActivePanelResponseEvent updateActivePanelSuccess(String result) {
        UpdateActivePanelResponseEvent event = new UpdateActivePanelResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

}
