package com.marcelmika.lims.core.domain;

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

    /**
     * Factory method which creates new Presence from PresenceDetails
     *
     * @param presenceDetails PresenceDetails
     * @return Presence
     */
    public static Presence fromPresenceDetails(PresenceDetails presenceDetails) {

        if(presenceDetails == PresenceDetails.STATE_ACTIVE) {
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

    /**
     * Maps Presence to PresenceDetails
     *
     * @return PresenceDetails
     */
    public PresenceDetails toPresenceDetails() {
        if(this == Presence.STATE_ACTIVE) {
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


    private Presence(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
