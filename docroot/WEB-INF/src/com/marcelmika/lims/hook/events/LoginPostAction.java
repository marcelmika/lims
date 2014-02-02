
package com.marcelmika.lims.hook.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;
import com.marcelmika.lims.hook.domain.Buddy;
import com.marcelmika.lims.hook.service.BuddyPortalEventHandler;
import com.marcelmika.lims.hook.service.BuddyPortalService;

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
    // Portal service
    // TODO: Inject
    private BuddyPortalService portalService = new BuddyPortalEventHandler();

    public void setPortalService(BuddyPortalService portalService) {
        this.portalService = portalService;
    }

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response) {

        try {
            // Create buddy from portal request
            Buddy buddy = Buddy.fromPortalServletRequest(request);
            // Login buddy
            BuddyLoginResponseEvent responseEvent = portalService.loginBuddy(
                    new BuddyLoginRequestEvent(buddy.toBuddyDetails())
            );

            // Login to Jabber server
            // ChatUtil.login(user.getUserId(), user.getScreenName(), password);

        } catch (Exception e) {
            // Log error
            log.error(e);
        }
    }
}