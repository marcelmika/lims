
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

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public class JabberUtil {

    // Jabber Facade
    private static Jabber jabber;

    /**
     * Return Jabber facade implementation
     *
     * @return Jabber
     */
    protected static Jabber getJabber() {
        return jabber;
    }

    /**
     * Inject Jabber via Dependency Injection
     *
     * @param jabber Jabber
     */
    public void setJabber(Jabber jabber) {
        JabberUtil.jabber = jabber;
    }

    /**
     * @done
     */
    public static void sendMessage(long userId, Conversation conversation, String message) throws Exception {
        getJabber().sendMessage(userId, conversation, message, null);
    }

    /**
     * @done
     */
    public static void changeStatus(long userId, String status) throws Exception {
        getJabber().changeStatus(userId, status, null);
    }

    /**
     * @done
     */
//    public static Conversation createConversation(long userId, List<Buddy> participants, String message) throws Exception {
//        Conversation conversation;
//        // Single user conversation
//        if (participants.size() == 1) {
//            // @todo: This is not going to be in v0.2 version, so just do the multi user anyway
////            conversation = getJabber().createMUConversation(userId, participants, message, null);
//            // conversation = getJabber().createSUConversation(userId, participants, message);
//        }
//        // Multi user conversation
//        else if (participants.size() > 1) {
////            conversation = getJabber().createMUConversation(userId, participants, message, null);
//        } else {
//            throw new Exception("Unknown number of participants");
//        }
//
//        return null;
//    }

    /**
     * @done
     */
//    public static void addParticipants(long userId, Conversation conversation, List<Buddy> participants) throws Exception {
////        getJabber().addParticipants(userId, conversation, participants, null);
//    }

    /**
     * @done
     */
    public static List<Conversation> getConversations(long userId) throws Exception {
        return getJabber().getAllConversations(userId);
    }

    /**
     * @done
     */
    public static void leaveConversation(long userId, String conversationId) throws Exception {
        getJabber().leaveConversation(userId, conversationId, null);
    }

    /**
     * @done
     */
    public static Conversation getConversation(long userId, String conversationId) throws Exception {
        return getJabber().getConversation(userId, conversationId);
    }

    /**
     * @done
     */
    public static void restartConversations(long userId) throws Exception {
        getJabber().restartConversations(userId);
    }

    /**
     * @done
     */
    public static String getResource(String jabberId) {
        return getJabber().getResource(jabberId);
    }

    /**
     * @done
     */
    public static String getScreenName(String jabberId) {
        return getJabber().getScreenName(jabberId);
    }

    /**
     * @done
     */
    public static String getFullRoomId(String roomJID) {
        return getJabber().getFullRoomId(roomJID);
    }

}