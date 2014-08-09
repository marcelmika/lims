package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 4:03 PM
 */
public class ReadSettingsRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;

    public ReadSettingsRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }
}
