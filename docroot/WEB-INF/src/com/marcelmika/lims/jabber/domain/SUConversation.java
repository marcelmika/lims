package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.jabber.JabberUtil;
import com.marcelmika.lims.model.json.JSONable;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.marcelmika.lims.model.Buddy;
import com.marcelmika.lims.service.BuddyLocalServiceUtil;
import org.jivesoftware.smack.Chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
// @todo: Not implemented in v0.2
public class SUConversation implements Conversation, JSONable {

    private Buddy owner;
    private Chat chat;
    private LinkedList<MessageDeprecated> messages = new LinkedList<MessageDeprecated>();
    private List<Buddy> participants = new ArrayList<Buddy>();
    private String conversationId;
    private int lastMessageSent = 0;

    public SUConversation(Buddy owner, Chat chat) {
        this.owner = owner;
        this.chat = chat;
        // Participant
        attachParticipant();
    }

    private void attachParticipant() {
        // Find participant in local db based on the screen name
        String participantsScreenName = JabberUtil.getScreenName(chat.getParticipant());
        Buddy participant = BuddyLocalServiceUtil.getBuddyByScreenName(owner.getCompanyId(), participantsScreenName);
        // There is only one participant but multi user conversation has a multiple of them
        // thus the interface accepts a list, not just a single participant
        participants.add(participant);
        // Conversation id is based on the participant's screen name
        conversationId = participantsScreenName;
    }

    public Chat getChat() {
        return this.chat;
    }

    @Override
    public List<Buddy> getParticipants() {
        return participants;
    }

    @Override
    public void addParticipant(Buddy participant) {
//        System.out.println("Connot add another participant to the single user conversaion");
    }

    @Override
    public List<MessageDeprecated> getMessages() {
        return messages;
    }

    @Override
    public Buddy getOwner() {
        return owner;
    }

    @Override
    public String getConversationId() {
        return conversationId;
    }

    //@todo: This should probably be in a separate layer to avoid model specific call
    private com.marcelmika.lims.model.Conversation getConversationModel() {
//        return com.marcelmika.lims.service.ConversationLocalServiceUtil.getConversation(owner.getUserId(), conversationId);
        return null;
    }

    @Override
    public String getConversationName() {
        return null;
    }

    @Override
    public String getConversationType() {
        return getConversationModel().getConversationType();
    }

    @Override
    public String getConversationVisibility() {
        return null;
    }

    @Override
    public void restart() {
        messages.clear();
        participants.clear();
        attachParticipant();
    }

    @Override
    public MessageDeprecated getLastMessage() {
        return messages.peekLast();
    }

    @Override
    public int getLastMessageSent() {
        return lastMessageSent;
    }

    @Override
    public void setLastMessageSent(int lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }

    @Override
    public int getIndexOfLastMessage() {
        return messages.indexOf(messages.peekLast()) + 1;
    }

    @Override
    public int getUnreadMessages() {
        //@todo: implement
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JSONObject toJSON() {
        // Buddies
        JSONArray jsonBuddies = JSONFactoryUtil.createJSONArray();
        for (Buddy buddy : getParticipants()) {
            jsonBuddies.put(buddy.toJSON());
        }

        // Conversation properites
        JSONObject jsonConversation = JSONFactoryUtil.createJSONObject();
        jsonConversation.put("buddies", jsonBuddies);
        jsonConversation.put("title", conversationId);
        jsonConversation.put("roomJID", conversationId);
        jsonConversation.put("roomName", conversationId);
        jsonConversation.put("roomType", "public");

        // Last message
        MessageDeprecated lastMessage = getLastMessage();
        if (lastMessage != null) {
            jsonConversation.put("lastMessage", lastMessage.toJSON());
        }

        return jsonConversation;
    }

    @Override
    public JSONObject toFullJSON() {
        JSONObject jsonConversation = toJSON();
        JSONArray jsonMessages = JSONFactoryUtil.createJSONArray();

        // Get only messages that havn't been sent yet
        List<MessageDeprecated> subList = messages.subList(lastMessageSent, getIndexOfLastMessage());

        // Iterate all messages in conversation
        for (MessageDeprecated message : subList) {
            JSONObject jsonMessage = message.toJSON();
            // Put to messages
            jsonMessages.put(jsonMessage);
        }

        // Put to conversation
        jsonConversation.put("unreadMessages", getUnreadMessages());
        jsonConversation.put("messages", jsonMessages);

        return jsonConversation;
    }
}
