package com.marcelmika.lims.events.group;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.GroupDetails;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:12 PM
 */
public class GetGroupsResponseEvent extends ResponseEvent {

    List<GroupDetails> groups;

    public static GetGroupsResponseEvent getGroupsSuccess(List<GroupDetails> groups) {
        GetGroupsResponseEvent event = new GetGroupsResponseEvent();
        event.groups = groups;
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

    public List<GroupDetails> getGroups() {
        return groups;
    }
}
