package com.marcelmika.lims.jabber.group;

import com.marcelmika.lims.jabber.domain.Group;
import org.jivesoftware.smack.Roster;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:09 PM
 */
public interface GroupManager {

    /**
     * Returns group manager ID
     *
     * @return String
     */
    public String getId();

    /**
     * Manage groups from roster
     *
     * @param roster Roster
     */
    public void setRoster(Roster roster);

    /**
     * Get buddy's groups.
     *
     * @return Buddy's groups.
     */
    public List<Group> getGroups();

}
