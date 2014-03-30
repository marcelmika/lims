package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.events.details.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class UpdateStatusBuddyRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final PresenceDetails presenceDetails;

    public UpdateStatusBuddyRequestEvent(Long buddyId, PresenceDetails presenceDetails) {
        this.buddyId = buddyId;
        this.presenceDetails = presenceDetails;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }
}
