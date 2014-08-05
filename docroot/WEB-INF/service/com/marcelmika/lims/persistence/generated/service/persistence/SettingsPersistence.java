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

import com.marcelmika.lims.persistence.generated.model.Settings;

/**
 * The persistence interface for the settings service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SettingsPersistenceImpl
 * @see SettingsUtil
 * @generated
 */
public interface SettingsPersistence extends BasePersistence<Settings> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SettingsUtil} to access the settings persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the settings in the entity cache if it is enabled.
	*
	* @param settings the settings
	*/
	public void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Settings settings);

	/**
	* Caches the settingses in the entity cache if it is enabled.
	*
	* @param settingses the settingses
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> settingses);

	/**
	* Creates a new settings with the primary key. Does not add the settings to the database.
	*
	* @param sid the primary key for the new settings
	* @return the new settings
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings create(
		long sid);

	/**
	* Removes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the settings
	* @return the settings that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSettingsException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings remove(
		long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	public com.marcelmika.lims.persistence.generated.model.Settings updateImpl(
		com.marcelmika.lims.persistence.generated.model.Settings settings,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the settings with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchSettingsException} if it could not be found.
	*
	* @param sid the primary key of the settings
	* @return the settings
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSettingsException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings findByPrimaryKey(
		long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	/**
	* Returns the settings with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sid the primary key of the settings
	* @return the settings, or <code>null</code> if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings fetchByPrimaryKey(
		long sid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the settings where userId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchSettingsException} if it could not be found.
	*
	* @param userId the user ID
	* @return the matching settings
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSettingsException if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings findByUserId(
		long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	/**
	* Returns the settings where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @return the matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings fetchByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the settings where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings fetchByUserId(
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the settingses where presence = &#63;.
	*
	* @param presence the presence
	* @return the matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> findByPresence(
		java.lang.String presence)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the settingses where presence = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param presence the presence
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @return the range of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> findByPresence(
		java.lang.String presence, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the settingses where presence = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param presence the presence
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> findByPresence(
		java.lang.String presence, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first settings in the ordered set where presence = &#63;.
	*
	* @param presence the presence
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching settings
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSettingsException if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings findByPresence_First(
		java.lang.String presence,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	/**
	* Returns the first settings in the ordered set where presence = &#63;.
	*
	* @param presence the presence
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings fetchByPresence_First(
		java.lang.String presence,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last settings in the ordered set where presence = &#63;.
	*
	* @param presence the presence
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching settings
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSettingsException if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings findByPresence_Last(
		java.lang.String presence,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	/**
	* Returns the last settings in the ordered set where presence = &#63;.
	*
	* @param presence the presence
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings fetchByPresence_Last(
		java.lang.String presence,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the settingses before and after the current settings in the ordered set where presence = &#63;.
	*
	* @param sid the primary key of the current settings
	* @param presence the presence
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next settings
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSettingsException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings[] findByPresence_PrevAndNext(
		long sid, java.lang.String presence,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	/**
	* Returns all the settingses.
	*
	* @return the settingses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the settingses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @return the range of settingses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the settingses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of settingses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the settings where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @return the settings that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Settings removeByUserId(
		long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSettingsException;

	/**
	* Removes all the settingses where presence = &#63; from the database.
	*
	* @param presence the presence
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPresence(java.lang.String presence)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the settingses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of settingses where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of settingses where presence = &#63;.
	*
	* @param presence the presence
	* @return the number of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public int countByPresence(java.lang.String presence)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of settingses.
	*
	* @return the number of settingses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}