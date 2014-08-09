package com.marcelmika.lims.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.core.service.GroupCoreService;
import com.marcelmika.lims.core.service.GroupCoreServiceUtil;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.GroupCollection;
import com.marcelmika.lims.portal.http.HttpStatus;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:33 PM
 */
public class GroupController extends BaseController {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupController.class);

    // Dependencies
    GroupCoreService groupCoreService = GroupCoreServiceUtil.getGroupCoreService();

    /**
     * Fetches all groups related to the buddy.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getGroupList(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);
        // Get groups request
        GetGroupsResponseEvent responseEvent = groupCoreService.getGroups(
                new GetGroupsRequestEvent(buddy.toBuddyDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map groups from group details
            GroupCollection groupCollection = GroupCollection.fromGroupCollectionDetails(
                    responseEvent.getGroupCollection()
            );

            // Groups are cached, so take the etag from request ...
            String etag = request.getParameter("etag");
            // ... and compare it with group collection etag
            if (etag.equals(Integer.toString(groupCollection.getEtag()))) {
                // Etags equal which means that nothing has changed.
                // Write only the group collection without groups and buddies (no extra traffic needed)
                writeResponse(JSONFactoryUtil.looseSerialize(groupCollection), HttpStatus.OK, response);
            } else {
                // Etags are different which means that groups were modified
                // Send the whole package to the client
                writeResponse(
                        JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies"),
                        HttpStatus.OK,
                        response
                );
            }
        }
        // Failure
        else {
            log.error(responseEvent.getException());
            // TODO: Add status handling
            writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

}
