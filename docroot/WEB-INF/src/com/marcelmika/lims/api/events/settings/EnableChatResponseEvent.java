package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 4:25 PM
 */
public class EnableChatResponseEvent extends ResponseEvent {

    public static EnableChatResponseEvent enableChatSuccess(String result) {
        EnableChatResponseEvent event = new EnableChatResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static EnableChatResponseEvent enableChatFailure(String result, Throwable exception) {
        EnableChatResponseEvent event = new EnableChatResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
