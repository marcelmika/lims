package com.marcelmika.lims.jabber.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.JabberMapper;
import com.marcelmika.lims.jabber.JabberUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.service.SettingsLocalServiceUtil;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class JabberRosterListener implements RosterListener {

    private static Log log = LogFactoryUtil.getLog(JabberRosterListener.class);

    private long userId;
    private long companyId;


    public JabberRosterListener(long userId, long companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }

    @Override
    public void entriesAdded(Collection<String> strings) {

    }

    @Override
    public void entriesUpdated(Collection<String> strings) {

    }

    @Override
    public void entriesDeleted(Collection<String> strings) {

    }

    @Override
    public void presenceChanged(Presence presence) {
        try {
            // Get the screen name from presence
            String fromScreenName = JabberUtil.getScreenName(presence.getFrom());
            // Find user by his/her company ID and screen name
            User fromUser = UserLocalServiceUtil.getUserByScreenName(companyId, fromScreenName);
            // Map Jabber status to LIMS status
            String status = JabberMapper.mapPresenceToStatus(presence);
            // Save to settings
            SettingsLocalServiceUtil.changeStatus(fromUser.getUserId(), status);

        } catch (Exception e) {
            log.error("There was an error while changing the presence. Reason: " + e.getMessage());
        }
    }
}
