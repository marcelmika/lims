package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.buddy.*;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Presence;
import com.marcelmika.lims.persistence.generated.model.Settings;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:56 PM
 */
public class BuddyPersistenceServiceImpl implements BuddyPersistenceService {

    /**
     * Login buddy to System
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Take presence from user settings
            Settings settings = SettingsLocalServiceUtil.getSettingsByUser(buddy.getBuddyId());

            // If the user disabled the chat keep it offline
            if (settings.isChatEnabled()) {
                // When the user logs in, change the presence to active
                SettingsLocalServiceUtil.changePresence(buddy.getBuddyId(), Presence.ACTIVE.getDescription());
            }

            // Call success
            return LoginBuddyResponseEvent.loginSuccess("User successfully logged in", buddy.toBuddyDetails());

        } catch (Exception e) {
            // Call failure
            return LoginBuddyResponseEvent.loginFailure(e.getLocalizedMessage(), buddy.toBuddyDetails());
        }
    }

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        // Get buddy from buddy details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        try {
            // Change presence to offline
            SettingsLocalServiceUtil.changePresence(buddy.getBuddyId(), Presence.OFFLINE.getDescription());

            // Call success
            return LogoutBuddyResponseEvent.logoutSuccess("User successfully logged out", buddy.toBuddyDetails());

        } catch (Exception e) {
            // Call failure
            return LogoutBuddyResponseEvent.logoutFailure(e.getLocalizedMessage(), buddy.toBuddyDetails());
        }
    }

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
                // Create Presence from string
                Presence presence = Presence.fromDescription(settings.getPresence());
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
     * Change buddy's presence
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
            SettingsLocalServiceUtil.changePresence(event.getBuddyId(), presence.getDescription());

            return UpdatePresenceBuddyResponseEvent.updatePresenceSuccess(
                    "Presence " + presence.getDescription() + " saved to persistence layer for user " + event.getBuddyId()
            );
        } catch (Exception exception) {
            return UpdatePresenceBuddyResponseEvent.updatePresenceFailure(
                    "Cannot update presence to a persistence layer", exception
            );
        }
    }
}
