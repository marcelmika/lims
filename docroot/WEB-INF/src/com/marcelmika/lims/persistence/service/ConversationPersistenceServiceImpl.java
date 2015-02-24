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

package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Conversation;
import com.marcelmika.lims.persistence.domain.Message;
import com.marcelmika.lims.persistence.generated.NoSuchConversationException;
import com.marcelmika.lims.persistence.generated.NoSuchParticipantException;
import com.marcelmika.lims.persistence.generated.model.Participant;
import com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.MessageLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:48 AM
 */
public class ConversationPersistenceServiceImpl implements ConversationPersistenceService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ConversationPersistenceServiceImpl.class);

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

            // Get the participant
            Buddy participant = conversation.getParticipants().get(0);
            // User cannot create conversation with himself
            if (participant.getBuddyId().equals(creator.getBuddyId())) {
                return CreateConversationResponseEvent.createConversationFailure(
                        CreateConversationResponseEvent.Status.ERROR_COLLISION
                );
            }

            // Save conversation
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.addConversation(
                            conversation.getConversationId(), conversation.getConversationType().toString()
                    );

            // Add participants to response
            List<Buddy> participants = conversation.getParticipants();

            // Save Participants
            for (Buddy buddy : participants) {
                ParticipantLocalServiceUtil.addParticipant(conversationModel.getCid(), buddy.getBuddyId());
            }

            // Creator is also participant
            com.marcelmika.lims.persistence.generated.model.Participant participantModel =
                    ParticipantLocalServiceUtil.addParticipant(conversationModel.getCid(), creator.getBuddyId());
            participants.add(creator);

            // Create updated conversation
            conversation = Conversation.fromConversationModel(conversationModel);
            conversation.setUnreadMessagesCount(participantModel.getUnreadMessagesCount());
            conversation.setBuddy(creator);
            conversation.setParticipants(participants);

            // Call Success
            return CreateConversationResponseEvent.createConversationSuccess(conversation.toConversationDetails());
        }
        // Failure
        catch (Exception exception) {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
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
        Buddy buddy = Buddy.fromBuddyDetails(event.getParticipant());

        // Read from persistence
        try {
            // Find conversation
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(
                            conversation.getConversationId()
                    );

            // No such conversation was found
            if (conversationModel == null) {
                return ReadSingleUserConversationResponseEvent.readConversationFailure(
                        ReadSingleUserConversationResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Get user's participant model for the given conversation
            Participant participant = ParticipantLocalServiceUtil.getParticipant(
                    conversationModel.getCid(), buddy.getBuddyId()
            );

            // User is not in the conversation thus he can't read it
            if (participant == null) {
                return ReadSingleUserConversationResponseEvent.readConversationFailure(
                        ReadSingleUserConversationResponseEvent.Status.ERROR_FORBIDDEN
                );
            }

            conversation = Conversation.fromConversationModel(conversationModel);

            // Get messages from persistence
            List<com.marcelmika.lims.persistence.generated.model.Message> messageModels =
                    MessageLocalServiceUtil.readMessages(
                            conversationModel.getCid(), 0, Environment.getConversationListMaxMessages());

            // Map to persistence
            List<Message> messages = new LinkedList<Message>();
            for (com.marcelmika.lims.persistence.generated.model.Message messageModel : messageModels) {
                messages.add(Message.fromMessageModel(messageModel));
            }
            // Add to conversation
            conversation.setMessages(messages);

            // Add to conversation
            conversation.setUnreadMessagesCount(participant.getUnreadMessagesCount());

            // Call Success
            return ReadSingleUserConversationResponseEvent.readConversationSuccess(
                    conversation.toConversationDetails()
            );

        } catch (Exception exception) {
            // Call Failure
            return ReadSingleUserConversationResponseEvent.readConversationFailure(
                    ReadSingleUserConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Returns a list of participants related to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public GetConversationParticipantsResponseEvent getParticipants(GetConversationParticipantsRequestEvent event) {
        // Get conversation from event
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());

        try {
            // Find conversation based on conversation ID
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(conversation.getConversationId());

            // Find participant related to the conversation
            List<Participant> participants = ParticipantLocalServiceUtil.getConversationParticipants(
                    conversationModel.getCid()
            );

            // Map participants to buddies
            List<Buddy> buddies = new LinkedList<Buddy>();
            for (Participant participant : participants) {

                // Find user in Liferay
                User user = UserLocalServiceUtil.fetchUser(participant.getParticipantId());

                // If the user is no longer in the liferay don't include him
                if (user == null) {
                    continue;
                }

                // Map Liferay user to buddy
                Buddy buddy = Buddy.fromUser(user);
                // Add to list
                buddies.add(buddy);
            }

            // Add buddies to conversation
            conversation.setParticipants(buddies);

            // Success
            return GetConversationParticipantsResponseEvent.getParticipantsSuccess(
                    conversation.toConversationDetails()
            );
        }
        // Failure
        catch (Exception exception) {
            return GetConversationParticipantsResponseEvent.getParticipantsFailure(
                    GetConversationParticipantsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event) {
        // Get parameters from event
        String conversationId = event.getConversationId();
        Long participantId = event.getBuddyId();

        try {
            // Close conversation for participant
            ParticipantLocalServiceUtil.closeConversation(conversationId, participantId);

            // Call success
            return CloseConversationResponseEvent.success();
        }
        // Persistence error
        catch (SystemException exception) {
            return CloseConversationResponseEvent.failure(
                    CloseConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
        // Conversation not found
        catch (NoSuchConversationException exception) {
            return CloseConversationResponseEvent.failure(
                    CloseConversationResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND, exception
            );
        }
        // Participant not found
        catch (NoSuchParticipantException exception) {
            return CloseConversationResponseEvent.failure(
                    CloseConversationResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND, exception
            );
        }
    }

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent event) {
        // Get parameters from event
        String conversationId = event.getConversationId();
        Long participantId = event.getBuddyId();

        try {
            // Reset the counter
            ParticipantLocalServiceUtil.resetUnreadMessagesCounter(conversationId, participantId);

            // Call success
            return ResetUnreadMessagesCounterResponseEvent.success();

        }
        // Persistence error
        catch (SystemException exception) {
            return ResetUnreadMessagesCounterResponseEvent.failure(
                    ResetUnreadMessagesCounterResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
        // Conversation not found
        catch (NoSuchConversationException exception) {
            return ResetUnreadMessagesCounterResponseEvent.failure(
                    ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND, exception
            );
        }
        // Participant not found
        catch (NoSuchParticipantException exception) {
            return ResetUnreadMessagesCounterResponseEvent.failure(
                    ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND, exception
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
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(conversation.getConversationId());

            // No such conversation was found
            if (conversationModel == null) {
                return SendMessageResponseEvent.sendMessageFailure(
                        SendMessageResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Check if the user is in the conversation
            Participant participant = ParticipantLocalServiceUtil.getParticipant(
                    conversationModel.getCid(), buddy.getBuddyId()
            );

            // User is not in the conversation thus he can't leave it
            if (participant == null) {
                // Failure
                return SendMessageResponseEvent.sendMessageFailure(SendMessageResponseEvent.Status.ERROR_FORBIDDEN);
            }

            // Create new message
            com.marcelmika.lims.persistence.generated.model.Message messageModel = MessageLocalServiceUtil.addMessage(
                    conversationModel.getCid(), // Message is related to the conversation
                    buddy.getBuddyId(),         // Message is created by buddy
                    message.getBody(),          // Body of message
                    message.getCreatedAt()      // Date of creation
            );

            // Notify participants about newly created messages. This will basically update message counters,
            // open conversation to users, etc.
            ParticipantLocalServiceUtil.updateParticipants(conversationModel.getCid(), buddy.getBuddyId());

            // Map message from message model
            Message successMessage = Message.fromMessageModel(messageModel);
            // Don't forget to add the buddy as the creator
            successMessage.setFrom(buddy);

            // Call Success
            return SendMessageResponseEvent.sendMessageSuccess(successMessage.toMessageDetails());

        } catch (Exception exception) {
            // Call Failure
            return SendMessageResponseEvent.sendMessageFailure(
                    SendMessageResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event) {
        // Map to persistence objects
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        try {

            // Prepare conversations container
            List<Conversation> conversations = new LinkedList<Conversation>();

            // Find participants
            List<Participant> buddyParticipates = ParticipantLocalServiceUtil.getOpenedConversations(
                    buddy.getBuddyId()
            );

            // Find conversations where the user participates
            for (Participant participates : buddyParticipates) {

                // Find by cid
                com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                        ConversationLocalServiceUtil.getConversation(participates.getCid());

                // No need to map anything else
                if (conversationModel == null) {
                    break;
                }

                // Get participants
                List<Participant> participantModels = ParticipantLocalServiceUtil.getConversationParticipants(
                        conversationModel.getCid()
                );

                // Map to persistence
                List<Buddy> participants = new LinkedList<Buddy>();
                for (Participant participantModel : participantModels) {
                    participants.add(Buddy.fromParticipantModel(participantModel));
                }

                // Finally, we have everything we needed
                Conversation conversation = Conversation.fromConversationModel(conversationModel);
                conversation.setUnreadMessagesCount(participates.getUnreadMessagesCount());
                conversation.setParticipants(participants);
                conversation.setBuddy(buddy);

                // Add to container
                conversations.add(conversation);
            }

            // Create details from persistence object
            List<ConversationDetails> conversationDetails = new LinkedList<ConversationDetails>();
            for (Conversation conversation : conversations) {
                conversationDetails.add(conversation.toConversationDetails());
            }

            // Call Success
            return GetOpenedConversationsResponseEvent.success(conversationDetails);

        } catch (Exception exception) {
            // Call Failure
            return GetOpenedConversationsResponseEvent.failure(
                    GetOpenedConversationsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }


}
