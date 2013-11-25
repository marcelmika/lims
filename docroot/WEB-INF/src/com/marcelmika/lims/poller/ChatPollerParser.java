
package com.marcelmika.lims.poller;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class ChatPollerParser {

    public List<com.marcelmika.lims.model.Buddy> parseUsersToBuddies(String users) throws Exception {
        List<com.marcelmika.lims.model.Buddy> buddies = new ArrayList<com.marcelmika.lims.model.Buddy>();
        StringTokenizer stringtokenizer = new StringTokenizer(users, ",");
        while (stringtokenizer.hasMoreElements()) {
            int userId = Integer.parseInt(stringtokenizer.nextToken());
            if (userId != 0) {
                User user = UserLocalServiceUtil.fetchUserById(userId);
                com.marcelmika.lims.model.Buddy buddy = com.marcelmika.lims.service.BuddyLocalServiceUtil.mapBuddyFromUser(user);
                buddies.add(buddy);
            }
        }
        return buddies;
    }
}
