
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.util.WebKeys;
import com.marcelmika.lims.util.ChatUtil;

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

    @Override
    public void run(HttpSession session) {
        // Get user ID from session
        Long userId = (Long) session.getAttribute(WebKeys.USER_ID);
        // Disconnect user from jabber
        ChatUtil.logout(userId);
    }
}