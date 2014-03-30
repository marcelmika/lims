package com.marcelmika.lims.jabber.group;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:14 PM
 */
public interface GroupManagerStore {

    /**
     * Adds group manager to the store
     *
     * @param groupManager GroupManager
     */
    public void addGroupManager(GroupManager groupManager);


    /**
     * Removes group manager from the store
     *
     * @param groupManager GroupManager
     */
    public void removeGroupManager(GroupManager groupManager);


}
