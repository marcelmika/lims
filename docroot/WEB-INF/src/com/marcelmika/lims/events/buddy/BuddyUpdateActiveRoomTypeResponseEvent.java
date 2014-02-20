package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:58 PM
 */
public class BuddyUpdateActiveRoomTypeResponseEvent extends ResponseEvent {

    public static BuddyUpdateActiveRoomTypeResponseEvent updateActiveRoomTypeSuccess(String result) {
        BuddyUpdateActiveRoomTypeResponseEvent event = new BuddyUpdateActiveRoomTypeResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static BuddyUpdateActiveRoomTypeResponseEvent updateActiveRoomTypeFailure(String result,
                                                                                     Throwable exception) {
        BuddyUpdateActiveRoomTypeResponseEvent event = new BuddyUpdateActiveRoomTypeResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
