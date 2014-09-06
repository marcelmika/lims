/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.environment.Environment;
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
    public BuddyCoreServiceImpl(final BuddyJabberService buddyJabberService,
                                final BuddyPersistenceService buddyPersistenceService) {

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

        // Login locally
        LoginBuddyResponseEvent responseEvent = buddyPersistenceService.loginBuddy(event);

        // Login to Jabber if enabled
        if (Environment.isJabberEnabled()) {

            // [1] Connect buddy with jabber server
            ConnectBuddyResponseEvent connectResponseEvent = buddyJabberService.connectBuddy(
                    new ConnectBuddyRequestEvent(event.getDetails())
            );
            // [1.1] Return error on failure
            if (!connectResponseEvent.isSuccess()) {
                return LoginBuddyResponseEvent.loginFailure(
                        LoginBuddyResponseEvent.Status.ERROR_JABBER,
                        connectResponseEvent.getException()
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
                buddyJabberService.updatePresence(new UpdatePresenceBuddyRequestEvent(
                                loginResponseEvent.getDetails().getBuddyId(),
                                readPresenceEvent.getPresenceDetails())
                );
            }
        }

        return responseEvent;
    }

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        // Logout from jabber as well if enabled
        if (Environment.isJabberEnabled()) {
            buddyJabberService.logoutBuddy(event);
        }

        return buddyPersistenceService.logoutBuddy(event);
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
     * Change buddy's presence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event) {

        // Save presence to persistent service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyPersistenceService.updatePresence(event);
        // Do not continue if the change presence event failed
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }
        // Save buddy presence in Jabber as well
        if (Environment.isJabberEnabled()) {
            buddyJabberService.updatePresence(event);
        }

        return responseEvent;
    }
}
