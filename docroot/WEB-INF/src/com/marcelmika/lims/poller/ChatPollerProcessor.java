package com.marcelmika.lims.poller;

import com.marcelmika.lims.conversation.Conversation;
import com.marcelmika.lims.util.ChatUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.Validator;
import com.marcelmika.lims.model.Buddy;
import com.marcelmika.lims.model.Settings;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ChatPollerProcessor extends BasePollerProcessor {

    private ChatPollerParser parser = new ChatPollerParser();

    // ------------------------------------------------------------------------------
    //   DONE
    // ------------------------------------------------------------------------------
    protected void getBuddyList(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
        List<Buddy> buddies = ChatUtil.getBuddyList(pollerRequest.getUserId());

        // Compose json array
        JSONArray buddiesJSON = JSONFactoryUtil.createJSONArray();
        for (Buddy buddy : buddies) {
            buddiesJSON.put(buddy.toJSON());
        }

        pollerResponse.setParameter("buddies", buddiesJSON);
    }

    protected void saveSettings(PollerRequest pollerRequest) throws Exception {
        boolean mute = getBoolean(pollerRequest, "mute");
        // Get settings
        Settings settings = ChatUtil.getSettings(pollerRequest.getUserId());
        // Set new values
        settings.setMute(mute);
        // Save settings
        ChatUtil.updateSettings(settings);
    }

    protected void changeStatus(PollerRequest pollerRequest) throws Exception {
        String status = getString(pollerRequest, "status");
        // Change status
        ChatUtil.changeStatus(pollerRequest.getUserId(), status);
    }

    protected void changeActivePanel(PollerRequest pollerRequest) throws Exception {
        String activePanelId = getString(pollerRequest, "activePanelId");
//        System.out.println("CHANGING PANEL: " + activePanelId);
        // Change active panel
        ChatUtil.changeActivePanel(pollerRequest.getUserId(), activePanelId);
        // While user opens panel unread messages should be set to zero
        if (!Validator.isNull(activePanelId)) {
            ChatUtil.setUnreadMessages(pollerRequest.getUserId(), activePanelId, 0);
        }
    }

    protected void setChatEnabled(PollerRequest pollerRequest) throws Exception {
        boolean enabled = getBoolean(pollerRequest, "enabled");
        String status = getString(pollerRequest, "status");
        ChatUtil.setChatEnabled(pollerRequest.getUserId(), enabled, status);
    }

    /**
     * Fetches all conversations related to the particular user
     * Returns conversations without messages
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     * @throws Exception
     */
    protected void getAllConversations(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
        // Fetch conversations
        List<Conversation> conversations = ChatUtil.getConversations(pollerRequest.getUserId());

        // Json array of conversations
        JSONArray conversationsJSON = JSONFactoryUtil.createJSONArray();
        // Compose array
        for (Conversation conversation : conversations) {
            // Add only conversations that are alive
            if (conversation.getParticipants().size() > 1) {
                conversationsJSON.put(conversation.toJSON());
            }
        }

        // Set response
        pollerResponse.setParameter("conversations", conversationsJSON);
    }

    /**
     * Fetches all conversations which were opened by the user. Returns full
     * conversation with all messages
     *
     * @param pollerRequest  Poller Request
     * @param pollerResponse Poller Response
     * @throws Exception
     */
    protected void getOpenedConversations(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
        // Params
        long userId = pollerRequest.getUserId();
        List<Conversation> conversations = ChatUtil.getOpenedConversations(userId, false);

        // Compose json array
        JSONArray conversationsJSON = JSONFactoryUtil.createJSONArray();

        // Compose array
        for (Conversation conversation : conversations) {
            String conversationId = conversation.getConversationId();
            // [1] We want to send all messages on the initial request
            if (pollerRequest.isInitialRequest()) {
                conversation.setLastMessageSent(0);
            }

            // [2] Make poller faster while sending new messages
            if (conversation.getLastMessageSent() < conversation.getMessages().size()) {
                pollerResponse.setParameter(PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY, Boolean.TRUE.toString());
            }

            // [3] Active conversations don't have unread messages             
            if (ChatUtil.isConversationActive(userId, conversationId)) {
                ChatUtil.setUnreadMessages(userId, conversationId, 0);
            }

            // [4] Add conversation to json
            conversationsJSON.put(conversation.toFullJSON());

            // [5] Set last message counter to the index of last message
            conversation.setLastMessageSent(conversation.getIndexOfLastMessage());
        }

        // Set response
        pollerResponse.setParameter("openedConversations", conversationsJSON);
    }

    /**
     * Opens conversation for the particular user
     *
     * @param pollerRequest Poller Request
     * @throws Exception
     */
    protected void openConversation(PollerRequest pollerRequest) throws Exception {
        // Params
        String conversationId = getString(pollerRequest, "roomJID");
        // Open conversation
        ChatUtil.openConversation(pollerRequest.getUserId(), conversationId);
    }

    /**
     * Closes conversation for the particular user
     *
     * @param pollerRequest Poller Request
     * @throws Exception
     */
    protected void closeConversation(PollerRequest pollerRequest) throws Exception {
        // Params
        String conversationId = getString(pollerRequest, "roomJID");

        // Close only opened conversations
        if (ChatUtil.isConversationOpened(pollerRequest.getUserId(), conversationId)) {
            // Close conversation
            Conversation c = ChatUtil.closeConversation(pollerRequest.getUserId(), conversationId);
            // Reset message counter
            if (c != null) {
                c.setLastMessageSent(0);
            }
        }
    }

    /**
     * Leaves conversation for the particular user
     *
     * @param pollerRequest Poller Request
     * @throws Exception
     */
    protected void leaveConversation(PollerRequest pollerRequest) throws Exception {
        // Params
        String conversationId = getString(pollerRequest, "roomJID");
        // Leave conversation
        ChatUtil.leaveConversation(pollerRequest.getUserId(), conversationId);
    }

    /**
     * Creates Multi user conversation
     *
     * @param pollerRequest pollerRequest
     * @throws Exception
     */
    private void createConversation(PollerRequest pollerRequest) throws Exception {
        // Params
        String message = getString(pollerRequest, "message");
        String users = getString(pollerRequest, "users");
        // Parse buddies from request
        List<Buddy> participants = parser.parseUsersToBuddies(users);

        // [1] Create conversation
        Conversation conversation = ChatUtil.createConversation(pollerRequest.getUserId(), participants, message);

        // [2] Open conversation for the owner
        ChatUtil.openConversation(pollerRequest.getUserId(), conversation.getConversationId());

        // [3] Open conversation for all participants
        for (Buddy participant : participants) {
            ChatUtil.openConversation(participant.getUserId(), conversation.getConversationId());
        }
    }

    protected void sendMessage(PollerRequest pollerRequest) throws Exception {
//        System.out.println("[POLLER][START SENDING][" + pollerRequest.getUserId() + "]");

        // Params
        String message = getString(pollerRequest, "message");
        String conversationId = getString(pollerRequest, "roomJID");

        // [1] Find conversation
        Conversation conversation = ChatUtil.getConversation(pollerRequest.getUserId(), conversationId);

        // [2] Send message
        ChatUtil.sendMessage(pollerRequest.getUserId(), conversation, message);

        // [3] Handle buddies in conversation
        for (Buddy participant : conversation.getParticipants()) {

            // [4] Open conversation for all buddies in the conversation
            if (!ChatUtil.isConversationOpened(participant, conversationId)) {
                Conversation c = ChatUtil.openConversation(participant, conversationId);
                // Reset message counter
                if (c != null) {
                    c.setLastMessageSent(0);
                }
            }

            // [5] Increment number of unread messages
            Settings settings = ChatUtil.getSettings(participant);
            // Increment only for not active panels
            if (!settings.getActivePanelId().equals(conversationId)) {
                ChatUtil.incrementUnreadMessages(participant.getUserId(), conversationId);
            }
        }
    }

    // ------------------------------------------------------------------------------
    //   NOT IN THIS VERSION
    // ------------------------------------------------------------------------------
    // @todo: Not implemented in v0.2
    protected void addToConversation(PollerRequest pollerRequest) throws Exception {
        // Params
        String users = getString(pollerRequest, "users");
        String roomJID = getString(pollerRequest, "roomJID");
        // Parse buddies from request
        List<Buddy> buddies = parser.parseUsersToBuddies(users);

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
     * Changes active room type setting
     *
     * @param pollerRequest PollerRequest
     * @throws Exception
     */
    protected void changeActiveRoomType(PollerRequest pollerRequest) throws Exception {
        String roomType = getString(pollerRequest, "roomVisibility");
        ChatUtil.changeActiveRoomType(pollerRequest.getUserId(), roomType);
    }

    // ------------------------------------------------------------------------------
    //   POLLER REQUEST/RESPONSE
    // ------------------------------------------------------------------------------
    @Override
    protected void doReceive(PollerRequest pollerRequest, PollerResponse pollerResponse) throws Exception {
//        System.out.println(new Date() + " [POLLER][RECEIVE] " + pollerRequest.toString());

        // Will be called once during the initial request
        if (pollerRequest.isInitialRequest()) {
            // Make poller faster on the initial request
            pollerResponse.setParameter(PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY, Boolean.TRUE.toString());
        }

        // Will be called every time 
        try {
            getBuddyList(pollerRequest, pollerResponse);
            getAllConversations(pollerRequest, pollerResponse);
            getOpenedConversations(pollerRequest, pollerResponse);
        } catch (Exception ex) {
            pollerResponse.setParameter("error", ex.getMessage());
            System.out.println("[ERROR] " + ex.getMessage());
            // Uncomment for testing purpouses
//            throw ex;
        }
    }

    @Override
    protected void doSend(PollerRequest pollerRequest) throws Exception {
//        System.out.println(new Date() + " [POLLER][SEND] " + pollerRequest.toString());

        // All requests must have a chunkId
        String chunkId = pollerRequest.getChunkId();
        if (chunkId == null) {
            return;
        }

        try {
            // Create conversation
            if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_CREATE_MESSAGE)) {
                createConversation(pollerRequest);
            } // Open conversation
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_OPEN_CONVERSATION)) {
                openConversation(pollerRequest);
            } // Close conversation
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_CLOSE_CONVERSATION)) {
                closeConversation(pollerRequest);
            } // Leave conversation
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_LEAVE_CONVERSATION)) {
                leaveConversation(pollerRequest);
            } // Add to conversation
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_ADD_TO_CONVERSATION)) {
                // @todo: Not implemented in v0.2
                // addToConversation(pollerRequest);
            } // Send Message            
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_SEND_MESSAGE)) {
                sendMessage(pollerRequest);
            } // Save settings
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_SAVE_SETTINGS)) {
                saveSettings(pollerRequest);
            } // Change status
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_CHANGE_STATUS)) {
                changeStatus(pollerRequest);
            } // Change active panel
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_CHANGE_ACTIVE_PANEL)) {
                changeActivePanel(pollerRequest);
            } // Enable/Disable chat
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_CHAT_ENABLED)) {
                setChatEnabled(pollerRequest);
            } // Change active room type
            else if (chunkId.equals(ChatPollerKeys.POLLER_ACTION_CHANGE_ACTIVE_ROOM_TYPE)) {
                changeActiveRoomType(pollerRequest);
            }
        } catch (Exception ex) {
            System.out.println("[ERROR] " + ex.getMessage());
            // Uncomment for testing purpouses
//            throw ex;
        }

    }
}