package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:15 AM
 */
public class ConversationOpenResponseEvent extends ResponseEvent {


    public static ConversationOpenResponseEvent openConversationSuccess(String result) {
        ConversationOpenResponseEvent event = new ConversationOpenResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static ConversationOpenResponseEvent openConversationFailure(String result, Throwable exception) {
        ConversationOpenResponseEvent event = new ConversationOpenResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
