package com.marcelmika.lims.jabber.domain;

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


    public List<Buddy> getBuddies() {
        return buddies;
    }

    public void setBuddies(List<Buddy> buddies) {
        this.buddies = buddies;
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
