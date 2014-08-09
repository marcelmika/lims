package com.marcelmika.lims.jabber.session.store;

import com.marcelmika.lims.jabber.session.UserSession;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:41 PM
 */
public interface UserSessionStore {

    /**
     * Returns stored user session
     *
     * @param id of the user session
     * @return UserSession
     */
    public UserSession getUserSession(Long id);

    /**
     * Removes user session from the store
     *
     * @param id of the user session
     */
    public void removeUserSession(Long id);

    /**
     * Adds user session to the store
     *
     * @param userSession UserSession
     */
    public void addUserSession(UserSession userSession);

    /**
     * Returns true if the store contains user session
     *
     * @param id of the user session
     * @return true if the store contains user session
     */
    public boolean containsUserSession(Long id);

}
