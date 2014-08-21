/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.connection.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.connection.sasl.LiferaySaslMechanism;
import com.marcelmika.lims.portal.properties.PortletPropertiesValues;
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
        if (Environment.isSaslPlainEnabled()) {
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
            if (Environment.isSaslPlainEnabled()) {
                // Login via SASL
                connection.login(username, Environment.getSaslPlainPassword(), Environment.getJabberResource());
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
                Environment.getJabberHost(),
                Environment.getJabberPort(),
                Environment.getJabberServiceName());

        // Init configuration values
        // SASL Plain auth
        connectionConfiguration.setSASLAuthenticationEnabled(Environment.isSaslPlainEnabled());
        // Is the initial available presence going to be send to the server?
        connectionConfiguration.setSendPresence(true);

        // Sets the socket factory used to create new xmppConnection sockets.
        // This is useful when connecting through SOCKS5 proxies.
        SmackConfiguration.setLocalSocks5ProxyEnabled(Environment.isJabberSock5ProxyEnabled());
        SmackConfiguration.setLocalSocks5ProxyPort(Environment.getJabberSock5ProxyPort());

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
