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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.marcelmika.lims.persistence.generated.model.Message;

import java.util.List;

/**
 * The persistence utility for the message service. This utility wraps {@link MessagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MessagePersistence
 * @see MessagePersistenceImpl
 * @generated
 */
public class MessageUtil {
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
	public static void clearCache(Message message) {
		getPersistence().clearCache(message);
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
	public static List<Message> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Message> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Message> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Message update(Message message, boolean merge)
		throws SystemException {
		return getPersistence().update(message, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Message update(Message message, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(message, merge, serviceContext);
	}

	/**
	* Caches the message in the entity cache if it is enabled.
	*
	* @param message the message
	*/
	public static void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Message message) {
		getPersistence().cacheResult(message);
	}

	/**
	* Caches the messages in the entity cache if it is enabled.
	*
	* @param messages the messages
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Message> messages) {
		getPersistence().cacheResult(messages);
	}

	/**
	* Creates a new message with the primary key. Does not add the message to the database.
	*
	* @param mid the primary key for the new message
	* @return the new message
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message create(
		long mid) {
		return getPersistence().create(mid);
	}

	/**
	* Removes the message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mid the primary key of the message
	* @return the message that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchMessageException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message remove(
		long mid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence().remove(mid);
	}

	public static com.marcelmika.lims.persistence.generated.model.Message updateImpl(
		com.marcelmika.lims.persistence.generated.model.Message message,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(message, merge);
	}

	/**
	* Returns the message with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchMessageException} if it could not be found.
	*
	* @param mid the primary key of the message
	* @return the message
	* @throws com.marcelmika.lims.persistence.generated.NoSuchMessageException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message findByPrimaryKey(
		long mid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence().findByPrimaryKey(mid);
	}

	/**
	* Returns the message with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param mid the primary key of the message
	* @return the message, or <code>null</code> if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message fetchByPrimaryKey(
		long mid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(mid);
	}

	/**
	* Returns the message where creatorId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchMessageException} if it could not be found.
	*
	* @param creatorId the creator ID
	* @return the matching message
	* @throws com.marcelmika.lims.persistence.generated.NoSuchMessageException if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message findByCreatorId(
		long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence().findByCreatorId(creatorId);
	}

	/**
	* Returns the message where creatorId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param creatorId the creator ID
	* @return the matching message, or <code>null</code> if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message fetchByCreatorId(
		long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCreatorId(creatorId);
	}

	/**
	* Returns the message where creatorId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param creatorId the creator ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message, or <code>null</code> if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message fetchByCreatorId(
		long creatorId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCreatorId(creatorId, retrieveFromCache);
	}

	/**
	* Returns all the messages where cid = &#63;.
	*
	* @param cid the cid
	* @return the matching messages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Message> findByCid(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCid(cid);
	}

	/**
	* Returns a range of all the messages where cid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param cid the cid
	* @param start the lower bound of the range of messages
	* @param end the upper bound of the range of messages (not inclusive)
	* @return the range of matching messages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Message> findByCid(
		long cid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCid(cid, start, end);
	}

	/**
	* Returns an ordered range of all the messages where cid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param cid the cid
	* @param start the lower bound of the range of messages
	* @param end the upper bound of the range of messages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching messages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Message> findByCid(
		long cid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCid(cid, start, end, orderByComparator);
	}

	/**
	* Returns the first message in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message
	* @throws com.marcelmika.lims.persistence.generated.NoSuchMessageException if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message findByCid_First(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence().findByCid_First(cid, orderByComparator);
	}

	/**
	* Returns the first message in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message, or <code>null</code> if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message fetchByCid_First(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCid_First(cid, orderByComparator);
	}

	/**
	* Returns the last message in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message
	* @throws com.marcelmika.lims.persistence.generated.NoSuchMessageException if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message findByCid_Last(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence().findByCid_Last(cid, orderByComparator);
	}

	/**
	* Returns the last message in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message, or <code>null</code> if a matching message could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message fetchByCid_Last(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCid_Last(cid, orderByComparator);
	}

	/**
	* Returns the messages before and after the current message in the ordered set where cid = &#63;.
	*
	* @param mid the primary key of the current message
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message
	* @throws com.marcelmika.lims.persistence.generated.NoSuchMessageException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message[] findByCid_PrevAndNext(
		long mid, long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence()
				   .findByCid_PrevAndNext(mid, cid, orderByComparator);
	}

	/**
	* Returns all the messages.
	*
	* @return the messages
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Message> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Message> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Message> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the message where creatorId = &#63; from the database.
	*
	* @param creatorId the creator ID
	* @return the message that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Message removeByCreatorId(
		long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchMessageException {
		return getPersistence().removeByCreatorId(creatorId);
	}

	/**
	* Removes all the messages where cid = &#63; from the database.
	*
	* @param cid the cid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCid(long cid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCid(cid);
	}

	/**
	* Removes all the messages from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of messages where creatorId = &#63;.
	*
	* @param creatorId the creator ID
	* @return the number of matching messages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCreatorId(long creatorId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCreatorId(creatorId);
	}

	/**
	* Returns the number of messages where cid = &#63;.
	*
	* @param cid the cid
	* @return the number of matching messages
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCid(long cid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCid(cid);
	}

	/**
	* Returns the number of messages.
	*
	* @return the number of messages
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MessagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MessagePersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					MessagePersistence.class.getName());

			ReferenceRegistry.registerReference(MessageUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(MessagePersistence persistence) {
	}

	private static MessagePersistence _persistence;
}