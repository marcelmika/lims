package com.marcelmika.lims.jabber.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:42 PM
 */
public class UserSessionStoreImpl implements UserSessionStore {

    // Map of Connection Managers
    private Map<Long, UserSession> connectionManagerMap = new HashMap<Long, UserSession>();


    /**
     * Returns stored user session
     *
     * @param id of the user session
     * @return UserSession
     */
    @Override
    public UserSession getUserSession(Long id) {
        return connectionManagerMap.get(id);
    }

    /**
     * Removes user session from the store
     *
     * @param id of the user session
     */
    @Override
    public void removeUserSession(Long id) {
        connectionManagerMap.remove(id);
    }

    /**
     * Adds user session to the store
     *
     * @param userSession UserSession
     */
    @Override
    public void addUserSession(UserSession userSession) {
        connectionManagerMap.put(userSession.getSessionId(), userSession);
    }

    /**
     * Returns true if the store contains user session
     *
     * @param id of the user session
     * @return true if the store contains user session
     */
    @Override
    public boolean containsUserSession(Long id) {
        return connectionManagerMap.containsKey(id);
    }
}
