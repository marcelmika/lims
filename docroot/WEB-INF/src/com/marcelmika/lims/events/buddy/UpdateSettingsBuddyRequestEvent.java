package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.events.details.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class UpdateSettingsBuddyRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final SettingsDetails settingsDetails;

    public UpdateSettingsBuddyRequestEvent(Long buddyId, SettingsDetails settingsDetails) {
        this.buddyId = buddyId;
        this.settingsDetails = settingsDetails;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}
