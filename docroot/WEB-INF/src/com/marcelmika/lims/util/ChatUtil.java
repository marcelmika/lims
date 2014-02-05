
package com.marcelmika.lims.util;

import com.marcelmika.lims.conversation.Conversation;
import com.marcelmika.lims.jabber.JabberUtil;
import com.marcelmika.lims.model.OpenedConversation;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.service.OpenedConversationLocalServiceUtil;
import com.marcelmika.lims.service.SettingsLocalServiceUtil;

import java.util.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ChatUtil {

    /**
     * Returns a list of buddies in all conversations where the user is
     * participating. List has no duplicates.
     *
     * @param userId
     * @return List of buddies with no duplicates
     * @throws Exception
     */
    public static List<com.marcelmika.lims.model.Buddy> getBuddyList(long userId) throws Exception {
        List<Conversation> conversations = getConversations(userId);
        List<com.marcelmika.lims.model.Buddy> buddies = new LinkedList<com.marcelmika.lims.model.Buddy>();
        Set<Long> buddyIds = new HashSet<Long>();

        // Find set of buddies
        for (Conversation conversation : conversations) {
            for (com.marcelmika.lims.model.Buddy buddy : conversation.getParticipants()) {
                // User who called this methods is not his own buddy
                if (buddy.getUserId() == userId) {
                    continue;
                }
                // We want a set of buddies with no duplicates
                boolean isNew = buddyIds.add(buddy.getUserId());
                if (isNew) {
                    buddies.add(buddy);
                }
            }
        }
        return buddies;
    }

    // ------------------------------------------------------------------------------
    //   Message
    // ------------------------------------------------------------------------------ 
    public static void sendMessage(long userId, Conversation conversation, String message) throws Exception {
        JabberUtil.sendMessage(userId, conversation, message);
    }

    // ------------------------------------------------------------------------------
    //   Conversation
    // ------------------------------------------------------------------------------
    public static Conversation createConversation(long userId, List<com.marcelmika.lims.model.Buddy> participants, String message) throws Exception {
        return JabberUtil.createConversation(userId, participants, message);
    }

    // @todo: Not implemented in v0.2
    public static void addParticipants(long userId, Conversation conversation, List<com.marcelmika.lims.model.Buddy> participants) throws Exception {
        JabberUtil.addParticipants(userId, conversation, participants);
    }

    public static List<Conversation> getConversations(long userId) throws Exception {
        return JabberUtil.getConversations(userId);
    }

    public static Conversation getConversation(long userId, String conversationId) throws Exception {
        return JabberUtil.getConversation(userId, conversationId);
    }

    /**
     * @param userId
     * @param resetStatus Whether unread messages should be set to zero and last
     *                    message sent counter should be set to the index of the last message
     * @return
     * @throws Exception
     */
    public static List<Conversation> getOpenedConversations(long userId, boolean resetStatus) throws Exception {
        List<Conversation> conversations = new ArrayList<Conversation>();
        List<OpenedConversation> openedConversations = OpenedConversationLocalServiceUtil.getOpenedConversations(userId);

        for (OpenedConversation openedConversation : openedConversations) {
            Conversation conversation = JabberUtil.getConversation(userId, openedConversation.getConversationId());

            if (conversation == null) {
                continue;
            }

            // Add only conversations that have participants
            if (conversation.getParticipants().size() > 0) {
                conversations.add(conversation);
            }

            // This will set unread messages to zero and last message sent counter to the index of
            // last message
            if (resetStatus) {
                String conversationId = conversation.getConversationId();
                // Active conversations don't have unread messages             
                if (ChatUtil.isConversationActive(userId, conversationId)) {
                    ChatUtil.setUnreadMessages(userId, conversationId, 0);
                }

                // Set last message counter to the index of last message
                conversation.setLastMessageSent(conversation.getIndexOfLastMessage());
            }
        }

        return conversations;
    }

    /**
     * Opens conversation and returns Conversation object if the user is
     * connected
     *
     * @param userId
     * @return Conversation if the user is connected. Null if conversation was
     * opened but user is not connected.
     * @throws Exception If the conversation wasn't opened.
     */
    public static Conversation openConversation(long userId, String conversationId) throws Exception {
        OpenedConversationLocalServiceUtil.openConversation(userId, conversationId);

        Conversation conversation = null;
        // Conversation was opened but user doesn't need to be connected.
        // Because of that we may get an exception while trying to get a particular
        // conversation.
        try {
            conversation = getConversation(userId, conversationId);
        } catch (Exception ex) {
        }

        return conversation;
    }

    /**
     * Opens conversation and returns Conversation object if the user is
     * connected
     *
     * @param buddy
     * @return Conversation if user is connected. Null if conversation was
     * opened but user is not connected.
     * @throws Exception If the conversation wasn't opened.
     */
    public static Conversation openConversation(com.marcelmika.lims.model.Buddy buddy, String conversationId) throws Exception {
        OpenedConversationLocalServiceUtil.openConversation(buddy.getUserId(), conversationId);

        Conversation conversation = null;
        // Conversation was opened but user doesn't need to be connected.
        // Because of that we may get an exception while trying to get a particular
        // conversation.
        try {
            conversation = getConversation(buddy.getUserId(), conversationId);
        } catch (Exception ex) {
        }

        return conversation;
    }

    public static Conversation closeConversation(long userId, String conversationId) throws Exception {
        OpenedConversationLocalServiceUtil.closeConversation(userId, conversationId);
        return getConversation(userId, conversationId);
    }

    public static void leaveConversation(long userId, String conversationId) throws Exception {
        JabberUtil.leaveConversation(userId, conversationId);
        // Remove on the local side
        ConversationLocalServiceUtil.removeConversation(userId, conversationId);
    }

    public static boolean isConversationActive(long userId, String conversationId) throws Exception {
        Settings settings = getSettings(userId);
        String activePanelId = settings.getActivePanelId();

        return activePanelId.equals(conversationId);
    }

    public static boolean isConversationOpened(long userId, String conversationId) {
        return OpenedConversationLocalServiceUtil.isConversationOpened(userId, conversationId);
    }

    public static boolean isConversationOpened(com.marcelmika.lims.model.Buddy buddy, String conversationId) {
        return OpenedConversationLocalServiceUtil.isConversationOpened(buddy.getUserId(), conversationId);
    }

    // ------------------------------------------------------------------------------
    //   Unread messages
    // ------------------------------------------------------------------------------
    public static void incrementUnreadMessages(long userId, String conversationId) throws Exception {
        ConversationLocalServiceUtil.incrementUnreadMessages(userId, conversationId);
    }

    public static void setUnreadMessages(long userId, String conversationId, int unreadMessages) throws Exception {
        ConversationLocalServiceUtil.setUnreadMessages(userId, conversationId, unreadMessages);
    }

    // ------------------------------------------------------------------------------
    //   Settings
    // ------------------------------------------------------------------------------

    /**
     * @param userId
     * @param roomType
     * @throws Exception
     * @deprecated
     */
    public static void changeActiveRoomType(long userId, String roomType) throws Exception {
        SettingsLocalServiceUtil.changeActiveRoomType(userId, roomType);
    }

    public static Settings getSettings(long userId) throws Exception {
        return SettingsLocalServiceUtil.getSettings(userId);
    }

    public static Settings getSettings(com.marcelmika.lims.model.Buddy buddy) throws Exception {
        return SettingsLocalServiceUtil.getSettings(buddy.getUserId());
    }

    /**
     * @param userId
     * @param status
     * @throws Exception
     * @deprecated
     */
    public static void changeStatus(long userId, String status) throws Exception {
        // Save to settings                
        SettingsLocalServiceUtil.changeStatus(userId, status);
        // Send to jabber
        JabberUtil.changeStatus(userId, status);
    }

    /**
     * @param userId
     * @param activePanelId
     * @throws Exception
     * @deprecated
     */
    public static void changeActivePanel(long userId, String activePanelId) throws Exception {
        // Get settings
        Settings settings = SettingsLocalServiceUtil.getSettings(userId);
        // Set values                
        settings.setActivePanelId(activePanelId);
        // Save settings
        SettingsLocalServiceUtil.updateSettings(settings, false);
    }

    public static void updateSettings(Settings settings) throws Exception {
        SettingsLocalServiceUtil.updateSettings(settings, false);
    }

    public static void setChatEnabled(long userId, boolean enabled, String status) throws Exception {
        // Save to server
        SettingsLocalServiceUtil.setChatEnabled(userId, enabled);
        // Change status
        changeStatus(userId, status);
        // There is no active panel anymore
        changeActivePanel(userId, "");

        // Refresh conversations
        if (enabled) {
            JabberUtil.restartConversations(userId);
        }
    }
}
