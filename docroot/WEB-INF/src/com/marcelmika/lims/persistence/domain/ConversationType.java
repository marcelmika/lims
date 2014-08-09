package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.ConversationTypeDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:57 AM
 */
public enum ConversationType {

    // Possible Values
    SINGLE_USER(0),
    MULTI_USER(1),
    UNRECOGNIZED(2);
    // Current value
    private int code;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

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


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    private ConversationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
