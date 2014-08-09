package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:39 PM
 */
public class DeleteBuddyRequestEvent extends RequestEvent {

    private final BuddyDetails details;

    public DeleteBuddyRequestEvent(BuddyDetails details) {
        this.details = details;
    }

    public BuddyDetails getDetails() {
        return details;
    }

}
