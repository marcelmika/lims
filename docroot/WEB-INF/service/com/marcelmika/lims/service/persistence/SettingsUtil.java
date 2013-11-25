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

import com.marcelmika.lims.model.Settings;

import java.util.List;

/**
 * The persistence utility for the settings service. This utility wraps {@link SettingsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SettingsPersistence
 * @see SettingsPersistenceImpl
 * @generated
 */
public class SettingsUtil {
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
	public static void clearCache(Settings settings) {
		getPersistence().clearCache(settings);
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
	public static List<Settings> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Settings> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Settings> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Settings update(Settings settings, boolean merge)
		throws SystemException {
		return getPersistence().update(settings, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Settings update(Settings settings, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(settings, merge, serviceContext);
	}

	/**
	* Caches the settings in the entity cache if it is enabled.
	*
	* @param settings the settings
	*/
	public static void cacheResult(com.marcelmika.lims.model.Settings settings) {
		getPersistence().cacheResult(settings);
	}

	/**
	* Caches the settingses in the entity cache if it is enabled.
	*
	* @param settingses the settingses
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.model.Settings> settingses) {
		getPersistence().cacheResult(settingses);
	}

	/**
	* Creates a new settings with the primary key. Does not add the settings to the database.
	*
	* @param sid the primary key for the new settings
	* @return the new settings
	*/
	public static com.marcelmika.lims.model.Settings create(long sid) {
		return getPersistence().create(sid);
	}

	/**
	* Removes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the settings
	* @return the settings that was removed
	* @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings remove(long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence().remove(sid);
	}

	public static com.marcelmika.lims.model.Settings updateImpl(
		com.marcelmika.lims.model.Settings settings, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(settings, merge);
	}

	/**
	* Returns the settings with the primary key or throws a {@link com.marcelmika.lims.NoSuchSettingsException} if it could not be found.
	*
	* @param sid the primary key of the settings
	* @return the settings
	* @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings findByPrimaryKey(long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence().findByPrimaryKey(sid);
	}

	/**
	* Returns the settings with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sid the primary key of the settings
	* @return the settings, or <code>null</code> if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings fetchByPrimaryKey(long sid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(sid);
	}

	/**
	* Returns the settings where userId = &#63; or throws a {@link com.marcelmika.lims.NoSuchSettingsException} if it could not be found.
	*
	* @param userId the user ID
	* @return the matching settings
	* @throws com.marcelmika.lims.NoSuchSettingsException if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings findByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns the settings where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @return the matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings fetchByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	* Returns the settings where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings fetchByUserId(
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId(userId, retrieveFromCache);
	}

	/**
	* Returns all the settingses where status = &#63;.
	*
	* @param status the status
	* @return the matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Settings> findByStatus(
		java.lang.String status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStatus(status);
	}

	/**
	* Returns a range of all the settingses where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @return the range of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Settings> findByStatus(
		java.lang.String status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStatus(status, start, end);
	}

	/**
	* Returns an ordered range of all the settingses where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Settings> findByStatus(
		java.lang.String status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStatus(status, start, end, orderByComparator);
	}

	/**
	* Returns the first settings in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching settings
	* @throws com.marcelmika.lims.NoSuchSettingsException if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings findByStatus_First(
		java.lang.String status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence().findByStatus_First(status, orderByComparator);
	}

	/**
	* Returns the first settings in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings fetchByStatus_First(
		java.lang.String status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByStatus_First(status, orderByComparator);
	}

	/**
	* Returns the last settings in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching settings
	* @throws com.marcelmika.lims.NoSuchSettingsException if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings findByStatus_Last(
		java.lang.String status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence().findByStatus_Last(status, orderByComparator);
	}

	/**
	* Returns the last settings in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching settings, or <code>null</code> if a matching settings could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings fetchByStatus_Last(
		java.lang.String status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByStatus_Last(status, orderByComparator);
	}

	/**
	* Returns the settingses before and after the current settings in the ordered set where status = &#63;.
	*
	* @param sid the primary key of the current settings
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next settings
	* @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings[] findByStatus_PrevAndNext(
		long sid, java.lang.String status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence()
				   .findByStatus_PrevAndNext(sid, status, orderByComparator);
	}

	/**
	* Returns all the settingses.
	*
	* @return the settingses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Settings> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.marcelmika.lims.model.Settings> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.marcelmika.lims.model.Settings> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the settings where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @return the settings that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Settings removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchSettingsException {
		return getPersistence().removeByUserId(userId);
	}

	/**
	* Removes all the settingses where status = &#63; from the database.
	*
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStatus(java.lang.String status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStatus(status);
	}

	/**
	* Removes all the settingses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of settingses where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns the number of settingses where status = &#63;.
	*
	* @param status the status
	* @return the number of matching settingses
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStatus(java.lang.String status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStatus(status);
	}

	/**
	* Returns the number of settingses.
	*
	* @return the number of settingses
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SettingsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SettingsPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.service.ClpSerializer.getServletContextName(),
					SettingsPersistence.class.getName());

			ReferenceRegistry.registerReference(SettingsUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(SettingsPersistence persistence) {
	}

	private static SettingsPersistence _persistence;
}