package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.details.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:39 PM
 */
public class BuddyDeleteRequestEvent {

    private final BuddyDetails details;

    public BuddyDeleteRequestEvent(BuddyDetails details) {
        this.details = details;
    }

    public BuddyDetails getDetails() {
        return details;
    }

}
