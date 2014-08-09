package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:07 PM
 */
public interface GroupJabberService {

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event);


}
