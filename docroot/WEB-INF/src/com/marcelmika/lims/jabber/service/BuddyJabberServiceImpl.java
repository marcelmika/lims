package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.buddy.*;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManagerFactory;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Presence;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;

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
    private UserSessionStore userSessionStore;

    /**
     * BuddyJabberServiceImpl
     *
     * @param userSessionStore UserSessionStore
     */
    public BuddyJabberServiceImpl(final UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
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

        // We use buddy ID as a user identification
        Long buddyId = buddy.getBuddyId();

        // Buddy Id cannot be null
        if (buddyId == null) {
            return ConnectBuddyResponseEvent.connectFailure(
                    "Cannot connect buddy without buddy id", event.getDetails()
            );
        }

        // Create new connection manager (screen name is the ID)
        ConnectionManager connectionManager = ConnectionManagerFactory.buildManager();

        try {
            // Connect
            connectionManager.createConnection();
        } catch (JabberException e) {
            // Failure
            return ConnectBuddyResponseEvent.connectFailure(e.getMessage(), buddy.toBuddyDetails());
        }

        // Connection with jabber server was successfully created. Consequently, we should
        // create a session in memory
        UserSession userSession = UserSession.fromConnectionManager(buddyId, connectionManager);
        // Add user session to store so it can be queried later
        userSessionStore.addUserSession(userSession);


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
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return LoginBuddyResponseEvent.loginFailure(
                    "Cannot find session for buddy.", event.getDetails()
            );
        }
        // We need connection manager to login
        ConnectionManager connectionManager = userSession.getConnectionManager();

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
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return LogoutBuddyResponseEvent.logoutFailure(
                    "Cannot find session for buddy.", event.getDetails()
            );
        }
        // We need connection manager to login
        ConnectionManager connectionManager = userSession.getConnectionManager();
        // Logout
        connectionManager.logout();
        // Destroy user session
        userSessionStore.removeUserSession(buddyId);

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
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event) {
        // We use buddy ID as an identification
        Long buddyId = event.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return UpdatePresenceBuddyResponseEvent.updatePresenceFailure(
                    "There is no session for buddy", null
            );
        }
        // We need connection manager to login
        ConnectionManager connectionManager = userSession.getConnectionManager();
        // Map presence
        Presence presence = Presence.fromPresenceDetails(event.getPresenceDetails());

        log.info("SETTING PRESENCE: " + presence);

        try {
            // Set presence on server
            connectionManager.setPresence(presence.toSmackPresence());
            // Success
            return UpdatePresenceBuddyResponseEvent.updatePresenceSuccess("Status successfully updated");
        } catch (Exception e) {
            // Failure
            return UpdatePresenceBuddyResponseEvent.updatePresenceFailure("Cannot update presence", e);
        }
    }

}
