package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/1/14
 * Time: 5:05 PM
 */
public interface GroupPersistenceService {

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event);

}
