package com.marcelmika.lims.events.conversation;

import com.marcelmika.lims.events.RequestEvent;
import com.marcelmika.lims.events.details.BuddyDetails;
import com.marcelmika.lims.events.details.MessageDetails;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:06 PM
 */
public class ConversationCreateRequestEvent extends RequestEvent {

    List<BuddyDetails> buddies;
    MessageDetails initialMessage;

    public ConversationCreateRequestEvent(List<BuddyDetails> buddies, MessageDetails initialMessage) {
        this.buddies = buddies;
        this.initialMessage = initialMessage;
    }

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }

    public MessageDetails getInitialMessage() {
        return initialMessage;
    }
}
