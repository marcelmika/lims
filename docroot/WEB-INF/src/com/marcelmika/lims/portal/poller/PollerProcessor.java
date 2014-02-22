package com.marcelmika.lims.portal.poller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.marcelmika.lims.core.service.*;
import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.buddy.UpdateStatusBuddyRequestEvent;
import com.marcelmika.lims.events.conversation.*;
import com.marcelmika.lims.events.settings.UpdateActiveRoomTypeRequestEvent;
import com.marcelmika.lims.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.BuddyCollection;
import com.marcelmika.lims.portal.domain.Conversation;
import com.marcelmika.lims.portal.domain.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Receives and sends messages to the Liferay frontend javascript later where is periodically consumed.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PollerProcessor extends BasePollerProcessor {

    // Log
    private static Log log = LogFactoryUtil.getLog(PollerProcessor.class);

    // Dependencies
    BuddyCoreService buddyCoreService = BuddyCoreServiceUtil.getBuddyCoreService();
    ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();


    /**
     * This method is called whenever the user calls submitRequest(portletId, data, key) method in
     * browser (used in Liferay.Chat.Poller.js).
     * Unfortunately, this method does not return any value. As a result, there is no way
     * to confirm success or failure of the given request because no response (!) is received.
     * Consequently, there is no other way to receive data from the backend besides waiting
     * for the response of the receive action (i.e. waiting for the result of doReceive() method).
     *
     * @param pollerRequest sent via the submitRequest(portletId, data, key) method from browser
     * @throws IllegalArgumentException if request does not contain chunkID used to determine request type
     */
    @Override
    protected void doSend(PollerRequest pollerRequest) throws IllegalArgumentException {
        // Poller dispatcher will call appropriate method on poller processor
        ResponseEvent responseEvent = PollerDispatcher.dispatchSendRequest(pollerRequest, this);

        // Log info
        if (responseEvent != null) {
            log.info(responseEvent.getResult());
        }
    }

    /**
     * This method serves as a request/response gateway. However, it cannot be explicitly triggered.
     * Liferay has its own timer, which repeats the given operation for each allotted amount of time.
     * This method is periodically called from the browser (called in Liferay.Chat.Poller.js).
     *
     * @param pollerRequest  sent periodically from browser
     * @param pollerResponse provided response
     * @throws Exception
     */
    @Override
    protected void doReceive(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
        // Initial request is the first request sent to the poller (i.e. whenever the user opens any page).
        // If the user moves to another page within the portal, new initial request is sent.
        if (pollerRequest.isInitialRequest()) {
            // Make polling faster on the initial request
            pollerResponse.setParameter(PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY, Boolean.TRUE.toString());
        }

        // Poller dispatcher will call appropriate methods on poller processor scheduled for
        // the do receive event
        PollerDispatcher.dispatchReceiveRequest(pollerRequest, pollerResponse, this);
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Buddy Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Update buddy's status
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent updateStatus(PollerRequest pollerRequest) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return buddyCoreService.updateStatus(new UpdateStatusBuddyRequestEvent(
                buddy.getBuddyId(), buddy.getStatus())
        );
    }

    /**
     * Update buddy's settings
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent updateSettings(PollerRequest pollerRequest) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return settingsCoreService.updateSettings(new UpdateSettingsRequestEvent(
                buddy.getBuddyId(), buddy.getSettings().toSettingsDetails())
        );
    }

    /**
     * Update buddy's active room type
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent updateActiveRoomType(PollerRequest pollerRequest) {
        // Create buddy from poller request
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return settingsCoreService.updateActiveRoomType(new UpdateActiveRoomTypeRequestEvent(
                buddy.getBuddyId(), buddy.getSettings().getActiveRoomType())
        );
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Conversation Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Creates conversation from a list of buddies involved in the conversation and an initial message
     *
     * @param pollerRequest PollerRequest
     * @return ResponseEvent
     */
    protected ResponseEvent createConversation(PollerRequest pollerRequest) {
        // Create objects from poller request
        Message message = Message.fromPollerRequest(pollerRequest);
        BuddyCollection buddies = BuddyCollection.fromPollerRequest(pollerRequest);

        // Send request to core service
        return conversationCoreService.createConversation(new CreateConversationRequestEvent(
                buddies.toBuddyCollectionDetails(), message.toMessageDetails())
        );
    }

    /**
     * Open conversation for the particular buddy
     *
     * @param pollerRequest Poller Request
     * @return ResponseEvent
     */
    protected ResponseEvent openConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy from the poller request
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.openConversation(new OpenConversationRequestEvent(
                buddy.getBuddyId(), conversation.getConversationId())
        );
    }

    /**
     * Close conversation for the particular buddy
     *
     * @param pollerRequest Poller Request
     * @return ResponseEvent
     */
    protected ResponseEvent closeConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy from poller request
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.closeConversation(new CloseConversationRequestEvent(
                buddy.getBuddyId(), conversation.getConversationId()
        ));
    }

    /**
     * Leave conversation for the particular buddy
     *
     * @param pollerRequest Poller Request
     * @return ResponseEvent
     */
    protected ResponseEvent leaveConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy from poller request
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        Buddy buddy = Buddy.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.leaveConversation(new LeaveConversationRequestEvent(
                buddy.getBuddyId(), conversation.getConversationId()
        ));
    }

    /**
     * Add a list of buddies to the conversation
     *
     * @param pollerRequest Poller Request
     * @return ResponseEvent
     */
    protected ResponseEvent addToConversation(PollerRequest pollerRequest) {
        // Create conversation and buddy collection
        Conversation conversation = Conversation.fromPollerRequest(pollerRequest);
        BuddyCollection buddies = BuddyCollection.fromPollerRequest(pollerRequest);
        // Send request to core service
        return conversationCoreService.addBuddies(new AddBuddiesRequestEvent(
                buddies.toBuddyCollectionDetails(), conversation.toConversationDetails()
        ));
    }


    // ------------------------------------------------------------------------------
    //   To Refactor:
    // ------------------------------------------------------------------------------
    protected ResponseEvent changeActivePanel(PollerRequest pollerRequest) {
        throw new NotImplementedException();
//        String activePanelId = getString(pollerRequest, "activePanelId");
//        ChatUtil.changeActivePanel(pollerRequest.getUserId(), activePanelId);
//        // While user opens panel unread messages should be set to zero
//        if (!Validator.isNull(activePanelId)) {
//            ChatUtil.setUnreadMessages(pollerRequest.getUserId(), activePanelId, 0);
//        }
    }


    protected void getBuddyList(PollerRequest pollerRequest, PollerResponse pollerResponse) {
        log.info("getBuddyList not implemented");
//        List<com.marcelmika.lims.model.Buddy> buddies = ChatUtil.getBuddyList(pollerRequest.getUserId());

        // Compose json array
//        JSONArray buddiesJSON = JSONFactoryUtil.createJSONArray();
//        for (com.marcelmika.lims.model.Buddy buddy : buddies) {
//            buddiesJSON.put(buddy.toJSON());
//        }

//        pollerResponse.setParameter("buddies", buddiesJSON);
    }


    protected ResponseEvent setChatEnabled(PollerRequest pollerRequest) {
        throw new NotImplementedException();
//        boolean enabled = getBoolean(pollerRequest, "enabled");
//        String status = getString(pollerRequest, "status");
//        ChatUtil.setChatEnabled(pollerRequest.getUserId(), enabled, status);
    }

    /**
     * Fetches all conversations related to the particular user
     * Returns conversations without messages
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     * @throws Exception
     */
    protected void getAllConversations(PollerRequest pollerRequest, PollerResponse pollerResponse) {
        log.info("getAllConversations not implemented");
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
     * Fetches all conversations which were opened by the user. Returns full
     * conversation with all messages
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     * @throws Exception
     */
    protected void getOpenedConversations(PollerRequest pollerRequest, PollerResponse pollerResponse) {
        log.info("getOpenedConversations not implemented");
        // Params
//        long userId = pollerRequest.getUserId();
//        List<Conversation> conversations = ChatUtil.getOpenedConversations(userId, false);
//
//        // Compose json array
//        JSONArray conversationsJSON = JSONFactoryUtil.createJSONArray();
//
//        // Compose array
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
//
//        // Set response
//        pollerResponse.setParameter("openedConversations", conversationsJSON);
    }


    protected ResponseEvent sendMessage(PollerRequest pollerRequest) {
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