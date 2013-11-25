
package com.marcelmika.lims.hook.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.util.ChatUtil;

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

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response) {

        try {
            // Get user from the request
            User user = PortalUtil.getUser(request);
            // Get password from request
            String password = PortalUtil.getUserPassword(request);
            // Login to Jabber server
            ChatUtil.login(user.getUserId(), user.getScreenName(), password);

        } catch (Exception e) {
            // Log error
            log.error(e);
        }
    }
}