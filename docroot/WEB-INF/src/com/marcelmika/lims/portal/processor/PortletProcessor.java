package com.marcelmika.lims.portal.processor;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.UpdatePresenceBuddyResponseEvent;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.lims.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.lims.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.lims.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.lims.core.service.*;
import com.marcelmika.lims.portal.domain.*;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.processor.parameters.CloseConversationParameters;
import com.marcelmika.lims.portal.processor.parameters.CreateMessageParameters;
import com.marcelmika.lims.portal.processor.parameters.ReadConversationParameters;
import com.marcelmika.lims.portal.processor.parameters.ResetUnreadMessagesCounterParameters;

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

    // Constants
    private static final String KEY_CONTENT = "content";
    private static final String KEY_PARAMETERS = "parameters";
    private static final String KEY_QUERY = "query";

    /**
     * Decides which method on PortletProcessor should be called based on the request
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void processRequest(ResourceRequest request, ResourceResponse response) {

        // Log query
        if (request.getParameter(KEY_QUERY) != null) {
            log.info("QUERY: " + request.getParameter(KEY_QUERY));
        }
        // Log request params
        if (request.getParameter(KEY_PARAMETERS) != null) {
            log.info("PARAMETERS: " + request.getParameter(KEY_PARAMETERS));
        }
        // Log request content
        if (request.getParameter(KEY_CONTENT) != null) {
            log.info("CONTENT: " + request.getParameter(KEY_CONTENT));
        }

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
        // Create buddy from poller request
        Buddy buddy = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Buddy.class);
        Presence presence = buddy.getPresence();

        // Send request to core service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyCoreService.updatePresence(
                new UpdatePresenceBuddyRequestEvent(buddy.getBuddyId(), buddy.getPresence().toPresenceDetails())
        );

        // Disable chat if presence is offline
        if (presence == Presence.STATE_OFFLINE) {
            settingsCoreService.disableChat(new DisableChatRequestEvent(buddy.toBuddyDetails()));
        }
        // Enable otherwise
        else {
            settingsCoreService.enableChat(new EnableChatRequestEvent(buddy.toBuddyDetails()));
        }

        // Success
        if (responseEvent.isSuccess()) {
            writeResponse(null, HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            // TODO: Add status handling
            writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
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
        // Create buddy from request
        Buddy buddy = Buddy.fromResourceRequest(request);

        // Deserialize Content
        Conversation conversation = JSONFactoryUtil.looseDeserialize(
                request.getParameter(KEY_CONTENT), Conversation.class
        );
        log.info(conversation);

        // TODO: This should be solved more conceptually ----
        conversation.setConversationType(ConversationType.SINGLE_USER);
        List<Buddy> participants = conversation.getParticipants();
        Long companyId = PortalUtil.getCompanyId(request);
        for (Buddy participant : participants) {
            try {
                Long userId = UserLocalServiceUtil.getUserIdByScreenName(companyId, participant.getScreenName());
                participant.setBuddyId(userId);
            } catch (Exception e) {
                writeResponse(HttpStatus.NOT_FOUND, response);
                return;
            }
        }
        // ----
        CreateConversationResponseEvent responseEvent = conversationCoreService.createConversation(
                new CreateConversationRequestEvent(buddy.toBuddyDetails(), conversation.toConversationDetails(), null)
        );

        // Success
        if (responseEvent.isSuccess()) {
            writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            CreateConversationResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == CreateConversationResponseEvent.Status.ERROR_NO_SESSION) {
                writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad request
            else if (status == CreateConversationResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE ||
                    status == CreateConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
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
                request.getParameter(KEY_PARAMETERS), ReadConversationParameters.class
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
            if (conversation.getEtag().equals(parameters.getEntityTag())) {
                log.info("Return from cache");
                writeResponse(HttpStatus.NO_CONTENT, response);
                return;
            }

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(conversation, "messages", "messages.from");
            log.info(serialized);
            // Write success to response
            writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            ReadSingleUserConversationResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_NOT_FOUND) {
                writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Unauthorized
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_NO_SESSION) {
                writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad request
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
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
                request.getParameter(KEY_PARAMETERS), CloseConversationParameters.class
        );

        // Close conversation
        CloseConversationResponseEvent responseEvent = conversationCoreService.closeConversation(
                new CloseConversationRequestEvent(buddy.getBuddyId(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            CloseConversationResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == CloseConversationResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND ||
                    status == CloseConversationResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND) {
                writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Everything else is server fault
            else {
                writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
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
                request.getParameter(KEY_PARAMETERS), ResetUnreadMessagesCounterParameters.class
        );

        // Reset counter
        ResetUnreadMessagesCounterResponseEvent responseEvent = conversationCoreService.resetUnreadMessagesCounter(
                new ResetUnreadMessagesCounterRequestEvent(buddy.getBuddyId(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            ResetUnreadMessagesCounterResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND ||
                    status == ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND) {
                writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Everything else is server fault
            else {
                writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
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
            writeResponse(serializedConversations, HttpStatus.OK, response);
        }
        // Failure
        else {
            GetOpenedConversationsResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == GetOpenedConversationsResponseEvent.Status.ERROR_NO_SESSION) {
                writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad Request
            else if (status == GetOpenedConversationsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
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
                request.getParameter(KEY_PARAMETERS), CreateMessageParameters.class
        );
        // Deserialize Content
        Message message = JSONFactoryUtil.looseDeserialize(
                request.getParameter(KEY_CONTENT), Message.class
        );

        Conversation conversation = new Conversation();
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
            writeResponse(serializedMessage, HttpStatus.OK, response);
        }
        // Failure
        else {
            SendMessageResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == SendMessageResponseEvent.Status.ERROR_NO_SESSION) {
                writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Not found
            else if (status == SendMessageResponseEvent.Status.ERROR_NOT_FOUND) {
                writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Bad Request
            else if (status == SendMessageResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE ||
                    status == SendMessageResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
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

        // Success
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
                writeResponse(JSONFactoryUtil.looseSerialize(groupCollection), HttpStatus.OK, response);
            } else {
                // Etags are different which means that groups were modified
                // Send the whole package to the client
                writeResponse(
                        JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies"),
                        HttpStatus.OK,
                        response
                );
            }
        }
        // Failure
        else {
            // TODO: Add status handling
            writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
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
        Settings settings = JSONFactoryUtil.looseDeserialize(request.getParameter("data"), Settings.class);

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
        // Log
        log.info("STATUS: " + statusCode.toString());
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
