package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:58 PM
 */
public class UpdateActiveRoomTypeResponseEvent extends ResponseEvent {

    public static UpdateActiveRoomTypeResponseEvent updateActiveRoomTypeSuccess(String result) {
        UpdateActiveRoomTypeResponseEvent event = new UpdateActiveRoomTypeResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static UpdateActiveRoomTypeResponseEvent updateActiveRoomTypeFailure(String result,
                                                                                     Throwable exception) {
        UpdateActiveRoomTypeResponseEvent event = new UpdateActiveRoomTypeResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
