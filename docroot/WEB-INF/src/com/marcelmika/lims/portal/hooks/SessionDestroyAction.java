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

package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.buddy.LogoutBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.LogoutBuddyResponseEvent;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.portal.domain.Buddy;

import javax.servlet.http.HttpSession;

/**
 * Listens to the session destroy action.
 * Whenever it occurs it tries to login the user to the LIMS system
 * server with the credentials from the request.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 */
public class SessionDestroyAction extends SessionAction {

    // Log
    private static Log log = LogFactoryUtil.getLog(SessionDestroyAction.class);
    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    @Override
    public void run(HttpSession session) {

        // Create buddy from session
        Buddy buddy = Buddy.fromHttpSession(session);
        // Logout buddy
        LogoutBuddyResponseEvent responseEvent = coreService.logoutBuddy(
                new LogoutBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Log error
        if (!responseEvent.isSuccess()) {
            log.error(responseEvent.getException());
        }
    }
}