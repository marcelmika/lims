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

package com.marcelmika.lims.jabber.connection.manager;

import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 4:10 PM
 */
public interface ConnectionManager {

    /**
     * Creates new connection with jabber server
     *
     * @throws JabberException if connection creation fails
     */
    public void createConnection() throws JabberException;

    /**
     * Log user in
     *
     * @param buddy Buddy
     * @throws JabberException if login fails
     */
    public void login(Buddy buddy) throws JabberException;

    /**
     * Logout buddy
     */
    public void logout();

    /**
     * Returns connection of the user
     */
    public Connection getConnection();

    /**
     * Returns buddy's roster
     *
     * @return Roster
     */
    public Roster getRoster();

    /**
     * Returns buddy's chat manager
     *
     * @return ChatManager
     */
    public ChatManager getChatManager();

    /**
     * Set or updates buddy's presence
     *
     * @param presence Presence of the concrete buddy.
     */
    public void setPresence(final Presence presence);

}
