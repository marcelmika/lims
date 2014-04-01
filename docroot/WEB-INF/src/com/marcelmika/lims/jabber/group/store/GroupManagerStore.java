package com.marcelmika.lims.jabber.group.store;

import com.marcelmika.lims.jabber.group.manager.GroupManager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:14 PM
 */
public interface GroupManagerStore {

    /**
     * Returns group manager based on its id
     *
     * @param id of the group manager
     * @return GroupManager
     */
    public GroupManager getConnectionManager(Long id);

    /**
     * Removes group manager from the group store
     *
     * @param id of the group manager
     */
    public void removeGroupManager(Long id);

    /**
     * Adds group manager to the group store
     *
     * @param groupManager GroupManager
     */
    public void addGroupManager(GroupManager groupManager);

    /**
     * Returns true if the group store contains group manager
     *
     * @param id of the group manager
     * @return true if contains group manager
     */
    public boolean containsGroupManager(Long id);


}
