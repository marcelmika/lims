package com.marcelmika.lims.core.service;

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.jabber.service.GroupJabberService;
import com.marcelmika.lims.persistence.service.GroupPersistenceService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:38 PM
 */
public class GroupCoreServiceImpl implements GroupCoreService {

    // Dependencies
    GroupJabberService groupJabberService;
    GroupPersistenceService groupPersistenceService;

    /**
     * Constructor
     *
     * @param groupJabberService jabber service
     */
    public GroupCoreServiceImpl(final GroupJabberService groupJabberService,
                                final GroupPersistenceService groupPersistenceService) {
        this.groupJabberService = groupJabberService;
        this.groupPersistenceService = groupPersistenceService;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {

        Environment.BuddyListSource source = Environment.getBuddyListSource();

        // Take the groups from jabber only if the jabber is enabled
        if (source == Environment.BuddyListSource.JABBER && Environment.isJabberEnabled()) {
            return groupJabberService.getGroups(event);
        }
        // Otherwise, take them from Liferay
        else {
            return groupPersistenceService.getGroups(event);
        }
    }

}
