package com.marcelmika.lims.jabber.group.store;

import com.marcelmika.lims.jabber.group.manager.GroupManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:15 PM
 */
public class GroupManagerStoreImpl implements GroupManagerStore {


    private Map<Long, GroupManager> groupManagerMap = new HashMap<Long, GroupManager>();

    /**
     * Adds group manager to the store
     *
     * @param groupManager GroupManager
     */
    @Override
    public void addGroupManager(GroupManager groupManager) {
        groupManagerMap.put(groupManager.getId(), groupManager);
    }

    /**
     * Returns true if the group store contains group manager
     *
     * @param id of the group manager
     * @return true if contains group manager
     */
    @Override
    public boolean containsGroupManager(Long id) {
        return groupManagerMap.containsKey(id);
    }

    /**
     * Returns group manager based on its id
     *
     * @param id of the group manager
     * @return GroupManager
     */
    @Override
    public GroupManager getConnectionManager(Long id) {
        return groupManagerMap.get(id);
    }

    /**
     * Removes group manager from the store
     *
     * @param id of the group manager
     */
    @Override
    public void removeGroupManager(Long id) {
        groupManagerMap.remove(id);
    }
}
