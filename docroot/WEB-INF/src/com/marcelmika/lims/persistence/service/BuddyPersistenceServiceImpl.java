package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.buddy.*;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Presence;
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
    public DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Remove settings from database
            Settings settings = SettingsLocalServiceUtil.getSettingsByUser(buddy.getBuddyId());
            if (settings != null) {
                // Delete only if the user is in the system otherwise do nothing
                SettingsLocalServiceUtil.deleteSettings(settings);
            }
            // Success
            return DeleteBuddyResponseEvent.removeSuccess("Buddy " + buddy.getBuddyId() + " has been successfully " +
                    "removed from the persistence layer", buddy.toBuddyDetails());
        } catch (Exception e) {
            // Failure
            return DeleteBuddyResponseEvent.removeFailure(
                    "Cannot remove buddy from persistence layer.",
                    buddy.toBuddyDetails()
            );
        }
    }

    /**
     * Reads buddy's presence
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadPresenceBuddyResponseEvent readPresence(ReadPresenceBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        try {
            // Take presence from user settings
            Settings settings = SettingsLocalServiceUtil.getSettingsByUser(buddy.getBuddyId());

            if (settings != null) {
                // Create Presence from status
                Presence presence = Presence.fromStatus(settings.getStatus());
                // Success
                return ReadPresenceBuddyResponseEvent.readPresenceSuccess(
                        "Presence successfully read", presence.toPresenceDetails()
                );
            } else {
                // Failure
                return ReadPresenceBuddyResponseEvent.readPresenceFailure(
                        new Exception(String.format("Cannot find settings for buddy with ID: %s", buddy.getBuddyId()))
                );
            }

        } catch (Exception e) {
            // Failure
            return ReadPresenceBuddyResponseEvent.readPresenceFailure(e);
        }
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event) {
        // Get presence
        Presence presence = Presence.fromPresenceDetails(event.getPresenceDetails());

        try {
            // Save to settings
            SettingsLocalServiceUtil.changeStatus(event.getBuddyId(), presence.toStatus());

            return UpdatePresenceBuddyResponseEvent.updateStatusSuccess(
                    "Status " + presence.toStatus() + " saved to persistence layer for user " + event.getBuddyId()
            );
        } catch (Exception exception) {
            return UpdatePresenceBuddyResponseEvent.updateStatusFailure(
                    "Cannot update Status to a persistence layer", exception
            );
        }
    }
}
