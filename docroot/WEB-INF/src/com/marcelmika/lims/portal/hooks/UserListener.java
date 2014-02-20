
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.events.buddy.BuddyDeleteRequestEvent;
import com.marcelmika.lims.events.buddy.BuddyDeleteResponseEvent;
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

    @Override
    public void onAfterRemove(User user) {
        // Create buddy from portal user
        Buddy buddy = Buddy.fromPortalUser(user);
        // Logout buddy
        BuddyDeleteResponseEvent responseEvent = coreService.removeBuddy(
                new BuddyDeleteRequestEvent(buddy.toBuddyDetails())
        );
        // Log result
        log.info(responseEvent.getResult());
    }

    @Override
    public void onAfterUpdate(User user) {
        // TODO: Implement update user service method
    }


}