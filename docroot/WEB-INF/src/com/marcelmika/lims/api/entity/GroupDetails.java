package com.marcelmika.lims.api.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:11 PM
 */
public class GroupDetails {

    private String name;
    private List<BuddyDetails> buddies = new ArrayList<BuddyDetails>();

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }

    public void addBuddyDetails(BuddyDetails buddy) {
        buddies.add(buddy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
