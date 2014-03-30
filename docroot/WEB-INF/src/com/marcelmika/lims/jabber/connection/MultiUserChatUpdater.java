package com.marcelmika.lims.jabber.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.jabber.JabberUtil;
import com.marcelmika.lims.util.PortletPropsValues;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.Collection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 *
 */
public class MultiUserChatUpdater {

    private static Log log = LogFactoryUtil.getLog(MultiUserChatUpdater.class);
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void start() {
        log.info("Jabber multi user chat updater started");

        if (connection != null) {
            log.info("logging");
            try {
                update();
            } catch (XMPPException e) {
                log.error(e);
            }
        }

        log.info("Jabber multi user chat updater ended");
    }

    public void update() throws XMPPException {
        Collection<HostedRoom> hostedRooms = MultiUserChat.getHostedRooms(connection, PortletPropsValues.JABBER_SERVICE_MULTICHAT_NAME);

        // Add public room to users list
        for (HostedRoom hostedRoom : hostedRooms) {
            String roomJID = hostedRoom.getJid();
            String roomName = JabberUtil.getFullRoomId(hostedRoom.getName());


            // TODO: Add only if the user is owner
            if (roomJID.startsWith(PortletPropsValues.JABBER_PP_PREFIX_PUBLIC)) {

                MultiUserChat muc = new MultiUserChat(connection, roomName);
                muc.join("proxy", "proxy");
                log.info("Logging into: " + roomName);

                // TODO: Sync db if user is owner
                Collection<Affiliate> affiliates = muc.getOwners();
                for (Affiliate affiliate : affiliates) {
                    log.info("Affiliates: " + affiliate.getJid());
                }

                muc.leave();
            }
        }
    }


}
