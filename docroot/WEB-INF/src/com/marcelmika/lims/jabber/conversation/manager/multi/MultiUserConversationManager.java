package com.marcelmika.lims.jabber.conversation.manager.multi;

import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import org.jivesoftware.smack.Connection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:39 PM
 */
public interface MultiUserConversationManager {

    /**
     * Sets connection
     *
     * @param connection Connection
     */
    public void setConnection(Connection connection);

}
