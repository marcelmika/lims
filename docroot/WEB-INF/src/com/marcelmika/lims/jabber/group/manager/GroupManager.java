package com.marcelmika.lims.jabber.group.manager;

import com.marcelmika.lims.jabber.domain.GroupCollection;
import org.jivesoftware.smack.Roster;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:09 PM
 */
public interface GroupManager {

    /**
     * Manage groups from roster
     *
     * @param roster Roster
     */
    public void setRoster(Roster roster);

    /**
     * Get buddy's collection of groups.
     *
     * @return Buddy's collection of groups.
     */
    public GroupCollection getGroupCollection();

}
