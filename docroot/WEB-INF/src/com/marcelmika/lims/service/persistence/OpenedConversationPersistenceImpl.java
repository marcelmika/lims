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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.marcelmika.lims.NoSuchOpenedConversationException;
import com.marcelmika.lims.model.OpenedConversation;
import com.marcelmika.lims.model.impl.OpenedConversationImpl;
import com.marcelmika.lims.model.impl.OpenedConversationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the opened conversation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OpenedConversationPersistence
 * @see OpenedConversationUtil
 * @generated
 */
public class OpenedConversationPersistenceImpl extends BasePersistenceImpl<OpenedConversation>
	implements OpenedConversationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link OpenedConversationUtil} to access the opened conversation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = OpenedConversationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED,
			OpenedConversationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUserId_ConversationId",
			new String[] { Long.class.getName(), String.class.getName() },
			OpenedConversationModelImpl.USERID_COLUMN_BITMASK |
			OpenedConversationModelImpl.CONVERSATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId_ConversationId",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED,
			OpenedConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED,
			OpenedConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			OpenedConversationModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED,
			OpenedConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED,
			OpenedConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the opened conversation in the entity cache if it is enabled.
	 *
	 * @param openedConversation the opened conversation
	 */
	public void cacheResult(OpenedConversation openedConversation) {
		EntityCacheUtil.putResult(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationImpl.class, openedConversation.getPrimaryKey(),
			openedConversation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
			new Object[] {
				Long.valueOf(openedConversation.getUserId()),
				
			openedConversation.getConversationId()
			}, openedConversation);

		openedConversation.resetOriginalValues();
	}

	/**
	 * Caches the opened conversations in the entity cache if it is enabled.
	 *
	 * @param openedConversations the opened conversations
	 */
	public void cacheResult(List<OpenedConversation> openedConversations) {
		for (OpenedConversation openedConversation : openedConversations) {
			if (EntityCacheUtil.getResult(
						OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
						OpenedConversationImpl.class,
						openedConversation.getPrimaryKey()) == null) {
				cacheResult(openedConversation);
			}
			else {
				openedConversation.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all opened conversations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(OpenedConversationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(OpenedConversationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the opened conversation.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OpenedConversation openedConversation) {
		EntityCacheUtil.removeResult(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationImpl.class, openedConversation.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(openedConversation);
	}

	@Override
	public void clearCache(List<OpenedConversation> openedConversations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OpenedConversation openedConversation : openedConversations) {
			EntityCacheUtil.removeResult(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
				OpenedConversationImpl.class, openedConversation.getPrimaryKey());

			clearUniqueFindersCache(openedConversation);
		}
	}

	protected void cacheUniqueFindersCache(
		OpenedConversation openedConversation) {
		if (openedConversation.isNew()) {
			Object[] args = new Object[] {
					Long.valueOf(openedConversation.getUserId()),
					
					openedConversation.getConversationId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
				args, openedConversation);
		}
		else {
			OpenedConversationModelImpl openedConversationModelImpl = (OpenedConversationModelImpl)openedConversation;

			if ((openedConversationModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(openedConversation.getUserId()),
						
						openedConversation.getConversationId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
					args, openedConversation);
			}
		}
	}

	protected void clearUniqueFindersCache(
		OpenedConversation openedConversation) {
		OpenedConversationModelImpl openedConversationModelImpl = (OpenedConversationModelImpl)openedConversation;

		Object[] args = new Object[] {
				Long.valueOf(openedConversation.getUserId()),
				
				openedConversation.getConversationId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
			args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
			args);

		if ((openedConversationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(openedConversationModelImpl.getOriginalUserId()),
					
					openedConversationModelImpl.getOriginalConversationId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
				args);
		}
	}

	/**
	 * Creates a new opened conversation with the primary key. Does not add the opened conversation to the database.
	 *
	 * @param ocid the primary key for the new opened conversation
	 * @return the new opened conversation
	 */
	public OpenedConversation create(long ocid) {
		OpenedConversation openedConversation = new OpenedConversationImpl();

		openedConversation.setNew(true);
		openedConversation.setPrimaryKey(ocid);

		return openedConversation;
	}

	/**
	 * Removes the opened conversation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ocid the primary key of the opened conversation
	 * @return the opened conversation that was removed
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation remove(long ocid)
		throws NoSuchOpenedConversationException, SystemException {
		return remove(Long.valueOf(ocid));
	}

	/**
	 * Removes the opened conversation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the opened conversation
	 * @return the opened conversation that was removed
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OpenedConversation remove(Serializable primaryKey)
		throws NoSuchOpenedConversationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OpenedConversation openedConversation = (OpenedConversation)session.get(OpenedConversationImpl.class,
					primaryKey);

			if (openedConversation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOpenedConversationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(openedConversation);
		}
		catch (NoSuchOpenedConversationException nsee) {
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
	protected OpenedConversation removeImpl(
		OpenedConversation openedConversation) throws SystemException {
		openedConversation = toUnwrappedModel(openedConversation);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, openedConversation);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(openedConversation);

		return openedConversation;
	}

	@Override
	public OpenedConversation updateImpl(
		com.marcelmika.lims.model.OpenedConversation openedConversation,
		boolean merge) throws SystemException {
		openedConversation = toUnwrappedModel(openedConversation);

		boolean isNew = openedConversation.isNew();

		OpenedConversationModelImpl openedConversationModelImpl = (OpenedConversationModelImpl)openedConversation;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, openedConversation, merge);

			openedConversation.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !OpenedConversationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((openedConversationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(openedConversationModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(openedConversationModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
			OpenedConversationImpl.class, openedConversation.getPrimaryKey(),
			openedConversation);

		clearUniqueFindersCache(openedConversation);
		cacheUniqueFindersCache(openedConversation);

		return openedConversation;
	}

	protected OpenedConversation toUnwrappedModel(
		OpenedConversation openedConversation) {
		if (openedConversation instanceof OpenedConversationImpl) {
			return openedConversation;
		}

		OpenedConversationImpl openedConversationImpl = new OpenedConversationImpl();

		openedConversationImpl.setNew(openedConversation.isNew());
		openedConversationImpl.setPrimaryKey(openedConversation.getPrimaryKey());

		openedConversationImpl.setOcid(openedConversation.getOcid());
		openedConversationImpl.setUserId(openedConversation.getUserId());
		openedConversationImpl.setConversationId(openedConversation.getConversationId());

		return openedConversationImpl;
	}

	/**
	 * Returns the opened conversation with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the opened conversation
	 * @return the opened conversation
	 * @throws com.liferay.portal.NoSuchModelException if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OpenedConversation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the opened conversation with the primary key or throws a {@link com.marcelmika.lims.NoSuchOpenedConversationException} if it could not be found.
	 *
	 * @param ocid the primary key of the opened conversation
	 * @return the opened conversation
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation findByPrimaryKey(long ocid)
		throws NoSuchOpenedConversationException, SystemException {
		OpenedConversation openedConversation = fetchByPrimaryKey(ocid);

		if (openedConversation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ocid);
			}

			throw new NoSuchOpenedConversationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				ocid);
		}

		return openedConversation;
	}

	/**
	 * Returns the opened conversation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the opened conversation
	 * @return the opened conversation, or <code>null</code> if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public OpenedConversation fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the opened conversation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ocid the primary key of the opened conversation
	 * @return the opened conversation, or <code>null</code> if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation fetchByPrimaryKey(long ocid)
		throws SystemException {
		OpenedConversation openedConversation = (OpenedConversation)EntityCacheUtil.getResult(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
				OpenedConversationImpl.class, ocid);

		if (openedConversation == _nullOpenedConversation) {
			return null;
		}

		if (openedConversation == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				openedConversation = (OpenedConversation)session.get(OpenedConversationImpl.class,
						Long.valueOf(ocid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (openedConversation != null) {
					cacheResult(openedConversation);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(OpenedConversationModelImpl.ENTITY_CACHE_ENABLED,
						OpenedConversationImpl.class, ocid,
						_nullOpenedConversation);
				}

				closeSession(session);
			}
		}

		return openedConversation;
	}

	/**
	 * Returns the opened conversation where userId = &#63; and conversationId = &#63; or throws a {@link com.marcelmika.lims.NoSuchOpenedConversationException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the matching opened conversation
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation findByUserId_ConversationId(long userId,
		String conversationId)
		throws NoSuchOpenedConversationException, SystemException {
		OpenedConversation openedConversation = fetchByUserId_ConversationId(userId,
				conversationId);

		if (openedConversation == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", conversationId=");
			msg.append(conversationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOpenedConversationException(msg.toString());
		}

		return openedConversation;
	}

	/**
	 * Returns the opened conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation fetchByUserId_ConversationId(long userId,
		String conversationId) throws SystemException {
		return fetchByUserId_ConversationId(userId, conversationId, true);
	}

	/**
	 * Returns the opened conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation fetchByUserId_ConversationId(long userId,
		String conversationId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, conversationId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
					finderArgs, this);
		}

		if (result instanceof OpenedConversation) {
			OpenedConversation openedConversation = (OpenedConversation)result;

			if ((userId != openedConversation.getUserId()) ||
					!Validator.equals(conversationId,
						openedConversation.getConversationId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_OPENEDCONVERSATION_WHERE);

			query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_USERID_2);

			if (conversationId == null) {
				query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_1);
			}
			else {
				if (conversationId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (conversationId != null) {
					qPos.add(conversationId);
				}

				List<OpenedConversation> list = q.list();

				result = list;

				OpenedConversation openedConversation = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
						finderArgs, list);
				}
				else {
					openedConversation = list.get(0);

					cacheResult(openedConversation);

					if ((openedConversation.getUserId() != userId) ||
							(openedConversation.getConversationId() == null) ||
							!openedConversation.getConversationId()
												   .equals(conversationId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
							finderArgs, openedConversation);
					}
				}

				return openedConversation;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
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
				return (OpenedConversation)result;
			}
		}
	}

	/**
	 * Returns all the opened conversations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<OpenedConversation> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the opened conversations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of opened conversations
	 * @param end the upper bound of the range of opened conversations (not inclusive)
	 * @return the range of matching opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<OpenedConversation> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the opened conversations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of opened conversations
	 * @param end the upper bound of the range of opened conversations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<OpenedConversation> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<OpenedConversation> list = (List<OpenedConversation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (OpenedConversation openedConversation : list) {
				if ((userId != openedConversation.getUserId())) {
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

			query.append(_SQL_SELECT_OPENEDCONVERSATION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

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

				qPos.add(userId);

				list = (List<OpenedConversation>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first opened conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching opened conversation
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchOpenedConversationException, SystemException {
		OpenedConversation openedConversation = fetchByUserId_First(userId,
				orderByComparator);

		if (openedConversation != null) {
			return openedConversation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOpenedConversationException(msg.toString());
	}

	/**
	 * Returns the first opened conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<OpenedConversation> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last opened conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching opened conversation
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchOpenedConversationException, SystemException {
		OpenedConversation openedConversation = fetchByUserId_Last(userId,
				orderByComparator);

		if (openedConversation != null) {
			return openedConversation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOpenedConversationException(msg.toString());
	}

	/**
	 * Returns the last opened conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching opened conversation, or <code>null</code> if a matching opened conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<OpenedConversation> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the opened conversations before and after the current opened conversation in the ordered set where userId = &#63;.
	 *
	 * @param ocid the primary key of the current opened conversation
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next opened conversation
	 * @throws com.marcelmika.lims.NoSuchOpenedConversationException if a opened conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation[] findByUserId_PrevAndNext(long ocid,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchOpenedConversationException, SystemException {
		OpenedConversation openedConversation = findByPrimaryKey(ocid);

		Session session = null;

		try {
			session = openSession();

			OpenedConversation[] array = new OpenedConversationImpl[3];

			array[0] = getByUserId_PrevAndNext(session, openedConversation,
					userId, orderByComparator, true);

			array[1] = openedConversation;

			array[2] = getByUserId_PrevAndNext(session, openedConversation,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OpenedConversation getByUserId_PrevAndNext(Session session,
		OpenedConversation openedConversation, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OPENEDCONVERSATION_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(openedConversation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OpenedConversation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the opened conversations.
	 *
	 * @return the opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<OpenedConversation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the opened conversations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of opened conversations
	 * @param end the upper bound of the range of opened conversations (not inclusive)
	 * @return the range of opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<OpenedConversation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the opened conversations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of opened conversations
	 * @param end the upper bound of the range of opened conversations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<OpenedConversation> findAll(int start, int end,
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

		List<OpenedConversation> list = (List<OpenedConversation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_OPENEDCONVERSATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OPENEDCONVERSATION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<OpenedConversation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<OpenedConversation>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes the opened conversation where userId = &#63; and conversationId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the opened conversation that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public OpenedConversation removeByUserId_ConversationId(long userId,
		String conversationId)
		throws NoSuchOpenedConversationException, SystemException {
		OpenedConversation openedConversation = findByUserId_ConversationId(userId,
				conversationId);

		return remove(openedConversation);
	}

	/**
	 * Removes all the opened conversations where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (OpenedConversation openedConversation : findByUserId(userId)) {
			remove(openedConversation);
		}
	}

	/**
	 * Removes all the opened conversations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (OpenedConversation openedConversation : findAll()) {
			remove(openedConversation);
		}
	}

	/**
	 * Returns the number of opened conversations where userId = &#63; and conversationId = &#63;.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the number of matching opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId_ConversationId(long userId, String conversationId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, conversationId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_OPENEDCONVERSATION_WHERE);

			query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_USERID_2);

			if (conversationId == null) {
				query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_1);
			}
			else {
				if (conversationId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (conversationId != null) {
					qPos.add(conversationId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of opened conversations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OPENEDCONVERSATION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of opened conversations.
	 *
	 * @return the number of opened conversations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OPENEDCONVERSATION);

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
	 * Initializes the opened conversation persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.model.OpenedConversation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<OpenedConversation>> listenersList = new ArrayList<ModelListener<OpenedConversation>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<OpenedConversation>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(OpenedConversationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = BuddyPersistence.class)
	protected BuddyPersistence buddyPersistence;
	@BeanReference(type = ConversationPersistence.class)
	protected ConversationPersistence conversationPersistence;
	@BeanReference(type = OpenedConversationPersistence.class)
	protected OpenedConversationPersistence openedConversationPersistence;
	@BeanReference(type = PanelPersistence.class)
	protected PanelPersistence panelPersistence;
	@BeanReference(type = SettingsPersistence.class)
	protected SettingsPersistence settingsPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_OPENEDCONVERSATION = "SELECT openedConversation FROM OpenedConversation openedConversation";
	private static final String _SQL_SELECT_OPENEDCONVERSATION_WHERE = "SELECT openedConversation FROM OpenedConversation openedConversation WHERE ";
	private static final String _SQL_COUNT_OPENEDCONVERSATION = "SELECT COUNT(openedConversation) FROM OpenedConversation openedConversation";
	private static final String _SQL_COUNT_OPENEDCONVERSATION_WHERE = "SELECT COUNT(openedConversation) FROM OpenedConversation openedConversation WHERE ";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_USERID_2 = "openedConversation.userId = ? AND ";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_1 =
		"openedConversation.conversationId IS NULL";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_2 =
		"openedConversation.conversationId = ?";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_3 =
		"(openedConversation.conversationId IS NULL OR openedConversation.conversationId = ?)";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "openedConversation.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "openedConversation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OpenedConversation exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OpenedConversation exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(OpenedConversationPersistenceImpl.class);
	private static OpenedConversation _nullOpenedConversation = new OpenedConversationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<OpenedConversation> toCacheModel() {
				return _nullOpenedConversationCacheModel;
			}
		};

	private static CacheModel<OpenedConversation> _nullOpenedConversationCacheModel =
		new CacheModel<OpenedConversation>() {
			public OpenedConversation toEntityModel() {
				return _nullOpenedConversation;
			}
		};
}