package com.marcelmika.lims.core.service;

import com.marcelmika.lims.api.events.settings.*;

/**
 * Several settings values are related to each buddy.
 * Those values can be changed via SettingsCoreService that serves as a port to the
 * business logic related to settings.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:43 PM
 */
public interface SettingsCoreService {

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event);

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event
     * @return Response event
     */
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event);

    /**
     * Update buddy's active room type (i.e. public or private)
     *
     * @param event Request event
     * @return Response event
     */
    public UpdateActiveRoomTypeResponseEvent updateActiveRoomType(UpdateActiveRoomTypeRequestEvent event);

    /**
     * Update buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event);

    /**
     * Enables chat for buddy
     *
     * @param event Request event
     * @return Response event
     */
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event);

    /**
     * Disables chat for buddy
     *
     * @param event Request event
     * @return Response event
     */
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event);

}
