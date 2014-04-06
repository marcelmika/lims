package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.entity.GroupDetails;
import com.marcelmika.lims.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Group;
import com.marcelmika.lims.jabber.group.manager.GroupManager;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;

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
    private UserSessionStore userSessionStore;

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public GroupJabberServiceImpl(UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return GetGroupsResponseEvent.getGroupsFailure(
                    new JabberException(String.format("No session for user %d found", buddyId))
            );
        }

        // Get groups manager related to buddy
        GroupManager groupManager = userSession.getGroupManager();
        // Get a list of groups
        List<Group> groups = groupManager.getGroups();
        // Map it to group details
        List<GroupDetails> details = Group.toGroupDetailsList(groups);

        // Return success
        return GetGroupsResponseEvent.getGroupsSuccess(details);
    }
}
