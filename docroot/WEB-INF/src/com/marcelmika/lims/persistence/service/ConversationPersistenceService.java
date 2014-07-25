package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.conversation.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:47 AM
 */
public interface ConversationPersistenceService {

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event);

    /**
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event);

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event request event for method
     * @return response event for method
     */
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event);

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent
                                                                                      event);

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event);

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event);

}
