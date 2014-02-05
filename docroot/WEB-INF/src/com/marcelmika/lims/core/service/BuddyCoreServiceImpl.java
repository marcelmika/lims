package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.session.*;
import com.marcelmika.lims.jabber.service.BuddyJabberService;
import com.marcelmika.lims.persistence.service.BuddyPersistenceService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public class BuddyCoreServiceImpl implements BuddyCoreService {

    // Dependencies
    BuddyJabberService buddyJabberService;
    BuddyPersistenceService buddyPersistenceService;

    /**
     * Constructor
     *
     * @param buddyJabberService Buddy Jabber Service
     */
    public BuddyCoreServiceImpl(
            BuddyJabberService buddyJabberService,
            BuddyPersistenceService buddyPersistenceService) {

        this.buddyJabberService = buddyJabberService;
        this.buddyPersistenceService = buddyPersistenceService;
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

    /**
     * Completely removes buddy from the System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyDeleteResponseEvent removeBuddy(BuddyDeleteRequestEvent event) {
        return buddyPersistenceService.removeBuddy(event);
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyUpdateStatusResponseEvent updateStatus(BuddyUpdateStatusRequestEvent event) {
        // Save status to persistent service
        BuddyUpdateStatusResponseEvent responseEvent = buddyPersistenceService.changeStatus(event);
        // Do not continue if the change status event failed
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }
        // Save buddy status in Jabber as well
        return buddyJabberService.updateStatus(event);
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyUpdateActivePanelResponseEvent updateActivePanel(BuddyUpdateActivePanelRequestEvent event) {
        return null;
    }
}
