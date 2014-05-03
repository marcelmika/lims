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

    GroupCollectionDetails groupCollection;

    public static GetGroupsResponseEvent getGroupsSuccess(GroupCollectionDetails groupCollectionDetails) {
        GetGroupsResponseEvent event = new GetGroupsResponseEvent();
        event.groupCollection = groupCollectionDetails;
        event.success = true;

        return event;
    }

    public static GetGroupsResponseEvent getGroupsFailure(Throwable exception) {
        GetGroupsResponseEvent event = new GetGroupsResponseEvent();
        event.result = exception.getMessage();
        event.success = false;
        event.exception = exception;

        return event;
    }

    public GroupCollectionDetails getGroupCollection() {
        return groupCollection;
    }
}
