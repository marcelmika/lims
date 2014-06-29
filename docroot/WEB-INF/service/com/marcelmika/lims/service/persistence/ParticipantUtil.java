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

import com.marcelmika.lims.model.Participant;

import java.util.List;

/**
 * The persistence utility for the participant service. This utility wraps {@link ParticipantPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParticipantPersistence
 * @see ParticipantPersistenceImpl
 * @generated
 */
public class ParticipantUtil {
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
	public static void clearCache(Participant participant) {
		getPersistence().clearCache(participant);
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
	public static List<Participant> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Participant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Participant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Participant update(Participant participant, boolean merge)
		throws SystemException {
		return getPersistence().update(participant, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Participant update(Participant participant, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(participant, merge, serviceContext);
	}

	/**
	* Caches the participant in the entity cache if it is enabled.
	*
	* @param participant the participant
	*/
	public static void cacheResult(
		com.marcelmika.lims.model.Participant participant) {
		getPersistence().cacheResult(participant);
	}

	/**
	* Caches the participants in the entity cache if it is enabled.
	*
	* @param participants the participants
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.model.Participant> participants) {
		getPersistence().cacheResult(participants);
	}

	/**
	* Creates a new participant with the primary key. Does not add the participant to the database.
	*
	* @param participantPK the primary key for the new participant
	* @return the new participant
	*/
	public static com.marcelmika.lims.model.Participant create(
		ParticipantPK participantPK) {
		return getPersistence().create(participantPK);
	}

	/**
	* Removes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param participantPK the primary key of the participant
	* @return the participant that was removed
	* @throws com.marcelmika.lims.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant remove(
		ParticipantPK participantPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchParticipantException {
		return getPersistence().remove(participantPK);
	}

	public static com.marcelmika.lims.model.Participant updateImpl(
		com.marcelmika.lims.model.Participant participant, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(participant, merge);
	}

	/**
	* Returns the participant with the primary key or throws a {@link com.marcelmika.lims.NoSuchParticipantException} if it could not be found.
	*
	* @param participantPK the primary key of the participant
	* @return the participant
	* @throws com.marcelmika.lims.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant findByPrimaryKey(
		ParticipantPK participantPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchParticipantException {
		return getPersistence().findByPrimaryKey(participantPK);
	}

	/**
	* Returns the participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param participantPK the primary key of the participant
	* @return the participant, or <code>null</code> if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant fetchByPrimaryKey(
		ParticipantPK participantPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(participantPK);
	}

	/**
	* Returns the participant where participantId = &#63; or throws a {@link com.marcelmika.lims.NoSuchParticipantException} if it could not be found.
	*
	* @param participantId the participant ID
	* @return the matching participant
	* @throws com.marcelmika.lims.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant findByParticipantId(
		long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchParticipantException {
		return getPersistence().findByParticipantId(participantId);
	}

	/**
	* Returns the participant where participantId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param participantId the participant ID
	* @return the matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant fetchByParticipantId(
		long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByParticipantId(participantId);
	}

	/**
	* Returns the participant where participantId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param participantId the participant ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant fetchByParticipantId(
		long participantId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByParticipantId(participantId, retrieveFromCache);
	}

	/**
	* Returns all the participants.
	*
	* @return the participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Participant> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @return the range of participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Participant> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.model.Participant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the participant where participantId = &#63; from the database.
	*
	* @param participantId the participant ID
	* @return the participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.model.Participant removeByParticipantId(
		long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.NoSuchParticipantException {
		return getPersistence().removeByParticipantId(participantId);
	}

	/**
	* Removes all the participants from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of participants where participantId = &#63;.
	*
	* @param participantId the participant ID
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByParticipantId(long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByParticipantId(participantId);
	}

	/**
	* Returns the number of participants.
	*
	* @return the number of participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ParticipantPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ParticipantPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.service.ClpSerializer.getServletContextName(),
					ParticipantPersistence.class.getName());

			ReferenceRegistry.registerReference(ParticipantUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(ParticipantPersistence persistence) {
	}

	private static ParticipantPersistence _persistence;
}