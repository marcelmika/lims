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
import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.api.events.settings.*;
import com.marcelmika.lims.persistence.domain.Settings;
import com.marcelmika.lims.persistence.generated.service.PanelLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;


/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 8:29 PM
 */
public class SettingsPersistenceServiceImpl implements SettingsPersistenceService {

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event) {
        // Get buddy
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Create settings domain from service builder domain
            Settings settings = Settings.fromServiceBuilderModel(
                    PanelLocalServiceUtil.getPanelByUser(buddy.getBuddyId()),
                    SettingsLocalServiceUtil.getSettingsByUser(buddy.getBuddyId())
            );

            // Success
            return ReadSettingsResponseEvent.readSettingsSuccess(settings.toSettingsDetails());

        } catch (Exception exception) {
            // Failure
            return ReadSettingsResponseEvent.readSettingsFailure(
                    ReadSettingsResponseEvent.Status.ERROR_PERSISTENCE, exception
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
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event) {

        // Check params
        if (event.getBuddyId() == null) {
            return UpdateActivePanelResponseEvent.updateActivePanelFailure(
                    UpdateActivePanelResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Update active panel
            PanelLocalServiceUtil.updateActivePanel(event.getBuddyId(), event.getActivePanel());

            // Success
            return UpdateActivePanelResponseEvent.updateActivePanelSuccess(event.getActivePanel());

        } catch (Exception exception) {
            // Failure
            return UpdateActivePanelResponseEvent.updateActivePanelFailure(
                    UpdateActivePanelResponseEvent.Status.ERROR_PERSISTENCE, exception
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
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event) {
        SettingsDetails details = event.getSettingsDetails();

        // Check params
        if (event.getBuddyId() == null) {
            return UpdateSettingsResponseEvent.updateSettingsFailure(
                    UpdateSettingsResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Get settings
            com.marcelmika.lims.persistence.generated.model.Settings settings = SettingsLocalServiceUtil.getSettingsByUser(
                    event.getBuddyId()
            );
            // Set new values
            settings.setMute(details.isMute());

            // Save
            SettingsLocalServiceUtil.saveSettings(settings);

            // Success
            return UpdateSettingsResponseEvent.updateSettingsSuccess(details);

        } catch (Exception exception) {
            // Failure
            return UpdateSettingsResponseEvent.updateSettingsFailure(
                    UpdateSettingsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {
        // Get buddy
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            SettingsLocalServiceUtil.setChatEnabled(buddy.getBuddyId(), true);
            // Success
            return EnableChatResponseEvent.enableChatSuccess("User chat enabled");

        } catch (Exception e) {
            // Failure
            return EnableChatResponseEvent.enableChatFailure("Cannot enable chat", e);
        }
    }

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event) {
        // Get buddy
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            SettingsLocalServiceUtil.setChatEnabled(buddy.getBuddyId(), false);
            // Success
            return DisableChatResponseEvent.disableChatSuccess("Chat disabled");

        } catch (Exception e) {
            // Failure
            return DisableChatResponseEvent.disableChatFailure("Cannot disable chat", e);
        }
    }
}
