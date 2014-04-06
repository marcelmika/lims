package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.entity.MessageDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 3:50 PM
 */
public class SendMessageRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;
    private ConversationDetails conversationDetails;
    private MessageDetails messageDetails;

    public SendMessageRequestEvent(BuddyDetails buddyDetails,
                                   ConversationDetails conversationDetails,
                                   MessageDetails messageDetails) {
        this.buddyDetails = buddyDetails;
        this.conversationDetails = conversationDetails;
        this.messageDetails = messageDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }

    public ConversationDetails getConversationDetails() {
        return conversationDetails;
    }

    public MessageDetails getMessageDetails() {
        return messageDetails;
    }
}
