package com.marcelmika.lims.jabber.domain;

import com.liferay.portal.kernel.json.JSONObject;
import com.marcelmika.lims.jabber.domain.Message;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public interface Conversation {

    // Buddies
    public com.marcelmika.lims.model.Buddy getOwner();

    public List<com.marcelmika.lims.model.Buddy> getParticipants();

    public void addParticipant(com.marcelmika.lims.model.Buddy participant);

    // Metadata
    public String getConversationId();

    public String getConversationName();

    public String getConversationType();

    public String getConversationVisibility();

    // Restart
    public void restart();

    // Message related stuff
    public List<Message> getMessages();

    public Message getLastMessage();

    public int getLastMessageSent();

    public void setLastMessageSent(int lastMessageSent);

    public int getIndexOfLastMessage();

    public int getUnreadMessages();

    // @todo: Move to separate json mapper
    public JSONObject toJSON();

    public JSONObject toFullJSON();
}
