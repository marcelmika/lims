package com.marcelmika.lims.portal.portlet;

import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.marcelmika.lims.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.lims.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.core.service.SettingsCoreServiceUtil;
import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.portal.domain.Buddy;
import com.marcelmika.lims.portal.domain.Settings;
import com.marcelmika.lims.portal.processor.PortletProcessor;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class LIMSPortlet extends MVCPortlet {

    // Dependencies
    // TODO: Inject
    PortletProcessor processor = new PortletProcessor();
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();

    // Constants
    private static final String VIEW_JSP_PATH = "/view.jsp"; // Path to the view.jsp

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
        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);

        // Read settings for the buddy
        ReadSettingsResponseEvent settingsResponse = settingsCoreService.readSettings(
                new ReadSettingsRequestEvent(buddy.toBuddyDetails())
        );

        // Pass them to jsp
        renderRequest.setAttribute("settings", settingsResponse.getSettingsDetails());
        renderRequest.setAttribute("show", isCorrectAttempt(renderRequest));
        renderRequest.setAttribute("screenName", buddy.getScreenName());
        // todo: implement
        renderRequest.setAttribute("openedConversations", new ArrayList<Conversation>());

        // Set correct content type
        renderResponse.setContentType(renderRequest.getResponseContentType());

        // Set response to view.jsp
        include(VIEW_JSP_PATH, renderRequest, renderResponse);
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
     * Checks if the server request attempt is correct. In other words checks if the user is signed in, does not
     * use old version of IE and is not on mobile.
     *
     * @param request Request
     * @return true if the request attempt is correct
     */
    private boolean isCorrectAttempt(PortletRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        // Check possible properties
        boolean isSignedIn = themeDisplay.isSignedIn();
        // Todo: this will fail
//        boolean isIe = (BrowserSnifferUtil.isIe((HttpServletRequest) request) && (BrowserSnifferUtil.getMajorVersion((HttpServletRequest) request) < 7));
//        boolean isMobile = BrowserSnifferUtil.isMobile((HttpServletRequest) request);

        return (isSignedIn);
    }
}
