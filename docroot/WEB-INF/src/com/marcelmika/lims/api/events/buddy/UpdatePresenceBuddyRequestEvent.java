package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.events.RequestEvent;
import com.marcelmika.lims.api.entity.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class UpdatePresenceBuddyRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final PresenceDetails presenceDetails;

    public UpdatePresenceBuddyRequestEvent(Long buddyId, PresenceDetails presenceDetails) {
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
