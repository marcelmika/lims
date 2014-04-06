package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/4/14
 * Time: 9:19 PM
 */
public class GetBuddiesRequestEvent extends RequestEvent {

    private final BuddyDetails details;

    public GetBuddiesRequestEvent(BuddyDetails details) {
        this.details = details;
    }

    public BuddyDetails getDetails() {
        return details;
    }


}
