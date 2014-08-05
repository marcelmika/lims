package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 3:11 PM
 */

public enum Presence {
    ACTIVE(0, "presence.online"),
    AWAY(1, "presence.busy"),
    DND(2, "presence.unavailable"),
    OFFLINE(3, "presence.off"),
    UNRECOGNIZED(4, "presence.unrecognized");

    // Code which uniquely determines presence
    private int code;
    // String description of presence
    private String description;

    /**
     * Constructor
     *
     * @param code        which uniquely determines presence
     * @param description string description of presence
     */
    private Presence(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Factory method which creates new Presence from PresenceDetails
     *
     * @param presenceDetails PresenceDetails
     * @return Presence
     */
    public static Presence fromPresenceDetails(PresenceDetails presenceDetails) {

        // Active
        if (presenceDetails == PresenceDetails.ACTIVE) {
            return Presence.ACTIVE;
        }
        // Away
        else if (presenceDetails == PresenceDetails.AWAY) {
            return Presence.AWAY;
        }
        // Dnd
        else if (presenceDetails == PresenceDetails.DND) {
            return Presence.DND;
        }
        // Offline
        else if (presenceDetails == PresenceDetails.OFFLINE) {
            return Presence.OFFLINE;
        }
        // Unrecognized
        else {
            return Presence.UNRECOGNIZED;
        }
    }

    /**
     * Maps Presence to PresenceDetails
     *
     * @return PresenceDetails
     */
    public PresenceDetails toPresenceDetails() {

        // Active
        if (this == Presence.ACTIVE) {
            return PresenceDetails.ACTIVE;
        }
        // Away
        else if (this == Presence.AWAY) {
            return PresenceDetails.AWAY;
        }
        // Dnd
        else if (this == Presence.DND) {
            return PresenceDetails.DND;
        }
        // Offline
        else if (this == Presence.OFFLINE) {
            return PresenceDetails.OFFLINE;
        }
        // Unrecognized
        else {
            return PresenceDetails.UNRECOGNIZED;
        }
    }

    /**
     * Creates Presence from String description
     *
     * @param description String
     * @return Presence
     */
    public static Presence fromDescription(String description) {

        // Check preconditions
        if (description == null) {
            return Presence.UNRECOGNIZED;
        }

        // Active
        if (description.equals(Presence.ACTIVE.getDescription())) {
            return Presence.ACTIVE;
        }
        // Away
        else if (description.equals(Presence.AWAY.getDescription())) {
            return Presence.AWAY;
        }
        // Dnd
        else if (description.equals(Presence.DND.getDescription())) {
            return Presence.DND;
        }
        // Offline
        else if (description.equals(Presence.OFFLINE.getDescription())) {
            return Presence.OFFLINE;
        }
        // Unrecognized
        else {
            return Presence.UNRECOGNIZED;
        }
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
