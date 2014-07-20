package com.marcelmika.lims.jabber.session;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManagerImpl;
import com.marcelmika.lims.jabber.conversation.ConversationManager;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.JabberKeys;
import com.marcelmika.lims.jabber.JabberMapper;
import com.marcelmika.lims.jabber.listener.JabberRosterListener;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.portal.properties.PortletPropertiesValues;
import com.marcelmika.lims.service.SettingsLocalServiceUtil;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 * @deprecated
 */
public class JabberSessionManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(JabberSessionManager.class);
    // Dependencies
    private ConnectionManagerImpl connectionManager;
    /** @deprecated */
    private ConversationManager conversationManager;

    /**
     * JabberSessionManager
     *
     * @param connectionManager JabberConnectionManager
     */
    public JabberSessionManager(ConnectionManagerImpl connectionManager,
                                ConversationManager conversationManager) {
        this.connectionManager = connectionManager;
        this.conversationManager = conversationManager;
    }

    /**
     * Connects to server and performs login action
     *
     * @param userId   long
     * @param username String
     * @param password String
     */
    public void login(long userId, String username, String password) throws JabberException {
        // User is already connected
//        if (connectionManager.isUserConnected(userId)) {
//            return;
//        }
        // Create new connection and connect
//        Connection connection = connectionManager.createConnection();

        // Connect to server
//        try {
//            connectionManager.connect(connection);
//        } catch (XMPPException e) {
//            throw new JabberException("Cannot connect to server", e);
//        }

//        try {
//            // If the SASL is enabled login with username, password and resource
//            if (PortletPropsValues.JABBER_SASL_PLAIN_ENABLED) {
//                connection.login(
//                        username,
//                        PortletPropsValues.JABBER_SASL_PLAIN_PASSWORD,
//                        PortletPropsValues.JABBER_RESOURCE
//                );
//            } else {
//                // Login with username and password
//                connection.login(username, password);
//            }
//
//            // Error occurred
//        } catch (Exception e) {
//            // Get message returned from the Jabber server
//            String message = e.getMessage();
//            // Check if the reason of failure was authorization
//            if (Validator.isNotNull(message) && message.contains("not-authorized")) {
//                // Call Session did not authorize
//                log.info("Session for user: " + userId + " did not authorize. Trying to import a user " +
//                        "(if enabled in config) and reauthorize.");
//                // Try to import user and login again
//                importUserAndLogin(userId, username, password, connection);
//            } else {
//                // Session Did Not Login
//                throw new JabberException("Cannot log in user: " + userId, e);
//            }
//        }
//
//        addToSystem(userId, connection);
    }

    /**
     * Signs out from jabber server and remove conversation from container
     *
     * @param userId long
     */
    public void logout(long userId) throws JabberException {
        // User must be connected
//        if (!connectionManager.isUserConnected(userId)) {
//            throw new JabberException("User cannot be signed out since he was not connected.");
//        }

        // Get connection from connection store
//        Connection connection = connectionManager.getConnection(userId);

    }

    /**
     * Imports user to jabber server if enabled and tries to login again
     *
     * @param userId     long
     * @param username   String
     * @param password   String
     * @param connection Connection
     */
    private void importUserAndLogin(long userId, String username, String password, Connection connection)
            throws JabberException {

        try {
            // Import user
            if (PortletPropertiesValues.JABBER_IMPORT_USER_ENABLED) {
                importUser(userId, username, password, connection);
            }

            // If the SASL is enabled login with username, password and resource
            if (PortletPropertiesValues.JABBER_SASL_PLAIN_ENABLED) {
                connection.login(
                        username,
                        PortletPropertiesValues.JABBER_SASL_PLAIN_PASSWORD,
                        PortletPropertiesValues.JABBER_RESOURCE
                );
            } else {
                // Login with username and password
                connection.login(username, password);
            }

            addToSystem(userId, connection);

            // Error occurred
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("conflict(409)")) {
                // Session did not login
                throw new JabberException("User " + userId + " already exists but he/she has a different password.");

            } else {
                // Call Session did not login
                throw new JabberException("User did not login", e);
            }
        }
    }


    /**
     * TODO: Refactor, rename, etc.
     * @param userId
     * @param connection
     */
    private void addToSystem(long userId, Connection connection) {
        // Add connection to the connection container
//        connectionManager.putConnection(userId, connection);
        // Build conversations for the user
        conversationManager.buildConversations(userId, connection);
        // Set initial presence
        setInitialPresence(userId, connection);
        // Set roster listener
        setRosterListener(userId, connection);

        // Log
        log.info("Session for user " + userId + " was successfully created.");
    }

    /**
     * Imports user to the Jabber server if enabled
     *
     * @param userId   long
     * @param password String
     * @throws Exception
     */
    private void importUser(long userId, String username, String password, Connection connection) throws Exception {

        // Check the config if the import is enabled
        if (!PortletPropertiesValues.JABBER_IMPORT_USER_ENABLED) {
            throw new Exception("Import user is disabled");
        }

        // Get account manager
        AccountManager accountManager = connection.getAccountManager();
        // Check if the server supports account creation
        if (!accountManager.supportsAccountCreation()) {
            throw new Exception("Server does not support account creation.");
        }

        // Get user
        User user = UserLocalServiceUtil.getUserById(userId);
        // Map params
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("email", user.getEmailAddress());
        attributes.put("first", user.getFirstName());
        attributes.put("last", user.getLastName());
        attributes.put("name", user.getFullName());

        // Create an account
        log.info("Importing user: " + attributes.toString() + " Username: " + username);
        accountManager.createAccount(username, password, attributes);
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
