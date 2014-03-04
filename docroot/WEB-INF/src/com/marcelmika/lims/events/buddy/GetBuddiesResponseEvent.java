package com.marcelmika.lims.events.buddy;

import com.marcelmika.lims.events.ResponseEvent;
import com.marcelmika.lims.events.details.BuddyDetails;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/4/14
 * Time: 9:19 PM
 */
public class GetBuddiesResponseEvent extends ResponseEvent {

    private List<BuddyDetails> details;

    /**
     * Factory method which creates new failure response object
     *
     * @param result  textual description of the failure
     * @param details related to the event
     * @return GetBuddiesResponseEvent
     */
    public static GetBuddiesResponseEvent getBuddiesSuccess(String result, List<BuddyDetails> details) {
        GetBuddiesResponseEvent event = new GetBuddiesResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    /**
     * Factory method which creates new success response object
     *
     * @param result  textual description of the success
     * @param details related to the event
     * @return GetBuddiesResponseEvent
     */
    public static GetBuddiesResponseEvent getBuddiesFailure(String result, List<BuddyDetails> details) {
        GetBuddiesResponseEvent event = new GetBuddiesResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public List<BuddyDetails> getBuddies() {
        return details;
    }

}
