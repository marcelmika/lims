package com.marcelmika.lims.persistence.group;

import com.marcelmika.lims.persistence.domain.GroupCollection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 9:44 AM
 */
public interface GroupManager {

    /**
     * Returns Group Collection of all groups related to the user
     *
     * @param userId Long
     * @param start  of the list
     * @param end    of the list
     * @return GroupCollection of groups related to the user
     */
    public GroupCollection getGroups(Long userId, int start, int end) throws Exception;

}
