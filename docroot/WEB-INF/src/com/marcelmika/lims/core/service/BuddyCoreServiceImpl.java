package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;
import com.marcelmika.lims.events.session.BuddyLogoutRequestEvent;
import com.marcelmika.lims.events.session.BuddyLogoutResponseEvent;
import com.marcelmika.lims.jabber.service.BuddyJabberService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public class BuddyCoreServiceImpl implements BuddyCoreService {

    // Dependencies
    BuddyJabberService buddyJabberService;

    /**
     * Constructor
     *
     * @param buddyJabberService Buddy Jabber Service
     */
    public BuddyCoreServiceImpl(BuddyJabberService buddyJabberService) {
        this.buddyJabberService = buddyJabberService;
    }

    /**
     * Login buddy to System
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        return buddyJabberService.loginBuddy(event);
    }

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event) {
        return buddyJabberService.logoutBuddy(event);
    }
}
