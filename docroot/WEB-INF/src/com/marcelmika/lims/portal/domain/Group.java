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

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.GroupDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:37 PM
 */
public class Group {


    public static final String KEY_BUDDIES = "buddies";

    private String name;
    private List<Buddy> buddies = new ArrayList<Buddy>();

    /**
     * Create new group and maps data from group details
     *
     * @param details GroupDetails
     * @return Group
     */
    public static Group fromGroupDetails(GroupDetails details) {
        // Create new group
        Group group = new Group();
        // Map data to group details
        group.name = details.getName();

        // Relations
        if (details.getBuddies() != null) {
            group.buddies = Buddy.fromBuddyDetailsList(details.getBuddies());
        }

        return group;
    }

    /**
     * Create a list of groups from a list of group details
     *
     * @param groupDetails list of group details
     * @return list of groups
     */
    public static List<Group> fromGroupDetails(List<GroupDetails> groupDetails) {
        List<Group> groups = new ArrayList<Group>();

        // Map all group details to group
        for(GroupDetails details : groupDetails) {
            groups.add(Group.fromGroupDetails(details));
        }

        return groups;
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
            for(Buddy buddy : buddies) {
                details.addBuddyDetails(buddy.toBuddyDetails());
            }
        }

        return details;
    }


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
        return name;
    }


}
