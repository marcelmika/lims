package com.marcelmika.lims.events.details;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Container which holds a collection of buddy details
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 7:27 AM
 */
public class BuddyCollectionDetails {

    // Properties
    private Map<Long, BuddyDetails> buddyDetailsMap = new HashMap<Long, BuddyDetails>();


    /**
     * Get the collection of buddy details
     *
     * @return Collection of buddy details
     */
    public Collection<BuddyDetails> getBuddies() {
        return buddyDetailsMap.values();
    }

    /**
     * Add buddy details to the collection
     *
     * @param buddyDetails to be added
     */
    public void addBuddyDetails(BuddyDetails buddyDetails) {
        if (buddyDetails.getBuddyId() == null) {
            throw new RuntimeException("Cannot store buddy details without buddy ID");
        }

        // Add
        this.buddyDetailsMap.put(buddyDetails.getBuddyId(), buddyDetails);
    }

    /**
     * Remove buddy details from the collection.
     *
     * @param buddyDetails to be removed
     */
    public void removeBuddyDetails(BuddyDetails buddyDetails) {
        if (buddyDetails.getBuddyId() == null) {
            throw new RuntimeException("Cannot remove buddy details without buddy ID");
        }

        // Remove
        this.buddyDetailsMap.remove(buddyDetails.getBuddyId());
    }


}
