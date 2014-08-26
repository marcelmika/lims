
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

package com.marcelmika.lims.jabber.listener;

import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.jabber.conversation.ConversationContainer;
import com.marcelmika.lims.jabber.conversation.ConversationStore;
import com.marcelmika.lims.jabber.JabberUtil;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
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
//                Buddy owner = BuddyLocalServiceUtil.getBuddyByUserId(this.userId);
//                conversation = new SUConversation(owner, chat);
                // Attach Message listener
                JabberMessageListener messageListener = new JabberMessageListener(conversation);
                chat.addMessageListener(messageListener);
                // Add it to user's container. The id of the conversation will be the participants screen name
                conversationContainer.addConversation(participant, conversation);
            }
        }
    }
}