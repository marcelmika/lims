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

        GetGroupListParameters parameters;  // Parameters for the request
        Buddy buddy;                        // Currently logged user

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
            log.error(exception);
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
                // Etags equal which means that nothing has changed.
                // Write only the group collection without groups and buddies (no extra traffic needed)
                ResponseUtil.writeResponse(JSONFactoryUtil.looseSerialize(groupCollection), HttpStatus.OK, response);
            }
            // Not cached
            else {
                // Etags are different which means that groups were modified
                // Send the whole package to the client
                ResponseUtil.writeResponse(
                        JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies"),
                        HttpStatus.OK,
                        response
                );
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
