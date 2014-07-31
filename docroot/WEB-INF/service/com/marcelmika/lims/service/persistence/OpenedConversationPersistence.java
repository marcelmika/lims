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

package com.marcelmika.lims.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the opened conversation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OpenedConversationPersistenceImpl
 * @see OpenedConversationUtil
 * @generated
 */
public interface OpenedConversationPersistence extends BasePersistence<OpenedConversation> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OpenedConversationUtil} to access the opened conversation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the opened conversation in the entity cache if it is enabled.
	*
	* @param openedConversation the opened conversation
	*/
	public void cacheResult(
		com.marcelmika.lims.model.OpenedConversation openedConversation);

	/**
	* Caches the opened conversations in the entity cache if it is enabled.
	*
	* @param openedConversations the opened conversations
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.model.OpenedConversation> openedConversations);

	/**
	* Creates a new opened conversation with the primary key. Does not add the opened conversation to the database.
	*
	* @param ocid the primary key for the new opened conversation
	* @return the new opened conversation
	*/
	public com.marcelmika.lims.model.OpenedConversation create(long ocid);

	/**
	* Removes the opened conversation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ocid the primary key of the opened conversation
	* @return the opened conversation that was removed
	* @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation remove(long ocid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	public com.marcelmika.lims.model.OpenedConversation updateImpl(
		com.marcelmika.lims.model.OpenedConversation openedConversation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the opened conversation with the primary key or throws a {@link com.marcelmika.lims.NoSuchOpenedConversationException} if it could not be found.
	*
	* @param ocid the primary key of the opened conversation
	* @return the opened conversation
	* @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation findByPrimaryKey(
		long ocid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	/**
	* Returns the opened conversation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ocid the primary key of the opened conversation
	* @return the opened conversation, or <code>null</code> if a opened conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation fetchByPrimaryKey(
		long ocid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the opened conversation where userId = &#63; and conversationId = &#63; or throws a {@link com.marcelmika.lims.NoSuchOpenedConversationException} if it could not be found.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the matching opened conversation
	* @throws com.marcelmika.lims.NoSuchOpenedConversationException if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation findByUserId_ConversationId(
		long userId, java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	/**
	* Returns the opened conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation fetchByUserId_ConversationId(
		long userId, java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the opened conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation fetchByUserId_ConversationId(
		long userId, java.lang.String conversationId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the opened conversations where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.OpenedConversation> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the opened conversations where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of opened conversations
	* @param end the upper bound of the range of opened conversations (not inclusive)
	* @return the range of matching opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.OpenedConversation> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the opened conversations where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of opened conversations
	* @param end the upper bound of the range of opened conversations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.OpenedConversation> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first opened conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching opened conversation
	* @throws com.marcelmika.lims.NoSuchOpenedConversationException if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	/**
	* Returns the first opened conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last opened conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching opened conversation
	* @throws com.marcelmika.lims.NoSuchOpenedConversationException if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	/**
	* Returns the last opened conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the opened conversations before and after the current opened conversation in the ordered set where userId = &#63;.
	*
	* @param ocid the primary key of the current opened conversation
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next opened conversation
	* @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation[] findByUserId_PrevAndNext(
		long ocid, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	/**
	* Returns all the opened conversations.
	*
	* @return the opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.OpenedConversation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the opened conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of opened conversations
	* @param end the upper bound of the range of opened conversations (not inclusive)
	* @return the range of opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.OpenedConversation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the opened conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of opened conversations
	* @param end the upper bound of the range of opened conversations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.OpenedConversation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the opened conversation where userId = &#63; and conversationId = &#63; from the database.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the opened conversation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.OpenedConversation removeByUserId_ConversationId(
		long userId, java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchOpenedConversationException;

	/**
	* Removes all the opened conversations where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the opened conversations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of opened conversations where userId = &#63; and conversationId = &#63;.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the number of matching opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId_ConversationId(long userId,
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of opened conversations where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of opened conversations.
	*
	* @return the number of opened conversations
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}