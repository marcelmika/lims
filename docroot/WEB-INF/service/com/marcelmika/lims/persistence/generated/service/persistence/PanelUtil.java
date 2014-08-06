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

import com.marcelmika.lims.persistence.generated.model.Panel;

import java.util.List;

/**
 * The persistence utility for the panel service. This utility wraps {@link PanelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PanelPersistence
 * @see PanelPersistenceImpl
 * @generated
 */
public class PanelUtil {
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
	public static void clearCache(Panel panel) {
		getPersistence().clearCache(panel);
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
	public static List<Panel> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Panel> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Panel> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Panel update(Panel panel) throws SystemException {
		return getPersistence().update(panel);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Panel update(Panel panel, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(panel, serviceContext);
	}

	/**
	* Returns the panel where userId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchPanelException} if it could not be found.
	*
	* @param userId the user ID
	* @return the matching panel
	* @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a matching panel could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel findByUserId(
		long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchPanelException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns the panel where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @return the matching panel, or <code>null</code> if a matching panel could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel fetchByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId(userId);
	}

	/**
	* Returns the panel where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching panel, or <code>null</code> if a matching panel could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel fetchByUserId(
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId(userId, retrieveFromCache);
	}

	/**
	* Removes the panel where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @return the panel that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel removeByUserId(
		long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchPanelException {
		return getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of panels where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching panels
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Caches the panel in the entity cache if it is enabled.
	*
	* @param panel the panel
	*/
	public static void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Panel panel) {
		getPersistence().cacheResult(panel);
	}

	/**
	* Caches the panels in the entity cache if it is enabled.
	*
	* @param panels the panels
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Panel> panels) {
		getPersistence().cacheResult(panels);
	}

	/**
	* Creates a new panel with the primary key. Does not add the panel to the database.
	*
	* @param pid the primary key for the new panel
	* @return the new panel
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel create(
		long pid) {
		return getPersistence().create(pid);
	}

	/**
	* Removes the panel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pid the primary key of the panel
	* @return the panel that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel remove(
		long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchPanelException {
		return getPersistence().remove(pid);
	}

	public static com.marcelmika.lims.persistence.generated.model.Panel updateImpl(
		com.marcelmika.lims.persistence.generated.model.Panel panel)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(panel);
	}

	/**
	* Returns the panel with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchPanelException} if it could not be found.
	*
	* @param pid the primary key of the panel
	* @return the panel
	* @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel findByPrimaryKey(
		long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchPanelException {
		return getPersistence().findByPrimaryKey(pid);
	}

	/**
	* Returns the panel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pid the primary key of the panel
	* @return the panel, or <code>null</code> if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel fetchByPrimaryKey(
		long pid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(pid);
	}

	/**
	* Returns all the panels.
	*
	* @return the panels
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Panel> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the panels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.PanelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of panels
	* @param end the upper bound of the range of panels (not inclusive)
	* @return the range of panels
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Panel> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the panels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.PanelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of panels
	* @param end the upper bound of the range of panels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of panels
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Panel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the panels from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of panels.
	*
	* @return the number of panels
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PanelPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PanelPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					PanelPersistence.class.getName());

			ReferenceRegistry.registerReference(PanelUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(PanelPersistence persistence) {
	}

	private static PanelPersistence _persistence;
}