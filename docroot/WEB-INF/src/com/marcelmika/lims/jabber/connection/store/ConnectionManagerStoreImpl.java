package com.marcelmika.lims.jabber.connection.store;

import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all connection managers within the system for all connected buddies
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ConnectionManagerStoreImpl implements ConnectionManagerStore {

    // Map of Connection Managers
    private Map<Long, ConnectionManager> connectionManagerMap = new HashMap<Long, ConnectionManager>();

    /**
     * Returns connection manager which belongs to the particular user based on the username
     *
     * @param id of the connection manager
     * @return ConnectionManager
     */
    @Override
    public ConnectionManager getConnectionManager(Long id) {
        return connectionManagerMap.get(id);
    }

    /**
     * Removes connection manager from the connection store
     *
     * @param id of the connection manager
     */
    @Override
    public void removeConnectionManager(Long id) {
        connectionManagerMap.remove(id);
    }

    /**
     * Adds connection manager to the connection store
     *
     * @param connectionManager ConnectionManager
     */
    @Override
    public void addConnectionManager(ConnectionManager connectionManager) {
        connectionManagerMap.put(connectionManager.getId(), connectionManager);
    }

    /**
     * Returns true if the connection store contains connection manager
     *
     * @param id of the connection manager
     * @return true if contains connection manager
     */
    @Override
    public boolean containsConnectionManager(Long id) {
        return connectionManagerMap.containsKey(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Connection Manager Store: [%d] contains: %s", this.hashCode(), connectionManagerMap.toString()
        );
    }
}
