package com.marcelmika.lims.persistence.group;

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.environment.Environment.BuddyListSocialRelation;
import com.marcelmika.lims.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Group;
import com.marcelmika.lims.persistence.domain.GroupCollection;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.ArrayList;
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
        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        // Some site may be excluded
        String[] excludedSites = Environment.getBuddyListExcludes();
        // Relation types
        BuddyListSocialRelation[] relationTypes = Environment.getBuddyListAllowedSocialRelationTypes();

        // All buddies
        if (strategy == BuddyListStrategy.ALL) {
            return getAllGroup(
                    userId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
            );
        }
        // Buddies from sites
        else if (strategy == BuddyListStrategy.SITES) {
            return getSitesGroups(
                    userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
            );
        }
        // Socialized buddies
        else if (strategy == BuddyListStrategy.SOCIAL) {
            return getSocialGroups(
                    userId, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end
            );
        }
        // Socialized and buddies from sites
        else {
            return getSitesAndSocialGroups(
                    userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, relationTypes, start, end
            );
        }
    }

    /**
     * Returns group collection which contains all buddies in the system.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getAllGroup(Long userId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser,
                                        int start,
                                        int end) throws Exception {

        // Get users from persistence
        List<Object[]> users = SettingsLocalServiceUtil.getAllGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );

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

    /**
     * Returns group collection which contains groups that represents sites where the user participates.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedSites         names of sites (groups) that should be excluded from the group collection
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getSitesGroups(Long userId,
                                           boolean ignoreDefaultUser,
                                           boolean ignoreDeactivatedUser,
                                           String[] excludedSites,
                                           int start,
                                           int end) throws Exception {

        // Get sites groups
        List<Object[]> groupObjects = SettingsLocalServiceUtil.getSitesGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
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

            // Deserialize buddy from object, buddy starts at 1
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

    /**
     * Returns group collection which contains groups that represent social relations of the user.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param relationTypes         an array of relation type enums
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getSocialGroups(Long userId,
                                            boolean ignoreDefaultUser,
                                            boolean ignoreDeactivatedUser,
                                            BuddyListSocialRelation[] relationTypes,
                                            int start,
                                            int end) throws Exception {

        // Get int codes from relation types since the persistence only consumes an int array.
        int[] relationCodes = new int[relationTypes.length];
        for (int i = 0; i < relationTypes.length; i++) {
            relationCodes[i] = relationTypes[i].getCode();
        }

        // Get social groups
        List<Object[]> groupObjects = SettingsLocalServiceUtil.getSocialGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, relationCodes, start, end
        );

        // We are about to build a collection of groups that will contain
        // users within the groups. However, we only get "flat" object which contains
        // both group data and user data. Thus we need to create a hash map that will
        // hold each group under the unique key (groupName). This should improved the
        // speed of mapping since we can reuse groups that we already mapped.
        Map<String, Group> groupMap = new HashMap<String, Group>();

        // Build groups and users
        for (Object[] object : groupObjects) {
            // Relation type is first element
            Integer relationCode = (Integer) object[0];
            // Map to enum
            BuddyListSocialRelation relationType = BuddyListSocialRelation.fromCode(relationCode);
            // Get group name from relation type description
            String groupName = relationType.getDescription();

            // Get cached group
            Group group = groupMap.get(groupName);

            // If no group was created build a new one
            if (groupMap.get(groupName) == null) {
                group = new Group();
                group.setName(groupName);
                groupMap.put(groupName, group);
            }

            // Deserialize buddy from object, buddy start at 1
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

    /**
     * Returns group collection which contains groups that represent social relations of the user.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedSites         names of sites (groups) that should be excluded from the group collection
     * @param relationTypes         an array of relation type enums
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getSitesAndSocialGroups(Long userId,
                                                    boolean ignoreDefaultUser,
                                                    boolean ignoreDeactivatedUser,
                                                    String[] excludedSites,
                                                    BuddyListSocialRelation[] relationTypes,
                                                    int start,
                                                    int end) throws Exception {
        // Get site groups
        GroupCollection sitesGroupCollection = getSitesGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
        );
        // Get social groups
        GroupCollection socialGroupCollection = getSocialGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end
        );

        // Merge site and social groups
        List<Group> mergedGroups = new ArrayList<Group>();
        mergedGroups.addAll(sitesGroupCollection.getGroups());
        mergedGroups.addAll(socialGroupCollection.getGroups());

        // Merge
        GroupCollection groupCollection = new GroupCollection();
        groupCollection.setGroups(mergedGroups);

        return groupCollection;
    }
}
