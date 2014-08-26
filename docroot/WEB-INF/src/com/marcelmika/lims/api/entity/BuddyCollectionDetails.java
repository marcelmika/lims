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

package com.marcelmika.lims.api.entity;

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
