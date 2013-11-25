package com.marcelmika.lims.jabber.connection;

import org.jivesoftware.smack.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all jabber connections within the system for all users. It uses singleton pattern to ensure that only one
 * instance of connection will be available during the runtime.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberConnectionStore {

    private Map<Long, Connection> connections = new HashMap<Long, Connection>();

    // Private constructor prevents instantiation from other classes
    private JabberConnectionStore() {
    }

    public static JabberConnectionStore getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Returns connection which belongs to the particular user based on the userID
     *
     * @param userId Liferay ID of the user
     * @return Connection
     */
    public Connection getConnection(long userId) {
        return connections.get(userId);
    }

    /**
     * Removes connection from the connection store based on the userID
     *
     * @param userId Liferay ID of the user
     */
    public void removeConnection(long userId) {
        connections.remove(userId);
    }

    /**
     * Puts connection to the connection store
     *
     * @param userId     Liferay ID of the user
     * @param connection Smack connection
     */
    public void putConnection(long userId, Connection connection) {
        connections.put(userId, connection);
    }

    /**
     * Returns true if the connection store contains connection
     * for the given user ID
     *
     * @param userId Liferay ID of the user
     * @return true if contains connection
     */
    public boolean containsConnection(long userId) {
        return connections.containsKey(userId);
    }

    @Override
    public String toString() {
        return connections.toString();
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before. This is based on
     * the Bill Pugh solution for singleton
     */
    private static class SingletonHolder {
        public static final JabberConnectionStore INSTANCE = new JabberConnectionStore();
    }
}
