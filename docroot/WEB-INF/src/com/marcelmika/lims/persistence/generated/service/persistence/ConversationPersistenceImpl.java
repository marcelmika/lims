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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.marcelmika.lims.persistence.generated.NoSuchConversationException;
import com.marcelmika.lims.persistence.generated.model.Conversation;
import com.marcelmika.lims.persistence.generated.model.impl.ConversationImpl;
import com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the conversation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ConversationPersistence
 * @see ConversationUtil
 * @generated
 */
public class ConversationPersistenceImpl extends BasePersistenceImpl<Conversation>
	implements ConversationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ConversationUtil} to access the conversation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ConversationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_CONVERSATIONID = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByConversationId",
			new String[] { String.class.getName() },
			ConversationModelImpl.CONVERSATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONVERSATIONID = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByConversationId",
			new String[] { String.class.getName() });

	/**
	 * Returns the conversation where conversationId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchConversationException} if it could not be found.
	 *
	 * @param conversationId the conversation ID
	 * @return the matching conversation
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation findByConversationId(String conversationId)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = fetchByConversationId(conversationId);

		if (conversation == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("conversationId=");
			msg.append(conversationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchConversationException(msg.toString());
		}

		return conversation;
	}

	/**
	 * Returns the conversation where conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param conversationId the conversation ID
	 * @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation fetchByConversationId(String conversationId)
		throws SystemException {
		return fetchByConversationId(conversationId, true);
	}

	/**
	 * Returns the conversation where conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param conversationId the conversation ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation fetchByConversationId(String conversationId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { conversationId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
					finderArgs, this);
		}

		if (result instanceof Conversation) {
			Conversation conversation = (Conversation)result;

			if (!Validator.equals(conversationId,
						conversation.getConversationId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CONVERSATION_WHERE);

			boolean bindConversationId = false;

			if (conversationId == null) {
				query.append(_FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_1);
			}
			else if (conversationId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_3);
			}
			else {
				bindConversationId = true;

				query.append(_FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConversationId) {
					qPos.add(conversationId);
				}

				List<Conversation> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ConversationPersistenceImpl.fetchByConversationId(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Conversation conversation = list.get(0);

					result = conversation;

					cacheResult(conversation);

					if ((conversation.getConversationId() == null) ||
							!conversation.getConversationId()
											 .equals(conversationId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
							finderArgs, conversation);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
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
			return (Conversation)result;
		}
	}

	/**
	 * Removes the conversation where conversationId = &#63; from the database.
	 *
	 * @param conversationId the conversation ID
	 * @return the conversation that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation removeByConversationId(String conversationId)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = findByConversationId(conversationId);

		return remove(conversation);
	}

	/**
	 * Returns the number of conversations where conversationId = &#63;.
	 *
	 * @param conversationId the conversation ID
	 * @return the number of matching conversations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByConversationId(String conversationId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONVERSATIONID;

		Object[] finderArgs = new Object[] { conversationId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONVERSATION_WHERE);

			boolean bindConversationId = false;

			if (conversationId == null) {
				query.append(_FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_1);
			}
			else if (conversationId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_3);
			}
			else {
				bindConversationId = true;

				query.append(_FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConversationId) {
					qPos.add(conversationId);
				}

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

	private static final String _FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_1 = "conversation.conversationId IS NULL";
	private static final String _FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_2 = "conversation.conversationId = ?";
	private static final String _FINDER_COLUMN_CONVERSATIONID_CONVERSATIONID_3 = "(conversation.conversationId IS NULL OR conversation.conversationId = '')";

	public ConversationPersistenceImpl() {
		setModelClass(Conversation.class);
	}

	/**
	 * Caches the conversation in the entity cache if it is enabled.
	 *
	 * @param conversation the conversation
	 */
	@Override
	public void cacheResult(Conversation conversation) {
		EntityCacheUtil.putResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationImpl.class, conversation.getPrimaryKey(), conversation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
			new Object[] { conversation.getConversationId() }, conversation);

		conversation.resetOriginalValues();
	}

	/**
	 * Caches the conversations in the entity cache if it is enabled.
	 *
	 * @param conversations the conversations
	 */
	@Override
	public void cacheResult(List<Conversation> conversations) {
		for (Conversation conversation : conversations) {
			if (EntityCacheUtil.getResult(
						ConversationModelImpl.ENTITY_CACHE_ENABLED,
						ConversationImpl.class, conversation.getPrimaryKey()) == null) {
				cacheResult(conversation);
			}
			else {
				conversation.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all conversations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ConversationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ConversationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the conversation.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Conversation conversation) {
		EntityCacheUtil.removeResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationImpl.class, conversation.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(conversation);
	}

	@Override
	public void clearCache(List<Conversation> conversations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Conversation conversation : conversations) {
			EntityCacheUtil.removeResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
				ConversationImpl.class, conversation.getPrimaryKey());

			clearUniqueFindersCache(conversation);
		}
	}

	protected void cacheUniqueFindersCache(Conversation conversation) {
		if (conversation.isNew()) {
			Object[] args = new Object[] { conversation.getConversationId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONVERSATIONID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
				args, conversation);
		}
		else {
			ConversationModelImpl conversationModelImpl = (ConversationModelImpl)conversation;

			if ((conversationModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CONVERSATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { conversation.getConversationId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONVERSATIONID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
					args, conversation);
			}
		}
	}

	protected void clearUniqueFindersCache(Conversation conversation) {
		ConversationModelImpl conversationModelImpl = (ConversationModelImpl)conversation;

		Object[] args = new Object[] { conversation.getConversationId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONVERSATIONID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONVERSATIONID, args);

		if ((conversationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CONVERSATIONID.getColumnBitmask()) != 0) {
			args = new Object[] {
					conversationModelImpl.getOriginalConversationId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONVERSATIONID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONVERSATIONID,
				args);
		}
	}

	/**
	 * Creates a new conversation with the primary key. Does not add the conversation to the database.
	 *
	 * @param cid the primary key for the new conversation
	 * @return the new conversation
	 */
	@Override
	public Conversation create(long cid) {
		Conversation conversation = new ConversationImpl();

		conversation.setNew(true);
		conversation.setPrimaryKey(cid);

		return conversation;
	}

	/**
	 * Removes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cid the primary key of the conversation
	 * @return the conversation that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation remove(long cid)
		throws NoSuchConversationException, SystemException {
		return remove((Serializable)cid);
	}

	/**
	 * Removes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the conversation
	 * @return the conversation that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation remove(Serializable primaryKey)
		throws NoSuchConversationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Conversation conversation = (Conversation)session.get(ConversationImpl.class,
					primaryKey);

			if (conversation == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchConversationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(conversation);
		}
		catch (NoSuchConversationException nsee) {
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
	protected Conversation removeImpl(Conversation conversation)
		throws SystemException {
		conversation = toUnwrappedModel(conversation);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(conversation)) {
				conversation = (Conversation)session.get(ConversationImpl.class,
						conversation.getPrimaryKeyObj());
			}

			if (conversation != null) {
				session.delete(conversation);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (conversation != null) {
			clearCache(conversation);
		}

		return conversation;
	}

	@Override
	public Conversation updateImpl(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation)
		throws SystemException {
		conversation = toUnwrappedModel(conversation);

		boolean isNew = conversation.isNew();

		Session session = null;

		try {
			session = openSession();

			if (conversation.isNew()) {
				session.save(conversation);

				conversation.setNew(false);
			}
			else {
				session.merge(conversation);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ConversationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationImpl.class, conversation.getPrimaryKey(), conversation);

		clearUniqueFindersCache(conversation);
		cacheUniqueFindersCache(conversation);

		return conversation;
	}

	protected Conversation toUnwrappedModel(Conversation conversation) {
		if (conversation instanceof ConversationImpl) {
			return conversation;
		}

		ConversationImpl conversationImpl = new ConversationImpl();

		conversationImpl.setNew(conversation.isNew());
		conversationImpl.setPrimaryKey(conversation.getPrimaryKey());

		conversationImpl.setCid(conversation.getCid());
		conversationImpl.setConversationId(conversation.getConversationId());
		conversationImpl.setConversationType(conversation.getConversationType());
		conversationImpl.setUpdatedAt(conversation.getUpdatedAt());

		return conversationImpl;
	}

	/**
	 * Returns the conversation with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the conversation
	 * @return the conversation
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = fetchByPrimaryKey(primaryKey);

		if (conversation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchConversationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return conversation;
	}

	/**
	 * Returns the conversation with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchConversationException} if it could not be found.
	 *
	 * @param cid the primary key of the conversation
	 * @return the conversation
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation findByPrimaryKey(long cid)
		throws NoSuchConversationException, SystemException {
		return findByPrimaryKey((Serializable)cid);
	}

	/**
	 * Returns the conversation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the conversation
	 * @return the conversation, or <code>null</code> if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Conversation conversation = (Conversation)EntityCacheUtil.getResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
				ConversationImpl.class, primaryKey);

		if (conversation == _nullConversation) {
			return null;
		}

		if (conversation == null) {
			Session session = null;

			try {
				session = openSession();

				conversation = (Conversation)session.get(ConversationImpl.class,
						primaryKey);

				if (conversation != null) {
					cacheResult(conversation);
				}
				else {
					EntityCacheUtil.putResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
						ConversationImpl.class, primaryKey, _nullConversation);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
					ConversationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return conversation;
	}

	/**
	 * Returns the conversation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cid the primary key of the conversation
	 * @return the conversation, or <code>null</code> if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation fetchByPrimaryKey(long cid) throws SystemException {
		return fetchByPrimaryKey((Serializable)cid);
	}

	/**
	 * Returns all the conversations.
	 *
	 * @return the conversations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Conversation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the conversations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of conversations
	 * @param end the upper bound of the range of conversations (not inclusive)
	 * @return the range of conversations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Conversation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the conversations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of conversations
	 * @param end the upper bound of the range of conversations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of conversations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Conversation> findAll(int start, int end,
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

		List<Conversation> list = (List<Conversation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CONVERSATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CONVERSATION;

				if (pagination) {
					sql = sql.concat(ConversationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Conversation>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Conversation>(list);
				}
				else {
					list = (List<Conversation>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the conversations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Conversation conversation : findAll()) {
			remove(conversation);
		}
	}

	/**
	 * Returns the number of conversations.
	 *
	 * @return the number of conversations
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

				Query q = session.createQuery(_SQL_COUNT_CONVERSATION);

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
	 * Initializes the conversation persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.persistence.generated.model.Conversation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Conversation>> listenersList = new ArrayList<ModelListener<Conversation>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Conversation>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ConversationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_CONVERSATION = "SELECT conversation FROM Conversation conversation";
	private static final String _SQL_SELECT_CONVERSATION_WHERE = "SELECT conversation FROM Conversation conversation WHERE ";
	private static final String _SQL_COUNT_CONVERSATION = "SELECT COUNT(conversation) FROM Conversation conversation";
	private static final String _SQL_COUNT_CONVERSATION_WHERE = "SELECT COUNT(conversation) FROM Conversation conversation WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "conversation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Conversation exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Conversation exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ConversationPersistenceImpl.class);
	private static Conversation _nullConversation = new ConversationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Conversation> toCacheModel() {
				return _nullConversationCacheModel;
			}
		};

	private static CacheModel<Conversation> _nullConversationCacheModel = new CacheModel<Conversation>() {
			@Override
			public Conversation toEntityModel() {
				return _nullConversation;
			}
		};
}