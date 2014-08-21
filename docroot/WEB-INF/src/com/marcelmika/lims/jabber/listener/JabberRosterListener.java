/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.JabberMapper;
import com.marcelmika.lims.jabber.JabberUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
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
//            SettingsLocalServiceUtil.changeStatus(fromUser.getUserId(), status);

        } catch (Exception e) {
            log.error("There was an error while changing the presence. Reason: " + e.getMessage());
        }
    }
}
