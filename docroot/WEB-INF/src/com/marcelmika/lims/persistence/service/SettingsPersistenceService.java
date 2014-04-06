package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.settings.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 8:29 PM
 */
public interface SettingsPersistenceService {

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event);

    /**
     * Update buddy's active room type (i.e. public or private)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public UpdateActiveRoomTypeResponseEvent updateActiveRoomType(UpdateActiveRoomTypeRequestEvent event);

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event);

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event);

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event);

}
