
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.api.events.buddy.LoginBuddyRequestEvent;
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
            // Login buddy
            coreService.loginBuddy(new LoginBuddyRequestEvent(buddy.toBuddyDetails()));

        } catch (Exception e) {
            // Log error
            log.error(e);
        }
    }
}