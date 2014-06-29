package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:08 PM
 */
public class CreateConversationResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_NO_SESSION, // User does not have a session
        ERROR_UNKNOWN_CONVERSATION_TYPE, // Unknown conversation type
        ERROR_NOT_IMPLEMENTED, // Functionality is not implemented yet
        ERROR_JABBER, // Error with jabber occurred
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private CreateConversationResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static CreateConversationResponseEvent createConversationSuccess() {
        CreateConversationResponseEvent event = new CreateConversationResponseEvent();
        event.success = true;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static CreateConversationResponseEvent createConversationFailure(final Status status) {

        CreateConversationResponseEvent event = new CreateConversationResponseEvent();

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
    public static CreateConversationResponseEvent createConversationFailure(final Status status,
                                                                            final Throwable exception) {

        CreateConversationResponseEvent event = new CreateConversationResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }
}
