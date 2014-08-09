package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.core.service.SettingsCoreServiceUtil;
import com.marcelmika.lims.portal.controller.BuddyController;
import com.marcelmika.lims.portal.controller.ConversationController;
import com.marcelmika.lims.portal.controller.GroupController;
import com.marcelmika.lims.portal.domain.Settings;
import com.marcelmika.lims.portal.http.HttpStatus;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    // Dependencies
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();

    /**
     * Constructor
     */
    public PortletProcessor() {
        this.buddyController = new BuddyController();
        this.conversationController = new ConversationController();
        this.groupController = new GroupController();
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
        if (request.getParameter(KEY_QUERY) == null) {
            writeResponse(null, HttpStatus.BAD_REQUEST, response);
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
        // Create buddy and settings from poller request
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);
        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateSettings(new UpdateSettingsRequestEvent(
                        settings.getBuddy().getBuddyId(), settings.toSettingsDetails())
        );

        // On success
        if (responseEvent.isSuccess()) {
            writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // On error
        else {
            // TODO: Add status handling
            writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    /**
     * Updates buddy's active panel
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    protected void updateActivePanel(ResourceRequest request, ResourceResponse response) {

        // Create buddy and settings from poller request
        Settings settings;
        try {
            settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);
        } catch (Exception exception) {
            writeResponse(HttpStatus.BAD_REQUEST, response);
            return;
        }

        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateActivePanel(new UpdateActivePanelRequestEvent(
                settings.getBuddy().getBuddyId(), settings.getActivePanelId()
        ));

        // Success
        if (responseEvent.isSuccess()) {
            writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            // TODO: Add status handling
            writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }


    // ------------------------------------------------------------------------------
    //   Helpers
    // ------------------------------------------------------------------------------

    /**
     * Sets status code to the response. Use for no-content responses.
     *
     * @param statusCode HTTP Status code
     * @param response   Resource response
     */
    private void writeResponse(HttpStatus statusCode, ResourceResponse response) {
        writeResponse(null, statusCode, response);
    }

    /**
     * Takes the response and writes a content given in parameter and sets status code.
     *
     * @param content    Which will be written to the response
     * @param statusCode HTTP Status code
     * @param response   Resource response
     */
    private void writeResponse(String content, HttpStatus statusCode, ResourceResponse response) {

        // Write the content to the output stream
        if (content != null) {
            // Get the writer
            PrintWriter writer = getResponseWriter(response);
            // If it fails it returns null. So write the content only if we have the writer.
            if (writer != null) {
                writer.print(content);
            }
        }

        // Set status code
        response.setProperty(ResourceResponse.HTTP_STATUS_CODE, statusCode.toString());

        // Disable caching. It needs to be here because Internet Explorer aggressively caches
        // ajax requests.
        response.addProperty("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.addProperty("Pragma", "no-cache"); // HTTP 1.0
        response.addProperty("Expires", "0"); // Proxies

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("RESPONSE STATUS CODE: %s", statusCode.toString()));
        }
    }

    /**
     * Returns writer from response, null on error
     *
     * @param response ResourceResponse
     * @return PrintWriter, null on error
     */
    private PrintWriter getResponseWriter(ResourceResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            log.error(e);
        }

        return writer;
    }

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
