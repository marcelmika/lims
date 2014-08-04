package com.marcelmika.lims.persistence.group;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Group;
import com.marcelmika.lims.persistence.domain.GroupCollection;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 9:45 AM
 */
public class GroupManagerImpl implements GroupManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupManagerImpl.class);

    /**
     * Returns Group Collection of all groups related to the user
     *
     * @param userId Long
     * @param start  of the list
     * @param end    of the list
     * @return GroupCollection of groups related to the user
     */
    @Override
    public GroupCollection getGroups(Long userId, int start, int end) throws Exception {
        // Get selected list strategy
        Environment.BuddyListStrategy strategy = Environment.getBuddyListStrategy();
        // Get the info if default user should be ignored
        boolean ignoreDefaultUser = Environment.getBuddyListIgnoreDefaultUser();
        // Some site may be excluded
        String[] excludedSites = Environment.getBuddyListExcludes();


        // All buddies
        if (strategy == BuddyListStrategy.ALL) {
            return getAllGroup(userId, ignoreDefaultUser, start, end);
        }
        // Buddies from sites
        else if (strategy == BuddyListStrategy.SITES) {
            return getSitesGroups(userId, ignoreDefaultUser, excludedSites, start, end);
        }
        // Socialized buddies
        else if (strategy == BuddyListStrategy.SOCIAL) {
            return getSocialGroups(userId);
        }
        // Socialized and buddies from sites
        else {
            return getSitesAndSocialGroups(userId);
        }
    }

    /**
     * Returns group collection which contains all buddies in the system.
     * Users
     *
     * @param userId            which should be excluded from the list
     * @param ignoreDefaultUser boolean set to true if the default user should be excluded
     * @param start             of the list
     * @param end               of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getAllGroup(Long userId,
                                        boolean ignoreDefaultUser,
                                        int start,
                                        int end) throws Exception {

        // Get users from persistence
        List<Object[]> users = SettingsLocalServiceUtil.getAllGroups(userId, ignoreDefaultUser, start, end);

        // Create group which will contain all users
        Group group = new Group();

        // Since we get an Object[] from persistence we need to map it to the persistence Buddy object
        for (Object[] userObject : users) {
            // Deserialize
            Buddy buddy = Buddy.fromPlainObject(userObject, 0);
            // Add to group
            group.addBuddy(buddy);
        }

        // Create group collection which will hold the only group that holds all users
        GroupCollection groupCollection = new GroupCollection();
        // Add group to collection
        groupCollection.addGroup(group);

        return groupCollection;
    }

    private GroupCollection getSitesGroups(Long userId,
                                           boolean ignoreDefaultUser,
                                           String[] excludedSites,
                                           int start,
                                           int end) throws Exception {

        // Get sites groups
        List<Object[]> groupObjects = SettingsLocalServiceUtil.getSitesGroups(
                userId, ignoreDefaultUser, excludedSites, start, end
        );

        // We are about to build a collection of groups that will contain
        // users within the groups. However, we only get "flat" object which contains
        // both group data and user data. Thus we need to create a hash map that will
        // hold each group under the unique key (groupName). This should improve the
        // speed of mapping since we can reuse groups that we already mapped.
        Map<String, Group> groupMap = new HashMap<String, Group>();

        // Build groups and users
        for (Object[] object : groupObjects) {

            // Deserialize group from object, group starts with 0
            Group group = Group.fromPlainObject(object, 0);

            // Check if the group is already cached
            if (groupMap.get(group.getName()) == null) {
                // Cache it
                groupMap.put(group.getName(), group);
            }
            // Take the cached one
            else {
                group = groupMap.get(group.getName());
            }

            // Deserialize buddy from object, buddy start with 1
            Buddy buddy = Buddy.fromPlainObject(object, 1);

            // Add it to group
            group.addBuddy(buddy);
        }

        // Create group collection
        GroupCollection groupCollection = new GroupCollection();
        // Add groups to collection
        for (Group group : groupMap.values()) {
            groupCollection.addGroup(group);
        }

        return groupCollection;
    }

    private GroupCollection getSocialGroups(Long userId) {

        return null;
    }

    private GroupCollection getSitesAndSocialGroups(Long userId) {

        return null;
    }
}
