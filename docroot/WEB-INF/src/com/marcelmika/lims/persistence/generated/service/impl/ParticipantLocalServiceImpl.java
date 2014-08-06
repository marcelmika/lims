/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.marcelmika.lims.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.lims.persistence.generated.NoSuchConversationException;
import com.marcelmika.lims.persistence.generated.NoSuchParticipantException;
import com.marcelmika.lims.persistence.generated.model.Conversation;
import com.marcelmika.lims.persistence.generated.model.Panel;
import com.marcelmika.lims.persistence.generated.model.Participant;
import com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.PanelLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.base.ParticipantLocalServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the participant local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.ParticipantLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.ParticipantLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil
 */
public class ParticipantLocalServiceImpl extends ParticipantLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil} to access the participant local service.
	 */

    /**
     * Adds new participant to the system
     *
     * @param cid           Id of the conversation to which the participant belongs to
     * @param participantId User Id of the participant
     * @return Participant Model
     * @throws com.liferay.portal.kernel.exception.SystemException
     */
    public Participant addParticipant(Long cid, Long participantId) throws SystemException {
        // Fetch possible existing conversation
        Participant participantModel;
        try {
            // Try to find participant
            participantModel = participantPersistence.findByCidParticipantId(cid, participantId);
        } catch (NoSuchParticipantException e) {
            // No participant was found, so create a new one
            participantModel = participantPersistence.create(counterLocalService.increment());
            participantModel.setCid(cid);
            participantModel.setParticipantId(participantId);
            participantModel.setUnreadMessagesCount(0);
        }

        // Open conversation for participant
        participantModel.setIsOpened(true);

        // Save
        participantPersistence.update(participantModel, false);

        return participantModel;
    }

    /**
     * Given method updates all participants related to the conversation. By updated we mean incrementing of the
     * unread message count if needed and opening the conversation if needed
     *
     * @param cid Id of the conversation related to the participants
     * @throws SystemException
     * @throws com.liferay.portal.kernel.exception.PortalException
     */
    public void updateParticipants(Long cid) throws SystemException, PortalException {
        // Fetch all participants by the conversation id
        List<Participant> participantList = participantPersistence.findByCid(cid);
        Conversation conversation = ConversationLocalServiceUtil.getConversation(cid);

        for (Participant participant : participantList) {
            Panel panel = PanelLocalServiceUtil.getPanelByUser(participant.getParticipantId());
            // Update message count only if user's currently opened panel is different then the one with conversation.
            // We don't want to increment unread message count for the conversation which is currently presented to
            // the user
            if (!conversation.getConversationId().equals(panel.getActivePanelId())) {
                int unreadMessageCount = participant.getUnreadMessagesCount();
                participant.setUnreadMessagesCount(++unreadMessageCount);
            }
            // Open the conversation. In other words if the user closed the conversation open it again for him.
            // By opening we doesn't mean opening the panel. The panel may be minimized but the conversation is still
            // opened.
            participant.setIsOpened(true);
            // Save the participant
            participantPersistence.update(participant, false);
        }
    }

    /**
     * Closes conversation for the particular participant id by setting isOpened flag to false.
     *
     * @param conversationId Conversation which should be closed
     * @param participantId  Participant whose conversation should be closed
     * @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException
     * @throws SystemException
     * @throws NoSuchParticipantException
     */
    public void closeConversation(String conversationId, Long participantId)
            throws NoSuchConversationException, SystemException, NoSuchParticipantException {

        // Find conversation
        Conversation conversation = conversationPersistence.findByConversationId(conversationId);

        // Find participant
        Participant participant = participantPersistence.findByCidParticipantId(conversation.getCid(), participantId);

        // Close conversation
        participant.setIsOpened(false);

        // Since the panel was closed no active panel is currently there
        Panel panel = PanelLocalServiceUtil.getPanelByUser(participant.getParticipantId());
        panel.setActivePanelId("");
        panelPersistence.update(panel, false);

        // Save
        participantPersistence.update(participant, false);
    }

    /**
     * Resets counter of unread messages for the user who participates in the given conversation
     *
     * @param conversationId Conversation where the counter should be reset
     * @param participantId  Participant whose counter should be reset
     * @throws NoSuchParticipantException
     * @throws SystemException
     * @throws NoSuchConversationException
     */
    public void resetUnreadMessagesCounter(String conversationId, Long participantId)
            throws NoSuchParticipantException, SystemException, NoSuchConversationException {

        // Find conversation
        Conversation conversation = conversationPersistence.findByConversationId(conversationId);

        // Find participant
        Participant participant = participantPersistence.findByCidParticipantId(conversation.getCid(), participantId);

        // Set counter to zero
        participant.setUnreadMessagesCount(0);

        // Save
        participantPersistence.update(participant, false);
    }


    /**
     * Returns a list of opened conversations where the the user participates
     *
     * @param participantId User Id of the participant
     * @return List of opened conversations
     * @throws SystemException
     */
    public List<Participant> getOpenedConversations(Long participantId) throws SystemException {
        return participantPersistence.findByParticipantIdIsOpened(participantId, true);
    }

    /**
     * Returns a list of users who participates in conversation
     *
     * @param cid Id of the conversation related to the participants
     * @return list of participants
     * @throws NoSuchParticipantException
     * @throws SystemException
     */
    public List<Participant> getConversationParticipants(Long cid) throws NoSuchParticipantException, SystemException {
        return participantPersistence.findByCid(cid);
    }

    /**
     * Returns particular participant based on the id
     *
     * @param participantId Id of the participant
     * @return participant
     * @throws NoSuchParticipantException
     * @throws SystemException
     */
    public Participant getParticipant(Long cid, Long participantId) throws NoSuchParticipantException, SystemException {
        return participantPersistence.findByCidParticipantId(cid, participantId);
    }
}