package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.buddy.*;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManagerFactory;
import com.marcelmika.lims.jabber.connection.store.ConnectionManagerStore;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:25 PM
 */
public class BuddyJabberServiceImpl implements BuddyJabberService {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyJabberServiceImpl.class);

    // Dependencies
    private ConnectionManagerStore connectionManagerStore;

    /**
     * BuddyJabberServiceImpl
     *
     * @param connectionManagerStore ConnectionManagerStore
     */
    public BuddyJabberServiceImpl(ConnectionManagerStore connectionManagerStore) {
        this.connectionManagerStore = connectionManagerStore;
    }

    /**
     * Connect buddy to the Jabber server
     *
     * @param event Request event for method
     * @return Response event for method
     */
    @Override
    public ConnectBuddyResponseEvent connectBuddy(ConnectBuddyRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        // Create new connection manager (screen name is the ID)
        ConnectionManager connectionManager = ConnectionManagerFactory.buildConnectionManager(buddy.getBuddyId());

        try {
            // Connect
            connectionManager.createConnection();
        } catch (JabberException e) {
            // Failure
            return ConnectBuddyResponseEvent.connectFailure(e.getMessage(), buddy.toBuddyDetails());
        }

        // Connect was successful so add the connection manager to the store
        connectionManagerStore.addConnectionManager(connectionManager);

        // Success
        return ConnectBuddyResponseEvent.connectSuccess(
                "User " + buddy.getBuddyId() + " successfully created connection to jabber server.",
                buddy.toBuddyDetails()
        );
    }

    /**
     * Login buddy to Jabber
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        // Get connection manager from store
        ConnectionManager connectionManager = connectionManagerStore.getConnectionManager(buddy.getBuddyId());

        // No connection manager for buddy
        if (connectionManager == null) {
            return LoginBuddyResponseEvent.loginFailure(
                    "Cannot find connection manager for buddy.", event.getDetails()
            );
        }

        try {
            // Login
            connectionManager.login(buddy.getScreenName(), buddy.getPassword());
        } catch (JabberException e) {
            // Failure
            return LoginBuddyResponseEvent.loginFailure(e.getMessage(), buddy.toBuddyDetails());
        }



        // Success
        return LoginBuddyResponseEvent.loginSuccess(
                "User " + buddy.getBuddyId() + " successfully signed in",
                buddy.toBuddyDetails()
        );
    }

    /**
     * Logout buddy from Jabber
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        // Get buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());
        // Get connection manager from store
        ConnectionManager connectionManager = connectionManagerStore.getConnectionManager(buddy.getBuddyId());

        // Cannot logout buddy if no connection manager is stored
        if (connectionManager == null) {
            return LogoutBuddyResponseEvent.logoutFailure(
                    "Cannot find connection manager for buddy.", buddy.toBuddyDetails()
            );
        }

        // Logout
        connectionManager.logout();

        // Remove connection manager from store
        connectionManagerStore.removeConnectionManager(buddy.getBuddyId());

        // Call Session Did Logout
        // TODO
//        try {
//            // Change local status to off
//            SettingsLocalServiceUtil.changeStatus(userId, JabberKeys.JABBER_STATUS_OFF);
//            // TODO: Move to conversation manager
//            // Remove conversation from the conversation store
//            ConversationStore.getInstance().removeConversationContainer(userId);
//        } catch (Exception e) {
//            throw new JabberException("Problems during logout occurred. However, user was successfully " +
//                    "logged out from the Jabber server", e);
//        }

        // Success
        return LogoutBuddyResponseEvent.logoutSuccess(
                "User " + buddy.getBuddyId() + " successfully signed out",
                buddy.toBuddyDetails()
        );
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateStatusBuddyResponseEvent updateStatus(UpdateStatusBuddyRequestEvent event) {
        // Map presence
        Presence presence = Presence.fromPresenceDetails(event.getPresenceDetails());
        // Get connection manager from store
        ConnectionManager connectionManager = connectionManagerStore.getConnectionManager(event.getBuddyId());

        try {
            // Set presence on server
            connectionManager.setPresence(presence.toSmackPresence());
            // Success
            return UpdateStatusBuddyResponseEvent.updateStatusSuccess("Status successfully updated");
        } catch (Exception e) {
            // Failure
            return UpdateStatusBuddyResponseEvent.updateStatusFailure("Cannot update presence", e);
        }
    }

}
