package com.marcelmika.lims.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsResponseEvent;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.portal.domain.Settings;
import com.marcelmika.lims.portal.http.HttpStatus;
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
        // Create buddy and settings from poller request
        // TODO: Replace with content
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);
        // Send request to core service
        UpdateSettingsResponseEvent responseEvent = settingsCoreService.updateSettings(new UpdateSettingsRequestEvent(
                        settings.getBuddy().getBuddyId(), settings.toSettingsDetails())
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

        // Create buddy and settings from poller request
        Settings settings;
        try {
            settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);
        } catch (Exception exception) {
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            return;
        }

        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateActivePanel(new UpdateActivePanelRequestEvent(
                settings.getBuddy().getBuddyId(), settings.getActivePanelId()
        ));

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            // TODO: Add status handling
            ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }


}
