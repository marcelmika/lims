package com.marcelmika.lims.portal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/3/14
 * Time: 10:52 PM
 */
public class BuddyPortalServiceUtil {

    private static BuddyPortalService portalService;

    /**
     * Return Buddy Portal Service Implementation
     *
     * @return Jabber
     */
    public static BuddyPortalService getBuddyPortalService() {
        return portalService;
    }

    /**
     * Inject Buddy Portal Service via Dependency Injection
     */
    public void setBuddyPortalService(BuddyPortalService portalService) {
        BuddyPortalServiceUtil.portalService = portalService;
    }
}
