package com.marcelmika.lims.core.service;

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
    public ConversationCoreServiceImpl(ConversationJabberService conversationJabberService,
                                       ConversationPersistenceService conversationPersistenceService) {
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
        return conversationJabberService.getConversations(event);
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
        // Create conversation in jabber
        CreateConversationResponseEvent responseEvent = conversationJabberService.createConversation(event);
        // Check error
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }
        // Save to persistence
        return conversationPersistenceService.createConversation(event);
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
        throw new NotImplementedException();
        // Params
//        String conversationId = getString(pollerRequest, "roomJID");
//
//        // Close only opened conversations
//        if (ChatUtil.isConversationOpened(pollerRequest.getUserId(), conversationId)) {
//            // Close conversation
//            Conversation c = ChatUtil.closeConversation(pollerRequest.getUserId(), conversationId);
//            // Reset message counter
//            if (c != null) {
//                c.setLastMessageSent(0);
//            }
//        }
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

        // Leave conversation
//        ChatUtil.leaveConversation(pollerRequest.getUserId(), conversationId);
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


        // [1] Get room
//        Room room = ChatUtil.getRoom(pollerRequest.getUserId(), pollerRequest.getCompanyId(), roomJID);
//        System.out.println("ROOM: " + room);
        // [2] Add users to the newly created room
//        ChatUtil.addBuddiesToRoom(pollerRequest.getUserId(), pollerRequest.getCompanyId(), room, buddies);

        // [4] Open conversation for all buddies
//        for (Buddy buddy : buddies) {
//            ChatUtil.openConversation(buddy.getUserId(), buddy.getCompanyId(), room.getRoomJID());
//        }
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
