package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.conversation.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:03 PM
 */
public class ConversationCoreServiceImpl implements ConversationCoreService {

    /**
     * Creates new conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {
        throw new RuntimeException("Not implemented");

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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event) {

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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Removes buddy from the conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event) {
        throw new RuntimeException("Not implemented");

        // Leave conversation
//        ChatUtil.leaveConversation(pollerRequest.getUserId(), conversationId);
    }


}
