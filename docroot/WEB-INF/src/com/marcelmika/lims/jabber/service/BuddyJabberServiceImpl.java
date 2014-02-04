package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.session.JabberSessionCallbackInterface;
import com.marcelmika.lims.jabber.session.JabberSessionManager;
import org.jivesoftware.smack.Connection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:25 PM
 */
public class BuddyJabberServiceImpl implements BuddyJabberService {

    private JabberSessionManager sessionManager;

    /**
     * BuddyJabberServiceImpl
     *
     * @param sessionManager JabberSessionManager
     */
    public BuddyJabberServiceImpl(JabberSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Login with session manager
            sessionManager.login(buddy.getBuddyId(), buddy.getScreenName(), buddy.getPassword());
        } catch (JabberException e) {
            return BuddyLoginResponseEvent.loginFailure(e.getMessage(), buddy.toBuddyDetails());
        }


        return BuddyLoginResponseEvent.loginSuccess("User successfully signed in", buddy.toBuddyDetails());
    }
}
