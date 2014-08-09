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

package com.marcelmika.lims.persistence.generated.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for Panel. This utility wraps
 * {@link com.marcelmika.lims.persistence.generated.service.impl.PanelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PanelLocalService
 * @see com.marcelmika.lims.persistence.generated.service.base.PanelLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.impl.PanelLocalServiceImpl
 * @generated
 */
public class PanelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.marcelmika.lims.persistence.generated.service.impl.PanelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the panel to the database. Also notifies the appropriate model listeners.
	*
	* @param panel the panel
	* @return the panel that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel addPanel(
		com.marcelmika.lims.persistence.generated.model.Panel panel)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPanel(panel);
	}

	/**
	* Creates a new panel with the primary key. Does not add the panel to the database.
	*
	* @param pid the primary key for the new panel
	* @return the new panel
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel createPanel(
		long pid) {
		return getService().createPanel(pid);
	}

	/**
	* Deletes the panel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pid the primary key of the panel
	* @return the panel that was removed
	* @throws PortalException if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel deletePanel(
		long pid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePanel(pid);
	}

	/**
	* Deletes the panel from the database. Also notifies the appropriate model listeners.
	*
	* @param panel the panel
	* @return the panel that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel deletePanel(
		com.marcelmika.lims.persistence.generated.model.Panel panel)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePanel(panel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.PanelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.PanelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.marcelmika.lims.persistence.generated.model.Panel fetchPanel(
		long pid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPanel(pid);
	}

	/**
	* Returns the panel with the primary key.
	*
	* @param pid the primary key of the panel
	* @return the panel
	* @throws PortalException if a panel with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel getPanel(
		long pid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPanel(pid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Panel> getPanels(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPanels(start, end);
	}

	/**
	* Returns the number of panels.
	*
	* @return the number of panels
	* @throws SystemException if a system exception occurred
	*/
	public static int getPanelsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPanelsCount();
	}

	/**
	* Updates the panel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param panel the panel
	* @return the panel that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel updatePanel(
		com.marcelmika.lims.persistence.generated.model.Panel panel)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePanel(panel);
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

	/**
	* Returns panel. Creates new if not found. Null on error
	*/
	public static com.marcelmika.lims.persistence.generated.model.Panel getPanelByUser(
		long userId) {
		return getService().getPanelByUser(userId);
	}

	public static void updateActivePanel(long userId,
		java.lang.String activePanel) throws java.lang.Exception {
		getService().updateActivePanel(userId, activePanel);
	}

	public static void clearService() {
		_service = null;
	}

	public static PanelLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PanelLocalService.class.getName());

			if (invokableLocalService instanceof PanelLocalService) {
				_service = (PanelLocalService)invokableLocalService;
			}
			else {
				_service = new PanelLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PanelLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PanelLocalService service) {
	}

	private static PanelLocalService _service;
}