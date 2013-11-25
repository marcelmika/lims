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

import com.marcelmika.lims.model.Buddy;

import java.util.List;

/**
 * The persistence utility for the buddy service. This utility wraps {@link BuddyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BuddyPersistence
 * @see BuddyPersistenceImpl
 * @generated
 */
public class BuddyUtil {
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
	public static void clearCache(Buddy buddy) {
		getPersistence().clearCache(buddy);
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
	public static List<Buddy> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Buddy> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Buddy> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Buddy update(Buddy buddy, boolean merge)
		throws SystemException {
		return getPersistence().update(buddy, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Buddy update(Buddy buddy, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(buddy, merge, serviceContext);
	}

	/**
	* Caches the buddy in the entity cache if it is enabled.
	*
	* @param buddy the buddy
	*/
	public static void cacheResult(com.marcelmika.lims.model.Buddy buddy) {
		getPersistence().cacheResult(buddy);
	}

	/**
	* Caches the buddies in the entity cache if it is enabled.
	*
	* @param buddies the buddies
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.model.Buddy> buddies) {
		getPersistence().cacheResult(buddies);
	}

	/**
	* Creates a new buddy with the primary key. Does not add the buddy to the database.
	*
	* @param bid the primary key for the new buddy
	* @return the new buddy
	*/
	public static com.marcelmika.lims.model.Buddy create(long bid) {
		return getPersistence().create(bid);
	}

	/**
	* Removes the buddy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param bid the primary key of the buddy
	* @return the buddy that was removed
	* @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy remove(long bid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchBuddyException {
		return getPersistence().remove(bid);
	}

	public static com.marcelmika.lims.model.Buddy updateImpl(
		com.marcelmika.lims.model.Buddy buddy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(buddy, merge);
	}

	/**
	* Returns the buddy with the primary key or throws a {@link com.marcelmika.lims.NoSuchBuddyException} if it could not be found.
	*
	* @param bid the primary key of the buddy
	* @return the buddy
	* @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy findByPrimaryKey(long bid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchBuddyException {
		return getPersistence().findByPrimaryKey(bid);
	}

	/**
	* Returns the buddy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param bid the primary key of the buddy
	* @return the buddy, or <code>null</code> if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy fetchByPrimaryKey(long bid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(bid);
	}

	/**
	* Returns all the buddies.
	*
	* @return the buddies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Buddy> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the buddies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of buddies
	* @param end the upper bound of the range of buddies (not inclusive)
	* @return the range of buddies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Buddy> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the buddies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of buddies
	* @param end the upper bound of the range of buddies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of buddies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Buddy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the buddies from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of buddies.
	*
	* @return the number of buddies
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static BuddyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BuddyPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.service.ClpSerializer.getServletContextName(),
					BuddyPersistence.class.getName());

			ReferenceRegistry.registerReference(BuddyUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(BuddyPersistence persistence) {
	}

	private static BuddyPersistence _persistence;
}