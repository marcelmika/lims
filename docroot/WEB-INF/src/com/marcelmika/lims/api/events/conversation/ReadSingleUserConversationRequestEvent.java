package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.entity.PaginationDetails;
import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:58 PM
 */
public class ReadSingleUserConversationRequestEvent extends RequestEvent {

    private final BuddyDetails participant;
    private final ConversationDetails conversation;
    private final PaginationDetails pagination;

    public ReadSingleUserConversationRequestEvent(final BuddyDetails participant,
                                                  final ConversationDetails conversation,
                                                  final PaginationDetails pagination) {
        this.participant = participant;
        this.conversation = conversation;
        this.pagination = pagination;
    }

    public BuddyDetails getParticipant() {
        return participant;
    }

    public ConversationDetails getConversation() {
        return conversation;
    }

    public PaginationDetails getPagination() {
        return pagination;
    }
}
