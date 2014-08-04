package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.GroupCollectionDetails;
import com.marcelmika.lims.api.entity.GroupDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:29 PM
 */
public class GroupCollection {

    private Date lastModified = new Date();
    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());

    /**
     * Maps a list of groups to a list of group details
     *
     * @return list of group details
     */
    public GroupCollectionDetails toGroupCollectionDetails() {
        // Create new collection
        GroupCollectionDetails details = new GroupCollectionDetails();
        // Groups
        List<GroupDetails> groups = new ArrayList<GroupDetails>();
        for (Group group : this.groups) {
            groups.add(group.toGroupDetails());
        }
        details.setGroups(groups);

        // Modification date
        details.setLastModified(lastModified);

        return details;
    }

    /**
     * Adds group to groups list
     *
     * @param group Group
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
