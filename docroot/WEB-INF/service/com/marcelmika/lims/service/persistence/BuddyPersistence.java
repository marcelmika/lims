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

import com.marcelmika.lims.model.Buddy;

/**
 * The persistence interface for the buddy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BuddyPersistenceImpl
 * @see BuddyUtil
 * @generated
 */
public interface BuddyPersistence extends BasePersistence<Buddy> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BuddyUtil} to access the buddy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the buddy in the entity cache if it is enabled.
	*
	* @param buddy the buddy
	*/
	public void cacheResult(com.marcelmika.lims.model.Buddy buddy);

	/**
	* Caches the buddies in the entity cache if it is enabled.
	*
	* @param buddies the buddies
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.model.Buddy> buddies);

	/**
	* Creates a new buddy with the primary key. Does not add the buddy to the database.
	*
	* @param bid the primary key for the new buddy
	* @return the new buddy
	*/
	public com.marcelmika.lims.model.Buddy create(long bid);

	/**
	* Removes the buddy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param bid the primary key of the buddy
	* @return the buddy that was removed
	* @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Buddy remove(long bid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchBuddyException;

	public com.marcelmika.lims.model.Buddy updateImpl(
		com.marcelmika.lims.model.Buddy buddy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the buddy with the primary key or throws a {@link com.marcelmika.lims.NoSuchBuddyException} if it could not be found.
	*
	* @param bid the primary key of the buddy
	* @return the buddy
	* @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Buddy findByPrimaryKey(long bid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchBuddyException;

	/**
	* Returns the buddy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param bid the primary key of the buddy
	* @return the buddy, or <code>null</code> if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.model.Buddy fetchByPrimaryKey(long bid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the buddies.
	*
	* @return the buddies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.model.Buddy> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.marcelmika.lims.model.Buddy> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.marcelmika.lims.model.Buddy> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the buddies from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of buddies.
	*
	* @return the number of buddies
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}