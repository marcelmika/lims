

package com.marcelmika.lims.jabber;

import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.model.Buddy;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public interface Jabber {

    // Message related stuff
    public void sendMessage(long userId, Conversation conversation, String message) throws Exception;

    // User related stuff
    public void changeStatus(long userId, String status) throws Exception;

    // Conversation related stuff
    public Conversation createMUConversation(long userId, List<Buddy> participants, String message) throws Exception;

    // @todo: Not implemented in v0.2
    public Conversation createSUConversation(long userId, List<Buddy> participants, String message) throws Exception;

    public Conversation getConversation(long userId, String conversationId) throws Exception;

    public List<Conversation> getAllConversations(long userId) throws Exception;

    public void addParticipants(long userId, Conversation conversation, List<Buddy> participants) throws Exception;

    public void leaveConversation(long userId, String conversationId) throws Exception;

    public void restartConversations(long userId) throws Exception;

    // Utils
    public String getResource(String jabberId);

    public String getScreenName(String jabberId);

    public String getFullRoomId(String roomJID);
}