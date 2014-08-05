package com.marcelmika.lims.core.service;

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.jabber.service.ConversationJabberService;
import com.marcelmika.lims.persistence.service.ConversationPersistenceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Implementation of ConversationCoreService
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:03 PM
 */
public class ConversationCoreServiceImpl implements ConversationCoreService {

    // Dependencies
    ConversationJabberService conversationJabberService;
    ConversationPersistenceService conversationPersistenceService;

    /**
     * Constructor
     *
     * @param conversationJabberService jabber service
     */
    public ConversationCoreServiceImpl(final ConversationJabberService conversationJabberService,
                                       final ConversationPersistenceService conversationPersistenceService) {
        this.conversationJabberService = conversationJabberService;
        this.conversationPersistenceService = conversationPersistenceService;
    }

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event) {
        throw new NotImplementedException();
    }

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event) {
        // Read from persistence
        return conversationPersistenceService.getOpenedConversations(event);
    }

    /**
     * Creates new conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {
        // Create conversation locally
        CreateConversationResponseEvent responseEvent = conversationPersistenceService.createConversation(event);
        // Check for error
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }

        // If enabled create in jabber too
        if (Environment.isJabberEnabled()) {
            conversationJabberService.createConversation(event);
        }

        return responseEvent;
    }

    /**
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event) {
        // Read from persistence
        return conversationPersistenceService.readConversation(event);
    }

    /**
     * Opens existing conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public OpenConversationResponseEvent openConversation(OpenConversationRequestEvent event) {
        throw new NotImplementedException();
    }

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event) {
        // Save to persistence
        return conversationPersistenceService.closeConversation(event);
    }

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent event) {
        // Save to persistence
        return conversationPersistenceService.resetUnreadMessagesCounter(event);
    }

    /**
     * Removes buddy from the conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event) {
        throw new NotImplementedException();
    }

    /**
     * Adds buddies to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public AddBuddiesResponseEvent addBuddies(AddBuddiesRequestEvent event) {
        throw new NotImplementedException();
    }

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {
// TODO: Implement
//        return conversationJabberService.createMessage(event);

        return conversationPersistenceService.sendMessage(event);
    }


}
