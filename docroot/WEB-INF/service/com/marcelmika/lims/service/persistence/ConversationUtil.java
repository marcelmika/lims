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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.marcelmika.lims.model.Conversation;

import java.util.List;

/**
 * The persistence utility for the conversation service. This utility wraps {@link ConversationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConversationPersistence
 * @see ConversationPersistenceImpl
 * @generated
 */
public class ConversationUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Conversation conversation) {
		getPersistence().clearCache(conversation);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Conversation> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Conversation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Conversation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Conversation update(Conversation conversation, boolean merge)
		throws SystemException {
		return getPersistence().update(conversation, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Conversation update(Conversation conversation, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(conversation, merge, serviceContext);
	}

	/**
	* Caches the conversation in the entity cache if it is enabled.
	*
	* @param conversation the conversation
	*/
	public static void cacheResult(
		com.marcelmika.lims.model.Conversation conversation) {
		getPersistence().cacheResult(conversation);
	}

	/**
	* Caches the conversations in the entity cache if it is enabled.
	*
	* @param conversations the conversations
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.model.Conversation> conversations) {
		getPersistence().cacheResult(conversations);
	}

	/**
	* Creates a new conversation with the primary key. Does not add the conversation to the database.
	*
	* @param cid the primary key for the new conversation
	* @return the new conversation
	*/
	public static com.marcelmika.lims.model.Conversation create(long cid) {
		return getPersistence().create(cid);
	}

	/**
	* Removes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cid the primary key of the conversation
	* @return the conversation that was removed
	* @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation remove(long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence().remove(cid);
	}

	public static com.marcelmika.lims.model.Conversation updateImpl(
		com.marcelmika.lims.model.Conversation conversation, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(conversation, merge);
	}

	/**
	* Returns the conversation with the primary key or throws a {@link com.marcelmika.lims.NoSuchConversationException} if it could not be found.
	*
	* @param cid the primary key of the conversation
	* @return the conversation
	* @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation findByPrimaryKey(
		long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence().findByPrimaryKey(cid);
	}

	/**
	* Returns the conversation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cid the primary key of the conversation
	* @return the conversation, or <code>null</code> if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation fetchByPrimaryKey(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(cid);
	}

	/**
	* Returns the conversation where userId = &#63; and conversationId = &#63; or throws a {@link com.marcelmika.lims.NoSuchConversationException} if it could not be found.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the matching conversation
	* @throws com.marcelmika.lims.NoSuchConversationException if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation findByUserId_conversationId(
		long userId, java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence()
				   .findByUserId_conversationId(userId, conversationId);
	}

	/**
	* Returns the conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation fetchByUserId_conversationId(
		long userId, java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserId_conversationId(userId, conversationId);
	}

	/**
	* Returns the conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation fetchByUserId_conversationId(
		long userId, java.lang.String conversationId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUserId_conversationId(userId, conversationId,
			retrieveFromCache);
	}

	/**
	* Returns all the conversations where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Conversation> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the conversations where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @return the range of matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Conversation> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the conversations where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Conversation> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching conversation
	* @throws com.marcelmika.lims.NoSuchConversationException if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching conversation
	* @throws com.marcelmika.lims.NoSuchConversationException if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last conversation in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the conversations before and after the current conversation in the ordered set where userId = &#63;.
	*
	* @param cid the primary key of the current conversation
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next conversation
	* @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation[] findByUserId_PrevAndNext(
		long cid, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence()
				   .findByUserId_PrevAndNext(cid, userId, orderByComparator);
	}

	/**
	* Returns all the conversations.
	*
	* @return the conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Conversation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.marcelmika.lims.model.Conversation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.marcelmika.lims.model.Conversation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the conversation where userId = &#63; and conversationId = &#63; from the database.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the conversation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Conversation removeByUserId_conversationId(
		long userId, java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchConversationException {
		return getPersistence()
				   .removeByUserId_conversationId(userId, conversationId);
	}

	/**
	* Removes all the conversations where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the conversations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of conversations where userId = &#63; and conversationId = &#63;.
	*
	* @param userId the user ID
	* @param conversationId the conversation ID
	* @return the number of matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId_conversationId(long userId,
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByUserId_conversationId(userId, conversationId);
	}

	/**
	* Returns the number of conversations where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of conversations.
	*
	* @return the number of conversations
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ConversationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ConversationPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.service.ClpSerializer.getServletContextName(),
					ConversationPersistence.class.getName());

			ReferenceRegistry.registerReference(ConversationUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(ConversationPersistence persistence) {
	}

	private static ConversationPersistence _persistence;
}