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

package com.marcelmika.lims.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ParticipantLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ParticipantLocalService
 * @generated
 */
public class ParticipantLocalServiceWrapper implements ParticipantLocalService,
	ServiceWrapper<ParticipantLocalService> {
	public ParticipantLocalServiceWrapper(
		ParticipantLocalService participantLocalService) {
		_participantLocalService = participantLocalService;
	}

	/**
	* Adds the participant to the database. Also notifies the appropriate model listeners.
	*
	* @param participant the participant
	* @return the participant that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Participant addParticipant(
		com.marcelmika.lims.model.Participant participant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.addParticipant(participant);
	}

	/**
	* Creates a new participant with the primary key. Does not add the participant to the database.
	*
	* @param pid the primary key for the new participant
	* @return the new participant
	*/
	public com.marcelmika.lims.model.Participant createParticipant(long pid) {
		return _participantLocalService.createParticipant(pid);
	}

	/**
	* Deletes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pid the primary key of the participant
	* @return the participant that was removed
	* @throws PortalException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Participant deleteParticipant(long pid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.deleteParticipant(pid);
	}

	/**
	* Deletes the participant from the database. Also notifies the appropriate model listeners.
	*
	* @param participant the participant
	* @return the participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Participant deleteParticipant(
		com.marcelmika.lims.model.Participant participant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.deleteParticipant(participant);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _participantLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.marcelmika.lims.model.Participant fetchParticipant(long pid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.fetchParticipant(pid);
	}

	/**
	* Returns the participant with the primary key.
	*
	* @param pid the primary key of the participant
	* @return the participant
	* @throws PortalException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Participant getParticipant(long pid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.getParticipant(pid);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @return the range of participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Participant> getParticipants(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.getParticipants(start, end);
	}

	/**
	* Returns the number of participants.
	*
	* @return the number of participants
	* @throws SystemException if a system exception occurred
	*/
	public int getParticipantsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.getParticipantsCount();
	}

	/**
	* Updates the participant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param participant the participant
	* @return the participant that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Participant updateParticipant(
		com.marcelmika.lims.model.Participant participant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.updateParticipant(participant);
	}

	/**
	* Updates the participant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param participant the participant
	* @param merge whether to merge the participant with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the participant that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Participant updateParticipant(
		com.marcelmika.lims.model.Participant participant, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.updateParticipant(participant, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _participantLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_participantLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _participantLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Adds new participant to the system
	*
	* @param cid           Id of the conversation to which the participant belongs to
	* @param participantId User Id of the participant
	* @return Participant Model
	* @throws SystemException
	*/
	public com.marcelmika.lims.model.Participant addParticipant(
		java.lang.Long cid, java.lang.Long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.addParticipant(cid, participantId);
	}

	/**
	* Given method updates all participants related to the conversation. By updated we mean incrementing of the
	* unread message count if needed and opening the conversation if needed
	*
	* @param cid Id of the conversation related to the participants
	* @throws SystemException
	* @throws PortalException
	*/
	public void updateParticipants(java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_participantLocalService.updateParticipants(cid);
	}

	/**
	* Closes conversation for the particular participant id by setting isOpened flag to false.
	*
	* @param conversationId Conversation which should be closed
	* @param participantId  Participant whose conversation should be closed
	* @throws NoSuchConversationException
	* @throws SystemException
	* @throws NoSuchParticipantException
	*/
	public void closeConversation(java.lang.String conversationId,
		java.lang.Long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException,
			com.marcelmika.lims.NoSuchParticipantException {
		_participantLocalService.closeConversation(conversationId, participantId);
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
	public void resetUnreadMessagesCounter(java.lang.String conversationId,
		java.lang.Long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException,
			com.marcelmika.lims.NoSuchParticipantException {
		_participantLocalService.resetUnreadMessagesCounter(conversationId,
			participantId);
	}

	/**
	* Returns a list of opened conversations where the the user participates
	*
	* @param participantId User Id of the participant
	* @return List of opened conversations
	* @throws SystemException
	*/
	public java.util.List<com.marcelmika.lims.model.Participant> getOpenedConversations(
		java.lang.Long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _participantLocalService.getOpenedConversations(participantId);
	}

	/**
	* Returns a list of users who participates in conversation
	*
	* @param cid Id of the conversation related to the participants
	* @return list of participants
	* @throws NoSuchParticipantException
	* @throws SystemException
	*/
	public java.util.List<com.marcelmika.lims.model.Participant> getConversationParticipants(
		java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchParticipantException {
		return _participantLocalService.getConversationParticipants(cid);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ParticipantLocalService getWrappedParticipantLocalService() {
		return _participantLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedParticipantLocalService(
		ParticipantLocalService participantLocalService) {
		_participantLocalService = participantLocalService;
	}

	public ParticipantLocalService getWrappedService() {
		return _participantLocalService;
	}

	public void setWrappedService(
		ParticipantLocalService participantLocalService) {
		_participantLocalService = participantLocalService;
	}

	private ParticipantLocalService _participantLocalService;
}