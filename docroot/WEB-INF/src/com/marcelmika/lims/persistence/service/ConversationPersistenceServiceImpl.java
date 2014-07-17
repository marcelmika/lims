package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.entity.MessageDetails;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Conversation;
import com.marcelmika.lims.persistence.domain.Message;
import com.marcelmika.lims.persistence.domain.Pagination;
import com.marcelmika.lims.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.service.MessageLocalServiceUtil;
import com.marcelmika.lims.service.ParticipantLocalServiceUtil;

import java.util.LinkedList;
import java.util.List;

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

        // TODO: check why we don't use creator
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
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event) {
        // Map to persistence objects
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());
        Pagination pagination = Pagination.fromPaginationDetails(event.getPagination());

        // Read from persistence
        try {
            // Find conversation
            com.marcelmika.lims.model.Conversation conversationModel = ConversationLocalServiceUtil.getConversation(
                    conversation.getConversationId()
            );

            // No such conversation was found
            if (conversationModel == null) {
                return ReadSingleUserConversationResponseEvent.readConversationFailure(
                        ReadSingleUserConversationResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // TODO: Add pagination
            // TODO: Check if participant in event is really in the conversation

            // Get messages from persistence
            List<com.marcelmika.lims.model.Message> messageModels = MessageLocalServiceUtil.readMessages(
                    conversationModel.getCid()
            );

            // Map to persistence
            List<MessageDetails> messageDetails = new LinkedList<MessageDetails>();
            for (com.marcelmika.lims.model.Message messageModel : messageModels) {
                Message message = Message.fromMessageModel(messageModel);
                messageDetails.add(message.toMessageDetails());
            }

            // Call Success
            return ReadSingleUserConversationResponseEvent.readConversationSuccess(messageDetails);

        } catch (Exception exception) {
            // Call Failure
            return ReadSingleUserConversationResponseEvent.readConversationFailure(
                    ReadSingleUserConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
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
            com.marcelmika.lims.model.Message messageModel = MessageLocalServiceUtil.addMessage(
                    conversationModel.getCid(), // Message is related to the conversation
                    buddy.getBuddyId(),         // Message is created by buddy
                    message.getBody(),          // Body of message
                    message.getMessageHash()    // Hash related to each message
            );

            // Notify participants about newly created messages. This will basically update message counters,
            // open conversation to users, etc.
            ParticipantLocalServiceUtil.updateParticipants(conversationModel.getCid());

            // Map message from message model
            Message successMessage = Message.fromMessageModel(messageModel);
            // Don't forget to add the buddy as the creator
            successMessage.setFrom(buddy);

            // Call Success
            return SendMessageResponseEvent.sendMessageSuccess(message.toMessageDetails());

        } catch (Exception exception) {
            // Call Failure
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }
}
