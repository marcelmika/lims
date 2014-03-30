package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.events.buddy.*;
import com.marcelmika.lims.events.settings.*;

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
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    public UpdateStatusBuddyResponseEvent changeStatus(UpdateStatusBuddyRequestEvent event);
}
