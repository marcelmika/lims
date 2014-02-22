package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:14 AM
 */
public class OpenConversationRequestEvent extends RequestEvent {

    private Long buddyId;
    private String conversationId;

    public OpenConversationRequestEvent(Long buddyId, String conversationId) {
        this.buddyId = buddyId;
        this.conversationId = conversationId;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
