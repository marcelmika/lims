package com.marcelmika.lims.core.service;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:38 PM
 */
public class GroupCoreServiceUtil {

    private static GroupCoreService groupCoreService;

    /**
     * Returns GroupCoreService implementation
     *
     * @return GroupCoreService
     */
    public static GroupCoreService getGroupCoreService() {
        return groupCoreService;
    }

    /**
     * Injects proper GroupCoreService via Dependency Injection
     *
     * @param groupCoreService GroupCoreService
     */
    public void setGroupCoreService(GroupCoreService groupCoreService) {
        GroupCoreServiceUtil.groupCoreService = groupCoreService;
    }




}
