package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 3:51 PM
 */
public class SendMessageResponseEvent extends ResponseEvent {

    public static SendMessageResponseEvent sendMessageSuccess(String result) {
        SendMessageResponseEvent event = new SendMessageResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static SendMessageResponseEvent sendMessageFailure(Throwable exception) {
        SendMessageResponseEvent event = new SendMessageResponseEvent();
        event.result = exception.getMessage();
        event.success = false;
        event.exception = exception;

        return event;
    }


}
