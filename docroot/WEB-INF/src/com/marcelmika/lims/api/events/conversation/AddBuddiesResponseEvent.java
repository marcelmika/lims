package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:41 PM
 */
public class AddBuddiesResponseEvent extends ResponseEvent {

    public static AddBuddiesResponseEvent addBuddiesSuccess(String result) {
        AddBuddiesResponseEvent event = new AddBuddiesResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static AddBuddiesResponseEvent addBuddiesFailure(String result, Throwable exception) {
        AddBuddiesResponseEvent event = new AddBuddiesResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }


}
