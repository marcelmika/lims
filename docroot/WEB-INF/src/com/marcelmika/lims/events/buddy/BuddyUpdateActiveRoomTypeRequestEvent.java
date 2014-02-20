package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:58 PM
 */
public class BuddyUpdateActiveRoomTypeRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final String activeRoomType;

    public BuddyUpdateActiveRoomTypeRequestEvent(Long buddyId, String activeRoomType) {
        this.buddyId = buddyId;
        this.activeRoomType = activeRoomType;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public String getActiveRoomType() {
        return activeRoomType;
    }

}
