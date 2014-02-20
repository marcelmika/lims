package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class BuddyUpdateStatusRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final String status;

    public BuddyUpdateStatusRequestEvent(Long buddyId, String status) {
        this.buddyId = buddyId;
        this.status = status;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public String getStatus() {
        return status;
    }
}
