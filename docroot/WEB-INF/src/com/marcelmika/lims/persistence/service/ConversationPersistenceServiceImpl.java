package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.UserFinderUtil;
import com.marcelmika.lims.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.lims.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Conversation;
import com.marcelmika.lims.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.service.OpenedConversationLocalServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:48 AM
 */
public class ConversationPersistenceServiceImpl implements ConversationPersistenceService {


    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {

        Buddy creator = Buddy.fromBuddyDetails(event.getCreator());
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());

        // Save to persistence
        try {
            ConversationLocalServiceUtil.addConversation(creator.getBuddyId(), conversation.getConversationId(),
                    conversation.getConversationType().toString(),"","");


            // Open conversation for the owner
            OpenedConversationLocalServiceUtil.openConversation(creator.getBuddyId(), conversation.getConversationId());
            // Open conversation for all participants
            for (Buddy participant : conversation.getParticipants()) {
                OpenedConversationLocalServiceUtil.openConversation(
                        participant.getBuddyId(), conversation.getConversationId()
                );
            }

        }
        // Failure
        catch (SystemException exception) {
            return CreateConversationResponseEvent.createConversationFailure(
                    CreateConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }

        // Success
        return CreateConversationResponseEvent.createConversationSuccess();
    }
}
