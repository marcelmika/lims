
package com.marcelmika.lims.jabber;

import com.marcelmika.lims.conversation.Conversation;
import com.marcelmika.lims.model.Buddy;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
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
     * Performs login action
     *
     * @param userId   long
     * @param username String
     * @param password String
     */
    public static void login(long userId, String username, String password) {
        getJabber().login(userId, username, password);
    }

    /**
     * Performs logout action
     *
     * @param userId long
     */
    public static void logout(long userId) {
        getJabber().logout(userId);
    }

    /**
     * @done
     */
    public static void sendMessage(long userId, Conversation conversation, String message) throws Exception {
        getJabber().sendMessage(userId, conversation, message);
    }

    /**
     * @done
     */
    public static void changeStatus(long userId, String status) throws Exception {
        getJabber().changeStatus(userId, status);
    }

    /**
     * @done
     */
    public static Conversation createConversation(long userId, List<Buddy> participants, String message) throws Exception {
        Conversation conversation;
        // Single user conversation
        if (participants.size() == 1) {
            // @todo: This is not going to be in v0.2 version, so just do the multi user anyway
            conversation = getJabber().createMUConversation(userId, participants, message);
            // conversation = getJabber().createSUConversation(userId, participants, message);
        }
        // Multi user conversation
        else if (participants.size() > 1) {
            conversation = getJabber().createMUConversation(userId, participants, message);
        } else {
            throw new Exception("Unknown number of participants");
        }

        return conversation;
    }

    /**
     * @done
     */
    public static void addParticipants(long userId, Conversation conversation, List<Buddy> participants) throws Exception {
        getJabber().addParticipants(userId, conversation, participants);
    }

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
        getJabber().leaveConversation(userId, conversationId);
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