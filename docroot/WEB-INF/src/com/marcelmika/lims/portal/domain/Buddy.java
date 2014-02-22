package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.events.details.BuddyDetails;

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
    private String status;
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
            buddy.setPortraitId(GetterUtil.getLong(parameterMap.get(KEY_PORTRAIT_ID)));
        }
        // Full name
        if (parameterMap.containsKey(KEY_FULL_NAME)) {
            buddy.setFullName(GetterUtil.getString(parameterMap.get(KEY_FULL_NAME)));
        }
        // Screen name
        if (parameterMap.containsKey(KEY_SCREEN_NAME)) {
            buddy.setScreenName(GetterUtil.getString(parameterMap.get(KEY_SCREEN_NAME)));
        }
        // Password
        if (parameterMap.containsKey(KEY_PASSWORD)) {
            buddy.setPassword(GetterUtil.getString(parameterMap.get(KEY_PASSWORD)));
        }
        // Status
        if (parameterMap.containsKey(KEY_STATUS)) {
            buddy.setStatus(GetterUtil.getString(parameterMap.get(KEY_STATUS)));
        }
        // Settings
        buddy.setSettings(Settings.fromPollerRequest(pollerRequest));

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

        buddy.setBuddyId(user.getUserId());
        buddy.setScreenName(user.getScreenName());
        buddy.setPassword(user.getPassword());

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

        buddy.setBuddyId(user.getUserId());
        buddy.setScreenName(user.getScreenName());
        buddy.setPassword(password);

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
        Long userId = (Long) session.getAttribute(WebKeys.USER_ID);
        // Set property
        buddy.setBuddyId(userId);

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
        buddy.setBuddyId(buddyDetails.getBuddyId());
        buddy.setFullName(buddyDetails.getFullName());
        buddy.setPortraitId(buddyDetails.getPortraitId());
        buddy.setScreenName(buddyDetails.getScreenName());
        buddy.setPassword(buddyDetails.getPassword());
        buddy.setStatus(buddyDetails.getStatus());
        // Relations
        if (buddyDetails.getStatus() != null) {
            buddy.setSettings(Settings.fromSettingsDetails(buddyDetails.getSettingsDetails()));
        }

        return buddy;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
