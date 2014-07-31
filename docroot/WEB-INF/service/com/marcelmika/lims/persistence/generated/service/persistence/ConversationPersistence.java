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

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.marcelmika.lims.persistence.generated.model.Conversation;

/**
 * The persistence interface for the conversation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConversationPersistenceImpl
 * @see ConversationUtil
 * @generated
 */
public interface ConversationPersistence extends BasePersistence<Conversation> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ConversationUtil} to access the conversation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the conversation in the entity cache if it is enabled.
	*
	* @param conversation the conversation
	*/
	public void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation);

	/**
	* Caches the conversations in the entity cache if it is enabled.
	*
	* @param conversations the conversations
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> conversations);

	/**
	* Creates a new conversation with the primary key. Does not add the conversation to the database.
	*
	* @param cid the primary key for the new conversation
	* @return the new conversation
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation create(
		long cid);

	/**
	* Removes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cid the primary key of the conversation
	* @return the conversation that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation remove(
		long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException;

	public com.marcelmika.lims.persistence.generated.model.Conversation updateImpl(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the conversation with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchConversationException} if it could not be found.
	*
	* @param cid the primary key of the conversation
	* @return the conversation
	* @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation findByPrimaryKey(
		long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException;

	/**
	* Returns the conversation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cid the primary key of the conversation
	* @return the conversation, or <code>null</code> if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation fetchByPrimaryKey(
		long cid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the conversation where conversationId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchConversationException} if it could not be found.
	*
	* @param conversationId the conversation ID
	* @return the matching conversation
	* @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation findByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException;

	/**
	* Returns the conversation where conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param conversationId the conversation ID
	* @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation fetchByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the conversation where conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param conversationId the conversation ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation fetchByConversationId(
		java.lang.String conversationId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the conversations.
	*
	* @return the conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the conversation where conversationId = &#63; from the database.
	*
	* @param conversationId the conversation ID
	* @return the conversation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Conversation removeByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException;

	/**
	* Removes all the conversations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of conversations where conversationId = &#63;.
	*
	* @param conversationId the conversation ID
	* @return the number of matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public int countByConversationId(java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of conversations.
	*
	* @return the number of conversations
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}