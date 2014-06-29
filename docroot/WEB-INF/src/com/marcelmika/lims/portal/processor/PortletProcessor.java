package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.lims.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.lims.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.lims.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.core.service.*;
import com.marcelmika.lims.portal.domain.*;

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


    public void processRequest(ResourceRequest request, ResourceResponse response) {
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
        // Create buddy from poller request
        Buddy buddy = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Buddy.class);
        Presence presence = buddy.getPresence();

        // Send request to core service
        ResponseEvent responseEvent = buddyCoreService.updatePresence(new UpdatePresenceBuddyRequestEvent(
                        buddy.getBuddyId(), buddy.getPresence().toPresenceDetails())
        );

        // Disable chat if presence is offline
        if (presence == Presence.STATE_OFFLINE) {
            settingsCoreService.disableChat(new DisableChatRequestEvent(buddy.toBuddyDetails()));
        }
        // Enable otherwise
        else {
            settingsCoreService.enableChat(new EnableChatRequestEvent(buddy.toBuddyDetails()));
        }

        // On success
        if (responseEvent.isSuccess()) {
            writeSuccess("", response);
        }
        // On error
        else {
            writeError(responseEvent.getResult(), response);
        }
    }


    // ---------------------------------------------------------------------------------------------------------
    //   Conversation
    // ---------------------------------------------------------------------------------------------------------

    /**
     * Creates single user conversation with a buddy selected in request
     */
    public void createSingleUserConversation(ResourceRequest request, ResourceResponse response) {
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        Conversation conversation = JSONFactoryUtil.looseDeserialize(
                request.getParameter("data"), Conversation.class
        );

        // TODO: This should be solved more conceptually ----
        conversation.setConversationType(ConversationType.SINGLE_USER);
        conversation.setConversationId(conversation.getParticipants().get(0).getScreenName());
        List<Buddy> participants = conversation.getParticipants();
        Long companyId = PortalUtil.getCompanyId(request);
        for (Buddy participant : participants) {
            try {
                Long userId = UserLocalServiceUtil.getUserIdByScreenName(companyId, participant.getScreenName());
                participant.setBuddyId(userId);
            } catch (Exception e) {
                writeError("No user found", response);
                return;
            }
        }
        // ----

        log.info(request.getParameter("data"));
        log.info(conversation);

        CreateConversationResponseEvent responseEvent = conversationCoreService.createConversation(
                new CreateConversationRequestEvent(buddy.toBuddyDetails(), conversation.toConversationDetails(), null)
        );

        log.info("Crate conversation STATUS: " + responseEvent.getStatus());

        // On success
        if (responseEvent.isSuccess()) {
            writeSuccess("", response);
        }
        // On error
        else {
            writeError(responseEvent.getStatus().toString(), response);
        }
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
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);
        // Get groups request
        GetGroupsResponseEvent responseEvent = groupCoreService.getGroups(
                new GetGroupsRequestEvent(buddy.toBuddyDetails())
        );

        // On success
        if (responseEvent.isSuccess()) {
            // Map groups from group details
            GroupCollection groupCollection = GroupCollection.fromGroupCollectionDetails(
                    responseEvent.getGroupCollection()
            );

            // Groups are cached, so take the etag from request ...
            String etag = request.getParameter("etag");
            // ... and compare it with group collection etag
            if (etag.equals(Integer.toString(groupCollection.getEtag()))) {
                // Etags equal which means that nothing has changed.
                // Write only the group collection without groups and buddies (no extra traffic needed)
                writeSuccess(JSONFactoryUtil.looseSerialize(groupCollection), response);
            } else {
                // Etags are different which means that groups were modified
                // Send the whole package to the client
                writeSuccess(JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies"), response);
            }
        }
        // On error
        else {
            // Write an error to the response so the client knows what went wrong
            writeError(responseEvent.getResult(), response);
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
        // Create buddy and settings from poller request
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);
        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateSettings(new UpdateSettingsRequestEvent(
                        settings.getBuddy().getBuddyId(), settings.toSettingsDetails())
        );

        // On success
        if (responseEvent.isSuccess()) {
            writeSuccess("", response);
        }
        // On error
        else {
            writeError(responseEvent.getResult(), response);
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
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);

        // Send request to core service
        ResponseEvent responseEvent = settingsCoreService.updateActivePanel(new UpdateActivePanelRequestEvent(
                settings.getBuddy().getBuddyId(), settings.getActivePanelId()
        ));

        // On success
        if (responseEvent.isSuccess()) {
            writeSuccess("", response);
        }
        // On error
        else {
            writeError(responseEvent.getResult(), response);
        }
    }


    // ------------------------------------------------------------------------------
    //   Helpers
    // ------------------------------------------------------------------------------

    /**
     * Takes the response and writes a content given in parameter to it.
     *
     * @param content  which will be written to the response
     * @param response resource response
     */
    private void writeSuccess(String content, ResourceResponse response) {
        // Get the writer
        PrintWriter writer = getResponseWriter(response);
        if (writer == null) {
            return;
        }

        // Write the content to the output stream
        writer.print(content);
    }

    /**
     * Takes the response and writes an error message to it.
     *
     * @param content  Error message which will be written to the response
     * @param response resource response
     */
    private void writeError(String content, ResourceResponse response) {
        // Get the writer
        PrintWriter writer = getResponseWriter(response);
        if (writer == null) {
            return;
        }

        // Writes an error to the output stream
        writer.print(String.format("{\"error\":\"%s\"}", content));
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
}
