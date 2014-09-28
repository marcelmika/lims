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

package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.portal.controller.*;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.Random;

/**
 * Portlet processor contains business logic which decides what controller
 * should be called based on the query received in request parameter.
 * This is an important part of the system. Every time you introduce new controller
 * or add a method to the existing one you need to add mapping to the processRequest() method.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/12/14
 * Time: 9:44 AM
 */
public class PortletProcessorImpl implements PortletProcessor {

    // Log
    private static Log log = LogFactoryUtil.getLog(PortletProcessorImpl.class);

    // Controllers
    BuddyController buddyController;
    ConversationController conversationController;
    GroupController groupController;
    SettingsController settingsController;
    ServerController serverController;


    // Client returns query parameter. Thanks to this we can decide which method should be called.
    // If we were RESTFul we would be using resources at different urls. However, since liferay gives us
    // only one url for AJAX communication we need such query parameter to decide which method and resource
    // should be used.
    private static final String QUERY_CREATE_SINGLE_USER_CONVERSATION = "CreateSingleUserConversation";
    private static final String QUERY_READ_SINGLE_USER_CONVERSATION = "ReadSingleUserConversation";
    private static final String QUERY_CLOSE_SINGLE_USER_CONVERSATION = "CloseSingleUserConversation";
    private static final String QUERY_RESET_UNREAD_MESSAGES_COUNTER = "ResetUnreadMessagesCounter";
    private static final String QUERY_READ_OPENED_CONVERSATIONS = "ReadOpenedConversations";
    private static final String QUERY_GET_GROUP_LIST = "GetGroupList";
    private static final String QUERY_CREATE_MESSAGE = "CreateMessage";
    private static final String QUERY_UPDATE_BUDDY_PRESENCE = "UpdateBuddyPresence";
    private static final String QUERY_UPDATE_ACTIVE_PANEL = "UpdateActivePanel";
    private static final String QUERY_UPDATE_SETTINGS = "UpdateSettings";
    private static final String QUERY_GET_SERVER_TIME = "GetServerTime";
    private static final String QUERY_SEARCH_BUDDIES = "SearchBuddies";

    /**
     * Constructor
     *
     * @param buddyController        BuddyController
     * @param conversationController ConversationController
     * @param groupController        GroupController
     * @param settingsController     SettingsController
     * @param serverController       ServerController
     */
    public PortletProcessorImpl(final BuddyController buddyController,
                                final ConversationController conversationController,
                                final GroupController groupController,
                                final SettingsController settingsController,
                                final ServerController serverController) {

        this.buddyController = buddyController;
        this.conversationController = conversationController;
        this.groupController = groupController;
        this.settingsController = settingsController;
        this.serverController = serverController;
    }

    /**
     * Adds request further to the system and writes data to response if needed.
     * Contains logic that decides which resource should be accessed.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void processRequest(ResourceRequest request, ResourceResponse response) {

        // Log request
        if (log.isDebugEnabled()) {
            logRequest(request);
        }

        // Get query type from parameter
        String query = request.getParameter(RequestParameterKeys.KEY_QUERY);

        // Return error response if no query was set
        if (query == null) {
            ResponseUtil.writeResponse(null, HttpStatus.BAD_REQUEST, response);
            return;
        }

        // Decide which method should be called
        dispatchRequest(request, response, query);
    }

    /**
     * Calls all appropriate methods on PollerProcessor that are scheduled for the receive request event.
     *
     * @param response request from browser
     * @param request  response sent to browser
     */
    private void dispatchRequest(ResourceRequest request,
                                 ResourceResponse response,
                                 String query) {

        Random random = new Random();
        int num = 10;

        // Create Single User Conversation
        if (query.equals(QUERY_CREATE_SINGLE_USER_CONVERSATION)) {
            if (num > 3) {
                conversationController.createSingleUserConversation(request, response);
            } else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
        // Read Single User Conversation
        else if (query.equals(QUERY_READ_SINGLE_USER_CONVERSATION)) {
            if (num > 3) {
                conversationController.readSingleUserConversation(request, response);
            } else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
        // Close Single User Conversation
        else if (query.equals(QUERY_CLOSE_SINGLE_USER_CONVERSATION)) {
            conversationController.closeSingleUserConversation(request, response);
        }
        // Reset Unread Messages Counter
        else if (query.equals(QUERY_RESET_UNREAD_MESSAGES_COUNTER)) {
            conversationController.resetUnreadMessagesCounter(request, response);
        }
        // Read Opened Conversations
        else if (query.equals(QUERY_READ_OPENED_CONVERSATIONS)) {
            conversationController.readOpenedConversations(request, response);
        }
        // Get Group List
        else if (query.equals(QUERY_GET_GROUP_LIST)) {
            if (num > 5) {
                groupController.getGroupList(request, response);
            } else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
        // Send message
        else if (query.equals(QUERY_CREATE_MESSAGE)) {
            if (num > 2) {
                conversationController.createMessage(request, response);
            } else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
        // Update buddy presence
        else if (query.equals(QUERY_UPDATE_BUDDY_PRESENCE)) {
            buddyController.updateBuddyPresence(request, response);
        }
        // Update active panel
        else if (query.equals(QUERY_UPDATE_ACTIVE_PANEL)) {
            settingsController.updateActivePanel(request, response);
        }
        // Update settings
        else if (query.equals(QUERY_UPDATE_SETTINGS)) {
            settingsController.updateSettings(request, response);
        }
        // Get Server Time
        else if (query.equals(QUERY_GET_SERVER_TIME)) {
            serverController.getServerTime(request, response);
        }
        // Search buddies
        else if (query.equals(QUERY_SEARCH_BUDDIES)) {
            if (num > 5) {
                buddyController.searchBuddies(request, response);
            } else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
        // No such query was found
        else {
            // Write 404 to response
            ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
        }
    }

    /**
     * Logs request data
     *
     * @param request ResourceRequest
     */
    private void logRequest(ResourceRequest request) {

        // Log query
        if (request.getParameter(RequestParameterKeys.KEY_QUERY) != null) {
            log.debug(String.format("REQUEST QUERY: %s",
                            request.getParameter(RequestParameterKeys.KEY_QUERY))
            );
        }
        // Log request params
        if (request.getParameter(RequestParameterKeys.KEY_PARAMETERS) != null) {
            log.debug(String.format("REQUEST PARAMETERS: %s",
                            request.getParameter(RequestParameterKeys.KEY_PARAMETERS))
            );
        }
        // Log request content
        if (request.getParameter(RequestParameterKeys.KEY_CONTENT) != null) {
            log.debug(String.format("REQUEST CONTENT: %s",
                            request.getParameter(RequestParameterKeys.KEY_CONTENT))
            );
        }
    }
}
