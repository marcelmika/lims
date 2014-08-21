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
    public SettingsCoreServiceImpl(final SettingsPersistenceService settingsPersistenceService) {
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
     * Change buddy's presence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event) {
        return settingsPersistenceService.updateActivePanel(event);
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

        return DisableChatResponseEvent.disableChatSuccess("Chat was successfully disabled");
    }
}
