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
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyResponseEvent;
import com.marcelmika.lims.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.lims.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.Presence;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
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

        Buddy buddy;            // Authorized user
        Presence presence;      // Buddy's presence

        // Deserialize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Presence
            Buddy deserializedBuddy = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT), Buddy.class
            );
            presence = deserializedBuddy.getPresence();
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            log.debug(exception);
            // End here
            return;
        }

        // Send request to core service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyCoreService.updatePresence(
                new UpdatePresenceBuddyRequestEvent(buddy.getBuddyId(), presence.toPresenceDetails())
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
            UpdatePresenceBuddyResponseEvent.Status status = responseEvent.getStatus();
            // Bad request
            if (status == UpdatePresenceBuddyResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Unauthorized
            else if (status == UpdatePresenceBuddyResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
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
