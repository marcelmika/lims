package com.marcelmika.lims.portal.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.model.Buddy;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.portal.processor.PortletProcessor;
import com.marcelmika.lims.service.BuddyLocalServiceUtil;
import com.marcelmika.lims.util.ChatUtil;

import javax.portlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class LIMSPortlet extends MVCPortlet {

    // TODO: Inject
    PortletProcessor processor = new PortletProcessor();


    private boolean isCorrectAttempt(RenderRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        boolean isSignedIn = themeDisplay.isSignedIn();
        // @todo: Implement browser sniffer
//        boolean isIe = (BrowserSnifferUtil.isIe((HttpServletRequest) request) && (BrowserSnifferUtil.getMajorVersion((HttpServletRequest) request) < 7));
//        boolean isMobile = BrowserSnifferUtil.isMobile((HttpServletRequest)request);

        return (isSignedIn);
    }

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {
        // Get user info
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long userId = themeDisplay.getUserId();
        String screenName = themeDisplay.getUser().getScreenName();

        // Prepare objects
        Settings settings = null;
        List<Conversation> openedConversations = null;

        // Fulfill them
        try {
            settings = ChatUtil.getSettings(userId);
            openedConversations = ChatUtil.getOpenedConversations(userId, true);
        } catch (Exception ex) {
//            System.out.println(ex);
        }

        // Pass them to jsp
        renderRequest.setAttribute("settings", settings);
        renderRequest.setAttribute("openedConversations", openedConversations);
        renderRequest.setAttribute("show", isCorrectAttempt(renderRequest));
        renderRequest.setAttribute("screenName", screenName);

        // Set response to view.jsp
        String url = "/view.jsp";
        renderResponse.setContentType(renderRequest.getResponseContentType());
        include(url, renderRequest, renderResponse);
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        response.setContentType("text/html");

        // Security check
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        if (!themeDisplay.isSignedIn()) {
            return;
        }



        processor.getGroupList(request, response);




//        PrintWriter writer = response.getWriter();
//
//        // Get parameter send via ajax
//        String query = request.getParameter("query");
//        if (query != null) {
//            // Compose json array from buddies found based on the query
//            JSONArray buddiesJSON = JSONFactoryUtil.createJSONArray();
//            List<Buddy> buddies = BuddyLocalServiceUtil.findByQuery(query);
//            for (Buddy buddy : buddies) {
//                buddiesJSON.put(buddy.toJSON());
//            }
//
//            writer.print(buddiesJSON);
//        }
    }
}
