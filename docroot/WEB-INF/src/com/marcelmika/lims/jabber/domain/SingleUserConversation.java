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

package com.marcelmika.lims.jabber.domain;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:07 AM
 */
public class SingleUserConversation implements MessageListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(SingleUserConversation.class);

    private String conversationId;
    private ConversationType conversationType;
    private Buddy buddy;
    private Buddy participant;
    private List<Message> messages = new ArrayList<Message>();


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method creates single user conversation from smack chat
     *
     * @param chat Conversation is created from the Chat.
     * @return SingleUserConversation
     */
    public static SingleUserConversation fromChat(Chat chat) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Map properties
        conversation.conversationId = chat.getParticipant();
        // Map relationships
        conversation.participant = Buddy.fromChat(chat);
        conversation.conversationType = ConversationType.SINGLE_USER;
        // Set conversation as a message listener. Thanks to that it
        // will be able to change inner content
        chat.addMessageListener(conversation);

        return conversation;
    }

    public static SingleUserConversation fromConversationDetails(ConversationDetails details) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Map properties
        conversation.conversationId = details.getConversationId();

        // Relations
        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        if (details.getParticipants() != null) {
            // The size of the participant list for a single user chat cannot be bigger than 2.
            // However, we should fail gracefully here. So we just take the first participant in the list
            // which isn't the owner of conversation and show a log message that should warn us.
            if (details.getParticipants().size() > 2) {
                log.error("The size of participants list for a single user conversation is bigger than 2." +
                        "This shouldn't normally happen.");
            }

            // Get from by listing the participants
            for (BuddyDetails participant : details.getParticipants()) {
                // The creator of the conversation is not the participant
                if (participant.getBuddyId().equals(details.getBuddy().getBuddyId())) {
                    continue;
                }
                // Take first participant
                conversation.participant = Buddy.fromBuddyDetails(participant);
                break;
            }
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetails(details.getMessages());
        }

        return conversation;
    }

    public static SingleUserConversation fromMessage(Message message) {

        Buddy from = message.getFrom();
        Buddy to = message.getTo();

        // Create new conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Todo: order alphabetically
        String conversationId = String.format("%s_%s", from.getScreenName(), to.getScreenName());

        // Properties
        conversation.conversationType = ConversationType.SINGLE_USER;
        conversation.conversationId = conversationId;

        // Relations
        conversation.buddy = from;
        conversation.participant = to;

        return conversation;
    }

    public static List<ConversationDetails> toConversationDetailsList(List<SingleUserConversation> conversations) {
        // Create new list
        List<ConversationDetails> details = new ArrayList<ConversationDetails>();
        // Map
        for (SingleUserConversation conversation : conversations) {
            details.add(conversation.toConversationDetails());
        }

        return details;
    }

    public ConversationDetails toConversationDetails() {
        // Create new details
        ConversationDetails details = new ConversationDetails();
        // Properties
        details.setConversationId(conversationId);

        // Relations
        if (conversationType != null) {
            details.setConversationType(conversationType.toConversationTypeDetails());
        }

        if (messages != null) {
            details.setMessages(Message.toMessageDetailsList(messages));
        }

        if (participant != null) {
            List<BuddyDetails> participants = new ArrayList<BuddyDetails>();
            participants.add(participant.toBuddyDetails());
            details.setParticipants(participants);
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Message Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called whenever the buddy receives message
     *
     * @param smackMessage which was received
     */
    @Override
    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message smackMessage) {
        // Create new message from smack message
        Message message = Message.fromSmackMessage(smackMessage);

        log.debug(String.format("From: %s, Body: %s", message.getFrom().getScreenName(), message.getBody()));

        messages.add(message);
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
    }

    public Buddy getParticipant() {
        return participant;
    }

    public void setParticipant(Buddy participant) {
        this.participant = participant;
    }

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
