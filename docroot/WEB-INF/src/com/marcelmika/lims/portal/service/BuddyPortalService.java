package com.marcelmika.lims.portal.service;

import com.marcelmika.lims.events.session.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:00 PM
 */
public interface BuddyPortalService {

    /**
     * Login buddy to Portal
     * @param event Request event for login method
     * @return Response event for login method
     */
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event);

    /**
     * Logout buddy from Portal
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event);

    /**
     * Completely removes buddy from Portal
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyRemoveResponseEvent removeBuddy(BuddyRemoveRequestEvent event);

}
