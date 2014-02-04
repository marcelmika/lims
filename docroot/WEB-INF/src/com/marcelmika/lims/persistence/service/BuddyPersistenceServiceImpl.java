package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.events.session.BuddyRemoveRequestEvent;
import com.marcelmika.lims.events.session.BuddyRemoveResponseEvent;
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
    public BuddyRemoveResponseEvent removeBuddy(BuddyRemoveRequestEvent event) {
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
            return BuddyRemoveResponseEvent.removeSuccess("Buddy " + buddy.getBuddyId() + " has been successfully " +
                    "removed from the persistence layer", buddy.toBuddyDetails());
        } catch (Exception e) {
            // Failure
            return BuddyRemoveResponseEvent.removeFailure(
                    "Cannot remove buddy from persistence layer.",
                    buddy.toBuddyDetails()
            );
        }
    }
}
