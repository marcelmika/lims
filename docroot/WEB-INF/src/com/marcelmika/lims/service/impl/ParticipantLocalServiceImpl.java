/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.marcelmika.lims.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.lims.model.Conversation;
import com.marcelmika.lims.model.Panel;
import com.marcelmika.lims.model.Participant;
import com.marcelmika.lims.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.service.PanelLocalServiceUtil;
import com.marcelmika.lims.service.base.ParticipantLocalServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the participant local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.service.ParticipantLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.service.base.ParticipantLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.ParticipantLocalServiceUtil
 */
public class ParticipantLocalServiceImpl extends ParticipantLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.service.ParticipantLocalServiceUtil} to access the participant local service.
	 */

    /**
     * Adds new participant to the system
     *
     * @param cid           Id of the conversation to which the participant belongs to
     * @param participantId User Id of the participant
     * @return Participant Model
     * @throws SystemException
     */
    public Participant addParticipant(Long cid, Long participantId) throws SystemException {
        // Fetch possible existing conversation
        Participant participantModel = participantPersistence.fetchBycid_ParticipantId(cid, participantId);

        if (participantModel == null) {
            participantModel = participantPersistence.create(counterLocalService.increment());
            participantModel.setCid(cid);
            participantModel.setParticipantId(participantId);
            participantModel.setIsOpened(true);
            participantModel.setUnreadMessagesCount(0);
            participantPersistence.update(participantModel, false);
        }

        return participantModel;
    }

    /**
     * Given method updates all participants related to the conversation. By updated we mean incrementing of the
     * unread message count if needed and opening the conversation if needed
     *
     * @param cid Id of the conversation related to the participants
     * @throws SystemException
     * @throws PortalException
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
}