package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.entity.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class UpdateSettingsResponseEvent extends ResponseEvent {

    private SettingsDetails settingsDetails;

    public static UpdateSettingsResponseEvent updateSettingsFailure(String result,
                                                                         SettingsDetails settingsDetails,
                                                                         Throwable exception) {
        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();
        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

    public static UpdateSettingsResponseEvent updateSettingsSuccess(String result,
                                                                         SettingsDetails settingsDetails) {

        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = true;

        return event;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}
