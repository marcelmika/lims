package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:55 PM
 */
public interface BuddyPersistenceService {

    /**
     * Completely removes buddy from Persistence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyDeleteResponseEvent removeBuddy(BuddyDeleteRequestEvent event);

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public BuddyUpdateStatusResponseEvent changeStatus(BuddyUpdateStatusRequestEvent event);

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
