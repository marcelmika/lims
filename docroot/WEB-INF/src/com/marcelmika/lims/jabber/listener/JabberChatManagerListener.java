
package com.marcelmika.lims.jabber.listener;

import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.jabber.conversation.ConversationContainer;
import com.marcelmika.lims.jabber.conversation.ConversationStore;
import com.marcelmika.lims.jabber.domain.SUConversation;
import com.marcelmika.lims.jabber.JabberUtil;
import com.marcelmika.lims.model.Buddy;
import com.marcelmika.lims.service.BuddyLocalServiceUtil;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberChatManagerListener implements ChatManagerListener {

    private long companyId;
    private long userId;

    public JabberChatManagerListener(long companyId, long userId) {
        this.companyId = companyId;
        this.userId = userId;
    }

    public void chatCreated(Chat chat, boolean createdLocally) {
        if (!createdLocally) {
            // Participant is the user we are chatting with
            String participant = JabberUtil.getScreenName(chat.getParticipant());
            // Get conversation container which is related to the user
            ConversationContainer conversationContainer = ConversationStore.getInstance().getConversationContainer(this.userId);
            // Conversation id is based on the participant username. Thus try to find a conversation with this id
            Conversation conversation = conversationContainer.getConversation(participant);
            // There is no conversation like that yet
            if (conversation == null) {
                // Create new conversation
                Buddy owner = BuddyLocalServiceUtil.getBuddyByUserId(this.userId);
                conversation = new SUConversation(owner, chat);
                // Attach Message listener
                JabberMessageListener messageListener = new JabberMessageListener(conversation);
                chat.addMessageListener(messageListener);
                // Add it to user's container. The id of the conversation will be the participants screen name
                conversationContainer.addConversation(participant, conversation);
            }
        }
    }
}