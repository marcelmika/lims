package com.marcelmika.lims.jabber.connection.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.connection.sasl.LiferaySaslMechanism;
import com.marcelmika.lims.util.PortletPropsValues;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;

/**
 * Manages user's connection to the server
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ConnectionManagerImpl implements ConnectionManager, ConnectionListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConnectionManagerImpl.class);
    // Configuration to use while establishing the connection to the server.
    private ConnectionConfiguration connectionConfiguration;
    // Connection to the server
    private Connection connection;

    // -------------------------------------------------------------------------------------------
    // Override: ConnectionManager
    // -------------------------------------------------------------------------------------------

    /**
     * Creates new connection with jabber server
     *
     * @throws JabberException if connection creation fails
     */
    @Override
    public void createConnection() throws JabberException {
        // Create new connection from the connection configuration
        connection = new XMPPConnection(getConnectionConfiguration());

        // Register for SASL Mechanism if enabled
        if (PortletPropsValues.JABBER_SASL_PLAIN_ENABLED) {
            SASLAuthentication.registerSASLMechanism("PLAIN", LiferaySaslMechanism.class);
            SASLAuthentication.supportSASLMechanism("PLAIN", 0);
        }

        try {
            // Connect
            connection.connect();
            // Register connection listener
            connection.addConnectionListener(this);
        } catch (XMPPException e) {
            // Disconnect
            connection.disconnect();
            throw new JabberException("Cannot connect to the jabber server", e);
        }
    }

    /**
     * Log user in with username and password
     *
     * @param username String
     * @param password String
     * @throws JabberException if login fails
     */
    @Override
    public void login(String username, String password) throws JabberException {
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
                log.info("Session for user: " + username + " did not authorize. Trying to import a user " +
                        "(if enabled in config) and reauthorize.");
                // Try to import user and login again
//                importUserAndLogin(userId, username, password, connection);
            } else {
                // Session Did Not Login
                throw new JabberException("Cannot log in user: " + username, e);
            }
        }
    }

    /**
     * Logout buddy
     */
    @Override
    public void logout() {
        // Disconnect
        connection.disconnect();
        // Un-register connection listener
        connection.removeConnectionListener(this);
    }

    /**
     * Returns connection of the user
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Returns buddy's roster
     *
     * @return Roster
     */
    @Override
    public Roster getRoster() {
        return connection.getRoster();
    }

    /**
     * Returns buddy's chat manager
     *
     * @return ChatManager
     */
    @Override
    public ChatManager getChatManager() {
        return connection.getChatManager();
    }

    /**
     * Set or updates buddy's presence
     *
     * @param presence Presence of the concrete buddy.
     */
    @Override
    public void setPresence(Presence presence) {
        connection.sendPacket(presence);
    }


    // -------------------------------------------------------------------------------------------
    // Override: ConnectionListener
    // -------------------------------------------------------------------------------------------

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception e) {

    }

    @Override
    public void reconnectingIn(int i) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }


    // -------------------------------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------------------------------

    /**
     * Returns Connection configuration.
     * Sets proper values of the configuration if they are not set yet
     *
     * @return ConnectionConfiguration
     */
    private ConnectionConfiguration getConnectionConfiguration() {
        // If there is one already return it
        if (connectionConfiguration != null) {
            return connectionConfiguration;
        }

        // Create new configuration
        connectionConfiguration = new ConnectionConfiguration(
                PortletPropsValues.JABBER_HOST,
                PortletPropsValues.JABBER_PORT,
                PortletPropsValues.JABBER_SERVICE_NAME);

        // Init configuration values
        // SASL Plain auth
        connectionConfiguration.setSASLAuthenticationEnabled(PortletPropsValues.JABBER_SASL_PLAIN_ENABLED);
        // Is the initial available presence going to be send to the server?
        connectionConfiguration.setSendPresence(true);

        // Sets the socket factory used to create new xmppConnection sockets.
        // This is useful when connecting through SOCKS5 proxies.
        SmackConfiguration.setLocalSocks5ProxyEnabled(PortletPropsValues.JABBER_SOCK5_PROXY_ENABLED);
        SmackConfiguration.setLocalSocks5ProxyPort(PortletPropsValues.JABBER_SOCK5_PROXY_PORT);

        return connectionConfiguration;
    }


    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("Connection manager for connection: [%s]", this.connection);
    }
}
