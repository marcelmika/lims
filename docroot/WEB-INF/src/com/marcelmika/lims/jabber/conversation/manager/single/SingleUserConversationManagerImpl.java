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

package com.marcelmika.lims.jabber.conversation.manager.single;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import com.marcelmika.lims.jabber.utils.Jid;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:20 PM
 */
public class SingleUserConversationManagerImpl implements SingleUserConversationManager, ChatManagerListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(SingleUserConversationManagerImpl.class);

    // Smack Chat Manager
    private ChatManager chatManager;

    // -------------------------------------------------------------------------------------------
    // Single User Conversation Manager
    // -------------------------------------------------------------------------------------------

    /**
     * Manage conversations from chat manager
     *
     * @param chatManager ChatManager
     */
    @Override
    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;

        // Add listener
        this.chatManager.addChatListener(this);
    }

    /**
     * Sends message to conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     */
    @Override
    public void sendMessage(SingleUserConversation conversation,
                            Message message) throws JabberException {

        // Log
        log.debug(String.format("Conversation %s, to: %s, body %s",
                        conversation.getConversationId(), conversation.getParticipant(), message.getBody())
        );

        // Send a message to the conversation which was already in the system
        try {
            // Receiver
            Buddy receiver = conversation.getParticipant();
            // Receiver's Jid
            String receiverJid = Jid.getJid(receiver.getScreenName());
            // Create a new chat
            Chat chat = chatManager.createChat(receiverJid, null);
            // Send message via new conversation
            chat.sendMessage(message.getBody());
        }
        // Failure
        catch (Exception e) {
            throw new JabberException(e.getMessage(), e);
        }
    }


    // -------------------------------------------------------------------------------------------
    // Chat Manager Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called when the chat was created
     *
     * @param chat           the chat that was created
     * @param createdLocally true if the chat was created by the local user
     */
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        // Only if the remote user created the conversation
        if (!createdLocally) {
            // Create new conversation
            // TODO: Implement
            log.debug("Chat creation not implemented yet");
//            createConversation(chat);
        } else {
            // We don't need to care about it here because the chat was already
            // created in createMessage() method called by the local user.
            log.debug("Chat created locally with id: " + chat.getThreadID());
        }
    }

}
