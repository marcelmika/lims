package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/23/14
 * Time: 4:50 PM
 */
public class PortletDispatcher {

    // Client returns query parameter. Thanks to this we can decide which method should be called.
    // If we were RESTFul we would be using resources at different urls. However, since liferay gives us
    // only one url for AJAX communication we need such query parameter to decide which method and resource
    // should be used.
    private static final String QUERY_CREATE_SINGLE_USER_CONVERSATION = "CreateSingleUserConversation";
    private static final String QUERY_READ_SINGLE_USER_CONVERSATION = "ReadSingleUserConversation";
    private static final String QUERY_CLOSE_SINGLE_USER_CONVERSATION = "CloseSingleUserConversation";
    private static final String QUERY_RESET_UNREAD_MESSAGES_COUNTER = "ResetUnreadMessagesCounter";
    private static final String QUERY_GET_GROUP_LIST = "GetGroupList";
    private static final String QUERY_CREATE_MESSAGE = "CreateMessage";
    private static final String QUERY_UPDATE_BUDDY_PRESENCE = "UpdateBuddyPresence";
    private static final String QUERY_UPDATE_ACTIVE_PANEL = "UpdateActivePanel";
    private static final String QUERY_UPDATE_SETTINGS = "UpdateSettings";
    // Keys
    private static final String KEY_QUERY = "query";
    // Log
    private static Log log = LogFactoryUtil.getLog(PortletDispatcher.class);

    /**
     * Calls all appropriate methods on PollerProcessor that are scheduled for the receive request event.
     *
     * @param response  request from browser
     * @param request   response sent to browser
     * @param processor poller processor
     */
    public static void dispatchRequest(ResourceRequest request,
                                       ResourceResponse response,
                                       PortletProcessor processor) {

        // Get query type from parameter
        String query = request.getParameter(KEY_QUERY);

        // Create Single User Conversation
        if (query.equals(QUERY_CREATE_SINGLE_USER_CONVERSATION)) {
            processor.createSingleUserConversation(request, response);
        }
        // Read Single User Conversation
        else if (query.equals(QUERY_READ_SINGLE_USER_CONVERSATION)) {
            processor.readSingleUserConversation(request, response);
        }
        // Close Single User Conversation
        else if (query.equals(QUERY_CLOSE_SINGLE_USER_CONVERSATION)) {
            processor.closeSingleUserConversation(request, response);
        }
        // Reset Unread Messages Counter
        else if (query.equals(QUERY_RESET_UNREAD_MESSAGES_COUNTER)) {
            processor.resetUnreadMessagesCounter(request, response);
        }
        // Get Group List
        else if (query.equals(QUERY_GET_GROUP_LIST)) {
            processor.getGroupList(request, response);
        }
        // Send message
        else if (query.equals(QUERY_CREATE_MESSAGE)) {
            processor.createMessage(request, response);
        }
        // Update buddy presence
        else if (query.equals(QUERY_UPDATE_BUDDY_PRESENCE)) {
            processor.updateBuddyPresence(request, response);
        }
        // Update active panel
        else if (query.equals(QUERY_UPDATE_ACTIVE_PANEL)) {
            processor.updateActivePanel(request, response);
        }
        // Update settings
        else if (query.equals(QUERY_UPDATE_SETTINGS)) {
            processor.updateSettings(request, response);
        }
    }
}
