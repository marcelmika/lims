package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 4:24 PM
 */
public class EnableChatRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;

    public EnableChatRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }
}
