package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 4:04 PM
 */
public class ReadSettingsResponseEvent extends ResponseEvent {

    private SettingsDetails settingsDetails;

    public static ReadSettingsResponseEvent readSettingsSuccess(String result, SettingsDetails settingsDetails) {
        ReadSettingsResponseEvent event = new ReadSettingsResponseEvent();
        event.settingsDetails = settingsDetails;
        event.result = result;
        event.success = true;

        return event;
    }

    public static ReadSettingsResponseEvent readSettingsFailure(Throwable exception) {
        ReadSettingsResponseEvent event = new ReadSettingsResponseEvent();
        event.result = exception.getMessage();
        event.success = false;
        event.exception = exception;

        return event;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}

