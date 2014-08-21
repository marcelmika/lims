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

package com.marcelmika.lims.api.events.group;

import com.marcelmika.lims.api.entity.GroupCollectionDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:12 PM
 */
public class GetGroupsResponseEvent extends ResponseEvent {

    private Status status;
    private GroupCollectionDetails groupCollection;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE,      // Error with persistence occurred
        ERROR_JABBER,           // Error with jabber occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetGroupsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetGroupsResponseEvent getGroupsSuccess(final GroupCollectionDetails groupCollection) {
        GetGroupsResponseEvent event = new GetGroupsResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.groupCollection = groupCollection;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetGroupsResponseEvent getGroupsFailure(final Status status) {
        GetGroupsResponseEvent event = new GetGroupsResponseEvent();

        event.success = false;
        event.status = status;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status    Status
     * @param exception Exception
     * @return ResponseEvent
     */
    public static GetGroupsResponseEvent getGroupsFailure(final Status status,
                                                          final Throwable exception) {

        GetGroupsResponseEvent event = new GetGroupsResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public GroupCollectionDetails getGroupCollection() {
        return groupCollection;
    }
}
