package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.events.details.GroupDetails;
import com.marcelmika.lims.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.jabber.connection.store.ConnectionManagerStore;

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
    private static Log log = LogFactoryUtil.getLog(BuddyJabberServiceImpl.class);

    // Dependencies
    private ConnectionManagerStore connectionManagerStore;

    /**
     * Constructor
     *
     * @param connectionManagerStore ConnectionManagerStore
     */
    public GroupJabberServiceImpl(ConnectionManagerStore connectionManagerStore) {
        this.connectionManagerStore = connectionManagerStore;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {
        List<GroupDetails> dummyGroups = new ArrayList<GroupDetails>();
        GroupDetails foo = new GroupDetails();
        foo.setName("FOO");
        dummyGroups.add(foo);

        return GetGroupsResponseEvent.getGroupsSuccess(dummyGroups);
    }
}
