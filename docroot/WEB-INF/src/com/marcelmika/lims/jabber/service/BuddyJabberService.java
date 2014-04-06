package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.api.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:22 PM
 */
public interface BuddyJabberService {


    /**
     * Connect buddy to the Jabber server
     *
     * @param event Request event for method
     * @return Response event for method
     */
    public ConnectBuddyResponseEvent connectBuddy(ConnectBuddyRequestEvent event);

    /**
     * Login buddy to Jabber
     *
     * @param event Request event for method
     * @return Response event for method
     */
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event);

    /**
     * Logout buddy from Jabber
     *
     * @param event Request event for method
     * @return Response event for method
     */
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event);

    /**
     * Change buddy's status
     *
     * @param event Request event for method
     * @return Response event for method
     */
    public UpdateStatusBuddyResponseEvent updateStatus(UpdateStatusBuddyRequestEvent event);

}
