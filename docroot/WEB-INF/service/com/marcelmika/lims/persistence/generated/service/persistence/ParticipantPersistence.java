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

import com.marcelmika.lims.persistence.generated.model.Participant;

/**
 * The persistence interface for the participant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParticipantPersistenceImpl
 * @see ParticipantUtil
 * @generated
 */
public interface ParticipantPersistence extends BasePersistence<Participant> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ParticipantUtil} to access the participant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the participant in the entity cache if it is enabled.
	*
	* @param participant the participant
	*/
	public void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Participant participant);

	/**
	* Caches the participants in the entity cache if it is enabled.
	*
	* @param participants the participants
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> participants);

	/**
	* Creates a new participant with the primary key. Does not add the participant to the database.
	*
	* @param pid the primary key for the new participant
	* @return the new participant
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant create(
		long pid);

	/**
	* Removes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pid the primary key of the participant
	* @return the participant that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant remove(
		long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	public com.marcelmika.lims.persistence.generated.model.Participant updateImpl(
		com.marcelmika.lims.persistence.generated.model.Participant participant,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the participant with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchParticipantException} if it could not be found.
	*
	* @param pid the primary key of the participant
	* @return the participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant findByPrimaryKey(
		long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pid the primary key of the participant
	* @return the participant, or <code>null</code> if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByPrimaryKey(
		long pid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the participants where cid = &#63;.
	*
	* @param cid the cid
	* @return the matching participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByCid(
		long cid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the participants where cid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param cid the cid
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @return the range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByCid(
		long cid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the participants where cid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param cid the cid
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByCid(
		long cid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant findByCid_First(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the first participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByCid_First(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant findByCid_Last(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the last participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByCid_Last(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the participants before and after the current participant in the ordered set where cid = &#63;.
	*
	* @param pid the primary key of the current participant
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant[] findByCid_PrevAndNext(
		long pid, long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the participant where cid = &#63; and participantId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchParticipantException} if it could not be found.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant findByCidParticipantId(
		long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the participant where cid = &#63; and participantId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByCidParticipantId(
		long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the participant where cid = &#63; and participantId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByCidParticipantId(
		long cid, long participantId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the participants where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @return the matching participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByParticipantIdIsOpened(
		long participantId, boolean isOpened)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the participants where participantId = &#63; and isOpened = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @return the range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByParticipantIdIsOpened(
		long participantId, boolean isOpened, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the participants where participantId = &#63; and isOpened = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByParticipantIdIsOpened(
		long participantId, boolean isOpened, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant findByParticipantIdIsOpened_First(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the first participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByParticipantIdIsOpened_First(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant findByParticipantIdIsOpened_Last(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns the last participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant fetchByParticipantIdIsOpened_Last(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the participants before and after the current participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param pid the primary key of the current participant
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant[] findByParticipantIdIsOpened_PrevAndNext(
		long pid, long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Returns all the participants.
	*
	* @return the participants
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the participants where cid = &#63; from the database.
	*
	* @param cid the cid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCid(long cid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the participant where cid = &#63; and participantId = &#63; from the database.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Participant removeByCidParticipantId(
		long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException;

	/**
	* Removes all the participants where participantId = &#63; and isOpened = &#63; from the database.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @throws SystemException if a system exception occurred
	*/
	public void removeByParticipantIdIsOpened(long participantId,
		boolean isOpened)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the participants from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of participants where cid = &#63;.
	*
	* @param cid the cid
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByCid(long cid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of participants where cid = &#63; and participantId = &#63;.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByCidParticipantId(long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of participants where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public int countByParticipantIdIsOpened(long participantId, boolean isOpened)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of participants.
	*
	* @return the number of participants
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}