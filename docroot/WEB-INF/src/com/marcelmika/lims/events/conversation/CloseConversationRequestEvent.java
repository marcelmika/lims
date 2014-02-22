package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:15 AM
 */
public class CloseConversationRequestEvent extends RequestEvent {

    private Long buddyId;
    private String conversationId;

    public CloseConversationRequestEvent(Long buddyId, String conversationId) {
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
