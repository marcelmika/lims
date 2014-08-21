

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

package com.marcelmika.lims.jabber;

import com.marcelmika.lims.jabber.domain.Conversation;
import org.jivesoftware.smack.Connection;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 *
 */
public interface Jabber {

    // Message related stuff
    public void sendMessage(long userId, Conversation conversation, String message,  Connection connection) throws Exception;

    // User related stuff
    public void changeStatus(long userId, String status, Connection connection) throws Exception;

    // Conversation related stuff
//    public Conversation createMUConversation(long userId, List<Buddy> participants, String message, Connection connection) throws Exception;

    // @todo: Not implemented in v0.2
//    public Conversation createSUConversation(long userId, List<Buddy> participants, String message, Connection connection) throws Exception;

    /**
     * @deprecated  This will be moved to conversation store
     */
    public Conversation getConversation(long userId, String conversationId) throws Exception;

    /**
     * @deprecated  This will be moved to conversation store
     */
    public List<Conversation> getAllConversations(long userId) throws Exception;

//    public void addParticipants(long userId, Conversation conversation, List<Buddy> participants, Connection connection) throws Exception;

    public void leaveConversation(long userId, String conversationId, Connection connection) throws Exception;

    public void restartConversations(long userId) throws Exception;

    // Utils
    public String getResource(String jabberId);

    public String getScreenName(String jabberId);

    public String getFullRoomId(String roomJID);
}