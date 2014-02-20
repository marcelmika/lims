package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:22 PM
 */
public interface BuddyJabberService {

    /**
     * Login buddy to Jabber
     * @param event Request event for login method
     * @return Response event for login method
     */
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event);

    /**
     * Logout buddy from Jabber
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event);

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public UpdateStatusBuddyResponseEvent updateStatus(UpdateStatusBuddyRequestEvent event);

}
