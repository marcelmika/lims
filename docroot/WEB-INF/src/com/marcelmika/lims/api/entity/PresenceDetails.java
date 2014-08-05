package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 3:11 PM
 */

public enum PresenceDetails {
    ACTIVE(0),
    AWAY(1),
    DND(2),
    OFFLINE(3),
    UNRECOGNIZED(4);

    private int code;

    private PresenceDetails(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
