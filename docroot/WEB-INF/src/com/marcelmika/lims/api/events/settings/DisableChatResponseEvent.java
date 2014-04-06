package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 4:25 PM
 */
public class DisableChatResponseEvent extends ResponseEvent {

    public static DisableChatResponseEvent disableChatSuccess(String result) {
        DisableChatResponseEvent event = new DisableChatResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static DisableChatResponseEvent disableChatFailure(String result, Throwable exception) {
        DisableChatResponseEvent event = new DisableChatResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }
}
