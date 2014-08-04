package com.marcelmika.lims.persistence.group;

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Group;
import com.marcelmika.lims.persistence.domain.GroupCollection;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 9:45 AM
 */
public class GroupManagerImpl implements GroupManager {


    /**
     * Returns Group Collection of all groups related to the user
     *
     * @param userId Long
     * @return GroupCollection of groups related to the user
     */
    @Override
    public GroupCollection getGroups(Long userId) throws Exception {
        // Get selected list strategy
        Environment.BuddyListStrategy strategy = Environment.getBuddyListStrategy();

        // All buddies
        if (strategy == Environment.BuddyListStrategy.ALL) {
            return getAllGroups(userId);
        }
        // Buddies from sites
        else if (strategy == Environment.BuddyListStrategy.SITES) {
            return getSitesGroups(userId);
        }
        // Socialized buddies
        else if (strategy == Environment.BuddyListStrategy.SOCIAL) {
            return getSocialGroups(userId);
        }
        // Socialized and buddies from sites
        else {
            return getSitesAndSocialGroups(userId);
        }
    }


    private GroupCollection getAllGroups(Long userId) throws Exception {
        List<Object[]> users = SettingsLocalServiceUtil.getAllGroups(userId, 0, 100);

        Group group = new Group();
        // TODO: i18n
        group.setName("All contacts");

        for (Object[] userObject : users) {
            Buddy buddy = Buddy.fromPlainObject(userObject);
            group.addBuddy(buddy);
        }

        List<Group> groups = new LinkedList<Group>();
        groups.add(group);

        GroupCollection groupCollection = new GroupCollection();

        groupCollection.setGroups(groups);


        return groupCollection;
    }

    private GroupCollection getSitesGroups(Long userId) {

        return null;
    }

    private GroupCollection getSocialGroups(Long userId) {

        return null;
    }

    private GroupCollection getSitesAndSocialGroups(Long userId) {

        return null;
    }


}
