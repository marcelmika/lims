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

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.ConversationTypeDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/6/14
 * Time: 1:09 PM
 */
public enum ConversationType {

    // Values
    SINGLE_USER(0),
    MULTI_USER(1),
    UNRECOGNIZED(2);
    // Keys
    private static final String KEY_SINGLE_USER = "jabber.conversation.type.singleUser";
    private static final String KEY_MULTI_USER = "jabber.conversation.type.multiUser";
    // Current value
    private int code;


    /**
     * Constructor
     *
     * @param code of the conversation type
     */
    private ConversationType(int code) {
        this.code = code;
    }


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new ConversationType from the string key
     *
     * @param value string key
     * @return ConversationType
     */
    public static ConversationType fromKey(String value) {
        // Active
        if (value.equals(KEY_SINGLE_USER)) {
            return ConversationType.SINGLE_USER;
        }
        // Away
        else if (value.equals(KEY_MULTI_USER)) {
            return ConversationType.MULTI_USER;
        }
        // Unknown
        else {
            return ConversationType.UNRECOGNIZED;
        }
    }

    /**
     * Factory method which creates new ConversationType from ConversationTypeDetails
     *
     * @param details conversation type details
     * @return ConversationType
     */
    public static ConversationType fromConversationTypeDetails(ConversationTypeDetails details) {

        if (details == ConversationTypeDetails.SINGLE_USER) {
            return ConversationType.SINGLE_USER;
        } else if (details == ConversationTypeDetails.MULTI_USER) {
            return ConversationType.MULTI_USER;
        } else {
            return ConversationType.UNRECOGNIZED;
        }
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    /**
     * Maps Presence to PresenceDetails
     *
     * @return PresenceDetails
     */
    public ConversationTypeDetails toConversationTypeDetails() {
        if (this == ConversationType.SINGLE_USER) {
            return ConversationTypeDetails.SINGLE_USER;
        } else if (this == ConversationType.MULTI_USER) {
            return ConversationTypeDetails.MULTI_USER;
        } else {
            return ConversationTypeDetails.UNRECOGNIZED;
        }
    }

    public int getCode() {
        return code;
    }
}
