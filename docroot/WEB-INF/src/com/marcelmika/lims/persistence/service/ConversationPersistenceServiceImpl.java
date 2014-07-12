package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.lims.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.lims.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Conversation;
import com.marcelmika.lims.persistence.domain.Message;
import com.marcelmika.lims.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.service.MessageLocalServiceUtil;
import com.marcelmika.lims.service.ParticipantLocalServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:48 AM
 */
public class ConversationPersistenceServiceImpl implements ConversationPersistenceService {


    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {

        Buddy creator = Buddy.fromBuddyDetails(event.getCreator());
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());

        // Save to persistence
        try {
            // Save conversation
            com.marcelmika.lims.model.Conversation conversationModel = ConversationLocalServiceUtil.addConversation(
                    conversation.getConversationId(), conversation.getConversationType().toString()
            );

            // Save Participants
            for (Buddy buddy : conversation.getParticipants()) {
                ParticipantLocalServiceUtil.addParticipant(conversationModel.getCid(), buddy.getBuddyId());
            }

        }
        // Failure
        catch (Exception exception) {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }

        // Success
        return CreateConversationResponseEvent.createConversationSuccess();
    }

    /**
     * Creates message within the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {
        // Map to persistence objects
        Conversation conversation = Conversation.fromConversationDetails(event.getConversationDetails());
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        Message message = Message.fromMessageDetails(event.getMessageDetails());

        // Save to persistence
        try {
            // Find conversation. Since each message is related to the conversation we need to find it first
            com.marcelmika.lims.model.Conversation conversationModel = ConversationLocalServiceUtil.getConversation(
                    conversation.getConversationId()
            );

            // No such conversation was found
            if (conversationModel == null) {
                return SendMessageResponseEvent.sendMessageFailure(
                        SendMessageResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Create new message
            MessageLocalServiceUtil.addMessage(
                    conversationModel.getCid(), // Message is related to the conversation
                    buddy.getBuddyId(),         // Message is created by buddy
                    message.getBody(),          // Body of message
                    message.getMessageHash()    // Hash related to each message
            );

            // Notify participants about newly created messages. This will basically update message counters,
            // open conversation to users, etc.
            ParticipantLocalServiceUtil.updateParticipants(conversationModel.getCid());

        } catch (Exception exception) {
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }

        // Success
        return SendMessageResponseEvent.sendMessageSuccess();
    }
}
