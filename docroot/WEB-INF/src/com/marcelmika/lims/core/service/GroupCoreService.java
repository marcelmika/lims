package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.events.group.GetGroupsResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:38 PM
 */
public interface GroupCoreService {

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event);

}
