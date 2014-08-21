/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.group.manager;

import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Group;
import com.marcelmika.lims.jabber.domain.GroupCollection;
import com.marcelmika.lims.jabber.domain.Presence;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;

import java.util.*;

/**
 * Group manager is responsible for the synchronization of groups and their entries. It keeps
 * fresh copy of Group Collection related to the user.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:13 PM
 */
public class GroupManagerImpl implements GroupManager, RosterListener {

    // Collection of groups related to the manager
    private GroupCollection groupCollection = new GroupCollection();
    // Represents a user's roster, which is the collection of users a person
    // receives presence updates for.
    private Roster roster;
    // Flag that describes if the groups were modified. Default is true because the groups are always
    // modified at the beginning
    private boolean wasModified = true;

    // -------------------------------------------------------------------------------------------
    // Override: GroupManager
    // -------------------------------------------------------------------------------------------

    /**
     * Sets roster to group manager.
     *
     * @param roster Roster
     */
    @Override
    public void setRoster(Roster roster) {
        this.roster = roster;
        // Set Roster's listener so we will be notified if
        // anything happens with buddies in groups
        roster.addRosterListener(this);
    }


    /**
     * Get buddy's collection of groups.
     *
     * @return Buddy's collection of groups.
     */
    @Override
    public GroupCollection getGroupCollection() {
        // Map groups only if they were somehow modified
        if (wasModified) {
            mapGroupsFromRoster();
        }

        return groupCollection;
    }

    // -------------------------------------------------------------------------------------------
    // Override: RosterListener
    // -------------------------------------------------------------------------------------------

    @Override
    public void entriesAdded(Collection<String> strings) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }
    }

    @Override
    public void entriesUpdated(Collection<String> strings) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }
    }

    @Override
    public void entriesDeleted(Collection<String> strings) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }
    }

    /**
     * Notify all listeners about changes.
     * Called when the presence of a roster entry is changed.
     * Care should be taken when using the presence data
     * delivered as part of this event. Specifically, when
     * a user account is online with multiple resources,
     * the UI should account for that. For example, say a user
     * is online with their desktop computer and mobile phone.
     * If the user logs out of the IM client on their mobile phone,
     * the user should not be shown in the roster (contact list)
     * as offline since they're still available as another resource.
     * To get the current "best presence" for a user after the presence update,
     * query the roster:
     * String user = presence.getFrom();
     * Presence bestPresence = roster.getPresence(user);
     */
    @Override
    public void presenceChanged(org.jivesoftware.smack.packet.Presence presence) {
        // Set was modified group to true. Thanks to that the groups will be mapped again whenever the
        // getGroups() method is called
        synchronized (this) {
            wasModified = true;
        }
    }

    // -------------------------------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------------------------------

    /**
     * Whenever called the method takes roster and maps its groups and entities to our groups and entities
     */
    private void mapGroupsFromRoster() {
        // Create temporary group list
        List<Group> groups = new ArrayList<Group>();
        // Go over all groups in roster
        for (RosterGroup rosterGroup : roster.getGroups()) {
            // Create new Group
            Group group = new Group();
            group.setName(rosterGroup.getName());

            // Add buddies to Group
            for (RosterEntry entry : rosterGroup.getEntries()) {
                // Map buddy
                Buddy buddy = Buddy.fromRosterEntry(entry);
                // Map presence
                Presence presence = Presence.fromSmackPresence(roster.getPresence(entry.getUser()));
                buddy.setPresence(presence);
                // Add buddy to the group
                group.addBuddy(buddy);
            }
            // Add Group to the collection
            groups.add(group);
        }
        // Clear global groups
        groupCollection.addGroups(groups);

        // Return modify flag
        wasModified = false;
    }
}
