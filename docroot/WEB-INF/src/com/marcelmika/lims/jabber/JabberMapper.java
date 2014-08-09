
package com.marcelmika.lims.jabber;

import org.jivesoftware.smack.packet.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public class JabberMapper {

    /**
     * Maps internal chat status to Jabber presence
     *
     * @param status String
     * @return Mapped Presence
     * @throws JabberException
     * @link http://igniterealtime.org/builds/smack/docs/3.2.2/javadoc/org/jivesoftware/smack/packet/Presence.html
     */
    public static Presence mapStatusToPresence(String status) throws JabberException {
        Presence presence = null;

        // Online
        if (status.equals(JabberKeys.JABBER_STATUS_ONLINE)) {
            presence = new Presence(Presence.Type.available);
            presence.setMode(Presence.Mode.available);
        }
        // Busy
        else if (status.equals(JabberKeys.JABBER_STATUS_BUSY)) {
            presence = new Presence(Presence.Type.available);
            presence.setMode(Presence.Mode.away);
        }
        // Unavailable
        else if (status.equals(JabberKeys.JABBER_STATUS_UNAVAILABLE)) {
            presence = new Presence(Presence.Type.available);
            presence.setMode(Presence.Mode.dnd);
        }
        // Invisible
        else if (status.equals(JabberKeys.JABBER_STATUS_INVISIBLE)) {
            presence = new Presence(Presence.Type.available);
        }
        // Off
        else if (status.equals(JabberKeys.JABBER_STATUS_OFF)) {
            presence = new Presence(Presence.Type.unavailable);
        }
        // Unknown
        else {
            throw new JabberException("Unknown status: " + status);
        }

        return presence;
    }

    public static String mapPresenceToStatus(Presence presence) throws JabberException {
        String status = null;
//        System.out.println("[PRESENCE][TYPE] " + presence.getType());
//        System.out.println("[PRESENCE][MODE] " + presence.getMode());
//        System.out.println("[PRESENCE][STATUS] " + presence.getStatus());


        if (presence.getType().equals(Presence.Type.unavailable)) {
            return JabberKeys.JABBER_STATUS_OFF;
        }

        // Mac Message weird behaviour ....
        if (presence.getMode() == null) {
            return JabberKeys.JABBER_STATUS_ONLINE;
        }


        // Available
        if (presence.getMode().equals(Presence.Mode.available)) {
            status = JabberKeys.JABBER_STATUS_ONLINE;
        }
        // Chat
        if (presence.getMode().equals(Presence.Mode.chat)) {
            status = JabberKeys.JABBER_STATUS_ONLINE;
        }
        // Away
        else if (presence.getMode().equals(Presence.Mode.away)) {
            status = JabberKeys.JABBER_STATUS_BUSY;
        }
        // Extended away
        else if (presence.getMode().equals(Presence.Mode.xa)) {
            status = JabberKeys.JABBER_STATUS_BUSY;
        }
        // Do not Disturb
        else if (presence.getMode().equals(Presence.Mode.dnd)) {
            status = JabberKeys.JABBER_STATUS_UNAVAILABLE;
        }
        // Unknown
        else {
            throw new JabberException("Unknown presence: " + presence);
        }


        return status;
    }
}
