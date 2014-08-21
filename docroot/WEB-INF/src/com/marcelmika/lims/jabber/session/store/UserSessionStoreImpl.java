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

package com.marcelmika.lims.jabber.session.store;

import com.marcelmika.lims.jabber.session.UserSession;

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
