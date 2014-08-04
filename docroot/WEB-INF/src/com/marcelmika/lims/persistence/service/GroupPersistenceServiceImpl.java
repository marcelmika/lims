package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.GroupCollection;
import com.marcelmika.lims.persistence.group.GroupManager;
import com.marcelmika.lims.persistence.group.GroupManagerImpl;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/1/14
 * Time: 5:06 PM
 */
public class GroupPersistenceServiceImpl implements GroupPersistenceService {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupPersistenceServiceImpl.class);

    // Dependencies
    // TODO: Inject
    GroupManager groupManager = new GroupManagerImpl();

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {
        // Map buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        try {
            // Get groups from manager
            GroupCollection groupCollection = groupManager.getGroups(buddy.getBuddyId());

            // Call success
            return GetGroupsResponseEvent.getGroupsSuccess(groupCollection.toGroupCollectionDetails());

        }
        // Something went wrong
        catch (Exception exception) {
            // Call error
            return GetGroupsResponseEvent.getGroupsFailure(exception);
        }
    }
}
