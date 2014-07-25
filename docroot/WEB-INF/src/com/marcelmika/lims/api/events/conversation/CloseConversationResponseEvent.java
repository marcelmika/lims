package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:16 AM
 */
public class CloseConversationResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_NO_CONVERSATION_FOUND, // User does not have a session
        ERROR_NO_PARTICIPANT_FOUND, // Unknown conversation type
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private CloseConversationResponseEvent() {
        // No params
    }


    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static CloseConversationResponseEvent success() {
        CloseConversationResponseEvent event = new CloseConversationResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static CloseConversationResponseEvent failure(final Status status) {
        CloseConversationResponseEvent event = new CloseConversationResponseEvent();

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
    public static CloseConversationResponseEvent failure(final Status status,
                                                        final Throwable exception) {
        CloseConversationResponseEvent event = new CloseConversationResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }
}
