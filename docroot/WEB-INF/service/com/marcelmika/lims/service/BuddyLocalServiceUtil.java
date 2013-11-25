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

package com.marcelmika.lims.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the buddy local service. This utility wraps {@link com.marcelmika.lims.service.impl.BuddyLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BuddyLocalService
 * @see com.marcelmika.lims.service.base.BuddyLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.impl.BuddyLocalServiceImpl
 * @generated
 */
public class BuddyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.marcelmika.lims.service.impl.BuddyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the buddy to the database. Also notifies the appropriate model listeners.
	*
	* @param buddy the buddy
	* @return the buddy that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy addBuddy(
		com.marcelmika.lims.model.Buddy buddy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addBuddy(buddy);
	}

	/**
	* Creates a new buddy with the primary key. Does not add the buddy to the database.
	*
	* @param bid the primary key for the new buddy
	* @return the new buddy
	*/
	public static com.marcelmika.lims.model.Buddy createBuddy(long bid) {
		return getService().createBuddy(bid);
	}

	/**
	* Deletes the buddy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param bid the primary key of the buddy
	* @return the buddy that was removed
	* @throws PortalException if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy deleteBuddy(long bid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteBuddy(bid);
	}

	/**
	* Deletes the buddy from the database. Also notifies the appropriate model listeners.
	*
	* @param buddy the buddy
	* @return the buddy that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy deleteBuddy(
		com.marcelmika.lims.model.Buddy buddy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteBuddy(buddy);
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

	public static com.marcelmika.lims.model.Buddy fetchBuddy(long bid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBuddy(bid);
	}

	/**
	* Returns the buddy with the primary key.
	*
	* @param bid the primary key of the buddy
	* @return the buddy
	* @throws PortalException if a buddy with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy getBuddy(long bid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBuddy(bid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.marcelmika.lims.model.Buddy> getBuddies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBuddies(start, end);
	}

	/**
	* Returns the number of buddies.
	*
	* @return the number of buddies
	* @throws SystemException if a system exception occurred
	*/
	public static int getBuddiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBuddiesCount();
	}

	/**
	* Updates the buddy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param buddy the buddy
	* @return the buddy that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy updateBuddy(
		com.marcelmika.lims.model.Buddy buddy)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateBuddy(buddy);
	}

	/**
	* Updates the buddy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param buddy the buddy
	* @param merge whether to merge the buddy with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the buddy that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Buddy updateBuddy(
		com.marcelmika.lims.model.Buddy buddy, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateBuddy(buddy, merge);
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

	public static java.util.List<com.marcelmika.lims.model.Buddy> findByQuery(
		java.lang.String query) {
		return getService().findByQuery(query);
	}

	public static com.marcelmika.lims.model.Buddy mapBuddyFromUser(
		com.liferay.portal.model.User user) {
		return getService().mapBuddyFromUser(user);
	}

	public static com.marcelmika.lims.model.Buddy getBuddyByScreenName(
		long companyId, java.lang.String screenName) {
		return getService().getBuddyByScreenName(companyId, screenName);
	}

	public static com.marcelmika.lims.model.Buddy getBuddyByUserId(long userId) {
		return getService().getBuddyByUserId(userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static BuddyLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					BuddyLocalService.class.getName());

			if (invokableLocalService instanceof BuddyLocalService) {
				_service = (BuddyLocalService)invokableLocalService;
			}
			else {
				_service = new BuddyLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(BuddyLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(BuddyLocalService service) {
	}

	private static BuddyLocalService _service;
}