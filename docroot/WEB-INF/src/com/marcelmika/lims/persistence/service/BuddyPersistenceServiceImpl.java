package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.events.session.*;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.service.SettingsLocalServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:56 PM
 */
public class BuddyPersistenceServiceImpl implements BuddyPersistenceService {

    /**
     * Completely removes buddy from Persistence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyDeleteResponseEvent removeBuddy(BuddyDeleteRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Remove settings from database
            Settings settings = SettingsLocalServiceUtil.getSettings(buddy.getBuddyId());
            if (settings != null) {
                // Delete only if the user is in the system otherwise do nothing
                SettingsLocalServiceUtil.deleteSettings(settings);
            }
            // Success
            return BuddyDeleteResponseEvent.removeSuccess("Buddy " + buddy.getBuddyId() + " has been successfully " +
                    "removed from the persistence layer", buddy.toBuddyDetails());
        } catch (Exception e) {
            // Failure
            return BuddyDeleteResponseEvent.removeFailure(
                    "Cannot remove buddy from persistence layer.",
                    buddy.toBuddyDetails()
            );
        }
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyUpdateStatusResponseEvent changeStatus(BuddyUpdateStatusRequestEvent event) {
        try {
            // Save to settings
            SettingsLocalServiceUtil.changeStatus(event.getBuddyId(), event.getStatus());

            return BuddyUpdateStatusResponseEvent.updateStatusSuccess(
                    "Status " + event.getStatus() + " saved to persistence layer for user " + event.getBuddyId()
            );
        } catch (Exception exception) {
            return BuddyUpdateStatusResponseEvent.updateStatusFailure(
                    "Cannot update Status to a persistence layer", exception
            );
        }
    }

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyUpdateActivePanelResponseEvent updateActivePanel(BuddyUpdateActivePanelRequestEvent event) {
        try {
            // Get settings
            Settings settings = SettingsLocalServiceUtil.getSettings(event.getBuddyId());
            // Set values
            settings.setActivePanelId(event.getActivePanel());
            // Save settings
            SettingsLocalServiceUtil.updateSettings(settings, false);

            return BuddyUpdateActivePanelResponseEvent.updateActivePanelSuccess(
                    "Active Panel" + event.getActivePanel() + " saved to persistence layer for user "
                            + event.getBuddyId()
            );

        } catch (Exception exception) {
            return BuddyUpdateActivePanelResponseEvent.updateActivePanelFailure(
                    "Cannot update Active Panel to a persistence layer", exception
            );
        }
    }

    /**
     * Update buddy's active room type (i.e. public or private)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyUpdateActiveRoomTypeResponseEvent updateActiveRoomType(BuddyUpdateActiveRoomTypeRequestEvent event) {
        try {
            // Change Active Room type
            SettingsLocalServiceUtil.changeActiveRoomType(event.getBuddyId(), event.getActiveRoomType());

            return BuddyUpdateActiveRoomTypeResponseEvent.updateActiveRoomTypeSuccess(
                    "Active Room Type " + event.getActiveRoomType() + " saved to persistence layer for user "
                            + event.getBuddyId()
            );
        } catch (Exception exception) {
            return BuddyUpdateActiveRoomTypeResponseEvent.updateActiveRoomTypeFailure(
                    "Cannot update Active Room type toa persistence layer", exception
            );
        }
    }

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public BuddyUpdateSettingsResponseEvent updateSettings(BuddyUpdateSettingsRequestEvent event) {
        return null;
    }
}
