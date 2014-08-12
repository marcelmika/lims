package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class UpdateSettingsResponseEvent extends ResponseEvent {

    private Status status;
    private SettingsDetails settingsDetails;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private UpdateSettingsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static UpdateSettingsResponseEvent updateSettingsSuccess(final SettingsDetails settingsDetails) {
        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

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
    public static UpdateSettingsResponseEvent updateSettingsFailure(final Status status) {
        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

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
    public static UpdateSettingsResponseEvent updateSettingsFailure(final Status status,
                                                                    final Throwable exception) {

        UpdateSettingsResponseEvent event = new UpdateSettingsResponseEvent();

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
