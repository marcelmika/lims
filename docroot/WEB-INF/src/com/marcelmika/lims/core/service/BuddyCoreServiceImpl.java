package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.buddy.*;
import com.marcelmika.lims.jabber.service.BuddyJabberService;
import com.marcelmika.lims.persistence.service.BuddyPersistenceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.naming.OperationNotSupportedException;

/**
 * Implementation of BuddyCoreService
 *
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
     * @param buddyJabberService      jabber service
     * @param buddyPersistenceService persistence service
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
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event) {
        return buddyJabberService.loginBuddy(event);
    }

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        return buddyJabberService.logoutBuddy(event);
    }

    /**
     * Completely removes buddy from the System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event) {
        return buddyPersistenceService.removeBuddy(event);
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateStatusBuddyResponseEvent updateStatus(UpdateStatusBuddyRequestEvent event) {
        // Save status to persistent service
        UpdateStatusBuddyResponseEvent responseEvent = buddyPersistenceService.changeStatus(event);
        // Do not continue if the change status event failed
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }
        // Save buddy status in Jabber as well
        return buddyJabberService.updateStatus(event);
    }

    /**
     * Returns a collection of buddies related to the buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public GetBuddiesResponseEvent getBuddies(GetBuddiesRequestEvent event) {
        throw new NotImplementedException();
//        List<com.marcelmika.lims.model.Buddy> buddies = ChatUtil.getBuddyList(pollerRequest.getUserId());
    }

}
