

package com.marcelmika.lims.jabber;

import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.model.Buddy;
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
    public Conversation createMUConversation(long userId, List<Buddy> participants, String message, Connection connection) throws Exception;

    // @todo: Not implemented in v0.2
    public Conversation createSUConversation(long userId, List<Buddy> participants, String message, Connection connection) throws Exception;

    /**
     * @deprecated  This will be moved to conversation store
     */
    public Conversation getConversation(long userId, String conversationId) throws Exception;

    /**
     * @deprecated  This will be moved to conversation store
     */
    public List<Conversation> getAllConversations(long userId) throws Exception;

    public void addParticipants(long userId, Conversation conversation, List<Buddy> participants, Connection connection) throws Exception;

    public void leaveConversation(long userId, String conversationId, Connection connection) throws Exception;

    public void restartConversations(long userId) throws Exception;

    // Utils
    public String getResource(String jabberId);

    public String getScreenName(String jabberId);

    public String getFullRoomId(String roomJID);
}