package com.marcelmika.lims.jabber.session;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.conversation.ConversationStore;
import com.marcelmika.lims.jabber.JabberKeys;
import com.marcelmika.lims.jabber.JabberMapper;
import com.marcelmika.lims.jabber.connection.JabberConnectionManager;
import com.marcelmika.lims.jabber.connection.JabberConversationManager;
import com.marcelmika.lims.jabber.listener.JabberRosterListener;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.service.SettingsLocalServiceUtil;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberSessionCallback implements JabberSessionCallbackInterface {

    // Log
    private static Log log = LogFactoryUtil.getLog(JabberSessionCallback.class);
    // Dependencies
    private JabberConnectionManager connectionManager;
    private JabberConversationManager conversationManager;

    /**
     * JabberSessionCallback
     *
     * @param connectionManager   JabberConnectionManager
     * @param conversationManager JabberConversationManager
     */
    public JabberSessionCallback(JabberConnectionManager connectionManager,
                                 JabberConversationManager conversationManager) {
        this.connectionManager = connectionManager;
        this.conversationManager = conversationManager;
    }

    @Override
    public void sessionDidLogin(long userId, Connection connection) {
        // Add connection to the connection container
        connectionManager.putConnection(userId, connection);
        // Build conversations for the user
        conversationManager.buildConversations(userId, connection);
        // Set initial presence
        setInitialPresence(userId, connection);
        // Set roster listener
        setRosterListener(userId, connection);

        // Log
        log.info("User with ID: " + userId + " is successfully logged in");
    }

    @Override
    public void sessionDidNotAuthorize(long userId, Connection connection, Exception e) {
        log.info("Session for user: " + userId + " did not authorize. Trying to import a user (if enabled in config)" +
                " and reauthorize.");
    }

    @Override
    public void sessionDidNotLogin(long userId, Connection connection, Exception e) {
        log.error("Cannot log in user: " + userId + ". Reason: " + e.getMessage());
    }

    @Override
    public void sessionDidLogout(long userId) {
        try {
            // Change local status to off
            SettingsLocalServiceUtil.changeStatus(userId, JabberKeys.JABBER_STATUS_OFF);
            // TODO: Move to conversation manager
            // Remove conversation from the conversation store
            ConversationStore.getInstance().removeConversationContainer(userId);
        } catch (Exception e) {
            log.error("Problems during logout occurred. However, user was successfully logged out from the " +
                    "Jabber server. Reason: " + e.getMessage());
            return;
        }

        log.info("User " + userId + " was successfully logged off.");
    }

    @Override
    public void sessionDidNotLogout(long userId, Exception e) {
        log.error("User " + userId + " cannot be logged off. Reason: " + e.getMessage());
    }

    /**
     * Set user initial presence in liferay and jabber server
     *
     * @param userId     long
     * @param connection Connection
     */
    private void setInitialPresence(long userId, Connection connection) {
        try {
            // Create empty present
            Presence presence;
            // Get settings object for the particular user
            Settings settings = SettingsLocalServiceUtil.getSettings(userId);
            // Only if the chat is enabled
            if (settings.getChatEnabled()) {
                // Change on Liferay server side
                SettingsLocalServiceUtil.changeStatus(userId, JabberKeys.JABBER_STATUS_ONLINE);
                // Change on Jabber server side
                presence = JabberMapper.mapStatusToPresence(JabberKeys.JABBER_STATUS_ONLINE);
            } else {
                // Chat is not enabled -> set to off
                presence = JabberMapper.mapStatusToPresence(JabberKeys.JABBER_STATUS_OFF);
            }

            // Set presence packet to jabber server
            connection.sendPacket(presence);
        } catch (Exception e) {
            log.error("Cannot set user presence. However, login continues. Reason: " + e.getMessage());
        }
    }

    /**
     * Sets roster listener for the particular user and connection
     *
     * @param userId     long
     * @param connection Connection
     */
    private void setRosterListener(long userId, Connection connection) {
        // Get user from the local db
        User user;
        try {
            user = UserLocalServiceUtil.getUserById(userId);
        } catch (Exception e) {
            log.error("Cannot set roster listener. However, login continues. Reason: " + e.getMessage());
            return;
        }

        // Get roster from connection
        Roster roster = connection.getRoster();
        // Add roster listener
        roster.addRosterListener(new JabberRosterListener(user.getUserId(), user.getCompanyId()));
    }

    /**
     * Adds local buddies to roster
     *
     * @param userId     long
     * @param connection Connection
     */
    private void addBuddiesToRoster(long userId, Connection connection) {
//        Roster roster = connection.getRoster();
//        List<Buddy> buddies = ChatUtil.getBuddyList(user.getUserId());
//        for (Buddy buddy : buddies) {
//            String buddyJabberId = getJabberId(buddy.getScreenName());
//            if (!roster.contains(buddyJabberId)) {
//                roster.createEntry(buddyJabberId, buddy.getFullName(), null);
//            }
//        }
    }

}
