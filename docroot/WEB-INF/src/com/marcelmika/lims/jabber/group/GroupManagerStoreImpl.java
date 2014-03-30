package com.marcelmika.lims.jabber.group;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:15 PM
 */
public class GroupManagerStoreImpl implements GroupManagerStore {


    private Map<String, GroupManager> groupManagerMap = new HashMap<String, GroupManager>();

    /**
     * Adds group manager to the store
     *
     * @param groupManager GroupManager
     */
    @Override
    public void addGroupManager(GroupManager groupManager) {
//        groupManagerMap.put(groupManager.get)

    }

    /**
     * Removes group manager from the store
     *
     * @param groupManager GroupManager
     */
    @Override
    public void removeGroupManager(GroupManager groupManager) {

    }
}
