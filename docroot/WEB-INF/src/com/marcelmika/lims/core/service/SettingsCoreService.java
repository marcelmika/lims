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
