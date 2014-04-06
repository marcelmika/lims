package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 5:50 PM
 */
public class GetConversationsRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;

    public GetConversationsRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }
}
