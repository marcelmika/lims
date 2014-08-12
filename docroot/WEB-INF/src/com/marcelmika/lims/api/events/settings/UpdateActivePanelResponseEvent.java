package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:30 PM
 */
public class UpdateActivePanelResponseEvent extends ResponseEvent {

    private Status status;
    private String activePanelId;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private UpdateActivePanelResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static UpdateActivePanelResponseEvent updateActivePanelSuccess(final String activePanelId) {
        UpdateActivePanelResponseEvent event = new UpdateActivePanelResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.activePanelId = activePanelId;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static UpdateActivePanelResponseEvent updateActivePanelFailure(final Status status) {
        UpdateActivePanelResponseEvent event = new UpdateActivePanelResponseEvent();

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
    public static UpdateActivePanelResponseEvent updateActivePanelFailure(final Status status,
                                                                          final Throwable exception) {

        UpdateActivePanelResponseEvent event = new UpdateActivePanelResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public String getActivePanelId() {
        return activePanelId;
    }
}
