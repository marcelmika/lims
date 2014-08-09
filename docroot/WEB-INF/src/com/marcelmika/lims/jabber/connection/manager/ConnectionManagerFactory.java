package com.marcelmika.lims.jabber.connection.manager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 2:49 PM
 */
public class ConnectionManagerFactory {

    public static ConnectionManager buildManager() {
        return new ConnectionManagerImpl();
    }
}
