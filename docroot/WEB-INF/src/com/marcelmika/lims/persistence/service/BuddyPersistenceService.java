package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.events.session.BuddyRemoveRequestEvent;
import com.marcelmika.lims.events.session.BuddyRemoveResponseEvent;

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
    public BuddyRemoveResponseEvent removeBuddy(BuddyRemoveRequestEvent event);
}
