
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.events.buddy.BuddyLogoutRequestEvent;
import com.marcelmika.lims.events.buddy.BuddyLogoutResponseEvent;
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
    private static Log log = LogFactoryUtil.getLog(LoginPostAction.class);
    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    @Override
    public void run(HttpSession session) {
        // Create buddy from session
        Buddy buddy = Buddy.fromHttpSession(session);
        // Logout buddy
        BuddyLogoutResponseEvent responseEvent = coreService.logoutBuddy(
                new BuddyLogoutRequestEvent(buddy.toBuddyDetails())
        );

        // Log result
        log.info(responseEvent.getResult());
    }
}