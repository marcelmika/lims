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

package com.marcelmika.lims.persistence.generated.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the settings local service. This utility wraps {@link com.marcelmika.lims.persistence.generated.service.impl.SettingsLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SettingsLocalService
 * @see com.marcelmika.lims.persistence.generated.service.base.SettingsLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.impl.SettingsLocalServiceImpl
 * @generated
 */
public class SettingsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.marcelmika.lims.persistence.generated.service.impl.SettingsLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the settings to the database. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings addSettings(
		com.marcelmika.lims.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSettings(settings);
	}

	/**
	* Creates a new settings with the primary key. Does not add the settings to the database.
	*
	* @param sid the primary key for the new settings
	* @return the new settings
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings createSettings(
		long sid) {
		return getService().createSettings(sid);
	}

	/**
	* Deletes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the settings
	* @return the settings that was removed
	* @throws PortalException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings deleteSettings(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSettings(sid);
	}

	/**
	* Deletes the settings from the database. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings deleteSettings(
		com.marcelmika.lims.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSettings(settings);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.marcelmika.lims.persistence.generated.model.Settings fetchSettings(
		long sid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSettings(sid);
	}

	/**
	* Returns the settings with the primary key.
	*
	* @param sid the primary key of the settings
	* @return the settings
	* @throws PortalException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings getSettings(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSettings(sid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Settings> getSettingses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSettingses(start, end);
	}

	/**
	* Returns the number of settingses.
	*
	* @return the number of settingses
	* @throws SystemException if a system exception occurred
	*/
	public static int getSettingsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSettingsesCount();
	}

	/**
	* Updates the settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings updateSettings(
		com.marcelmika.lims.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSettings(settings);
	}

	/**
	* Updates the settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @param merge whether to merge the settings with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the settings that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Settings updateSettings(
		com.marcelmika.lims.persistence.generated.model.Settings settings,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSettings(settings, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static com.marcelmika.lims.persistence.generated.model.Settings getSettingsByUser(
		long userId) throws java.lang.Exception {
		return getService().getSettingsByUser(userId);
	}

	public static void changeStatus(long userId, java.lang.String status)
		throws java.lang.Exception {
		getService().changeStatus(userId, status);
	}

	public static void setChatEnabled(long userId, boolean enabled)
		throws java.lang.Exception {
		getService().setChatEnabled(userId, enabled);
	}

	/**
	* Returns all buddies in the system
	*
	* @param userId            of excluded user
	* @param ignoreDefaultUser true if default users should be ignored
	* @param start             value of the list
	* @param end               value of the list
	* @return List of objects where each object contains user
	* @throws Exception
	*/
	public static java.util.List<java.lang.Object[]> getAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser, int start, int end)
		throws java.lang.Exception {
		return getService().getAllGroups(userId, ignoreDefaultUser, start, end);
	}

	/**
	* Returns
	*
	* @param userId            of excluded user
	* @param ignoreDefaultUser true if default users should be ignored
	* @param excludedSties     list of names of sites which should be excluded
	* @param start             value of the list
	* @param end               value of the list
	* @return List of objects where each object contains group where each group contains users
	* @throws Exception
	*/
	public static java.util.List<java.lang.Object[]> getSitesGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		java.lang.String[] excludedSties, int start, int end)
		throws java.lang.Exception {
		return getService()
				   .getSitesGroups(userId, ignoreDefaultUser, excludedSties,
			start, end);
	}

	public static void clearService() {
		_service = null;
	}

	public static SettingsLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					SettingsLocalService.class.getName());

			if (invokableLocalService instanceof SettingsLocalService) {
				_service = (SettingsLocalService)invokableLocalService;
			}
			else {
				_service = new SettingsLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(SettingsLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(SettingsLocalService service) {
	}

	private static SettingsLocalService _service;
}