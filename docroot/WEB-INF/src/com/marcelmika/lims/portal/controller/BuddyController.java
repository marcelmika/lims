package com.marcelmika.lims.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyResponseEvent;
import com.marcelmika.lims.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.lims.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.Presence;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 4:59 PM
 */
public class BuddyController {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyController.class);

    // Dependencies
    BuddyCoreService buddyCoreService;
    SettingsCoreService settingsCoreService;


    /**
     * Constructor
     *
     * @param buddyCoreService    BuddyCoreService
     * @param settingsCoreService SettingsCoreService
     */
    public BuddyController(final BuddyCoreService buddyCoreService,
                           final SettingsCoreService settingsCoreService) {

        this.buddyCoreService = buddyCoreService;
        this.settingsCoreService = settingsCoreService;
    }

    /**
     * Update buddy's status
     *
     * @param request  Request
     * @param response Response
     */
    public void updateBuddyPresence(ResourceRequest request, ResourceResponse response) {
        // Create buddy from poller request
        // TODO: Content should by passed via content parameter and not via data, refactor
        Buddy buddy = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Buddy.class);
        Presence presence = buddy.getPresence();

        // Send request to core service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyCoreService.updatePresence(
                new UpdatePresenceBuddyRequestEvent(buddy.getBuddyId(), buddy.getPresence().toPresenceDetails())
        );

        // Disable chat if presence is offline
        if (presence == Presence.OFFLINE) {
            settingsCoreService.disableChat(new DisableChatRequestEvent(buddy.toBuddyDetails()));
        }
        // Enable otherwise
        else {
            settingsCoreService.enableChat(new EnableChatRequestEvent(buddy.toBuddyDetails()));
        }

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(null, HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            log.error(responseEvent.getResult());
            log.error(responseEvent.getException());
            // TODO: Add status handling
            ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

}
