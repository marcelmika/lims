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

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.marcelmika.lims.persistence.generated.model.Conversation;

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
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
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
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Conversation update(Conversation conversation)
		throws SystemException {
		return getPersistence().update(conversation);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Conversation update(Conversation conversation,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(conversation, serviceContext);
	}

	/**
	* Returns the conversation where conversationId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchConversationException} if it could not be found.
	*
	* @param conversationId the conversation ID
	* @return the matching conversation
	* @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation findByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException {
		return getPersistence().findByConversationId(conversationId);
	}

	/**
	* Returns the conversation where conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param conversationId the conversation ID
	* @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation fetchByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByConversationId(conversationId);
	}

	/**
	* Returns the conversation where conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param conversationId the conversation ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation fetchByConversationId(
		java.lang.String conversationId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByConversationId(conversationId, retrieveFromCache);
	}

	/**
	* Removes the conversation where conversationId = &#63; from the database.
	*
	* @param conversationId the conversation ID
	* @return the conversation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation removeByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException {
		return getPersistence().removeByConversationId(conversationId);
	}

	/**
	* Returns the number of conversations where conversationId = &#63;.
	*
	* @param conversationId the conversation ID
	* @return the number of matching conversations
	* @throws SystemException if a system exception occurred
	*/
	public static int countByConversationId(java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByConversationId(conversationId);
	}

	/**
	* Caches the conversation in the entity cache if it is enabled.
	*
	* @param conversation the conversation
	*/
	public static void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation) {
		getPersistence().cacheResult(conversation);
	}

	/**
	* Caches the conversations in the entity cache if it is enabled.
	*
	* @param conversations the conversations
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> conversations) {
		getPersistence().cacheResult(conversations);
	}

	/**
	* Creates a new conversation with the primary key. Does not add the conversation to the database.
	*
	* @param cid the primary key for the new conversation
	* @return the new conversation
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation create(
		long cid) {
		return getPersistence().create(cid);
	}

	/**
	* Removes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cid the primary key of the conversation
	* @return the conversation that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation remove(
		long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException {
		return getPersistence().remove(cid);
	}

	public static com.marcelmika.lims.persistence.generated.model.Conversation updateImpl(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(conversation);
	}

	/**
	* Returns the conversation with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchConversationException} if it could not be found.
	*
	* @param cid the primary key of the conversation
	* @return the conversation
	* @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation findByPrimaryKey(
		long cid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchConversationException {
		return getPersistence().findByPrimaryKey(cid);
	}

	/**
	* Returns the conversation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cid the primary key of the conversation
	* @return the conversation, or <code>null</code> if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Conversation fetchByPrimaryKey(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(cid);
	}

	/**
	* Returns all the conversations.
	*
	* @return the conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @return the range of conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Conversation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
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
			_persistence = (ConversationPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					ConversationPersistence.class.getName());

			ReferenceRegistry.registerReference(ConversationUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ConversationPersistence persistence) {
	}

	private static ConversationPersistence _persistence;
}