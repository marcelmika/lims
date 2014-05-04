package com.marcelmika.lims.core.service;

import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.settings.*;
import com.marcelmika.lims.persistence.service.SettingsPersistenceService;

/**
 * Implementation of SettingsCoreService
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:44 PM
 */
public class SettingsCoreServiceImpl implements SettingsCoreService {

    // Dependencies
    SettingsPersistenceService settingsPersistenceService;

    /**
     * Constructor
     *
     * @param settingsPersistenceService persistence service
     */
    public SettingsCoreServiceImpl(SettingsPersistenceService settingsPersistenceService) {
        this.settingsPersistenceService = settingsPersistenceService;
    }

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event) {
        return settingsPersistenceService.readSettings(event);
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event) {
        return settingsPersistenceService.updateActivePanel(event);
// TODO: Refactor
//        // While user opens panel unread messages should be set to zero
//        if (!Validator.isNull(activePanelId)) {
//            ChatUtil.setUnreadMessages(pollerRequest.getUserId(), activePanelId, 0);
//        }
    }

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event) {
        return settingsPersistenceService.updateSettings(event);
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {
        // Chat is enabled
        ResponseEvent responseEvent = settingsPersistenceService.enableChat(event);
        if (!responseEvent.isSuccess()) {
            return EnableChatResponseEvent.enableChatFailure("Cannot enable chat", responseEvent.getException());
        }

        // No active panel
        responseEvent = settingsPersistenceService.updateActivePanel(
                new UpdateActivePanelRequestEvent(event.getBuddyDetails().getBuddyId(), "")
        );
        if (!responseEvent.isSuccess()) {
            return EnableChatResponseEvent.enableChatFailure("Cannot enable chat", responseEvent.getException());
        }

        // Change presence in jabber

        // Change presence locally


//        // Refresh conversations
//        if (enabled) {
//            JabberUtil.restartConversations(userId);
//        }

        return EnableChatResponseEvent.enableChatSuccess("Chat was successfully enabled");
    }

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event) {
        // Chat is disabled
        ResponseEvent responseEvent = settingsPersistenceService.disableChat(event);
        if (!responseEvent.isSuccess()) {
            return DisableChatResponseEvent.disableChatFailure("Cannot disable chat", responseEvent.getException());
        }

        // No active panel
        responseEvent = settingsPersistenceService.updateActivePanel(
                new UpdateActivePanelRequestEvent(event.getBuddyDetails().getBuddyId(), "")
        );
        if (!responseEvent.isSuccess()) {
            return DisableChatResponseEvent.disableChatFailure("Cannot disable chat", responseEvent.getException());
        }

        // Change presence in jabber

        // Change presence locally


        return DisableChatResponseEvent.disableChatSuccess("Chat was successfully disabled");

    }
}
