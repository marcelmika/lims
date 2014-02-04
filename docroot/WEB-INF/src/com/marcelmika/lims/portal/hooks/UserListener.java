
package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.marcelmika.lims.events.session.BuddyRemoveRequestEvent;
import com.marcelmika.lims.events.session.BuddyRemoveResponseEvent;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.service.BuddyPortalService;
import com.marcelmika.lims.portal.service.BuddyPortalServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class UserListener extends BaseModelListener<User> {

    // Log
    private static Log log = LogFactoryUtil.getLog(UserListener.class);
    // Services
    private BuddyPortalService portalService = BuddyPortalServiceUtil.getBuddyPortalService();

    @Override
    public void onAfterRemove(User user) {
        // Create buddy from portal user
        Buddy buddy = Buddy.fromPortalUser(user);
        // Logout buddy
        BuddyRemoveResponseEvent responseEvent = portalService.removeBuddy(
                new BuddyRemoveRequestEvent(buddy.toBuddyDetails())
        );
        // Log result
        log.info(responseEvent.getResult());
    }

    @Override
    public void onAfterUpdate(User user) {
        // TODO: Implement update user service method
    }


}