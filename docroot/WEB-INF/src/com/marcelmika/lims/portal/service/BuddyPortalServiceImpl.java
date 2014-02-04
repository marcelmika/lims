package com.marcelmika.lims.portal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.service.BuddyJabberService;
import com.marcelmika.lims.jabber.service.BuddyJabberServiceUtil;
import com.marcelmika.lims.events.session.BuddyLoginRequestEvent;
import com.marcelmika.lims.events.session.BuddyLoginResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:09 PM
 */
public class BuddyPortalServiceImpl implements BuddyPortalService {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyPortalServiceImpl.class);

    @Override
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event) {
        return BuddyJabberServiceUtil.getBuddyJabberService().loginBuddy(event);
    }
}
