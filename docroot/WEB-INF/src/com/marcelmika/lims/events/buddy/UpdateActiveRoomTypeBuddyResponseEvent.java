package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:58 PM
 */
public class UpdateActiveRoomTypeBuddyResponseEvent extends ResponseEvent {

    public static UpdateActiveRoomTypeBuddyResponseEvent updateActiveRoomTypeSuccess(String result) {
        UpdateActiveRoomTypeBuddyResponseEvent event = new UpdateActiveRoomTypeBuddyResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static UpdateActiveRoomTypeBuddyResponseEvent updateActiveRoomTypeFailure(String result,
                                                                                     Throwable exception) {
        UpdateActiveRoomTypeBuddyResponseEvent event = new UpdateActiveRoomTypeBuddyResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
