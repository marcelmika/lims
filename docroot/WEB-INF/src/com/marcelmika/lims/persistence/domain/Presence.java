package com.marcelmika.lims.persistence.domain;

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

    private static final String STATUS_ACTIVE = "jabber.status.online";
    private static final String STATUS_AWAY = "jabber.status.busy";
    private static final String STATUS_DND = "jabber.status.unavailable";
    private static final String STATUS_OFFLINE = "jabber.status.off";
    private static final String STATUS_UNRECOGNIZED = "jabber.status.unrecognized";

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

    /**
     * Creates Presence from Status
     *
     * @param status Status
     * @return Presence
     */
    public static Presence fromStatus(String status) {
        // Check preconditions
        if (status == null) {
            return Presence.STATE_UNRECOGNIZED;
        }

        // Active
        if (status.equals(STATUS_ACTIVE)) {
            return Presence.STATE_ACTIVE;
        }
        // Away
        else if (status.equals(STATUS_AWAY)) {
            return Presence.STATE_AWAY;
        }
        // Dnd
        else if (status.equals(STATUS_DND)) {
            return Presence.STATE_DND;
        }
        // Offline
        else if (status.equals(STATUS_OFFLINE)) {
            return Presence.STATE_OFFLINE;
        }
        // Unrecognized
        else {
            return Presence.STATE_UNRECOGNIZED;
        }
    }

    public String toStatus() {

        if (this == Presence.STATE_OFFLINE) {
            return STATUS_OFFLINE;
        }

        // Active
        if (this == Presence.STATE_ACTIVE) {
            return STATUS_ACTIVE;
        }
        // Away
        else if (this == Presence.STATE_AWAY) {
            return STATUS_AWAY;
        }
        // Dnd
        else if (this == Presence.STATE_DND) {
            return STATUS_DND;
        }
        // Unrecognized
        else {
            return STATUS_UNRECOGNIZED;
        }
    }
}
