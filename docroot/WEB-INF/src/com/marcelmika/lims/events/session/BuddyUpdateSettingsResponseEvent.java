package com.marcelmika.lims.events.session;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class BuddyUpdateSettingsResponseEvent extends ResponseEvent {

    private SettingsDetails settingsDetails;

    public static BuddyUpdateSettingsResponseEvent updateSettingsFailure(String result,
                                                                         SettingsDetails settingsDetails,
                                                                         Throwable exception) {
        BuddyUpdateSettingsResponseEvent event = new BuddyUpdateSettingsResponseEvent();
        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public static BuddyUpdateSettingsResponseEvent updateSettingsSuccess(String result,
                                                                         SettingsDetails settingsDetails) {

        BuddyUpdateSettingsResponseEvent event = new BuddyUpdateSettingsResponseEvent();

        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = true;

        return event;
    }
}
