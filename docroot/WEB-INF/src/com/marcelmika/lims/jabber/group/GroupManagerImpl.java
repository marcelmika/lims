package com.marcelmika.lims.jabber.group;

import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Group;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:13 PM
 */
public class GroupManagerImpl implements GroupManager, RosterListener {

    // Group manager id
    private final String id;
    // Represents a user's roster, which is the collection of users a person
    // receives presence updates for.
    private Roster roster;
    // List of groups managed by the manager
    private List<Group> groups;


    /**
     * Constructor
     *
     * @param id id of the group
     */
    public GroupManagerImpl(String id) {
        this.id = id;
    }

    // -------------------------------------------------------------------------------------------
    // Override: GroupManager
    // -------------------------------------------------------------------------------------------

    /**
     * Returns group manager ID
     *
     * @return String
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Manage groups from roster
     *
     * @param roster Roster
     */
    @Override
    public void setRoster(Roster roster) {
        this.roster = roster;
        // Map groups from roster
        mapGroupsFromRoster();
        // Set Roster's listener so we will be notified if
        // anything happens with buddies in groups
        roster.addRosterListener(this);
    }

    /**
     * Get buddy's groups.
     *
     * @return Buddy's groups.
     */
    @Override
    public List<Group> getGroups() {
        return groups;
    }

    // -------------------------------------------------------------------------------------------
    // Override: RosterListener
    // -------------------------------------------------------------------------------------------

    @Override
    public void entriesAdded(Collection<String> strings) {
        // Refresh data
        mapGroupsFromRoster();
    }

    @Override
    public void entriesUpdated(Collection<String> strings) {
        // Refresh data
        mapGroupsFromRoster();
    }

    @Override
    public void entriesDeleted(Collection<String> strings) {
        // Refresh data
        mapGroupsFromRoster();
    }

    @Override
    public void presenceChanged(Presence presence) {
        // Refresh data
        mapGroupsFromRoster();
    }

    // -------------------------------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------------------------------
    private void mapGroupsFromRoster() {
        // A list of "our" domain's groups.
        List<Group> groups = new ArrayList<Group>();

        // Go over all groups in roster
        for (RosterGroup rosterGroup : roster.getGroups()) {
            // Create new Group
            Group group = new Group();
            group.setName(rosterGroup.getName());

            // Add buddies to Group
            for (RosterEntry entry : rosterGroup.getEntries()) {

                Presence presence = roster.getPresence(entry.getUser());
                Buddy buddy = Buddy.fromRosterEntry(entry);
//                buddy.setStatus(new Status(presence));
                group.addBuddy(buddy);
            }

            // Add Group to the collection
            groups.add(group);
        }

        this.groups = groups;
    }


}
