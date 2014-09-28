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
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.core.service.GroupCoreService;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.GroupCollection;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.request.parameters.GetGroupListParameters;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:33 PM
 */
public class GroupController {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupController.class);

    // Dependencies
    GroupCoreService groupCoreService;

    /**
     * Constructor
     *
     * @param groupCoreService GroupCoreService
     */
    public GroupController(final GroupCoreService groupCoreService) {
        this.groupCoreService = groupCoreService;
    }

    /**
     * Fetches all groups related to the buddy.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getGroupList(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                        // Authorized user
        GetGroupListParameters parameters;  // Parameters for the request

        // Deserialize
        try {
            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), GetGroupListParameters.class
            );

            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);
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

        // Get groups
        GetGroupsResponseEvent responseEvent = groupCoreService.getGroups(
                new GetGroupsRequestEvent(buddy.toBuddyDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map groups from group details
            GroupCollection groupCollection = GroupCollection.fromGroupCollectionDetails(
                    responseEvent.getGroupCollection()
            );

            // ... and compare it with group collection etag
            // Cached
            if (parameters.getEtag().equals(groupCollection.getEtag())) {
                // Serialize
                String serialized = JSONFactoryUtil.looseSerialize(groupCollection);
                // Etags equal which means that nothing has changed.
                // Write only the group collection without groups and buddies (no extra traffic needed)
                ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
            }
            // Not cached
            else {
                // Serialize
                String serialized = JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies");
                // Etags are different which means that groups were modified
                // Send the whole package to the client
                ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
            }
        }
        // Failure
        else {
            GetGroupsResponseEvent.Status status = responseEvent.getStatus();
            // Bad request
            if (status == GetGroupsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
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
}
