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

    private Status status;
    private SettingsDetails settingsDetails;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private ReadSettingsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadSettingsResponseEvent readSettingsSuccess(SettingsDetails settingsDetails) {
        ReadSettingsResponseEvent event = new ReadSettingsResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.settingsDetails = settingsDetails;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static ReadSettingsResponseEvent readSettingsFailure(final Status status) {
        ReadSettingsResponseEvent event = new ReadSettingsResponseEvent();

        event.success = false;
        event.status = status;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status    Status
     * @param exception Exception
     * @return ResponseEvent
     */
    public static ReadSettingsResponseEvent readSettingsFailure(final Status status,
                                                                final Throwable exception) {
        ReadSettingsResponseEvent event = new ReadSettingsResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}

