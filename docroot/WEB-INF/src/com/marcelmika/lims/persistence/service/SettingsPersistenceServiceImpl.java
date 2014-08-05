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
            return ReadSettingsResponseEvent.readSettingsSuccess("Details read", settings.toSettingsDetails());

        } catch (Exception e) {
            // Failure
            return ReadSettingsResponseEvent.readSettingsFailure(e);
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
        try {
            // Update active panel
            PanelLocalServiceUtil.updateActivePanel(event.getBuddyId(), event.getActivePanel());

            // Success
            return UpdateActivePanelResponseEvent.updateActivePanelSuccess(
                    "Active Panel" + event.getActivePanel() + " saved to persistence layer for user "
                            + event.getBuddyId()
            );

        } catch (Exception exception) {
            // Failure
            return UpdateActivePanelResponseEvent.updateActivePanelFailure(
                    "Cannot update Active Panel to a persistence layer", exception
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
        try {
            // Get settings
            com.marcelmika.lims.persistence.generated.model.Settings settings = SettingsLocalServiceUtil.getSettingsByUser(
                    event.getBuddyId()
            );
            // Set new values
            settings.setMute(details.isMute());
            // Save
            SettingsLocalServiceUtil.updateSettings(settings, true);

            return UpdateSettingsResponseEvent.updateSettingsSuccess(
                    "Settings saved to persistence layer for user " + event.getBuddyId(), details
            );

        } catch (Exception exception) {
            return UpdateSettingsResponseEvent.updateSettingsFailure(
                    "Cannot update Settings to a persistence layer", details, exception
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
