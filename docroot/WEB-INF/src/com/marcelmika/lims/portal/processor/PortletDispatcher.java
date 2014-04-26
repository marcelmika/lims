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

    private static final String QUERY_CREATE_SINGLE_USER_CONVERSATION = "CreateSingleUserConversation";
    private static final String QUERY_GET_GROUP_LIST = "GetGroupList";
    private static final String QUERY_UPDATE_BUDDY_PRESENCE = "UpdateBuddyPresence";

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

        String query = request.getParameter("query");

        log.info("Dispatching query: " + query);

        // Create Single User conversation
        if(query.equals(QUERY_CREATE_SINGLE_USER_CONVERSATION)) {
            processor.createSingleUserConversation(request, response);
        }
        // Get Group List
        else if(query.equals(QUERY_GET_GROUP_LIST)) {
            processor.getGroupList(request, response);
        }
        // Update buddy presence
        else if(query.equals(QUERY_UPDATE_BUDDY_PRESENCE)) {
            processor.updateBuddyPresence(request, response);
        }
    }
}
