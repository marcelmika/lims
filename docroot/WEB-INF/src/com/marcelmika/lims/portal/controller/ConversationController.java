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

package com.marcelmika.lims.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.core.service.ConversationCoreService;
import com.marcelmika.lims.portal.domain.*;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.request.parameters.CloseConversationParameters;
import com.marcelmika.lims.portal.request.parameters.CreateMessageParameters;
import com.marcelmika.lims.portal.request.parameters.ReadConversationParameters;
import com.marcelmika.lims.portal.request.parameters.ResetUnreadMessagesCounterParameters;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:24 PM
 */
public class ConversationController {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationController.class);

    // Dependencies
    ConversationCoreService conversationCoreService;

    /**
     * Constructor
     *
     * @param conversationCoreService ConversationCoreService
     */
    public ConversationController(final ConversationCoreService conversationCoreService) {
        this.conversationCoreService = conversationCoreService;
    }

    /**
     * Creates single user conversation with a buddy selected in request
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void createSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Deserialize Content
        Conversation conversation = JSONFactoryUtil.looseDeserialize(
                request.getParameter(RequestParameterKeys.KEY_CONTENT), Conversation.class
        );

        // This is a single user chat conversation
        conversation.setConversationType(ConversationType.SINGLE_USER);

        CreateConversationResponseEvent responseEvent = conversationCoreService.createConversation(
                new CreateConversationRequestEvent(buddy.toBuddyDetails(), conversation.toConversationDetails(), null)
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map conversation from response
            Conversation conversationResponse = Conversation.fromConversationDetails(responseEvent.getConversation());
            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(conversationResponse);
            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            CreateConversationResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == CreateConversationResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad request
            else if (status == CreateConversationResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE ||
                    status == CreateConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }

    /**
     * Reads Single User Conversation messages
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Deserialize Parameters
        ReadConversationParameters parameters = JSONFactoryUtil.looseDeserialize(
                request.getParameter(RequestParameterKeys.KEY_PARAMETERS), ReadConversationParameters.class
        );

        // Create objects from parameters
        Conversation conversation = new Conversation();
        conversation.setConversationId(parameters.getConversationId());
        Pagination pagination = parameters.getPagination();

        // Read conversations
        ReadSingleUserConversationResponseEvent responseEvent = conversationCoreService.readConversation(
                new ReadSingleUserConversationRequestEvent(
                        buddy.toBuddyDetails(),                 // Buddy is participant
                        conversation.toConversationDetails(),   // Read proper conversation
                        pagination.toPaginationDetails())       // Pagination request
        );

        // Success
        if (responseEvent.isSuccess()) {
            conversation = Conversation.fromConversationDetails(responseEvent.getConversation());

            // Client has fresh copy so there is no need to send it
            if (conversation.getEtag().equals(parameters.getEtag())) {
                ResponseUtil.writeResponse(HttpStatus.NOT_MODIFIED, response);
                return;
            }

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(conversation, "messages", "messages.from");

            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            ReadSingleUserConversationResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Unauthorized
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad request
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }

    /**
     * Closes Single User Conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void closeSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Deserialize Parameters
        CloseConversationParameters parameters = JSONFactoryUtil.looseDeserialize(
                request.getParameter(RequestParameterKeys.KEY_PARAMETERS), CloseConversationParameters.class
        );

        // Close conversation
        CloseConversationResponseEvent responseEvent = conversationCoreService.closeConversation(
                new CloseConversationRequestEvent(buddy.getBuddyId(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            CloseConversationResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == CloseConversationResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND ||
                    status == CloseConversationResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }

    /**
     * Resets unread messages counter for the given conversation and participant
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void resetUnreadMessagesCounter(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Deserialize Parameters
        ResetUnreadMessagesCounterParameters parameters = JSONFactoryUtil.looseDeserialize(
                request.getParameter(RequestParameterKeys.KEY_PARAMETERS), ResetUnreadMessagesCounterParameters.class
        );

        // Reset counter
        ResetUnreadMessagesCounterResponseEvent responseEvent = conversationCoreService.resetUnreadMessagesCounter(
                new ResetUnreadMessagesCounterRequestEvent(buddy.getBuddyId(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            ResetUnreadMessagesCounterResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND ||
                    status == ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }

    /**
     * Reads currently opened conversations
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readOpenedConversations(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Read conversations
        GetOpenedConversationsResponseEvent responseEvent = conversationCoreService.getOpenedConversations(
                new GetOpenedConversationsRequestEvent(buddy.toBuddyDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map conversation from details
            List<Conversation> conversationList = Conversation.fromConversationDetailsList(
                    responseEvent.getConversationDetails()
            );
            // Serialize
            String serializedConversations = JSONFactoryUtil.looseSerialize(conversationList);
            // Write success to response
            ResponseUtil.writeResponse(serializedConversations, HttpStatus.OK, response);
        }
        // Failure
        else {
            GetOpenedConversationsResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == GetOpenedConversationsResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad Request
            else if (status == GetOpenedConversationsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }

    /**
     * Create new message in conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void createMessage(ResourceRequest request, ResourceResponse response) {
        // Buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Deserialize Parameters
        CreateMessageParameters parameters = JSONFactoryUtil.looseDeserialize(
                request.getParameter(RequestParameterKeys.KEY_PARAMETERS), CreateMessageParameters.class
        );
        // Deserialize Content
        Message message = JSONFactoryUtil.looseDeserialize(
                request.getParameter(RequestParameterKeys.KEY_CONTENT), Message.class
        );

        Conversation conversation = new Conversation();
        conversation.setConversationType(ConversationType.SINGLE_USER);
        conversation.setConversationId(parameters.getConversationId());

        // Add to system
        SendMessageResponseEvent responseEvent = conversationCoreService.sendMessage(
                new SendMessageRequestEvent(
                        buddy.toBuddyDetails(),                 // Creator
                        conversation.toConversationDetails(),   // Conversation
                        message.toMessageDetails())             // Message
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map message from response
            Message responseMessage = Message.fromMessageDetails(responseEvent.getMessage());
            // Serialize
            String serializedMessage = JSONFactoryUtil.looseSerialize(responseMessage);
            // Write success to response
            ResponseUtil.writeResponse(serializedMessage, HttpStatus.OK, response);
        }
        // Failure
        else {
            SendMessageResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == SendMessageResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Not found
            else if (status == SendMessageResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Bad Request
            else if (status == SendMessageResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE ||
                    status == SendMessageResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }


}
