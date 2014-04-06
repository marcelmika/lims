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
