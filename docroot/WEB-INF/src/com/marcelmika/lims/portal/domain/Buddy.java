package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:56 PM
 */
public class Buddy {

    // Log
    private static Log log = LogFactoryUtil.getLog(Buddy.class);

    // Constants
    private static final String KEY_PORTRAIT_ID = "portraitId";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_SCREEN_NAME = "screenName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STATUS = "status";

    // Properties
    private Long buddyId;
    private Long portraitId;
    private String fullName;
    private String screenName;
    private String password;
    /** @deprecated */
    private String status;
    private Presence presence;
    private Settings settings;


    /**
     * Factory method which creates new Buddy object from the PollerRequest
     *
     * @param pollerRequest request
     * @return Buddy
     */
    public static Buddy fromPollerRequest(PollerRequest pollerRequest) {
        // Map contains all parameters from request
        Map<String, String> parameterMap = pollerRequest.getParameterMap();
        // Create new buddy
        Buddy buddy = new Buddy();
        // BuddyID
        buddy.setBuddyId(pollerRequest.getUserId());
        // Portrait Id
        if (parameterMap.containsKey(KEY_PORTRAIT_ID)) {
            buddy.portraitId = GetterUtil.getLong(parameterMap.get(KEY_PORTRAIT_ID));
        }
        // Full name
        if (parameterMap.containsKey(KEY_FULL_NAME)) {
            buddy.fullName = GetterUtil.getString(parameterMap.get(KEY_FULL_NAME));
        }
        // Screen name
        if (parameterMap.containsKey(KEY_SCREEN_NAME)) {
            buddy.screenName = GetterUtil.getString(parameterMap.get(KEY_SCREEN_NAME));
        }
        // Password
        if (parameterMap.containsKey(KEY_PASSWORD)) {
            buddy.password = GetterUtil.getString(parameterMap.get(KEY_PASSWORD));
        }
        // Status
        if (parameterMap.containsKey(KEY_STATUS)) {
            // TODO: Deprecated, will be renamed to presence
            buddy.status = GetterUtil.getString(parameterMap.get(KEY_STATUS));
            String key = GetterUtil.getString(parameterMap.get(KEY_STATUS));
            buddy.presence = Presence.fromKey(key);

            log.info("BUDDY PRESENCE: " + buddy.presence);
        }
        // Settings
        buddy.settings = Settings.fromPollerRequest(pollerRequest);

        return buddy;
    }

    public static Buddy fromRenderRequest(RenderRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        Buddy buddy = new Buddy();
        buddy.buddyId = themeDisplay.getUserId();
        buddy.screenName = themeDisplay.getUser().getScreenName();
        buddy.fullName = themeDisplay.getUser().getFullName();

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from the PollerRequest
     *
     * @param request request
     * @return Buddy
     */
    public static Buddy fromResourceRequest(ResourceRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        // Map contains all parameters from request
        Map<String, String[]> parameterMap = request.getParameterMap();
        // Create new buddy
        Buddy buddy = new Buddy();
        // BuddyID
        buddy.buddyId = themeDisplay.getUserId();
        // Portrait Id
        if (parameterMap.containsKey(KEY_PORTRAIT_ID)) {
            buddy.portraitId = GetterUtil.getLong(parameterMap.get(KEY_PORTRAIT_ID));
        }
        // Full name
        if (parameterMap.containsKey(KEY_FULL_NAME)) {
            buddy.fullName = GetterUtil.getString(parameterMap.get(KEY_FULL_NAME));
        }
        // Screen name
        if (parameterMap.containsKey(KEY_SCREEN_NAME)) {
            buddy.screenName = GetterUtil.getString(parameterMap.get(KEY_SCREEN_NAME));
        }
        // Password
        if (parameterMap.containsKey(KEY_PASSWORD)) {
            buddy.password = GetterUtil.getString(parameterMap.get(KEY_PASSWORD));
        }
        // Status
        if (parameterMap.containsKey(KEY_STATUS)) {
            // TODO: Deprecated, will be renamed to presence
            buddy.status = GetterUtil.getString(parameterMap.get(KEY_STATUS));
            String key = GetterUtil.getString(parameterMap.get(KEY_STATUS));
            buddy.presence = Presence.fromKey(key);

            log.info("BUDDY PRESENCE: " + buddy.presence);
        }
        // Settings
//        buddy.settings = Settings.fromPollerRequest(request);

        return buddy;
    }


    /**
     * Factory method which creates new Buddy object from portal User
     *
     * @param user User
     * @return Buddy
     */
    public static Buddy fromPortalUser(User user) {
        // Create new empty buddy
        Buddy buddy = new Buddy();

        buddy.buddyId = user.getUserId();
        buddy.screenName = user.getScreenName();
        buddy.password = user.getPassword();

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from HttpServletRequest
     *
     * @param request HttpServletRequest
     * @return Buddy
     * @throws SystemException
     * @throws PortalException
     */
    public static Buddy fromPortalServletRequest(HttpServletRequest request) throws SystemException, PortalException {
        // Create new empty buddy
        Buddy buddy = new Buddy();
        // Get user from the request
        User user = PortalUtil.getUser(request);
        // Get password from request
        String password = PortalUtil.getUserPassword(request);

        buddy.buddyId = user.getUserId();
        buddy.screenName = user.getScreenName();
        buddy.password = password;

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from HttpSession
     *
     * @param session HttpSession
     * @return Buddy
     */
    public static Buddy fromHttpSession(HttpSession session) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Get user ID from http session
        buddy.buddyId = (Long) session.getAttribute(WebKeys.USER_ID);

        return buddy;
    }


    /**
     * Factory method which creates new Buddy object from BuddyDetails
     *
     * @param buddyDetails BuddyDetails
     * @return User
     */
    public static Buddy fromBuddyDetails(BuddyDetails buddyDetails) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data to user details
        buddy.buddyId = buddyDetails.getBuddyId();
        buddy.fullName = buddyDetails.getFullName();
        buddy.portraitId = buddyDetails.getPortraitId();
        buddy.screenName = buddyDetails.getScreenName();
        buddy.password = buddyDetails.getPassword();
        buddy.status = buddyDetails.getStatus();
        // Relations
        if (buddyDetails.getPresenceDetails() != null) {
            buddy.presence = Presence.fromPresenceDetails(buddyDetails.getPresenceDetails());
        }

        if (buddyDetails.getStatus() != null) {
            buddy.settings = Settings.fromSettingsDetails(buddyDetails.getSettingsDetails());
        }

        return buddy;
    }

    /**
     * Factory method which creates new list of Buddies from the list of BuddyDetails
     *
     * @param detailsList list of buddy details
     * @return List<Buddy> of buddies
     */
    public static List<Buddy> fromBuddyDetailsList(List<BuddyDetails> detailsList) {
        // Create new list of buddies
        List<Buddy> buddies = new ArrayList<Buddy>();

        // Iterate through details and create buddy based on that
        for (BuddyDetails details : detailsList) {
            buddies.add(Buddy.fromBuddyDetails(details));
        }

        return buddies;
    }

    /**
     * Maps user to user details
     *
     * @return UserDetails
     */
    public BuddyDetails toBuddyDetails() {
        // Create new user details
        BuddyDetails details = new BuddyDetails();
        // Map data from user
        details.setBuddyId(buddyId);
        details.setFullName(fullName);
        details.setPortraitId(portraitId);
        details.setScreenName(screenName);
        details.setPassword(password);
        details.setStatus(status);

        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        if (settings != null) {
            details.setSettingsDetails(settings.toSettingsDetails());
        }

        return details;
    }


    public Long getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(Long buddyId) {
        this.buddyId = buddyId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @JSON(include = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(Long portraitId) {
        this.portraitId = portraitId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /** @deprecated */
    public String getStatus() {
        return status;
    }

    /** @deprecated */
    public void setStatus(String status) {
        this.status = status;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
