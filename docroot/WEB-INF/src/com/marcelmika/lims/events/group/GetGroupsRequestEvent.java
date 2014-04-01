package com.marcelmika.lims.events.group;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:12 PM
 */
public class GetGroupsRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;

    public GetGroupsRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }

}
