package com.marcelmika.lims.core.service;

import com.marcelmika.lims.api.events.conversation.*;

/**
 * Serves as a port to the business logic related to conversation.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:01 PM
 */
public interface ConversationCoreService {

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event);

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event);

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event);

    /**
     * Opens existing conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public OpenConversationResponseEvent openConversation(OpenConversationRequestEvent event);

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event request event for method
     * @return response event for method
     */
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event);

    /**
     * Removes buddy from the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event);


    /**
     * Adds buddies to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public AddBuddiesResponseEvent addBuddies(AddBuddiesRequestEvent event);

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event);
}
