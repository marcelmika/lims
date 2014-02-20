package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class UpdateSettingsBuddyResponseEvent extends ResponseEvent {

    private SettingsDetails settingsDetails;

    public static UpdateSettingsBuddyResponseEvent updateSettingsFailure(String result,
                                                                         SettingsDetails settingsDetails,
                                                                         Throwable exception) {
        UpdateSettingsBuddyResponseEvent event = new UpdateSettingsBuddyResponseEvent();
        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public static UpdateSettingsBuddyResponseEvent updateSettingsSuccess(String result,
                                                                         SettingsDetails settingsDetails) {

        UpdateSettingsBuddyResponseEvent event = new UpdateSettingsBuddyResponseEvent();

        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = true;

        return event;
    }
}
