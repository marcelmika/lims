package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.api.entity.GroupDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:12 PM
 */
public class Group {

    private String name;
    private List<Buddy> buddies = new ArrayList<Buddy>();

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Create new group and maps data from group details
     *
     * @param groupDetails GroupDetails
     * @return Group
     */
    public static Group fromGroupDetails(GroupDetails groupDetails) {
        // Create new group
        Group group = new Group();
        // Map data to group details
        group.name = groupDetails.getName();

        // Relations
        if (groupDetails.getBuddies() != null) {
            group.buddies = Buddy.fromBuddyDetails(groupDetails.getBuddies());
        }

        return group;
    }

    /**
     * Maps group to group details
     *
     * @return GroupDetails
     */
    public GroupDetails toGroupDetails() {
        // Create new group details
        GroupDetails details = new GroupDetails();
        // Map data from group
        details.setName(name);

        // Relations
        if (buddies != null) {
            for (Buddy buddy : buddies) {
                details.addBuddyDetails(buddy.toBuddyDetails());
            }
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public List<Buddy> getBuddies() {
        return buddies;
    }

    public void addBuddy(Buddy buddy) {
        this.buddies.add(buddy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * String representation of the Group.
     *
     * @return A String representation of the Group.
     */
    @Override
    public String toString() {
        return String.format("Group: %s:", name);
    }


}
