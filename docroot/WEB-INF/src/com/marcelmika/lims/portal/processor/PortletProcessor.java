package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.buddy.UpdateStatusBuddyRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.core.service.*;
import com.marcelmika.lims.portal.domain.*;

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

    // Dependencies
    BuddyCoreService buddyCoreService = BuddyCoreServiceUtil.getBuddyCoreService();
    GroupCoreService groupCoreService = GroupCoreServiceUtil.getGroupCoreService();
    ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();


    public void processRequest(ResourceRequest request, ResourceResponse response) {
        PortletDispatcher.dispatchRequest(request, response, this);
    }


    // ---------------------------------------------------------------------------------------------------------
    //   Buddy Lifecycle
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Update buddy's status
     *
     * @param request  Request
     * @param response Response
     */
    public void updateBuddyPresence(ResourceRequest request, ResourceResponse response) {
        // Create buddy from poller request
        Buddy buddy = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Buddy.class);

        // Send request to core service
        ResponseEvent responseEvent = buddyCoreService.updateStatus(new UpdateStatusBuddyRequestEvent(
                        buddy.getBuddyId(), buddy.getPresence().toPresenceDetails())
        );


        // TODO: Add to separate function
        // Get the writer
        PrintWriter writer = getResponseWriter(response);
        if (writer == null) {
            return;
        }

        if (responseEvent.isSuccess()) {
            writer.print("success");
        } else {
            writer.print("error");
        }
    }

    /**
     * Creates single user conversation with a buddy selected in request
     */
    public void createSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        // Get the writer
        PrintWriter writer = getResponseWriter(response);
        if (writer == null) {
            return;
        }
        // Create buddy from poller request
//        Buddy buddy = Buddy.fromResourceRequest(request);

        Conversation conversation = JSONFactoryUtil.looseDeserialize(request.getParameter("data"),
                Conversation.class);


        log.info(request.getParameter("data"));

        log.info(conversation);


        writer.print("{\"error\":\"nazdar\"}");
    }

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
            GroupCollection groupCollection = GroupCollection.fromGroupCollectionDetails(
                    responseEvent.getGroupCollection()
            );

            log.info(request.getParameter("etag"));

            String etag = request.getParameter("etag");


            // Same etags -> nothing to be send
            if (etag.equals(Integer.toString(groupCollection.getEtag()))) {
                writer.print("{\"etag\":\"" + etag + "\"}");
                return;
            }

            // Serialize to json string (include buddies collection)
            String jsonString = JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies");

            writer.print(jsonString);
            log.info(jsonString);

        } else {
            // TODO: Handle error
            log.error(responseEvent.getResult());
            writer.print("{\"error\":\"error\"}");
        }
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
        // todo move to fromResourceRequest() method
        // Create buddy and settings from poller request
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);
        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateSettings(new UpdateSettingsRequestEvent(
                        settings.getBuddy().getBuddyId(), settings.toSettingsDetails())
        );

        log.info(settings.isMute());
    }

    /**
     * Updates buddy's active panel
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    protected void updateActivePanel(ResourceRequest request, ResourceResponse response) {
        // todo move to fromResourceRequest() method
        // Create buddy and settings from poller request
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);

        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateActivePanel(new UpdateActivePanelRequestEvent(
                settings.getBuddy().getBuddyId(), settings.getActivePanelId()
        ));

        log.info(responseEvent.getResult());
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
