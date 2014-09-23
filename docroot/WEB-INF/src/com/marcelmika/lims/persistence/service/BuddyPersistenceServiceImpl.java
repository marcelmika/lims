/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.buddy.*;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Presence;
import com.marcelmika.lims.persistence.generated.model.Settings;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;
import com.marcelmika.lims.persistence.manager.SearchManager;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:56 PM
 */
public class BuddyPersistenceServiceImpl implements BuddyPersistenceService {

    // Dependencies
    SearchManager searchManager;

    /**
     * Constructor
     *
     * @param searchManager SearchManager
     */
    public BuddyPersistenceServiceImpl(final SearchManager searchManager) {
        this.searchManager = searchManager;
    }

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
            return LoginBuddyResponseEvent.loginSuccess(buddy.toBuddyDetails());

        } catch (Exception exception) {
            // Call failure
            return LoginBuddyResponseEvent.loginFailure(
                    LoginBuddyResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
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

            // Success
            return UpdatePresenceBuddyResponseEvent.updatePresenceSuccess();

        } catch (Exception exception) {
            // Failure
            return UpdatePresenceBuddyResponseEvent.updatePresenceFailure(
                    UpdatePresenceBuddyResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Search buddies in the system
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SearchBuddiesResponseEvent searchBuddies(SearchBuddiesRequestEvent event) {
        // Map buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        // Check params
        if (buddy.getBuddyId() == null) {
            return SearchBuddiesResponseEvent.searchFailure(SearchBuddiesResponseEvent.Status.ERROR_WRONG_PARAMETERS);
        }

        try {
            // Define boundaries
            int start = 0;
            int end = Environment.getBuddyListMaxSearch();

            // Get buddies from manager
            List<Buddy> buddies = searchManager.searchBuddies(buddy.getBuddyId(), event.getSearchQuery(), start, end);

            // Create buddy details list from the buddy list
            List<BuddyDetails> buddyDetails = new LinkedList<BuddyDetails>();
            for (Buddy searchedBuddy : buddies) {
                buddyDetails.add(searchedBuddy.toBuddyDetails());
            }

            // Call success
            return SearchBuddiesResponseEvent.searchSuccess(buddyDetails);
        }
        // Failure
        catch (Exception exception) {
            return SearchBuddiesResponseEvent.searchFailure(
                    SearchBuddiesResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }
}
