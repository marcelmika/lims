package com.marcelmika.lims.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.buddy.*;
import com.marcelmika.lims.jabber.service.BuddyJabberService;
import com.marcelmika.lims.persistence.service.BuddyPersistenceService;

/**
 * Implementation of BuddyCoreService
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public class BuddyCoreServiceImpl implements BuddyCoreService {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyCoreServiceImpl.class);

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

        // [1] Connect buddy with jabber server
        ConnectBuddyResponseEvent connectResponseEvent = buddyJabberService.connectBuddy(
                new ConnectBuddyRequestEvent(event.getDetails())
        );
        // [1.1] Return error on failure
        if (!connectResponseEvent.isSuccess()) {
            return LoginBuddyResponseEvent.loginFailure(
                    connectResponseEvent.getResult(), connectResponseEvent.getDetails()
            );
        }

        // [2] Login buddy in jabber server
        LoginBuddyResponseEvent loginResponseEvent = buddyJabberService.loginBuddy(event);
        // [2.1] Return error on failure
        if (!loginResponseEvent.isSuccess()) {
            return loginResponseEvent;
        }

        // [3] Get buddy's stored presence. Thanks to that we can send buddy's last presence to the server.
        // Imagine that user logged out when he/she was e.g. DND. If they login again DND presence will be
        // sent to the jabber server.
        ReadPresenceBuddyResponseEvent readPresenceEvent = buddyPersistenceService.readPresence(
                new ReadPresenceBuddyRequestEvent(loginResponseEvent.getDetails())
        );
        // [3.1] Update it on server. However, this will be done only if the  buddy's presence read request
        // ended with success. If it fails we simply do nothing. We don't want to interrupt login process
        // only because we can't send initial presence to the jabber server
        if (readPresenceEvent.isSuccess()) {
            buddyJabberService.updateStatus(new UpdatePresenceBuddyRequestEvent(
                            loginResponseEvent.getDetails().getBuddyId(),
                            readPresenceEvent.getPresenceDetails())
            );
        }

        return loginResponseEvent;
    }

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        // Do request
        LogoutBuddyResponseEvent logoutResponseEvent = buddyJabberService.logoutBuddy(event);
        // Log
        log.info(logoutResponseEvent.getResult());

        return logoutResponseEvent;
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
    public UpdatePresenceBuddyResponseEvent updateStatus(UpdatePresenceBuddyRequestEvent event) {
        // Save status to persistent service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyPersistenceService.updatePresence(event);
        // Log
        log.info(responseEvent.getResult());
        // Do not continue if the change status event failed
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }
        // Save buddy status in Jabber as well
        return buddyJabberService.updateStatus(event);
    }
}
