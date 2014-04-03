package com.marcelmika.lims.jabber.session;

import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import com.marcelmika.lims.jabber.conversation.manager.multi.MultiUserConversationManager;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.lims.jabber.group.manager.GroupManager;
import com.marcelmika.lims.jabber.group.manager.GroupManagerFactory;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:34 PM
 */
public class UserSession {

    private Long sessionId;
    private ConnectionManager connectionManager;
    private GroupManager groupManager;
    private SingleUserConversationManager singleUserConversationManager;
    private MultiUserConversationManager multiUserConversationManager;


    public UserSession(Long sessionId) {
        this.sessionId = sessionId;
    }

    public static UserSession fromConnectionManager(Long sessionId, ConnectionManager connectionManager) {
        // Create new user session
        UserSession userSession = new UserSession(sessionId);
        // Map managers from connection manager
        userSession.connectionManager = connectionManager;
        // Group manager
        GroupManager groupManager = GroupManagerFactory.buildGroupManager(sessionId);
        groupManager.setRoster(connectionManager.getRoster());
        userSession.groupManager = groupManager;


        return userSession;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public SingleUserConversationManager getSingleUserConversationManager() {
        return singleUserConversationManager;
    }

    public void setSingleUserConversationManager(SingleUserConversationManager singleUserConversationManager) {
        this.singleUserConversationManager = singleUserConversationManager;
    }

    public MultiUserConversationManager getMultiUserConversationManager() {
        return multiUserConversationManager;
    }

    public void setMultiUserConversationManager(MultiUserConversationManager multiUserConversationManager) {
        this.multiUserConversationManager = multiUserConversationManager;
    }
}
