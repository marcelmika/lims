package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.events.buddy.*;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.session.JabberSessionManager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:25 PM
 */
public class BuddyJabberServiceImpl implements BuddyJabberService {

    // Dependencies
    private JabberSessionManager sessionManager;

    /**
     * BuddyJabberServiceImpl
     *
     * @param sessionManager JabberSessionManager
     */
    public BuddyJabberServiceImpl(JabberSessionManager sessionManager) {
        this.sessionManager = sessionManager;
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

        try {
            // Login via jabber session manager
            sessionManager.login(buddy.getBuddyId(), buddy.getScreenName(), buddy.getPassword());
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

        try {
            // Logout via jabber session manager
            sessionManager.logout(buddy.getBuddyId());
        } catch (JabberException e) {
            // Failure
            return LogoutBuddyResponseEvent.logoutFailure(e.getMessage(), buddy.toBuddyDetails());
        }

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
        return null;
    }

}
