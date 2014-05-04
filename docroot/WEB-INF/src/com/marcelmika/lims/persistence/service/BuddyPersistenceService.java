package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:55 PM
 */
public interface BuddyPersistenceService {

    /**
     * Completely removes buddy from Persistence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event);


    /**
     * Reads buddy's presence
     *
     * @param event Request event
     * @return Response event
     */
    public ReadPresenceBuddyResponseEvent readPresence(ReadPresenceBuddyRequestEvent event);

    /**
     * Change buddy's presence
     *
     * @param event Request event
     * @return Response event
     */
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event);
}
