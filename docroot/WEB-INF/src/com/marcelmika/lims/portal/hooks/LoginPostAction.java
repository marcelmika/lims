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

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.buddy.LoginBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.LoginBuddyResponseEvent;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.portal.domain.Buddy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Listens to login post action. Whenever it occurs it tries to login to the Jabber
 * server with the credentials from the request.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 */
public class LoginPostAction extends Action {

    // Log
    private static Log log = LogFactoryUtil.getLog(LoginPostAction.class);
    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response) {

        try {
            // Create buddy from portal request
            Buddy buddy = Buddy.fromPortalServletRequest(request);

            // Login
            loginBuddy(buddy);
        }
        // Failure
        catch (Exception e) {
            // Log error
            if (log.isErrorEnabled()) {
                log.error(e);
            }
        }
    }

    /**
     * Login buddy
     *
     * @param buddy Buddy
     */
    private void loginBuddy(Buddy buddy) {

        // Log
        if(log.isDebugEnabled()) {
            log.debug("Login user " + buddy.getScreenName());
        }

        // Login buddy
        LoginBuddyResponseEvent responseEvent = coreService.loginBuddy(
                new LoginBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Failure
        if (!responseEvent.isSuccess()) {

            // Notify the admin about the error
            if (log.isWarnEnabled()) {
                log.warn(String.format(
                        "Login error " + responseEvent.getStatus() + ": " + responseEvent.getExceptionMessage()
                ));
            }

            // Provide more detailed description of the issue by printing the exception
            if (log.isDebugEnabled()) {
                log.debug(responseEvent.getException());
            }
        }
    }
}