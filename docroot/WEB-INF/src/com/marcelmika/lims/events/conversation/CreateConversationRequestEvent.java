package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyCollectionDetails;
import com.marcelmika.lims.api.entity.MessageDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:06 PM
 */
public class CreateConversationRequestEvent extends RequestEvent {

    BuddyCollectionDetails buddyCollectionDetails;
    MessageDetails initialMessage;

    public CreateConversationRequestEvent(BuddyCollectionDetails buddyCollectionDetails, MessageDetails initialMessage) {
        this.buddyCollectionDetails = buddyCollectionDetails;
        this.initialMessage = initialMessage;
    }

    public BuddyCollectionDetails getBuddyCollectionDetails() {
        return buddyCollectionDetails;
    }

    public MessageDetails getInitialMessage() {
        return initialMessage;
    }
}
