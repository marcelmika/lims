package com.marcelmika.lims.jabber.session;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.conversation.ConversationStore;
import com.marcelmika.lims.jabber.JabberKeys;
import com.marcelmika.lims.jabber.connection.JabberConnectionManager;
import com.marcelmika.lims.jabber.connection.JabberConversationManager;
import com.marcelmika.lims.service.SettingsLocalServiceUtil;
import org.jivesoftware.smack.Connection;

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
//        setInitialPresence(userId, connection);
//        // Set roster listener
//        setRosterListener(userId, connection);

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



}
