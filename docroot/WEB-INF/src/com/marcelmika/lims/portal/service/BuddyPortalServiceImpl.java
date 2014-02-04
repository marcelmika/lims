package com.marcelmika.lims.portal.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;
import com.marcelmika.lims.events.session.BuddyLogoutRequestEvent;
import com.marcelmika.lims.events.session.BuddyLogoutResponseEvent;
import com.marcelmika.lims.jabber.service.BuddyJabberService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:09 PM
 */
public class BuddyPortalServiceImpl implements BuddyPortalService {

    // Dependencies
    BuddyJabberService buddyJabberService;

    /**
     * Constructor
     *
     * @param buddyJabberService Buddy Jabber Service
     */
    public BuddyPortalServiceImpl(BuddyJabberService buddyJabberService) {
        this.buddyJabberService = buddyJabberService;
    }

    /**
     * Login buddy to Portal
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        return buddyJabberService.loginBuddy(event);
    }

    /**
     * Logout buddy from Portal
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event) {
        return buddyJabberService.logoutBuddy(event);
    }
}
