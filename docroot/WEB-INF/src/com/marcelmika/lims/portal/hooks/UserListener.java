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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.marcelmika.lims.api.events.buddy.DeleteBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.DeleteBuddyResponseEvent;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.portal.domain.Buddy;

/**
 * Listens to the events from portal related to user.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class UserListener extends BaseModelListener<User> {

    // Log
    private static Log log = LogFactoryUtil.getLog(UserListener.class);
    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    /**
     * Called whenever the user is removed from the portal
     *
     * @param user User
     */
    @Override
    public void onAfterRemove(User user) {

        // Create buddy from portal user
        Buddy buddy = Buddy.fromPortalUser(user);

        // Delete the user
        deleteBuddy(buddy);
    }

    /**
     * Removes buddy from the system
     *
     * @param buddy Buddy
     */
    private void deleteBuddy(Buddy buddy) {

        // Remove buddy
        DeleteBuddyResponseEvent responseEvent = coreService.removeBuddy(
                new DeleteBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Failure
        if (!responseEvent.isSuccess()) {

            // Notify the admin about the error
            if (log.isWarnEnabled()) {
                log.warn(String.format("Remove user %s", responseEvent.getExceptionMessage()));
            }

            // Provide more detailed description of the issue by printing the exception
            if (log.isDebugEnabled()) {
                log.debug(responseEvent.getException());
            }
        }
    }
}