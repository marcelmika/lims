package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.jabber.service.GroupJabberService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:38 PM
 */
public class GroupCoreServiceImpl implements GroupCoreService {

    // Dependencies
    GroupJabberService groupJabberService;

    /**
     * Constructor
     *
     * @param groupJabberService jabber service
     */
    public GroupCoreServiceImpl(GroupJabberService groupJabberService) {
        this.groupJabberService = groupJabberService;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {
        return groupJabberService.getGroups(event);
    }
}
