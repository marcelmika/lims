package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.entity.MessageDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:58 PM
 */
public class ReadSingleUserConversationResponseEvent extends ResponseEvent {

    private Status status;
    private List<MessageDetails> messages;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_NO_SESSION, // User does not have a session
        ERROR_NOT_FOUND, // No Conversation was found
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private ReadSingleUserConversationResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadSingleUserConversationResponseEvent readConversationSuccess(List<MessageDetails> messages) {
        ReadSingleUserConversationResponseEvent event = new ReadSingleUserConversationResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;
        event.messages = messages;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static ReadSingleUserConversationResponseEvent readConversationFailure(final Status status) {
        ReadSingleUserConversationResponseEvent event = new ReadSingleUserConversationResponseEvent();

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
    public static ReadSingleUserConversationResponseEvent readConversationFailure(final Status status,
                                                                                  final Throwable exception) {

        ReadSingleUserConversationResponseEvent event = new ReadSingleUserConversationResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public List<MessageDetails> getMessages() {
        return messages;
    }
}
