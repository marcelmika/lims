package com.marcelmika.lims.hook.domain;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.marcelmika.lims.events.details.BuddyDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:56 PM
 */
public class Buddy {

    private Long buddyId;
    private String screenName;
    private String password;


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
     * Creates new buddy and maps data from buddy details
     *
     * @param buddyDetails BuddyDetails
     * @return BuddyDetails
     */
    public static Buddy fromBuddyDetails(BuddyDetails buddyDetails) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data to user details
        buddy.setBuddyId(buddyDetails.getBuddyId());
        buddy.setScreenName(buddyDetails.getScreenName());
        buddy.setPassword(buddyDetails.getPassword());

        return buddy;
    }

    /**
     * Maps buddy to buddy details
     *
     * @return BuddyDetails
     */
    public BuddyDetails toBuddyDetails() {
        // Create new user details
        BuddyDetails details = new BuddyDetails();
        // Map data from user
        details.setBuddyId(buddyId);
        details.setScreenName(screenName);
        details.setPassword(password);

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
}
