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

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.marcelmika.lims.persistence.generated.NoSuchParticipantException;
import com.marcelmika.lims.persistence.generated.model.Participant;
import com.marcelmika.lims.persistence.generated.model.impl.ParticipantImpl;
import com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the participant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParticipantPersistence
 * @see ParticipantUtil
 * @generated
 */
public class ParticipantPersistenceImpl extends BasePersistenceImpl<Participant>
	implements ParticipantPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ParticipantUtil} to access the participant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ParticipantImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCid",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCid",
			new String[] { Long.class.getName() },
			ParticipantModelImpl.CID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCid",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the participants where cid = &#63;.
	 *
	 * @param cid the cid
	 * @return the matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findByCid(long cid) throws SystemException {
		return findByCid(cid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the participants where cid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param cid the cid
	 * @param start the lower bound of the range of participants
	 * @param end the upper bound of the range of participants (not inclusive)
	 * @return the range of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findByCid(long cid, int start, int end)
		throws SystemException {
		return findByCid(cid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the participants where cid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param cid the cid
	 * @param start the lower bound of the range of participants
	 * @param end the upper bound of the range of participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findByCid(long cid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID;
			finderArgs = new Object[] { cid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CID;
			finderArgs = new Object[] { cid, start, end, orderByComparator };
		}

		List<Participant> list = (List<Participant>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Participant participant : list) {
				if ((cid != participant.getCid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CID_CID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ParticipantModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				if (!pagination) {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Participant>(list);
				}
				else {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first participant in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching participant
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant findByCid_First(long cid,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByCid_First(cid, orderByComparator);

		if (participant != null) {
			return participant;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("cid=");
		msg.append(cid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the first participant in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByCid_First(long cid,
		OrderByComparator orderByComparator) throws SystemException {
		List<Participant> list = findByCid(cid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last participant in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching participant
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant findByCid_Last(long cid,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByCid_Last(cid, orderByComparator);

		if (participant != null) {
			return participant;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("cid=");
		msg.append(cid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the last participant in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByCid_Last(long cid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCid(cid);

		if (count == 0) {
			return null;
		}

		List<Participant> list = findByCid(cid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public Participant[] findByCid_PrevAndNext(long pid, long cid,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		Participant participant = findByPrimaryKey(pid);

		Session session = null;

		try {
			session = openSession();

			Participant[] array = new ParticipantImpl[3];

			array[0] = getByCid_PrevAndNext(session, participant, cid,
					orderByComparator, true);

			array[1] = participant;

			array[2] = getByCid_PrevAndNext(session, participant, cid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Participant getByCid_PrevAndNext(Session session,
		Participant participant, long cid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PARTICIPANT_WHERE);

		query.append(_FINDER_COLUMN_CID_CID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ParticipantModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(cid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(participant);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Participant> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the participants where cid = &#63; from the database.
	 *
	 * @param cid the cid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByCid(long cid) throws SystemException {
		for (Participant participant : findByCid(cid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(participant);
		}
	}

	/**
	 * Returns the number of participants where cid = &#63;.
	 *
	 * @param cid the cid
	 * @return the number of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByCid(long cid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CID;

		Object[] finderArgs = new Object[] { cid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CID_CID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CID_CID_2 = "participant.cid = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CIDPARTICIPANTID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCidParticipantId",
			new String[] { Long.class.getName(), Long.class.getName() },
			ParticipantModelImpl.CID_COLUMN_BITMASK |
			ParticipantModelImpl.PARTICIPANTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CIDPARTICIPANTID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCidParticipantId",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the participant where cid = &#63; and participantId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchParticipantException} if it could not be found.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the matching participant
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant findByCidParticipantId(long cid, long participantId)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByCidParticipantId(cid, participantId);

		if (participant == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("cid=");
			msg.append(cid);

			msg.append(", participantId=");
			msg.append(participantId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchParticipantException(msg.toString());
		}

		return participant;
	}

	/**
	 * Returns the participant where cid = &#63; and participantId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByCidParticipantId(long cid, long participantId)
		throws SystemException {
		return fetchByCidParticipantId(cid, participantId, true);
	}

	/**
	 * Returns the participant where cid = &#63; and participantId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByCidParticipantId(long cid, long participantId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { cid, participantId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
					finderArgs, this);
		}

		if (result instanceof Participant) {
			Participant participant = (Participant)result;

			if ((cid != participant.getCid()) ||
					(participantId != participant.getParticipantId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CIDPARTICIPANTID_CID_2);

			query.append(_FINDER_COLUMN_CIDPARTICIPANTID_PARTICIPANTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				qPos.add(participantId);

				List<Participant> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ParticipantPersistenceImpl.fetchByCidParticipantId(long, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Participant participant = list.get(0);

					result = participant;

					cacheResult(participant);

					if ((participant.getCid() != cid) ||
							(participant.getParticipantId() != participantId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
							finderArgs, participant);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Participant)result;
		}
	}

	/**
	 * Removes the participant where cid = &#63; and participantId = &#63; from the database.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the participant that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant removeByCidParticipantId(long cid, long participantId)
		throws NoSuchParticipantException, SystemException {
		Participant participant = findByCidParticipantId(cid, participantId);

		return remove(participant);
	}

	/**
	 * Returns the number of participants where cid = &#63; and participantId = &#63;.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the number of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByCidParticipantId(long cid, long participantId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CIDPARTICIPANTID;

		Object[] finderArgs = new Object[] { cid, participantId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CIDPARTICIPANTID_CID_2);

			query.append(_FINDER_COLUMN_CIDPARTICIPANTID_PARTICIPANTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				qPos.add(participantId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CIDPARTICIPANTID_CID_2 = "participant.cid = ? AND ";
	private static final String _FINDER_COLUMN_CIDPARTICIPANTID_PARTICIPANTID_2 = "participant.participantId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED =
		new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParticipantIdIsOpened",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED =
		new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParticipantIdIsOpened",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			ParticipantModelImpl.PARTICIPANTID_COLUMN_BITMASK |
			ParticipantModelImpl.ISOPENED_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PARTICIPANTIDISOPENED = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParticipantIdIsOpened",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the participants where participantId = &#63; and isOpened = &#63;.
	 *
	 * @param participantId the participant ID
	 * @param isOpened the is opened
	 * @return the matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findByParticipantIdIsOpened(long participantId,
		boolean isOpened) throws SystemException {
		return findByParticipantIdIsOpened(participantId, isOpened,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the participants where participantId = &#63; and isOpened = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param participantId the participant ID
	 * @param isOpened the is opened
	 * @param start the lower bound of the range of participants
	 * @param end the upper bound of the range of participants (not inclusive)
	 * @return the range of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findByParticipantIdIsOpened(long participantId,
		boolean isOpened, int start, int end) throws SystemException {
		return findByParticipantIdIsOpened(participantId, isOpened, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the participants where participantId = &#63; and isOpened = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	@Override
	public List<Participant> findByParticipantIdIsOpened(long participantId,
		boolean isOpened, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED;
			finderArgs = new Object[] { participantId, isOpened };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED;
			finderArgs = new Object[] {
					participantId, isOpened,
					
					start, end, orderByComparator
				};
		}

		List<Participant> list = (List<Participant>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Participant participant : list) {
				if ((participantId != participant.getParticipantId()) ||
						(isOpened != participant.getIsOpened())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PARTICIPANTIDISOPENED_PARTICIPANTID_2);

			query.append(_FINDER_COLUMN_PARTICIPANTIDISOPENED_ISOPENED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ParticipantModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(participantId);

				qPos.add(isOpened);

				if (!pagination) {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Participant>(list);
				}
				else {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

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
	@Override
	public Participant findByParticipantIdIsOpened_First(long participantId,
		boolean isOpened, OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByParticipantIdIsOpened_First(participantId,
				isOpened, orderByComparator);

		if (participant != null) {
			return participant;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("participantId=");
		msg.append(participantId);

		msg.append(", isOpened=");
		msg.append(isOpened);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the first participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	 *
	 * @param participantId the participant ID
	 * @param isOpened the is opened
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByParticipantIdIsOpened_First(long participantId,
		boolean isOpened, OrderByComparator orderByComparator)
		throws SystemException {
		List<Participant> list = findByParticipantIdIsOpened(participantId,
				isOpened, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public Participant findByParticipantIdIsOpened_Last(long participantId,
		boolean isOpened, OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByParticipantIdIsOpened_Last(participantId,
				isOpened, orderByComparator);

		if (participant != null) {
			return participant;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("participantId=");
		msg.append(participantId);

		msg.append(", isOpened=");
		msg.append(isOpened);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the last participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	 *
	 * @param participantId the participant ID
	 * @param isOpened the is opened
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByParticipantIdIsOpened_Last(long participantId,
		boolean isOpened, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByParticipantIdIsOpened(participantId, isOpened);

		if (count == 0) {
			return null;
		}

		List<Participant> list = findByParticipantIdIsOpened(participantId,
				isOpened, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public Participant[] findByParticipantIdIsOpened_PrevAndNext(long pid,
		long participantId, boolean isOpened,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		Participant participant = findByPrimaryKey(pid);

		Session session = null;

		try {
			session = openSession();

			Participant[] array = new ParticipantImpl[3];

			array[0] = getByParticipantIdIsOpened_PrevAndNext(session,
					participant, participantId, isOpened, orderByComparator,
					true);

			array[1] = participant;

			array[2] = getByParticipantIdIsOpened_PrevAndNext(session,
					participant, participantId, isOpened, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Participant getByParticipantIdIsOpened_PrevAndNext(
		Session session, Participant participant, long participantId,
		boolean isOpened, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PARTICIPANT_WHERE);

		query.append(_FINDER_COLUMN_PARTICIPANTIDISOPENED_PARTICIPANTID_2);

		query.append(_FINDER_COLUMN_PARTICIPANTIDISOPENED_ISOPENED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ParticipantModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(participantId);

		qPos.add(isOpened);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(participant);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Participant> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the participants where participantId = &#63; and isOpened = &#63; from the database.
	 *
	 * @param participantId the participant ID
	 * @param isOpened the is opened
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByParticipantIdIsOpened(long participantId,
		boolean isOpened) throws SystemException {
		for (Participant participant : findByParticipantIdIsOpened(
				participantId, isOpened, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(participant);
		}
	}

	/**
	 * Returns the number of participants where participantId = &#63; and isOpened = &#63;.
	 *
	 * @param participantId the participant ID
	 * @param isOpened the is opened
	 * @return the number of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByParticipantIdIsOpened(long participantId, boolean isOpened)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PARTICIPANTIDISOPENED;

		Object[] finderArgs = new Object[] { participantId, isOpened };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PARTICIPANTIDISOPENED_PARTICIPANTID_2);

			query.append(_FINDER_COLUMN_PARTICIPANTIDISOPENED_ISOPENED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(participantId);

				qPos.add(isOpened);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PARTICIPANTIDISOPENED_PARTICIPANTID_2 =
		"participant.participantId = ? AND ";
	private static final String _FINDER_COLUMN_PARTICIPANTIDISOPENED_ISOPENED_2 = "participant.isOpened = ?";

	public ParticipantPersistenceImpl() {
		setModelClass(Participant.class);
	}

	/**
	 * Caches the participant in the entity cache if it is enabled.
	 *
	 * @param participant the participant
	 */
	@Override
	public void cacheResult(Participant participant) {
		EntityCacheUtil.putResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantImpl.class, participant.getPrimaryKey(), participant);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
			new Object[] { participant.getCid(), participant.getParticipantId() },
			participant);

		participant.resetOriginalValues();
	}

	/**
	 * Caches the participants in the entity cache if it is enabled.
	 *
	 * @param participants the participants
	 */
	@Override
	public void cacheResult(List<Participant> participants) {
		for (Participant participant : participants) {
			if (EntityCacheUtil.getResult(
						ParticipantModelImpl.ENTITY_CACHE_ENABLED,
						ParticipantImpl.class, participant.getPrimaryKey()) == null) {
				cacheResult(participant);
			}
			else {
				participant.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all participants.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ParticipantImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ParticipantImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the participant.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Participant participant) {
		EntityCacheUtil.removeResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantImpl.class, participant.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(participant);
	}

	@Override
	public void clearCache(List<Participant> participants) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Participant participant : participants) {
			EntityCacheUtil.removeResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
				ParticipantImpl.class, participant.getPrimaryKey());

			clearUniqueFindersCache(participant);
		}
	}

	protected void cacheUniqueFindersCache(Participant participant) {
		if (participant.isNew()) {
			Object[] args = new Object[] {
					participant.getCid(), participant.getParticipantId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CIDPARTICIPANTID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
				args, participant);
		}
		else {
			ParticipantModelImpl participantModelImpl = (ParticipantModelImpl)participant;

			if ((participantModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CIDPARTICIPANTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						participant.getCid(), participant.getParticipantId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CIDPARTICIPANTID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
					args, participant);
			}
		}
	}

	protected void clearUniqueFindersCache(Participant participant) {
		ParticipantModelImpl participantModelImpl = (ParticipantModelImpl)participant;

		Object[] args = new Object[] {
				participant.getCid(), participant.getParticipantId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CIDPARTICIPANTID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID, args);

		if ((participantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CIDPARTICIPANTID.getColumnBitmask()) != 0) {
			args = new Object[] {
					participantModelImpl.getOriginalCid(),
					participantModelImpl.getOriginalParticipantId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CIDPARTICIPANTID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CIDPARTICIPANTID,
				args);
		}
	}

	/**
	 * Creates a new participant with the primary key. Does not add the participant to the database.
	 *
	 * @param pid the primary key for the new participant
	 * @return the new participant
	 */
	@Override
	public Participant create(long pid) {
		Participant participant = new ParticipantImpl();

		participant.setNew(true);
		participant.setPrimaryKey(pid);

		return participant;
	}

	/**
	 * Removes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant remove(long pid)
		throws NoSuchParticipantException, SystemException {
		return remove((Serializable)pid);
	}

	/**
	 * Removes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the participant
	 * @return the participant that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant remove(Serializable primaryKey)
		throws NoSuchParticipantException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Participant participant = (Participant)session.get(ParticipantImpl.class,
					primaryKey);

			if (participant == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchParticipantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(participant);
		}
		catch (NoSuchParticipantException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Participant removeImpl(Participant participant)
		throws SystemException {
		participant = toUnwrappedModel(participant);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(participant)) {
				participant = (Participant)session.get(ParticipantImpl.class,
						participant.getPrimaryKeyObj());
			}

			if (participant != null) {
				session.delete(participant);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (participant != null) {
			clearCache(participant);
		}

		return participant;
	}

	@Override
	public Participant updateImpl(
		com.marcelmika.lims.persistence.generated.model.Participant participant)
		throws SystemException {
		participant = toUnwrappedModel(participant);

		boolean isNew = participant.isNew();

		ParticipantModelImpl participantModelImpl = (ParticipantModelImpl)participant;

		Session session = null;

		try {
			session = openSession();

			if (participant.isNew()) {
				session.save(participant);

				participant.setNew(false);
			}
			else {
				session.merge(participant);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ParticipantModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((participantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						participantModelImpl.getOriginalCid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID,
					args);

				args = new Object[] { participantModelImpl.getCid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID,
					args);
			}

			if ((participantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						participantModelImpl.getOriginalParticipantId(),
						participantModelImpl.getOriginalIsOpened()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARTICIPANTIDISOPENED,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED,
					args);

				args = new Object[] {
						participantModelImpl.getParticipantId(),
						participantModelImpl.getIsOpened()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARTICIPANTIDISOPENED,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARTICIPANTIDISOPENED,
					args);
			}
		}

		EntityCacheUtil.putResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantImpl.class, participant.getPrimaryKey(), participant);

		clearUniqueFindersCache(participant);
		cacheUniqueFindersCache(participant);

		return participant;
	}

	protected Participant toUnwrappedModel(Participant participant) {
		if (participant instanceof ParticipantImpl) {
			return participant;
		}

		ParticipantImpl participantImpl = new ParticipantImpl();

		participantImpl.setNew(participant.isNew());
		participantImpl.setPrimaryKey(participant.getPrimaryKey());

		participantImpl.setPid(participant.getPid());
		participantImpl.setCid(participant.getCid());
		participantImpl.setParticipantId(participant.getParticipantId());
		participantImpl.setUnreadMessagesCount(participant.getUnreadMessagesCount());
		participantImpl.setIsOpened(participant.isIsOpened());

		return participantImpl;
	}

	/**
	 * Returns the participant with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the participant
	 * @return the participant
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant findByPrimaryKey(Serializable primaryKey)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByPrimaryKey(primaryKey);

		if (participant == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchParticipantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return participant;
	}

	/**
	 * Returns the participant with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchParticipantException} if it could not be found.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant findByPrimaryKey(long pid)
		throws NoSuchParticipantException, SystemException {
		return findByPrimaryKey((Serializable)pid);
	}

	/**
	 * Returns the participant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the participant
	 * @return the participant, or <code>null</code> if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Participant participant = (Participant)EntityCacheUtil.getResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
				ParticipantImpl.class, primaryKey);

		if (participant == _nullParticipant) {
			return null;
		}

		if (participant == null) {
			Session session = null;

			try {
				session = openSession();

				participant = (Participant)session.get(ParticipantImpl.class,
						primaryKey);

				if (participant != null) {
					cacheResult(participant);
				}
				else {
					EntityCacheUtil.putResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
						ParticipantImpl.class, primaryKey, _nullParticipant);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
					ParticipantImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return participant;
	}

	/**
	 * Returns the participant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant, or <code>null</code> if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant fetchByPrimaryKey(long pid) throws SystemException {
		return fetchByPrimaryKey((Serializable)pid);
	}

	/**
	 * Returns all the participants.
	 *
	 * @return the participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of participants
	 * @param end the upper bound of the range of participants (not inclusive)
	 * @return the range of participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of participants
	 * @param end the upper bound of the range of participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Participant> list = (List<Participant>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PARTICIPANT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PARTICIPANT;

				if (pagination) {
					sql = sql.concat(ParticipantModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Participant>(list);
				}
				else {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the participants from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Participant participant : findAll()) {
			remove(participant);
		}
	}

	/**
	 * Returns the number of participants.
	 *
	 * @return the number of participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PARTICIPANT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the participant persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.persistence.generated.model.Participant")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Participant>> listenersList = new ArrayList<ModelListener<Participant>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Participant>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ParticipantImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PARTICIPANT = "SELECT participant FROM Participant participant";
	private static final String _SQL_SELECT_PARTICIPANT_WHERE = "SELECT participant FROM Participant participant WHERE ";
	private static final String _SQL_COUNT_PARTICIPANT = "SELECT COUNT(participant) FROM Participant participant";
	private static final String _SQL_COUNT_PARTICIPANT_WHERE = "SELECT COUNT(participant) FROM Participant participant WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "participant.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Participant exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Participant exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ParticipantPersistenceImpl.class);
	private static Participant _nullParticipant = new ParticipantImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Participant> toCacheModel() {
				return _nullParticipantCacheModel;
			}
		};

	private static CacheModel<Participant> _nullParticipantCacheModel = new CacheModel<Participant>() {
			@Override
			public Participant toEntityModel() {
				return _nullParticipant;
			}
		};
}