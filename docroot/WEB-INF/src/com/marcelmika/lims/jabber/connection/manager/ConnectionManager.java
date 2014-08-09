package com.marcelmika.lims.jabber.connection.manager;

import com.marcelmika.lims.jabber.JabberException;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 4:10 PM
 */
public interface ConnectionManager {

    /**
     * Creates new connection with jabber server
     *
     * @throws JabberException if connection creation fails
     */
    public void createConnection() throws JabberException;

    /**
     * Log user in with username and password
     *
     * @param username String
     * @param password String
     * @throws JabberException if login fails
     */
    public void login(String username, String password) throws JabberException;

    /**
     * Logout buddy
     */
    public void logout();

    /**
     * Returns connection of the user
     */
    public Connection getConnection();

    /**
     * Returns buddy's roster
     *
     * @return Roster
     */
    public Roster getRoster();

    /**
     * Returns buddy's chat manager
     *
     * @return ChatManager
     */
    public ChatManager getChatManager();

    /**
     * Set or updates buddy's presence
     *
     * @param presence Presence of the concrete buddy.
     */
    public void setPresence(final Presence presence);

}
