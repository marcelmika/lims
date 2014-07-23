package com.marcelmika.lims.portal.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.marcelmika.lims.api.events.conversation.GetOpenedConversationsRequestEvent;
import com.marcelmika.lims.api.events.conversation.GetOpenedConversationsResponseEvent;
import com.marcelmika.lims.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.lims.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.lims.core.service.ConversationCoreService;
import com.marcelmika.lims.core.service.ConversationCoreServiceUtil;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.core.service.SettingsCoreServiceUtil;
import com.marcelmika.lims.portal.domain.*;
import com.marcelmika.lims.portal.processor.PortletProcessor;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class LIMSPortlet extends MVCPortlet {

    // Processor
    // TODO: Inject
    PortletProcessor processor = new PortletProcessor();
    // Service Dependencies
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();
    ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();
    // Constants
    private static final String VIEW_JSP_PATH = "/view.jsp"; // Path to the view.jsp
    // Log
    private static Log log = LogFactoryUtil.getLog(LIMSPortlet.class);

    /**
     * This method is called whenever the view is rendered. All data needed to render the main LIMS view (i.e. panels
     * and their content) should be loaded here. Any heavy computation or long-term database operations should be
     * avoided. Always use asynchronous ajax request (via serveResource() method) to get proper data. This method
     * should be used for panel rendering only.
     * <p/>
     * All data is passed to view.jsp which is responsible for view
     *
     * @param renderRequest  RenderRequest
     * @param renderResponse RenderResponse
     * @throws PortletException
     * @throws IOException
     */
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {

        // Settings pane
        renderSettings(renderRequest);
        // Conversations pane
        renderConversations(renderRequest);
        // Additional parameters
        renderAdditions(renderRequest);

        // Set correct content type
        renderResponse.setContentType(renderRequest.getResponseContentType());

        // Set response to view.jsp
        include(VIEW_JSP_PATH, renderRequest, renderResponse);
    }

    /**
     * Renders settings pane within the request
     *
     * @param renderRequest RenderRequest
     */
    private void renderSettings(RenderRequest renderRequest) {

        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);

        // Get buddy's settings
        ReadSettingsResponseEvent responseEvent = settingsCoreService.readSettings(
                new ReadSettingsRequestEvent(buddy.toBuddyDetails())
        );

        // Pass them to jsp only if the request was successful
        if (responseEvent.isSuccess()) {

            // Map settings from details
            Settings settings = Settings.fromSettingsDetails(responseEvent.getSettingsDetails());

            // Pass to jsp
            renderRequest.setAttribute("settings", settings);
        }
        // Log failure
        else {
            // TODO:
//            log.error(responseEvent.getSt);
        }
    }

    /**
     * Renders opened conversations. Thanks to this whenever the user goes to different page the opened conversation
     * is already opened. In other words we don't need to wait for the ajax response.
     *
     * @param renderRequest RenderRequest
     */
    private void renderConversations(RenderRequest renderRequest) {

        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);

        // Get opened conversations
        GetOpenedConversationsResponseEvent responseEvent = conversationCoreService.getOpenedConversations(
                new GetOpenedConversationsRequestEvent(buddy.toBuddyDetails())
        );

        // Pass them to jsp only if the request was successful
        if (responseEvent.isSuccess()) {

            // Map conversation from details
            List<Conversation> conversationList = Conversation.fromConversationDetailsList(
                    responseEvent.getConversationDetails()
            );

            // Pass to jsp
            renderRequest.setAttribute("conversations", conversationList);
        }
        // Log failure
        else {
            log.error(responseEvent.getStatus());
            log.error(responseEvent.getException());
        }
    }

    /**
     * Renders additional parameters needed in jsp
     *
     * @param renderRequest RenderRequest
     */
    private void renderAdditions(RenderRequest renderRequest) {
        // TODO: Refactor "show" to "isPluginEnabled"
        // Check if lims is enabled and pass it to jsp as a parameter
        renderRequest.setAttribute("show", isCorrectAttempt(renderRequest));

        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);
        // Screen name cannot be accessed via javascript so we need to render it manually
        renderRequest.setAttribute("screenName", buddy.getScreenName());
    }

    /**
     * This method is called whenever the server gets an AJAX request from client. All asynchronous requests
     * should go over this method.
     *
     * @param request  Asynchronous request from client
     * @param response Response from server
     * @throws PortletException
     * @throws IOException
     */
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        // Do not send anything if the user is not signed in
        if (!isCorrectAttempt(request)) {
            return;
        }
        // Response content type is JSON
        response.setContentType(ContentTypes.APPLICATION_JSON);
        // This is an entry point to the whole app. Processor will do all the necessary work and fill the response.
        processor.processRequest(request, response);
    }

    /**
     * Checks if the server request attempt is correct. In other words checks if the user is signed in.
     *
     * @param request Request
     * @return true if the request attempt is correct
     */
    private boolean isCorrectAttempt(PortletRequest request) {
        // Check if the user is signed in
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        return themeDisplay.isSignedIn();
    }
}
