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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.marcelmika.lims.NoSuchParticipantException;
import com.marcelmika.lims.model.Participant;
import com.marcelmika.lims.model.impl.ParticipantImpl;
import com.marcelmika.lims.model.impl.ParticipantModelImpl;

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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
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
	public static final FinderPath FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID =
		new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByConversationIdParticipantId",
			new String[] { Long.class.getName() },
			ParticipantModelImpl.PARTICIPANTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID =
		new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByConversationIdParticipantId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CID_PARTICIPANTID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchBycid_ParticipantId",
			new String[] { Long.class.getName(), Long.class.getName() },
			ParticipantModelImpl.CID_COLUMN_BITMASK |
			ParticipantModelImpl.PARTICIPANTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CID_PARTICIPANTID = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBycid_ParticipantId",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, ParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the participant in the entity cache if it is enabled.
	 *
	 * @param participant the participant
	 */
	public void cacheResult(Participant participant) {
		EntityCacheUtil.putResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
			ParticipantImpl.class, participant.getPrimaryKey(), participant);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
			new Object[] { Long.valueOf(participant.getParticipantId()) },
			participant);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
			new Object[] {
				Long.valueOf(participant.getCid()),
				Long.valueOf(participant.getParticipantId())
			}, participant);

		participant.resetOriginalValues();
	}

	/**
	 * Caches the participants in the entity cache if it is enabled.
	 *
	 * @param participants the participants
	 */
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
					Long.valueOf(participant.getParticipantId())
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
				args, participant);

			args = new Object[] {
					Long.valueOf(participant.getCid()),
					Long.valueOf(participant.getParticipantId())
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CID_PARTICIPANTID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
				args, participant);
		}
		else {
			ParticipantModelImpl participantModelImpl = (ParticipantModelImpl)participant;

			if ((participantModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(participant.getParticipantId())
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
					args, participant);
			}

			if ((participantModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CID_PARTICIPANTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(participant.getCid()),
						Long.valueOf(participant.getParticipantId())
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CID_PARTICIPANTID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
					args, participant);
			}
		}
	}

	protected void clearUniqueFindersCache(Participant participant) {
		ParticipantModelImpl participantModelImpl = (ParticipantModelImpl)participant;

		Object[] args = new Object[] {
				Long.valueOf(participant.getParticipantId())
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID,
			args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
			args);

		if ((participantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(participantModelImpl.getOriginalParticipantId())
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
				args);
		}

		args = new Object[] {
				Long.valueOf(participant.getCid()),
				Long.valueOf(participant.getParticipantId())
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID_PARTICIPANTID,
			args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
			args);

		if ((participantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CID_PARTICIPANTID.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(participantModelImpl.getOriginalCid()),
					Long.valueOf(participantModelImpl.getOriginalParticipantId())
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID_PARTICIPANTID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
				args);
		}
	}

	/**
	 * Creates a new participant with the primary key. Does not add the participant to the database.
	 *
	 * @param pid the primary key for the new participant
	 * @return the new participant
	 */
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
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant remove(long pid)
		throws NoSuchParticipantException, SystemException {
		return remove(Long.valueOf(pid));
	}

	/**
	 * Removes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the participant
	 * @return the participant that was removed
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a participant with the primary key could not be found
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

			BatchSessionUtil.delete(session, participant);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(participant);

		return participant;
	}

	@Override
	public Participant updateImpl(
		com.marcelmika.lims.model.Participant participant, boolean merge)
		throws SystemException {
		participant = toUnwrappedModel(participant);

		boolean isNew = participant.isNew();

		ParticipantModelImpl participantModelImpl = (ParticipantModelImpl)participant;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, participant, merge);

			participant.setNew(false);
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
						Long.valueOf(participantModelImpl.getOriginalCid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID,
					args);

				args = new Object[] { Long.valueOf(participantModelImpl.getCid()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID,
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
	 * @throws com.liferay.portal.NoSuchModelException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the participant with the primary key or throws a {@link com.marcelmika.lims.NoSuchParticipantException} if it could not be found.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant findByPrimaryKey(long pid)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByPrimaryKey(pid);

		if (participant == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + pid);
			}

			throw new NoSuchParticipantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				pid);
		}

		return participant;
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
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the participant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant, or <code>null</code> if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant fetchByPrimaryKey(long pid) throws SystemException {
		Participant participant = (Participant)EntityCacheUtil.getResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
				ParticipantImpl.class, pid);

		if (participant == _nullParticipant) {
			return null;
		}

		if (participant == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				participant = (Participant)session.get(ParticipantImpl.class,
						Long.valueOf(pid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (participant != null) {
					cacheResult(participant);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ParticipantModelImpl.ENTITY_CACHE_ENABLED,
						ParticipantImpl.class, pid, _nullParticipant);
				}

				closeSession(session);
			}
		}

		return participant;
	}

	/**
	 * Returns all the participants where cid = &#63;.
	 *
	 * @param cid the cid
	 * @return the matching participants
	 * @throws SystemException if a system exception occurred
	 */
	public List<Participant> findByCid(long cid) throws SystemException {
		return findByCid(cid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	public List<Participant> findByCid(long cid, int start, int end)
		throws SystemException {
		return findByCid(cid, start, end, null);
	}

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
	public List<Participant> findByCid(long cid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CID_CID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				list = (List<Participant>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

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
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
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
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
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
	public Participant fetchByCid_Last(long cid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCid(cid);

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
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
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
	 * Returns the participant where participantId = &#63; or throws a {@link com.marcelmika.lims.NoSuchParticipantException} if it could not be found.
	 *
	 * @param participantId the participant ID
	 * @return the matching participant
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant findByConversationIdParticipantId(long participantId)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchByConversationIdParticipantId(participantId);

		if (participant == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("participantId=");
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
	 * Returns the participant where participantId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param participantId the participant ID
	 * @return the matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant fetchByConversationIdParticipantId(long participantId)
		throws SystemException {
		return fetchByConversationIdParticipantId(participantId, true);
	}

	/**
	 * Returns the participant where participantId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param participantId the participant ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching participant, or <code>null</code> if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant fetchByConversationIdParticipantId(long participantId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { participantId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
					finderArgs, this);
		}

		if (result instanceof Participant) {
			Participant participant = (Participant)result;

			if ((participantId != participant.getParticipantId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CONVERSATIONIDPARTICIPANTID_PARTICIPANTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(participantId);

				List<Participant> list = q.list();

				result = list;

				Participant participant = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
						finderArgs, list);
				}
				else {
					participant = list.get(0);

					cacheResult(participant);

					if ((participant.getParticipantId() != participantId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
							finderArgs, participant);
					}
				}

				return participant;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONVERSATIONIDPARTICIPANTID,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Participant)result;
			}
		}
	}

	/**
	 * Returns the participant where cid = &#63; and participantId = &#63; or throws a {@link com.marcelmika.lims.NoSuchParticipantException} if it could not be found.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the matching participant
	 * @throws com.marcelmika.lims.NoSuchParticipantException if a matching participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Participant findBycid_ParticipantId(long cid, long participantId)
		throws NoSuchParticipantException, SystemException {
		Participant participant = fetchBycid_ParticipantId(cid, participantId);

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
	public Participant fetchBycid_ParticipantId(long cid, long participantId)
		throws SystemException {
		return fetchBycid_ParticipantId(cid, participantId, true);
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
	public Participant fetchBycid_ParticipantId(long cid, long participantId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { cid, participantId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
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
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CID_PARTICIPANTID_CID_2);

			query.append(_FINDER_COLUMN_CID_PARTICIPANTID_PARTICIPANTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				qPos.add(participantId);

				List<Participant> list = q.list();

				result = list;

				Participant participant = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
						finderArgs, list);
				}
				else {
					participant = list.get(0);

					cacheResult(participant);

					if ((participant.getCid() != cid) ||
							(participant.getParticipantId() != participantId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
							finderArgs, participant);
					}
				}

				return participant;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CID_PARTICIPANTID,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Participant)result;
			}
		}
	}

	/**
	 * Returns all the participants.
	 *
	 * @return the participants
	 * @throws SystemException if a system exception occurred
	 */
	public List<Participant> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<Participant> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	public List<Participant> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
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
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Participant>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the participants where cid = &#63; from the database.
	 *
	 * @param cid the cid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCid(long cid) throws SystemException {
		for (Participant participant : findByCid(cid)) {
			remove(participant);
		}
	}

	/**
	 * Removes the participant where participantId = &#63; from the database.
	 *
	 * @param participantId the participant ID
	 * @return the participant that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Participant removeByConversationIdParticipantId(long participantId)
		throws NoSuchParticipantException, SystemException {
		Participant participant = findByConversationIdParticipantId(participantId);

		return remove(participant);
	}

	/**
	 * Removes the participant where cid = &#63; and participantId = &#63; from the database.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the participant that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Participant removeBycid_ParticipantId(long cid, long participantId)
		throws NoSuchParticipantException, SystemException {
		Participant participant = findBycid_ParticipantId(cid, participantId);

		return remove(participant);
	}

	/**
	 * Removes all the participants from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Participant participant : findAll()) {
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
	public int countByCid(long cid) throws SystemException {
		Object[] finderArgs = new Object[] { cid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CID,
				finderArgs, this);

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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CID, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of participants where participantId = &#63;.
	 *
	 * @param participantId the participant ID
	 * @return the number of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	public int countByConversationIdParticipantId(long participantId)
		throws SystemException {
		Object[] finderArgs = new Object[] { participantId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CONVERSATIONIDPARTICIPANTID_PARTICIPANTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(participantId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONVERSATIONIDPARTICIPANTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of participants where cid = &#63; and participantId = &#63;.
	 *
	 * @param cid the cid
	 * @param participantId the participant ID
	 * @return the number of matching participants
	 * @throws SystemException if a system exception occurred
	 */
	public int countBycid_ParticipantId(long cid, long participantId)
		throws SystemException {
		Object[] finderArgs = new Object[] { cid, participantId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CID_PARTICIPANTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_CID_PARTICIPANTID_CID_2);

			query.append(_FINDER_COLUMN_CID_PARTICIPANTID_PARTICIPANTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(cid);

				qPos.add(participantId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CID_PARTICIPANTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of participants.
	 *
	 * @return the number of participants
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PARTICIPANT);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

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
						"value.object.listener.com.marcelmika.lims.model.Participant")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Participant>> listenersList = new ArrayList<ModelListener<Participant>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<Participant>)InstanceFactory.newInstance(
							clazz.getClassLoader(), listenerClassName));
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
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = BuddyPersistence.class)
	protected BuddyPersistence buddyPersistence;
	@BeanReference(type = ConversationPersistence.class)
	protected ConversationPersistence conversationPersistence;
	@BeanReference(type = MessagePersistence.class)
	protected MessagePersistence messagePersistence;
	@BeanReference(type = OpenedConversationPersistence.class)
	protected OpenedConversationPersistence openedConversationPersistence;
	@BeanReference(type = PanelPersistence.class)
	protected PanelPersistence panelPersistence;
	@BeanReference(type = ParticipantPersistence.class)
	protected ParticipantPersistence participantPersistence;
	@BeanReference(type = SettingsPersistence.class)
	protected SettingsPersistence settingsPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_PARTICIPANT = "SELECT participant FROM Participant participant";
	private static final String _SQL_SELECT_PARTICIPANT_WHERE = "SELECT participant FROM Participant participant WHERE ";
	private static final String _SQL_COUNT_PARTICIPANT = "SELECT COUNT(participant) FROM Participant participant";
	private static final String _SQL_COUNT_PARTICIPANT_WHERE = "SELECT COUNT(participant) FROM Participant participant WHERE ";
	private static final String _FINDER_COLUMN_CID_CID_2 = "participant.cid = ?";
	private static final String _FINDER_COLUMN_CONVERSATIONIDPARTICIPANTID_PARTICIPANTID_2 =
		"participant.participantId = ?";
	private static final String _FINDER_COLUMN_CID_PARTICIPANTID_CID_2 = "participant.cid = ? AND ";
	private static final String _FINDER_COLUMN_CID_PARTICIPANTID_PARTICIPANTID_2 =
		"participant.participantId = ?";
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
			public Participant toEntityModel() {
				return _nullParticipant;
			}
		};
}