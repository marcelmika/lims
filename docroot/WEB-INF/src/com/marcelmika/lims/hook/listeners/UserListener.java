
package com.marcelmika.lims.hook.listeners;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.marcelmika.lims.util.ChatUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class UserListener extends BaseModelListener<User> {

    // Log
    private static Log log = LogFactoryUtil.getLog(UserListener.class);

    @Override
    public void onAfterRemove(User user) {
        try {
            // Log
            log.info("Removing LIMS entries and status for user " + user.getUserId());
            // Remove user from system
            ChatUtil.removeUserFromSystem(user.getUserId());

        } catch (Exception e) {
            log.error("Unable to remove LIMS entries and status for user " + user.getUserId());
        }
    }

    @Override
    public void onAfterUpdate(User user) {

    }


}