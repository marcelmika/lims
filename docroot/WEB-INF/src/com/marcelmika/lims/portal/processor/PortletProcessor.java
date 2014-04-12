package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.core.service.*;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.Group;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/12/14
 * Time: 9:44 AM
 */
public class PortletProcessor {


    // Log
    private static Log log = LogFactoryUtil.getLog(PortletProcessor.class);

    // Dependencies
    BuddyCoreService buddyCoreService = BuddyCoreServiceUtil.getBuddyCoreService();
    GroupCoreService groupCoreService = GroupCoreServiceUtil.getGroupCoreService();
    ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();


    // ---------------------------------------------------------------------------------------------------------
    //   Group Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Fetches all groups related to the buddy.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getGroupList(ResourceRequest request, ResourceResponse response) {
        // Get the writer
        PrintWriter writer = getResponseWriter(response);
        if (writer == null) {
            return;
        }

        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);
        // Get groups request
        GetGroupsResponseEvent responseEvent = groupCoreService.getGroups(
                new GetGroupsRequestEvent(buddy.toBuddyDetails())
        );


        if (responseEvent.isSuccess()) {
            // Get groups from group details
            List<Group> groups = Group.fromGroupDetails(responseEvent.getGroups());
            // Serialize to json string (include buddies collection)
            String jsonString = JSONFactoryUtil.looseSerialize(groups, Group.KEY_BUDDIES);

            writer.print(jsonString);
            // Add to response
//                pollerResponse.setParameter("groups", createJsonArray(jsonString));

        } else {
            // TODO: Handle error
            log.error(responseEvent.getResult());
            writer.print("{\"error\":\"error\"}");
        }
    }


    // ------------------------------------------------------------------------------
    //   Helpers
    // ------------------------------------------------------------------------------

    /**
     * Returns write from response, null on error
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
     * Creates JSON array from JSON string. Avoids exception so if there is any error returns null.
     *
     * @param jsonString serialized string
     * @return JSONArray
     */
    private JSONArray createJsonArray(String jsonString) {

        JSONArray jsonArray = null;

        try {
            jsonArray = JSONFactoryUtil.createJSONArray(jsonString);
        } catch (JSONException e) {
            // At least log what went wrong
            log.error(e);
        }

        return jsonArray;
    }

    /**
     * Creates JSON object from JSON string. Avoids exception so if there is any error returns null.
     *
     * @param jsonString serialized string
     * @return JSONObject
     */
    private JSONObject createJsonObject(String jsonString) {

        JSONObject jsonObject = null;

        try {
            jsonObject = JSONFactoryUtil.createJSONObject(jsonString);
        } catch (JSONException e) {
            // At least log what went wrong
            log.error(e);
        }

        return jsonObject;
    }

}
