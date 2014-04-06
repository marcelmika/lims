package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/4/14
 * Time: 9:41 PM
 */
public class GetOpenedConversationsRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;

    public GetOpenedConversationsRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }
}
