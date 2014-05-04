package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.entity.PresenceDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 8:29 AM
 */
public class ReadPresenceBuddyResponseEvent extends ResponseEvent {

    private PresenceDetails presenceDetails;

    public static ReadPresenceBuddyResponseEvent readPresenceSuccess(String result, PresenceDetails presenceDetails) {
        ReadPresenceBuddyResponseEvent event = new ReadPresenceBuddyResponseEvent();
        event.result = result;
        event.success = true;
        event.presenceDetails = presenceDetails;

        return event;
    }

    public static ReadPresenceBuddyResponseEvent readPresenceFailure(Throwable exception) {
        ReadPresenceBuddyResponseEvent event = new ReadPresenceBuddyResponseEvent();
        event.result = exception.getMessage();
        event.success = false;
        event.exception = exception;

        return event;
    }

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }
}
