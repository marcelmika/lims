package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;
import com.marcelmika.lims.events.session.BuddyLogoutRequestEvent;
import com.marcelmika.lims.events.session.BuddyLogoutResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public interface BuddyCoreService {

    /**
     * Login buddy to System
     * @param event Request event for login method
     * @return Response event for login method
     */
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event);

    /**
     * Logout buddy from System
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event);
}
