package com.marcelmika.lims.jabber.group.manager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 7:55 PM
 */
public class GroupManagerFactory {

    public static GroupManager buildGroupManager(Long id) {
        return new GroupManagerImpl(id);
    }
}
