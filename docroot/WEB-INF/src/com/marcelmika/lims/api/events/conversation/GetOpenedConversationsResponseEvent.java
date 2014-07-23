package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/4/14
 * Time: 9:41 PM
 */
public class GetOpenedConversationsResponseEvent extends ResponseEvent {

    private Status status;
    private List<ConversationDetails> conversationDetails;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_NO_SESSION, // User does not have a session
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetOpenedConversationsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetOpenedConversationsResponseEvent success(List<ConversationDetails> conversations) {
        GetOpenedConversationsResponseEvent event = new GetOpenedConversationsResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;
        event.conversationDetails = conversations;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetOpenedConversationsResponseEvent failure(final Status status) {
        GetOpenedConversationsResponseEvent event = new GetOpenedConversationsResponseEvent();

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
    public static GetOpenedConversationsResponseEvent failure(final Status status,
                                                              final Throwable exception) {

        GetOpenedConversationsResponseEvent event = new GetOpenedConversationsResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public List<ConversationDetails> getConversationDetails() {
        return conversationDetails;
    }
}
