package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;
import com.marcelmika.lims.events.session.BuddyLogoutRequestEvent;
import com.marcelmika.lims.events.session.BuddyLogoutResponseEvent;
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
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Login via jabber session manager
            sessionManager.login(buddy.getBuddyId(), buddy.getScreenName(), buddy.getPassword());
        } catch (JabberException e) {
            // Failure
            return BuddyLoginResponseEvent.loginFailure(e.getMessage(), buddy.toBuddyDetails());
        }

        // Success
        return BuddyLoginResponseEvent.loginSuccess(
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
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event) {
        // Get buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Logout via jabber session manager
            sessionManager.logout(buddy.getBuddyId());
        } catch (JabberException e) {
            // Failure
            return BuddyLogoutResponseEvent.logoutFailure(e.getMessage(), buddy.toBuddyDetails());
        }

        // Success
        return BuddyLogoutResponseEvent.logoutSuccess(
                "User " + buddy.getBuddyId() + " successfully signed out",
                buddy.toBuddyDetails()
        );
    }
}
