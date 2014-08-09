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

    /**
     * Constructor
     */
    public PortletProcessor() {
        this.buddyController = new BuddyController();
        this.conversationController = new ConversationController();
        this.groupController = new GroupController();
        this.settingsController = new SettingsController();
    }

    // Constants
    /**
     * @deprecated
     */
    private static final String KEY_CONTENT = "content";
    /**
     * @deprecated
     */
    private static final String KEY_PARAMETERS = "parameters";
    /**
     * @deprecated
     */
    private static final String KEY_QUERY = "query";

    /**
     * Decides which method on PortletProcessor should be called based on the request
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void processRequest(ResourceRequest request, ResourceResponse response) {

        // Log
        logRequest(request);

        // Return error response if no query was set
        if (request.getParameter(RequestParameterKeys.KEY_QUERY) == null) {
            ResponseUtil.writeResponse(null, HttpStatus.BAD_REQUEST, response);
            return;
        }

        // Decide which method should be called
        PortletDispatcher.dispatchRequest(request, response, this);
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Buddy
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Update buddy's status
     *
     * @param request  Request
     * @param response Response
     */
    public void updateBuddyPresence(ResourceRequest request, ResourceResponse response) {
        // TODO: Call directly
        buddyController.updateBuddyPresence(request, response);
    }


    // ---------------------------------------------------------------------------------------------------------
    //   Conversation
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Creates single user conversation with a buddy selected in request
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void createSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        conversationController.createSingleUserConversation(request, response);
    }

    /**
     * Reads Single User Conversation messages
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        conversationController.readSingleUserConversation(request, response);
    }

    /**
     * Closes Single User Conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void closeSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        conversationController.closeSingleUserConversation(request, response);
    }

    /**
     * Resets unread messages counter for the given conversation and participant
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void resetUnreadMessagesCounter(ResourceRequest request, ResourceResponse response) {
        conversationController.resetUnreadMessagesCounter(request, response);
    }

    /**
     * Reads currently opened conversations
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readOpenedConversations(ResourceRequest request, ResourceResponse response) {
        conversationController.readOpenedConversations(request, response);
    }

    /**
     * Create new message in conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void createMessage(ResourceRequest request, ResourceResponse response) {
        conversationController.createMessage(request, response);
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Group
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Fetches all groups related to the buddy.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getGroupList(ResourceRequest request, ResourceResponse response) {
        groupController.getGroupList(request, response);
    }

    // ---------------------------------------------------------------------------------------------------------
    //   Settings
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Update buddy's settings
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    protected void updateSettings(ResourceRequest request, ResourceResponse response) {
        settingsController.updateSettings(request, response);
    }

    /**
     * Updates buddy's active panel
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    protected void updateActivePanel(ResourceRequest request, ResourceResponse response) {
        settingsController.updateActivePanel(request, response);
    }


    // ------------------------------------------------------------------------------
    //   Helpers
    // ------------------------------------------------------------------------------

    /**
     * Logs request data
     *
     * @param request ResourceRequest
     */
    private void logRequest(ResourceRequest request) {

        // Only if debug is enabled
        if (log.isDebugEnabled()) {
            // Log query
            if (request.getParameter(KEY_QUERY) != null) {
                log.debug(String.format("REQUEST QUERY: %s", request.getParameter(KEY_QUERY)));
            }
            // Log request params
            if (request.getParameter(KEY_PARAMETERS) != null) {
                log.debug(String.format("REQUEST PARAMETERS: %s", request.getParameter(KEY_PARAMETERS)));
            }
            // Log request content
            if (request.getParameter(KEY_CONTENT) != null) {
                log.debug(String.format("REQUEST CONTENT: %s", request.getParameter(KEY_CONTENT)));
            }
        }
    }
}
