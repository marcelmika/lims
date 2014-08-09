package com.marcelmika.lims.jabber.session;

import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import com.marcelmika.lims.jabber.conversation.manager.multi.MultiUserConversationManager;
import com.marcelmika.lims.jabber.conversation.manager.multi.MultiUserConversationManagerFactory;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManagerFactory;
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


    /**
     * Constructor
     *
     * @param sessionId identifier
     */
    public UserSession(Long sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Factory method which creates user session form connection manager
     *
     * @param sessionId         id of the session
     * @param connectionManager used to create session manager
     * @return UserSession object
     */
    public static UserSession fromConnectionManager(Long sessionId, ConnectionManager connectionManager) {
        // Create new user session
        UserSession userSession = new UserSession(sessionId);

        // Connection manager
        userSession.connectionManager = connectionManager;
        // Group manager
        userSession.groupManager = createGroupManager(connectionManager);
        // Single User Conversation manager
        userSession.singleUserConversationManager = createSingleUserConversationManager(connectionManager);
        // Multi User Conversation manager
        userSession.multiUserConversationManager = createMultiUserConversationManager(connectionManager);

        return userSession;
    }

    /**
     * Creates new group manager
     *
     * @param connectionManager used to create group manager
     * @return Group manager
     */
    private static GroupManager createGroupManager(ConnectionManager connectionManager) {
        GroupManager groupManager = GroupManagerFactory.buildManager();
        groupManager.setRoster(connectionManager.getRoster());
        return groupManager;
    }

    /**
     * Create new single user conversation manager
     *
     * @param connectionManager used to create single user conversation manager
     * @return SingleUserConversationManager
     */
    private static SingleUserConversationManager createSingleUserConversationManager(
            ConnectionManager connectionManager) {
        SingleUserConversationManager singleManager = SingleUserConversationManagerFactory.buildManager();
        singleManager.setChatManager(connectionManager.getChatManager());
        return singleManager;
    }

    /**
     * Create new multi user conversation manager
     *
     * @param connectionManager used to create multi user conversation manager
     * @return MultiUserConversationManager
     */
    private static MultiUserConversationManager createMultiUserConversationManager(
            ConnectionManager connectionManager) {

        MultiUserConversationManager multiManager = MultiUserConversationManagerFactory.buildManager();
        multiManager.setConnection(connectionManager.getConnection());

        return multiManager;
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
