package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.details.GroupDetails;
import com.marcelmika.lims.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import com.marcelmika.lims.jabber.connection.store.ConnectionManagerStore;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Group;
import com.marcelmika.lims.jabber.group.manager.GroupManager;
import com.marcelmika.lims.jabber.group.manager.GroupManagerFactory;
import com.marcelmika.lims.jabber.group.store.GroupManagerStore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:22 PM
 */
public class GroupJabberServiceImpl implements GroupJabberService {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupJabberServiceImpl.class);

    // Dependencies
    private ConnectionManagerStore connectionManagerStore;
    private GroupManagerStore groupManagerStore;

    /**
     * Constructor
     *
     * @param connectionManagerStore ConnectionManagerStore
     */
    public GroupJabberServiceImpl(ConnectionManagerStore connectionManagerStore,
                                  GroupManagerStore groupManagerStore) {
        this.connectionManagerStore = connectionManagerStore;
        this.groupManagerStore = groupManagerStore;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {
        // Get buddy from event
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        // Get groups manager related to buddy
        GroupManager groupManager = getGroupManager(buddy.getBuddyId());

        List<Group> groups = groupManager.getGroups();
        log.info("groups:");
        log.info(groups);

        // todo: Implement mapping to group details

        return GetGroupsResponseEvent.getGroupsSuccess(null);
    }

    /**
     *
     * @param id
     * @return
     */
    private GroupManager getGroupManager(Long id) {
        // Add new group manager if does not exist
        if(!groupManagerStore.containsGroupManager(id)) {
            // Build new group manager
            GroupManager groupManager = GroupManagerFactory.buildGroupManager(id);
            // Get connection manager from store
            ConnectionManager connectionManager = connectionManagerStore.getConnectionManager(id);
            // Set roster to group manager
            groupManager.setRoster(connectionManager.getRoster());
            // Add it to store
            groupManagerStore.addGroupManager(groupManager);
        }

        return groupManagerStore.getConnectionManager(id);
    }
}
