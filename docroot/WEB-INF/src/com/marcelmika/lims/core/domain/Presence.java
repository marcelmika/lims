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

        if(presenceDetails == PresenceDetails.ACTIVE) {
            return Presence.STATE_ACTIVE;
        } else if (presenceDetails == PresenceDetails.AWAY) {
            return Presence.STATE_AWAY;
        } else if (presenceDetails == PresenceDetails.DND) {
            return Presence.STATE_DND;
        } else if (presenceDetails == PresenceDetails.OFFLINE) {
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
            return PresenceDetails.ACTIVE;
        } else if (this == Presence.STATE_AWAY) {
            return PresenceDetails.AWAY;
        } else if (this == Presence.STATE_DND) {
            return PresenceDetails.DND;
        } else if (this == Presence.STATE_OFFLINE) {
            return PresenceDetails.OFFLINE;
        } else {
            return PresenceDetails.UNRECOGNIZED;
        }
    }


    private Presence(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
