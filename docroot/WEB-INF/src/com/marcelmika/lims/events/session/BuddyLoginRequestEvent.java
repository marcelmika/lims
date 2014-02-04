package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.details.BuddyDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:44 PM
 */
public class BuddyLoginRequestEvent {

    private final BuddyDetails details;

    public BuddyLoginRequestEvent(BuddyDetails details) {
        this.details = details;
    }

    public BuddyDetails getDetails() {
        return details;
    }

}
