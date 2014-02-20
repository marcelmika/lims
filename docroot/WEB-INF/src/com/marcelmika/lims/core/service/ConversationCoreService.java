package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.conversation.ConversationCreateRequestEvent;
import com.marcelmika.lims.events.conversation.ConversationCreateResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:01 PM
 */
public interface ConversationCoreService {

    /**
     * Creates new conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public ConversationCreateResponseEvent createConversation(ConversationCreateRequestEvent event);
}
