package com.marcelmika.lims.api.entity;

import java.util.Date;
import java.util.List;

/**
 * Simple container that holds groups
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:26 PM
 */
public class GroupCollectionDetails {

    private List<GroupDetails> groups;
    private Date lastModified;


    public List<GroupDetails> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDetails> groups) {
        this.groups = groups;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
