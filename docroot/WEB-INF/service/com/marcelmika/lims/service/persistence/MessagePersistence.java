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

import com.marcelmika.lims.model.Message;

/**
 * The persistence interface for the message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MessagePersistenceImpl
 * @see MessageUtil
 * @generated
 */
public interface MessagePersistence extends BasePersistence<Message> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MessageUtil} to access the message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message in the entity cache if it is enabled.
	*
	* @param message the message
	*/
	public void cacheResult(com.marcelmika.lims.model.Message message);

	/**
	* Caches the messages in the entity cache if it is enabled.
	*
	* @param messages the messages
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.model.Message> messages);

	/**
	* Creates a new message with the primary key. Does not add the message to the database.
	*
	* @param messagePK the primary key for the new message
	* @return the new message
	*/
	public com.marcelmika.lims.model.Message create(MessagePK messagePK);

	/**
	* Removes the message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messagePK the primary key of the message
	* @return the message that was removed
	* @throws com.marcelmika.lims.NoSuchMessageException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message remove(MessagePK messagePK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchMessageException;

	public com.marcelmika.lims.model.Message updateImpl(
		com.marcelmika.lims.model.Message message, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message with the primary key or throws a {@link com.marcelmika.lims.NoSuchMessageException} if it could not be found.
	*
	* @param messagePK the primary key of the message
	* @return the message
	* @throws com.marcelmika.lims.NoSuchMessageException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message findByPrimaryKey(
		MessagePK messagePK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchMessageException;

	/**
	* Returns the message with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param messagePK the primary key of the message
	* @return the message, or <code>null</code> if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message fetchByPrimaryKey(
		MessagePK messagePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message where creatorId = &#63; or throws a {@link com.marcelmika.lims.NoSuchMessageException} if it could not be found.
	*
	* @param creatorId the creator ID
	* @return the matching message
	* @throws com.marcelmika.lims.NoSuchMessageException if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message findByCreatorId(long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchMessageException;

	/**
	* Returns the message where creatorId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param creatorId the creator ID
	* @return the matching message, or <code>null</code> if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message fetchByCreatorId(long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message where creatorId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param creatorId the creator ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message, or <code>null</code> if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message fetchByCreatorId(long creatorId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the messages.
	*
	* @return the messages
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Message> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of messages
	* @param end the upper bound of the range of messages (not inclusive)
	* @return the range of messages
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Message> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of messages
	* @param end the upper bound of the range of messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of messages
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Message> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message where creatorId = &#63; from the database.
	*
	* @param creatorId the creator ID
	* @return the message that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Message removeByCreatorId(long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchMessageException;

	/**
	* Removes all the messages from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of messages where creatorId = &#63;.
	*
	* @param creatorId the creator ID
	* @return the number of matching messages
	* @throws SystemException if a system exception occurred
	*/
	public int countByCreatorId(long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of messages.
	*
	* @return the number of messages
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}