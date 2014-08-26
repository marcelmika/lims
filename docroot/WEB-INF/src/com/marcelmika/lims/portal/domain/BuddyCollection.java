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

import com.liferay.portal.kernel.poller.PollerRequest;
import com.marcelmika.lims.api.entity.BuddyCollectionDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Container which holds a collection of buddies
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 7:11 AM
 */
public class BuddyCollection {

    // Constants
    private static final String KEY_BUDDIES = "users";

    // Properties
    private Map<Long, Buddy> buddiesMap = new HashMap<Long, Buddy>();


    /**
     * Factory method which creates a collection of buddies from the poller request
     *
     * @param pollerRequest from portal
     * @return Collection<Buddy> of buddies
     */
    public static BuddyCollection fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new conversation
        BuddyCollection buddyCollection = new BuddyCollection();

        // Parameters
        if (parameterMap.containsKey(KEY_BUDDIES)) {
            // Get the buddy string from parameters
            String buddyString = parameterMap.get(KEY_BUDDIES);
            // List of buddies is in a form of buddyIDs separated by comma
            String[] buddyIDs = buddyString.split(",");
            // Add buddies to buddies collection
            for (String buddyId : buddyIDs) {
                Buddy buddy = new Buddy();
                // Set property
                buddy.setBuddyId(Long.parseLong(buddyId));
                // Add it to collection
                buddyCollection.addBuddy(buddy);
            }
        }

        return buddyCollection;
    }

    /**
     * Maps buddy collection to details
     *
     * @return BuddyCollectionDetails
     */
    public BuddyCollectionDetails toBuddyCollectionDetails() {
        BuddyCollectionDetails buddyCollectionDetails = new BuddyCollectionDetails();

        for (Buddy buddy : new ArrayList<Buddy>(buddiesMap.values())) {
            buddyCollectionDetails.addBuddyDetails(buddy.toBuddyDetails());
        }

        return buddyCollectionDetails;
    }


    /**
     * Get the collection of buddies
     *
     * @return Collection of buddies
     */
    public Collection<Buddy> getBuddies() {
        return buddiesMap.values();
    }

    /**
     * Add buddy to the collection
     *
     * @param buddy Buddy to be added
     */
    public void addBuddy(Buddy buddy) {
        if (buddy.getBuddyId() == null) {
            throw new RuntimeException("Cannot store buddy without buddy ID");
        }

        // Add
        this.buddiesMap.put(buddy.getBuddyId(), buddy);
    }

    /**
     * Remove buddy from the collection.
     *
     * @param buddy Buddy to be removed
     */
    public void removeBuddy(Buddy buddy) {
        if (buddy.getBuddyId() == null) {
            throw new RuntimeException("Cannot remove buddy without buddy ID");
        }

        // Remove
        this.buddiesMap.remove(buddy.getBuddyId());
    }
}
