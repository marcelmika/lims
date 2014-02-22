package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.buddy.*;

/**
 * Serves as a port to the business logic related to buddy.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public interface BuddyCoreService {

    /**
     * Login buddy to System
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event);

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event);

    /**
     * Completely removes buddy from the System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event);

    /**
     * Update buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public UpdateStatusBuddyResponseEvent updateStatus(UpdateStatusBuddyRequestEvent event);

}
