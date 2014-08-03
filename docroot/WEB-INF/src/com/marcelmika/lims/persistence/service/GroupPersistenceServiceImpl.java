package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.api.entity.GroupCollectionDetails;
import com.marcelmika.lims.api.entity.GroupDetails;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.persistence.domain.Buddy;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/1/14
 * Time: 5:06 PM
 */
public class GroupPersistenceServiceImpl implements GroupPersistenceService {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupPersistenceServiceImpl.class);

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
            log.info("Getting groups");
            List<Group> groupModels = GroupLocalServiceUtil.getUserGroups(buddy.getBuddyId());

            log.info("GROUPS: " + groupModels);


            for (Group groupModel : groupModels) {
                log.info("HEY GROUP: " + groupModel);

                List<User> userModels = UserLocalServiceUtil.getGroupUsers(groupModel.getGroupId());

                for (User userModel : userModels) {
                    log.info(userModel.getFullName());
                }
            }

        } catch (PortalException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }


        GroupCollectionDetails det = new GroupCollectionDetails();
        det.setGroups(new LinkedList<GroupDetails>());
        det.setLastModified(new Date());

        return GetGroupsResponseEvent.getGroupsSuccess(new GroupCollectionDetails());
    }
}
