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

package com.marcelmika.lims.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelResponseEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsResponseEvent;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.Settings;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:32 PM
 */
public class SettingsController {

    // Log
    private static Log log = LogFactoryUtil.getLog(SettingsController.class);

    // Dependencies
    SettingsCoreService settingsCoreService;

    /**
     * Constructor
     *
     * @param settingsCoreService SettingsCoreService
     */
    public SettingsController(final SettingsCoreService settingsCoreService) {
        this.settingsCoreService = settingsCoreService;
    }

    /**
     * Update buddy's settings
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void updateSettings(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;        // Currently logged user
        Settings settings;  // Settings that should be updated

        // Deserialize
        try {
            // Request content
            String content = request.getParameter(RequestParameterKeys.KEY_CONTENT);
            // Settings
            settings = JSONFactoryUtil.looseDeserialize(content, Settings.class);
            // Buddy
            buddy = Buddy.fromResourceRequest(request);
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            log.error(exception);
            // End here
            return;
        }

        // Send request to core service
        UpdateSettingsResponseEvent responseEvent = settingsCoreService.updateSettings(
                new UpdateSettingsRequestEvent(buddy.getBuddyId(), settings.toSettingsDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            UpdateSettingsResponseEvent.Status status = responseEvent.getStatus();
            // Bad parameters
            if (status == UpdateSettingsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                log.error(responseEvent.getException());
            }
        }
    }

    /**
     * Updates buddy's active panel
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void updateActivePanel(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;        // Currently logged user
        Settings settings;  // Active panel is a part of settings

        // Deserialize
        try {
            // Request content
            String content = request.getParameter(RequestParameterKeys.KEY_CONTENT);
            // Settings
            settings = JSONFactoryUtil.looseDeserialize(content, Settings.class);
            // Buddy
            buddy = Buddy.fromResourceRequest(request);
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            log.error(exception);
            // End here
            return;
        }

        // Send request to core service
        UpdateActivePanelResponseEvent responseEvent = settingsCoreService.updateActivePanel(
                new UpdateActivePanelRequestEvent(buddy.getBuddyId(), settings.getActivePanelId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            UpdateActivePanelResponseEvent.Status status = responseEvent.getStatus();
            // Bad Request
            if (status == UpdateActivePanelResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                log.error(responseEvent.getException());
            }
        }
    }
}
