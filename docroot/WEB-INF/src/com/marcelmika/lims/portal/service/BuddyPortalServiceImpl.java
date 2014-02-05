package com.marcelmika.lims.portal.service;

import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:09 PM
 */
public class BuddyPortalServiceImpl implements BuddyPortalService {

    // Dependencies
    BuddyCoreService buddyCoreService;

    /**
     * Constructor
     *
     * @param buddyCoreService Buddy Jabber Service
     */
    public BuddyPortalServiceImpl(BuddyCoreService buddyCoreService) {
        this.buddyCoreService = buddyCoreService;
    }

    /**
     * Login buddy to Portal
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        return buddyCoreService.loginBuddy(event);
    }

    /**
     * Logout buddy from Portal
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event) {
        return buddyCoreService.logoutBuddy(event);
    }

    /**
     * Completely removes buddy from Portal
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyDeleteResponseEvent removeBuddy(BuddyDeleteRequestEvent event) {
        return buddyCoreService.removeBuddy(event);
    }
}
