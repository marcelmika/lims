package com.marcelmika.lims.jabber.connection.store;

import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 5:11 PM
 */
public interface ConnectionManagerStore {

    /**
     * Returns connection manager based on it's id
     *
     * @param id of the connection manager
     * @return ConnectionManager
     */
    public ConnectionManager getConnectionManager(Long id);

    /**
     * Removes connection manager from the connection store
     *
     * @param id of the connection manager
     */
    public void removeConnectionManager(Long id);

    /**
     * Adds connection manager to the connection store
     *
     * @param connectionManager ConnectionManager
     */
    public void addConnectionManager(ConnectionManager connectionManager);

    /**
     * Returns true if the connection store contains connection manager
     *
     * @param id of the connection manager
     * @return true if contains connection manager
     */
    public boolean containsConnectionManager(Long id);

}
