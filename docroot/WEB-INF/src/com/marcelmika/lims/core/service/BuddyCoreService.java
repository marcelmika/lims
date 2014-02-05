package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.session.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public interface BuddyCoreService {

    /**
     * Login buddy to System
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public BuddyLoginResponseEvent loginBuddy(BuddyLoginRequestEvent event);

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyLogoutResponseEvent logoutBuddy(BuddyLogoutRequestEvent event);

    /**
     * Completely removes buddy from the System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyDeleteResponseEvent removeBuddy(BuddyDeleteRequestEvent event);

    /**
     * Update buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyUpdateStatusResponseEvent updateStatus(BuddyUpdateStatusRequestEvent event);

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyUpdateActivePanelResponseEvent updateActivePanel(BuddyUpdateActivePanelRequestEvent event);

    /**
     * Update buddy's active room type (i.e. public or private)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyUpdateActiveRoomTypeResponseEvent updateActiveRoomType(BuddyUpdateActiveRoomTypeRequestEvent event);

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyUpdateSettingsResponseEvent updateSettings(BuddyUpdateSettingsRequestEvent event);

}
