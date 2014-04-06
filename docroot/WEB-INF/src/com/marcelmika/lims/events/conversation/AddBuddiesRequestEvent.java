package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyCollectionDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;


/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:41 PM
 */
public class AddBuddiesRequestEvent extends RequestEvent {

    private BuddyCollectionDetails buddyCollectionDetails;
    private ConversationDetails conversationDetails;

    public AddBuddiesRequestEvent(BuddyCollectionDetails buddyCollectionDetails, ConversationDetails conversationDetails) {
        this.buddyCollectionDetails = buddyCollectionDetails;
        this.conversationDetails = conversationDetails;
    }

    public BuddyCollectionDetails getBuddyCollectionDetails() {
        return buddyCollectionDetails;
    }

    public ConversationDetails getConversationDetails() {
        return conversationDetails;
    }
}
