package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.portal.controller.BuddyController;
import com.marcelmika.lims.portal.controller.ConversationController;
import com.marcelmika.lims.portal.controller.GroupController;
import com.marcelmika.lims.portal.controller.SettingsController;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/12/14
 * Time: 9:44 AM
 */
public class PortletProcessor {

    // Log
    private static Log log = LogFactoryUtil.getLog(PortletProcessor.class);

    // Controllers
    BuddyController buddyController;
    ConversationController conversationController;
    GroupController groupController;
    SettingsController settingsController;


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

    /**
     * Constructor
     */
    public PortletProcessor() {
        this.buddyController = new BuddyController();
        this.conversationController = new ConversationController();
        this.groupController = new GroupController();
        this.settingsController = new SettingsController();
    }

    /**
     * Decides which method on PortletProcessor should be called based on the request
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


        // Create Single User Conversation
        if (query.equals(QUERY_CREATE_SINGLE_USER_CONVERSATION)) {
            conversationController.createSingleUserConversation(request, response);
        }
        // Read Single User Conversation
        else if (query.equals(QUERY_READ_SINGLE_USER_CONVERSATION)) {
            conversationController.readSingleUserConversation(request, response);
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
            groupController.getGroupList(request, response);
        }
        // Send message
        else if (query.equals(QUERY_CREATE_MESSAGE)) {
            conversationController.createMessage(request, response);
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
