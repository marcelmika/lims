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

package com.marcelmika.lims.persistence.generated.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ConversationLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ConversationLocalService
 * @generated
 */
public class ConversationLocalServiceWrapper implements ConversationLocalService,
	ServiceWrapper<ConversationLocalService> {
	public ConversationLocalServiceWrapper(
		ConversationLocalService conversationLocalService) {
		_conversationLocalService = conversationLocalService;
	}

	/**
	* Adds the conversation to the database. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @return the conversation that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation addConversation(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.addConversation(conversation);
	}

	/**
	* Creates a new conversation with the primary key. Does not add the conversation to the database.
	*
	* @param cid the primary key for the new conversation
	* @return the new conversation
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation createConversation(
		long cid) {
		return _conversationLocalService.createConversation(cid);
	}

	/**
	* Deletes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cid the primary key of the conversation
	* @return the conversation that was removed
	* @throws PortalException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation deleteConversation(
		long cid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.deleteConversation(cid);
	}

	/**
	* Deletes the conversation from the database. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @return the conversation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation deleteConversation(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.deleteConversation(conversation);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _conversationLocalService.dynamicQuery();
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
		return _conversationLocalService.dynamicQuery(dynamicQuery);
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
		return _conversationLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _conversationLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _conversationLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.marcelmika.lims.persistence.generated.model.Conversation fetchConversation(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.fetchConversation(cid);
	}

	/**
	* Returns the conversation with the primary key.
	*
	* @param cid the primary key of the conversation
	* @return the conversation
	* @throws PortalException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation getConversation(
		long cid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.getConversation(cid);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @return the range of conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> getConversations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.getConversations(start, end);
	}

	/**
	* Returns the number of conversations.
	*
	* @return the number of conversations
	* @throws SystemException if a system exception occurred
	*/
	public int getConversationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.getConversationsCount();
	}

	/**
	* Updates the conversation in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @return the conversation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation updateConversation(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.updateConversation(conversation);
	}

	/**
	* Updates the conversation in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @param merge whether to merge the conversation with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the conversation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation updateConversation(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.updateConversation(conversation, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _conversationLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_conversationLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _conversationLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public com.marcelmika.lims.persistence.generated.model.Conversation addConversation(
		java.lang.String conversationId, java.lang.String conversationType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.addConversation(conversationId,
			conversationType);
	}

	public com.marcelmika.lims.persistence.generated.model.Conversation getConversation(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversationLocalService.getConversation(conversationId);
	}

	public void updateConversationTimestamp(long cid)
		throws java.lang.Exception {
		_conversationLocalService.updateConversationTimestamp(cid);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ConversationLocalService getWrappedConversationLocalService() {
		return _conversationLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedConversationLocalService(
		ConversationLocalService conversationLocalService) {
		_conversationLocalService = conversationLocalService;
	}

	public ConversationLocalService getWrappedService() {
		return _conversationLocalService;
	}

	public void setWrappedService(
		ConversationLocalService conversationLocalService) {
		_conversationLocalService = conversationLocalService;
	}

	private ConversationLocalService _conversationLocalService;
}