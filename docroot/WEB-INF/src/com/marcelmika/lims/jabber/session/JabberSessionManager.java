package com.marcelmika.lims.jabber.session;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.jabber.connection.JabberConnectionManager;
import com.marcelmika.lims.util.PortletPropsValues;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 */
public class JabberSessionManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(JabberSessionManager.class);
    // Dependencies
    private JabberConnectionManager connectionManager;
    private JabberSessionCallbackInterface callback;

    /**
     * JabberSessionManager
     *
     * @param connectionManager JabberConnectionManager
     * @param callback          JabberSessionCallbackInterface
     */
    public JabberSessionManager(JabberConnectionManager connectionManager,
                                JabberSessionCallbackInterface callback) {
        this.connectionManager = connectionManager;
        this.callback = callback;
    }

    /**
     * Connects to server and performs login action
     *
     * @param userId   long
     * @param username String
     * @param password String
     */
    public void login(long userId, String username, String password) {
        // User is already connected
        if (connectionManager.isUserConnected(userId)) {
            return;
        }
        // Create new connection and connect
        Connection connection = connectionManager.createConnection();

        // Connect to server
        try {
            connectionManager.connect(connection);
        } catch (XMPPException e) {
            log.error("Cannot connect to server: " + e.getMessage());
            return;
        }

        try {
            // If the SASL is enabled login with username, password and resource
            if (PortletPropsValues.JABBER_SASL_PLAIN_ENABLED) {
                connection.login(
                        username,
                        PortletPropsValues.JABBER_SASL_PLAIN_PASSWORD,
                        PortletPropsValues.JABBER_RESOURCE
                );
            } else {
                // Login with username and password
                connection.login(username, password);
            }

            // Error occurred
        } catch (Exception e) {
            // Get message returned from the Jabber server
            String message = e.getMessage();
            // Check if the reason of failure was authorization
            if (Validator.isNotNull(message) && message.contains("not-authorized")) {
                // Call Session did not authorize
                callback.sessionDidNotAuthorize(userId, connection, e);
                // Try to import user and login again
                importUserAndLogin(userId, username, password, connection);
            } else {
                // Call Session Did Not Login
                callback.sessionDidNotLogin(userId, connection, e);
            }
            return;
        }

        // Call Session Did Login
        callback.sessionDidLogin(userId, connection);
    }

    /**
     * Signs out from jabber server and remove conversation from container
     *
     * @param userId long
     */
    public void logout(long userId) {
        // User must be connected
        if (!connectionManager.isUserConnected(userId)) {
            callback.sessionDidNotLogout(userId, new Exception("User is not connected"));
        }

        // Get connection from connection store
        Connection connection = connectionManager.getConnection(userId);
        // Disconnect
        connection.disconnect();
        // Remove from manager
        connectionManager.removeConnection(userId);
        // Call Session Did Logout
        callback.sessionDidLogout(userId);
    }

    /**
     * Imports user to jabber server if enabled and tries to login again
     *
     * @param userId     long
     * @param username   String
     * @param password   String
     * @param connection Connection
     */
    private void importUserAndLogin(long userId, String username, String password, Connection connection) {
        try {
            // Import user
            if (PortletPropsValues.JABBER_IMPORT_USER_ENABLED) {
                importUser(userId, username, password, connection);
            }

            // If the SASL is enabled login with username, password and resource
            if (PortletPropsValues.JABBER_SASL_PLAIN_ENABLED) {
                connection.login(
                        username,
                        PortletPropsValues.JABBER_SASL_PLAIN_PASSWORD,
                        PortletPropsValues.JABBER_RESOURCE
                );
            } else {
                // Login with username and password
                connection.login(username, password);
            }

            // Error occurred
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("conflict(409)")) {
                // Call Session did not login
                callback.sessionDidNotLogin(userId, connection, new Exception(
                        "User " + userId + " already exists but he/she has a different password."
                ));

            } else {
                // Call Session did not login
                callback.sessionDidNotLogin(userId, connection, e);
            }
            return;
        }

        // Call Session did log in
        callback.sessionDidLogin(userId, connection);

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
        if (!PortletPropsValues.JABBER_IMPORT_USER_ENABLED) {
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
}
