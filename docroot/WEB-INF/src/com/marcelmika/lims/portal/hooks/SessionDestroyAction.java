
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.buddy.BuddyLogoutRequestEvent;
import com.marcelmika.lims.events.buddy.BuddyLogoutResponseEvent;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.service.BuddyPortalService;
import com.marcelmika.lims.portal.service.BuddyPortalServiceUtil;

import javax.servlet.http.HttpSession;

/**
 * Listens to the session destroy action. Whenever it occurs it tries to login to the Jabber
 * server with the credentials from request.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 */
public class SessionDestroyAction extends SessionAction {

    // Log
    private static Log log = LogFactoryUtil.getLog(LoginPostAction.class);
    // Service
    private BuddyPortalService portalService = BuddyPortalServiceUtil.getBuddyPortalService();

    @Override
    public void run(HttpSession session) {
        // Create buddy from session
        Buddy buddy = Buddy.fromHttpSession(session);
        // Logout buddy
        BuddyLogoutResponseEvent responseEvent = portalService.logoutBuddy(
                new BuddyLogoutRequestEvent(buddy.toBuddyDetails())
        );

        // Log result
        log.info(responseEvent.getResult());
    }
}