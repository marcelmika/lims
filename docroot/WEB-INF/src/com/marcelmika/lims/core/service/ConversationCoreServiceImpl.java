package com.marcelmika.lims.core.service;

import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.jabber.service.ConversationJabberService;
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

    /**
     * Constructor
     *
     * @param conversationJabberService jabber service
     */
    public ConversationCoreServiceImpl(ConversationJabberService conversationJabberService) {
        this.conversationJabberService = conversationJabberService;
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
        throw new NotImplementedException();
        //        for (Conversation conversation : conversations) {
//            String conversationId = conversation.getConversationId();
//            // [1] We want to send all messages on the initial request
//            if (pollerRequest.isInitialRequest()) {
//                conversation.setLastMessageSent(0);
//            }
//
//            // [2] Make poller faster while sending new messages
//            if (conversation.getLastMessageSent() < conversation.getMessages().size()) {
//                pollerResponse.setParameter(PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY, Boolean.TRUE.toString());
//            }
//
//            // [3] Active conversations don't have unread messages
//            if (ChatUtil.isConversationActive(userId, conversationId)) {
//                ChatUtil.setUnreadMessages(userId, conversationId, 0);
//            }
//
//            // [4] Add conversation to json
//            conversationsJSON.put(conversation.toFullJSON());
//
//            // [5] Set last message counter to the index of last message
//            conversation.setLastMessageSent(conversation.getIndexOfLastMessage());
//        }

    }

    /**
     * Creates new conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {
        throw new NotImplementedException();

// [1] Create conversation
//        Conversation conversation = ChatUtil.createConversation(pollerRequest.getUserId(), participants, message);
//
//        // [2] Open conversation for the owner
//        ChatUtil.openConversation(pollerRequest.getUserId(), conversation.getConversationId());
//
//        // [3] Open conversation for all participants
//        for (com.marcelmika.lims.model.Buddy participant : participants) {
//            ChatUtil.openConversation(participant.getUserId(), conversation.getConversationId());
//        }
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
//        throw new NotImplementedException();

        conversationJabberService.sendMessage(event);

         return null;
//        System.out.println("[POLLER][START SENDING][" + pollerRequest.getUserId() + "]");

//        // Params
//        String message = getString(pollerRequest, "message");
//        String conversationId = getString(pollerRequest, "roomJID");
//
//        // [1] Find conversation
//        Conversation conversation = ChatUtil.getConversation(pollerRequest.getUserId(), conversationId);
//
//        // [2] Send message
//        ChatUtil.sendMessage(pollerRequest.getUserId(), conversation, message);
//
//        // [3] Handle buddies in conversation
//        for (com.marcelmika.lims.model.Buddy participant : conversation.getParticipants()) {
//
//            // [4] Open conversation for all buddies in the conversation
//            if (!ChatUtil.isConversationOpened(participant, conversationId)) {
//                Conversation c = ChatUtil.openConversation(participant, conversationId);
//                // Reset message counter
//                if (c != null) {
//                    c.setLastMessageSent(0);
//                }
//            }
//
//            // [5] Increment number of unread messages
//            Settings settings = ChatUtil.getSettings(participant);
//            // Increment only for not active panels
//            if (!settings.getActivePanelId().equals(conversationId)) {
//                ChatUtil.incrementUnreadMessages(participant.getUserId(), conversationId);
//            }
//        }
    }


}
