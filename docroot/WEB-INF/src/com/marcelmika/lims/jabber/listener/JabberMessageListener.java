
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
import com.marcelmika.lims.jabber.domain.MessageDeprecated;
import com.marcelmika.lims.jabber.JabberUtil;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public class JabberMessageListener implements MessageListener {

    private Conversation conversation;

    public JabberMessageListener() {

    }

    public JabberMessageListener(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
        // Message adapter
        MessageDeprecated msg = new MessageDeprecated(message);
        // Extract  jabber name
        msg.setFrom(JabberUtil.getScreenName(msg.getFrom()));
//        msg.setCompanyId(conversation.getOwner().getCompanyId());

        // Add only messages with sender
        if (msg.getFrom() != null) {
            conversation.getMessages().add(msg);
//            System.out.println("[CONVERSATION][" + conversation.getOwner().getScreenName() + "] " + msg.getFrom() + ": " + msg.getBody());

//            System.out.println("[Current Messages stack:] " + conversation.getMessages());
        } else {
//            System.out.println("[CONVERSATION][" + conversation.getOwner().getScreenName() + "] Message with no sender: " + msg.getBody());
        }
    }

}