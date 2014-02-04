package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 7:51 PM
 */
public class BuddyJabberServiceUtil {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyJabberServiceUtil.class);

    private static BuddyJabberService buddyJabberService;

    /**
     * Return Buddy Portal Service Implementation
     *
     * @return Jabber
     */
    public static BuddyJabberService getBuddyJabberService() {
        return buddyJabberService;
    }

    /**
     * Inject Buddy Portal Service via Dependency Injection
     */
    public void setBuddyJabberService(BuddyJabberService buddyJabberService) {
        BuddyJabberServiceUtil.buddyJabberService = buddyJabberService;
    }

}
