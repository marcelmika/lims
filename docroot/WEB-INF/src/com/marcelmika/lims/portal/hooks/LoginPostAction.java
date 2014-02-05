
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.buddy.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.buddy.BuddyLoginResponseEvent;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.service.BuddyPortalService;
import com.marcelmika.lims.portal.service.BuddyPortalServiceUtil;

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
    private BuddyPortalService portalService = BuddyPortalServiceUtil.getBuddyPortalService();

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response) {

        try {
            // Create buddy from portal request
            Buddy buddy = Buddy.fromPortalServletRequest(request);
            // Login buddy
            BuddyLoginResponseEvent responseEvent = portalService.loginBuddy(
                    new BuddyLoginRequestEvent(buddy.toBuddyDetails())
            );

            // Log result
            log.info(responseEvent.getResult());

        } catch (Exception e) {
            // Log error
            log.error(e);
        }
    }
}