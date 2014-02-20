package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.conversation.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:01 PM
 */
public interface ConversationCoreService {

    /**
     * Creates new conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public ConversationCreateResponseEvent createConversation(ConversationCreateRequestEvent event);

    /**
     * Opens existing conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public ConversationOpenResponseEvent openConversation(ConversationOpenResponseEvent event);

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public ConversationCloseResponseEvent closeConversation(ConversationCloseRequestEvent event);

    /**
     * Removes buddy from the conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public ConversationLeaveResponseEvent leaveConversation(ConversationLeaveRequestEvent event);
}
