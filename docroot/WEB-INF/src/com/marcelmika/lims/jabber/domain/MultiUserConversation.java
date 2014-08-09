package com.marcelmika.lims.jabber.domain;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:07 AM
 */
public class MultiUserConversation {

    private ConversationType conversationType;


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
    }
}
