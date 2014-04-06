package com.marcelmika.lims.jabber.domain;

import com.marcelmika.lims.api.entity.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 3:11 PM
 */

public enum Presence {
    STATE_ACTIVE(0),
    STATE_AWAY(1),
    STATE_DND(2),
    STATE_OFFLINE(3),
    STATE_UNRECOGNIZED(4);

    private int code;

    private Presence(int code) {
        this.code = code;
    }

    /**
     * Factory method which creates new Presence from PresenceDetails
     *
     * @param presenceDetails PresenceDetails
     * @return Presence
     */
    public static Presence fromPresenceDetails(PresenceDetails presenceDetails) {

        if (presenceDetails == PresenceDetails.STATE_ACTIVE) {
            return Presence.STATE_ACTIVE;
        } else if (presenceDetails == PresenceDetails.STATE_AWAY) {
            return Presence.STATE_AWAY;
        } else if (presenceDetails == PresenceDetails.STATE_DND) {
            return Presence.STATE_DND;
        } else if (presenceDetails == PresenceDetails.STATE_OFFLINE) {
            return Presence.STATE_OFFLINE;
        } else {
            return Presence.STATE_UNRECOGNIZED;
        }
    }

    public static Presence fromSmackPresence(org.jivesoftware.smack.packet.Presence smackPresence) {
        if (smackPresence.getType().equals(org.jivesoftware.smack.packet.Presence.Type.unavailable)) {
            return Presence.STATE_OFFLINE;
        } else if (smackPresence.getMode().equals(org.jivesoftware.smack.packet.Presence.Mode.available)) {
            return Presence.STATE_ACTIVE;
        } else if (smackPresence.getMode().equals(org.jivesoftware.smack.packet.Presence.Mode.away)) {
            return Presence.STATE_AWAY;
        } else if (smackPresence.getMode().equals(org.jivesoftware.smack.packet.Presence.Mode.dnd)) {
            return Presence.STATE_DND;
        } else {
            return Presence.STATE_UNRECOGNIZED;
        }
    }

    public int getCode() {
        return code;
    }

    /**
     * Maps Presence to PresenceDetails
     *
     * @return PresenceDetails
     */
    public PresenceDetails toPresenceDetails() {
        if (this == Presence.STATE_ACTIVE) {
            return PresenceDetails.STATE_ACTIVE;
        } else if (this == Presence.STATE_AWAY) {
            return PresenceDetails.STATE_AWAY;
        } else if (this == Presence.STATE_DND) {
            return PresenceDetails.STATE_DND;
        } else if (this == Presence.STATE_OFFLINE) {
            return PresenceDetails.STATE_OFFLINE;
        } else {
            return PresenceDetails.STATE_UNRECOGNIZED;
        }
    }

    public org.jivesoftware.smack.packet.Presence toSmackPresence() {

        // Get connection manager from store
        org.jivesoftware.smack.packet.Presence.Type type = org.jivesoftware.smack.packet.Presence.Type.available;
        String statusDescription = "";
        int priority = 100;
        org.jivesoftware.smack.packet.Presence.Mode mode = org.jivesoftware.smack.packet.Presence.Mode.xa;

        // If the Status is OFFLINE, the getPresence() method
        // returns null, because Presence hasn't state for OFFLINE
        // You have to logout Buddy instead of call getPresence()
        if (this == Presence.STATE_OFFLINE) {
            type = org.jivesoftware.smack.packet.Presence.Type.unavailable;
            return new org.jivesoftware.smack.packet.Presence(type, statusDescription, priority, mode);
        }

        // Active
        if (this == Presence.STATE_ACTIVE) {
            mode = org.jivesoftware.smack.packet.Presence.Mode.available;
        }
        // Away
        else if (this == Presence.STATE_AWAY) {
            mode = org.jivesoftware.smack.packet.Presence.Mode.away;
        }
        // Dnd
        else if (this == Presence.STATE_DND) {
            mode = org.jivesoftware.smack.packet.Presence.Mode.dnd;
        }

        return new org.jivesoftware.smack.packet.Presence(type, statusDescription, priority, mode);
    }

}
