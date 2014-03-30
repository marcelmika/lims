package com.marcelmika.lims.core.domain;

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

    public int getCode() {
        return code;
    }
}
