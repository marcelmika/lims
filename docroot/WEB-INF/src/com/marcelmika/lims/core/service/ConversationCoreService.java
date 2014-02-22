package com.marcelmika.lims.core.service;

import com.marcelmika.lims.events.conversation.*;

/**
 * Serves as a port to the business logic related to conversation.
 *
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
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event);

    /**
     * Opens existing conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public OpenConversationResponseEvent openConversation(OpenConversationRequestEvent event);

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event);

    /**
     * Removes buddy from the conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event);
}
