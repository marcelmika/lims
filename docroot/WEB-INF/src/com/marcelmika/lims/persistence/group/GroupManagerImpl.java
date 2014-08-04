package com.marcelmika.lims.persistence.group;

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.persistence.domain.GroupCollection;

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
    public GroupCollection getGroups(Long userId) {
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


    private GroupCollection getAllGroups(Long userId) {


        return null;
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
