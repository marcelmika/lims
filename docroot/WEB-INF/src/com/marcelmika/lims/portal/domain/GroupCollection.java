package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.GroupCollectionDetails;
import com.marcelmika.lims.api.entity.GroupDetails;

import java.util.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 6:10 PM
 */
public class GroupCollection {

    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());
    private Date lastModified;
    private int etag;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Create new group and maps data from group details
     *
     * @return GroupCollection
     */
    public static GroupCollection fromGroupCollectionDetails(GroupCollectionDetails details) {
        // Create new group
        GroupCollection groupCollection = new GroupCollection();
        // Map data to group details
        groupCollection.lastModified = details.getLastModified();
        groupCollection.etag = details.getLastModified().hashCode();

        // Relations
        if (details.getGroups() != null) {
            for (GroupDetails groupDetail : details.getGroups()) {
                 groupCollection.groups.add(Group.fromGroupDetails(groupDetail));
            }
        }

        return groupCollection;
    }


    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getEtag() {
        return etag;
    }

    public void setEtag(int etag) {
        this.etag = etag;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
