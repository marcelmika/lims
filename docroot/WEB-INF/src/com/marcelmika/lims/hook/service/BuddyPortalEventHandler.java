package com.marcelmika.lims.hook.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:09 PM
 */
public class BuddyPortalEventHandler implements BuddyPortalService {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyPortalEventHandler.class);

    @Override
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        log.info("Jsme tu");

        return null;
    }
}
