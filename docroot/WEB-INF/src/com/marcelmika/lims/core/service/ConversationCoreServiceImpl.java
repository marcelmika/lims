package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.conversation.*;
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

    /**
     * Get all conversations related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event) {
        throw new NotImplementedException();
        // Fetch conversations
//        List<Conversation> conversations = ChatUtil.getConversations(pollerRequest.getUserId());
//
//        // Json array of conversations
//        JSONArray conversationsJSON = JSONFactoryUtil.createJSONArray();
//        // Compose array
//        for (Conversation conversation : conversations) {
//            // Add only conversations that are alive
//            if (conversation.getParticipants().size() > 1) {
//                conversationsJSON.put(conversation.toJSON());
//            }
//        }
//
//        // Set response
//        pollerResponse.setParameter("conversations", conversationsJSON);

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
        throw new NotImplementedException();


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
