package com.marcelmika.lims.jabber.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.util.PortletPropsValues;
import org.jivesoftware.smack.*;

/**
 * Jabber Connection Manager is responsible for all user jabber connections in the system. It uses Jabber
 * Connection Store to save all connections. Furthermore, it holds a connection configuration which is used
 * to during the connection creation. Parameters like host, port and service name are passed to the configuration.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberConnectionManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(JabberConnectionManager.class);
    // Configuration to use while establishing the connection to the server.
    private ConnectionConfiguration connectionConfiguration;
    // All connections are stored here
    private JabberConnectionStore connectionStore = JabberConnectionStore.getInstance();

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
     * Returns connection for the particular user
     *
     * @param userId UserID
     * @return Connection
     */
    public Connection getConnection(long userId) {
        return connectionStore.getConnection(userId);
    }

    /**
     * Puts connection to the connection store
     *
     * @param userId     userID
     * @param connection connection which belongs to the particular user
     */
    public void putConnection(long userId, Connection connection) {
        connectionStore.putConnection(userId, connection);
        log.info("Connection for user: " + userId + " added to the connection store");
    }

    /**
     * Remove connection from the store and disconnects
     *
     * @param userId userID
     */
    public void removeConnection(long userId) {
        // Disconnect
        Connection connection = connectionStore.getConnection(userId);
        connection.disconnect();
        // Remove from store
        connectionStore.removeConnection(userId);
        log.info("Connection for user: " + userId + " removed from the connection store");
    }

    /**
     * Returns true if the user has a valid connection stored in
     * the connection store
     *
     * @param userId userID
     * @return true if the user has a valid connection
     */
    public boolean isUserConnected(long userId) {
        return connectionStore.containsConnection(userId);
    }

    /**
     * Creates new connection
     *
     * @return Connection Newly created connection
     */
    public Connection createConnection() {
        return new XMPPConnection(getConnectionConfiguration());
    }

    /**
     * Connects to jabber based on the connection
     *
     * @param connection Connection
     * @throws XMPPException
     */
    public void connect(Connection connection) throws XMPPException {
        try {
            // Register for SASL Mechanism if enabled
            if (PortletPropsValues.JABBER_SASL_PLAIN_ENABLED) {
                SASLAuthentication.registerSASLMechanism("PLAIN", LiferaySaslMechanism.class);
                SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            }
            // Connect to server
            connection.connect();

        } catch (XMPPException e) {
            // Disconnect
            connection.disconnect();
            throw e;
        }
    }
}
