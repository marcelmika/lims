package com.marcelmika.lims.core.service;

/**
 * Utility class that holds an instance of BuddyCoreService.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/6/14
 * Time: 8:42 AM
 */
public class BuddyCoreServiceUtil {

    private static BuddyCoreService buddyCoreService;

    /**
     * Return Buddy Core Service implementation
     *
     * @return BuddyCoreService
     */
    public static BuddyCoreService getBuddyCoreService() {
        return buddyCoreService;
    }

    /**
     * Inject Buddy Core Service via Dependency Injection
     *
     * @param buddyCoreService BuddyCoreService
     */
    public void setBuddyCoreService(BuddyCoreService buddyCoreService) {

        BuddyCoreServiceUtil.buddyCoreService = buddyCoreService;
    }


}
