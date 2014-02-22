package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.settings.*;
import com.marcelmika.lims.persistence.service.BuddyPersistenceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    // TODO: Move the functionality to settingsPersistenceService
    BuddyPersistenceService buddyPersistenceService;

    /**
     * Constructor
     *
     * @param buddyPersistenceService persistence service
     */
    public SettingsCoreServiceImpl(BuddyPersistenceService buddyPersistenceService) {
        this.buddyPersistenceService = buddyPersistenceService;
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event) {
        return buddyPersistenceService.updateActivePanel(event);
// TODO: Refactor
//        // While user opens panel unread messages should be set to zero
//        if (!Validator.isNull(activePanelId)) {
//            ChatUtil.setUnreadMessages(pollerRequest.getUserId(), activePanelId, 0);
//        }
    }

    /**
     * Update buddy's active room type (i.e. public or private)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateActiveRoomTypeResponseEvent updateActiveRoomType(UpdateActiveRoomTypeRequestEvent event) {
        return buddyPersistenceService.updateActiveRoomType(event);
    }

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event) {
        return buddyPersistenceService.updateSettings(event);
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method @param event
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {
        throw new NotImplementedException();
        //        ChatUtil.setChatEnabled(pollerRequest.getUserId(), enabled, status);
    }

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event) {
        throw new NotImplementedException();
        //        ChatUtil.setChatEnabled(pollerRequest.getUserId(), enabled, status);
    }


}
