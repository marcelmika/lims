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

package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.lims.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.ConversationType;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 8:08 PM
 */
public class ConversationJabberServiceImpl implements ConversationJabberService {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationJabberServiceImpl.class);

    // Dependencies
    private UserSessionStore userSessionStore;

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public ConversationJabberServiceImpl(final UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }


    // -------------------------------------------------------------------------------------------
    // Conversation Jabber Service
    // -------------------------------------------------------------------------------------------

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {
        // Check preconditions
        if (event.getConversation() == null) {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        // Get buddy from details
        Buddy creator = Buddy.fromBuddyDetails(event.getCreator());

        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(creator.getBuddyId());
        // No session
        if (userSession == null) {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_NO_SESSION
            );
        }

        // Decide where to go based on the conversation type
        ConversationType conversationType = ConversationType.fromConversationTypeDetails(
                event.getConversation().getConversationType()
        );

        // Single user conversation
        if (conversationType == ConversationType.SINGLE_USER) {

            // There is no need to create a conversation if we are dealing with the single user chat.
            // Each time the message is set a new chat object is created. Thus we don't need to
            // explicitly trigger any create conversation action. Consequently, return success.
            return CreateConversationResponseEvent.createConversationSuccess(event.getConversation());
        }
        // Multi user conversation
        else if (conversationType == ConversationType.MULTI_USER) {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_NOT_IMPLEMENTED
            );
        }
        // Unrecognized type
        else {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE
            );
        }
    }

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {
        // Check preconditions
        if (event.getBuddyDetails() == null ||
                event.getMessageDetails() == null ||
                event.getConversationDetails() == null ||
                event.getConversationDetails().getBuddy() == null) {
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        Message message = Message.fromMessageDetails(event.getMessageDetails());

        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_NO_SESSION
            );
        }

        // Decide where to go based on the conversation type
        ConversationType conversationType = ConversationType.fromConversationTypeDetails(
                event.getConversationDetails().getConversationType()
        );

        // Single user conversation
        if (conversationType == ConversationType.SINGLE_USER) {
            // Map from conversation details
            SingleUserConversation conversation = SingleUserConversation.fromConversationDetails(
                    event.getConversationDetails()
            );

            // Send
            return sendSingleUserMessage(conversation, message, userSession);
        }
        // Multi user conversation
        else if (conversationType == ConversationType.MULTI_USER) {
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_NOT_IMPLEMENTED
            );
        }
        // Unrecognized type
        else {
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE
            );
        }
    }


    // -------------------------------------------------------------------------------------------
    // Private Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Sends message to a single user conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     * @param session      UserSession
     * @return SendMessageResponseEvent
     */
    private SendMessageResponseEvent sendSingleUserMessage(SingleUserConversation conversation,
                                                           Message message,
                                                           UserSession session) {
        // Send message via single user conversation manager taken from user session
        SingleUserConversationManager manager = session.getSingleUserConversationManager();

        try {
            manager.sendMessage(conversation, message);
        } catch (JabberException exception) {
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_JABBER, exception);
        }

        return SendMessageResponseEvent.sendMessageSuccess(message.toMessageDetails());
    }


}
