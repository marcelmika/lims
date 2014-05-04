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

import com.marcelmika.lims.NoSuchConversationException;
import com.marcelmika.lims.model.Conversation;
import com.marcelmika.lims.model.impl.ConversationImpl;
import com.marcelmika.lims.model.impl.ConversationModelImpl;

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
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId_conversationId",
			new String[] { Long.class.getName(), String.class.getName() },
			ConversationModelImpl.USERID_COLUMN_BITMASK |
			ConversationModelImpl.CONVERSATIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId_conversationId",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			ConversationModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, ConversationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the conversation in the entity cache if it is enabled.
	 *
	 * @param conversation the conversation
	 */
	public void cacheResult(Conversation conversation) {
		EntityCacheUtil.putResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationImpl.class, conversation.getPrimaryKey(), conversation);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
			new Object[] {
				Long.valueOf(conversation.getUserId()),
				
			conversation.getConversationId()
			}, conversation);

		conversation.resetOriginalValues();
	}

	/**
	 * Caches the conversations in the entity cache if it is enabled.
	 *
	 * @param conversations the conversations
	 */
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
			Object[] args = new Object[] {
					Long.valueOf(conversation.getUserId()),
					
					conversation.getConversationId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
				args, conversation);
		}
		else {
			ConversationModelImpl conversationModelImpl = (ConversationModelImpl)conversation;

			if ((conversationModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(conversation.getUserId()),
						
						conversation.getConversationId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
					args, conversation);
			}
		}
	}

	protected void clearUniqueFindersCache(Conversation conversation) {
		ConversationModelImpl conversationModelImpl = (ConversationModelImpl)conversation;

		Object[] args = new Object[] {
				Long.valueOf(conversation.getUserId()),
				
				conversation.getConversationId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
			args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
			args);

		if ((conversationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(conversationModelImpl.getOriginalUserId()),
					
					conversationModelImpl.getOriginalConversationId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
				args);
		}
	}

	/**
	 * Creates a new conversation with the primary key. Does not add the conversation to the database.
	 *
	 * @param cid the primary key for the new conversation
	 * @return the new conversation
	 */
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
	 * @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation remove(long cid)
		throws NoSuchConversationException, SystemException {
		return remove(Long.valueOf(cid));
	}

	/**
	 * Removes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the conversation
	 * @return the conversation that was removed
	 * @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
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

			BatchSessionUtil.delete(session, conversation);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(conversation);

		return conversation;
	}

	@Override
	public Conversation updateImpl(
		com.marcelmika.lims.model.Conversation conversation, boolean merge)
		throws SystemException {
		conversation = toUnwrappedModel(conversation);

		boolean isNew = conversation.isNew();

		ConversationModelImpl conversationModelImpl = (ConversationModelImpl)conversation;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, conversation, merge);

			conversation.setNew(false);
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

		else {
			if ((conversationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(conversationModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(conversationModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
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
		conversationImpl.setUserId(conversation.getUserId());
		conversationImpl.setConversationId(conversation.getConversationId());
		conversationImpl.setConversationType(conversation.getConversationType());
		conversationImpl.setConversationVisibility(conversation.getConversationVisibility());
		conversationImpl.setConversationName(conversation.getConversationName());
		conversationImpl.setUnreadMessages(conversation.getUnreadMessages());

		return conversationImpl;
	}

	/**
	 * Returns the conversation with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the conversation
	 * @return the conversation
	 * @throws com.liferay.portal.NoSuchModelException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Conversation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the conversation with the primary key or throws a {@link com.marcelmika.lims.NoSuchConversationException} if it could not be found.
	 *
	 * @param cid the primary key of the conversation
	 * @return the conversation
	 * @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation findByPrimaryKey(long cid)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = fetchByPrimaryKey(cid);

		if (conversation == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + cid);
			}

			throw new NoSuchConversationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				cid);
		}

		return conversation;
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
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the conversation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cid the primary key of the conversation
	 * @return the conversation, or <code>null</code> if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation fetchByPrimaryKey(long cid) throws SystemException {
		Conversation conversation = (Conversation)EntityCacheUtil.getResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
				ConversationImpl.class, cid);

		if (conversation == _nullConversation) {
			return null;
		}

		if (conversation == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				conversation = (Conversation)session.get(ConversationImpl.class,
						Long.valueOf(cid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (conversation != null) {
					cacheResult(conversation);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
						ConversationImpl.class, cid, _nullConversation);
				}

				closeSession(session);
			}
		}

		return conversation;
	}

	/**
	 * Returns the conversation where userId = &#63; and conversationId = &#63; or throws a {@link com.marcelmika.lims.NoSuchConversationException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the matching conversation
	 * @throws com.marcelmika.lims.NoSuchConversationException if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation findByUserId_conversationId(long userId,
		String conversationId)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = fetchByUserId_conversationId(userId,
				conversationId);

		if (conversation == null) {
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

			throw new NoSuchConversationException(msg.toString());
		}

		return conversation;
	}

	/**
	 * Returns the conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation fetchByUserId_conversationId(long userId,
		String conversationId) throws SystemException {
		return fetchByUserId_conversationId(userId, conversationId, true);
	}

	/**
	 * Returns the conversation where userId = &#63; and conversationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching conversation, or <code>null</code> if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation fetchByUserId_conversationId(long userId,
		String conversationId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, conversationId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
					finderArgs, this);
		}

		if (result instanceof Conversation) {
			Conversation conversation = (Conversation)result;

			if ((userId != conversation.getUserId()) ||
					!Validator.equals(conversationId,
						conversation.getConversationId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CONVERSATION_WHERE);

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

				List<Conversation> list = q.list();

				result = list;

				Conversation conversation = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
						finderArgs, list);
				}
				else {
					conversation = list.get(0);

					cacheResult(conversation);

					if ((conversation.getUserId() != userId) ||
							(conversation.getConversationId() == null) ||
							!conversation.getConversationId()
											 .equals(conversationId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID_CONVERSATIONID,
							finderArgs, conversation);
					}
				}

				return conversation;
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
				return (Conversation)result;
			}
		}
	}

	/**
	 * Returns all the conversations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Conversation> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the conversations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of conversations
	 * @param end the upper bound of the range of conversations (not inclusive)
	 * @return the range of matching conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Conversation> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the conversations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of conversations
	 * @param end the upper bound of the range of conversations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Conversation> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<Conversation> list = (List<Conversation>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Conversation conversation : list) {
				if ((userId != conversation.getUserId())) {
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

			query.append(_SQL_SELECT_CONVERSATION_WHERE);

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

				list = (List<Conversation>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching conversation
	 * @throws com.marcelmika.lims.NoSuchConversationException if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = fetchByUserId_First(userId,
				orderByComparator);

		if (conversation != null) {
			return conversation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchConversationException(msg.toString());
	}

	/**
	 * Returns the first conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching conversation, or <code>null</code> if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Conversation> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching conversation
	 * @throws com.marcelmika.lims.NoSuchConversationException if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = fetchByUserId_Last(userId, orderByComparator);

		if (conversation != null) {
			return conversation;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchConversationException(msg.toString());
	}

	/**
	 * Returns the last conversation in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching conversation, or <code>null</code> if a matching conversation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<Conversation> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the conversations before and after the current conversation in the ordered set where userId = &#63;.
	 *
	 * @param cid the primary key of the current conversation
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next conversation
	 * @throws com.marcelmika.lims.NoSuchConversationException if a conversation with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation[] findByUserId_PrevAndNext(long cid, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = findByPrimaryKey(cid);

		Session session = null;

		try {
			session = openSession();

			Conversation[] array = new ConversationImpl[3];

			array[0] = getByUserId_PrevAndNext(session, conversation, userId,
					orderByComparator, true);

			array[1] = conversation;

			array[2] = getByUserId_PrevAndNext(session, conversation, userId,
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

	protected Conversation getByUserId_PrevAndNext(Session session,
		Conversation conversation, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONVERSATION_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(conversation);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Conversation> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the conversations.
	 *
	 * @return the conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Conversation> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the conversations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of conversations
	 * @param end the upper bound of the range of conversations (not inclusive)
	 * @return the range of conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Conversation> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the conversations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of conversations
	 * @param end the upper bound of the range of conversations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of conversations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Conversation> findAll(int start, int end,
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
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Conversation>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Conversation>)QueryUtil.list(q, getDialect(),
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
	 * Removes the conversation where userId = &#63; and conversationId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the conversation that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Conversation removeByUserId_conversationId(long userId,
		String conversationId)
		throws NoSuchConversationException, SystemException {
		Conversation conversation = findByUserId_conversationId(userId,
				conversationId);

		return remove(conversation);
	}

	/**
	 * Removes all the conversations where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (Conversation conversation : findByUserId(userId)) {
			remove(conversation);
		}
	}

	/**
	 * Removes all the conversations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Conversation conversation : findAll()) {
			remove(conversation);
		}
	}

	/**
	 * Returns the number of conversations where userId = &#63; and conversationId = &#63;.
	 *
	 * @param userId the user ID
	 * @param conversationId the conversation ID
	 * @return the number of matching conversations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId_conversationId(long userId, String conversationId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, conversationId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID_CONVERSATIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CONVERSATION_WHERE);

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
	 * Returns the number of conversations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching conversations
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONVERSATION_WHERE);

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
	 * Returns the number of conversations.
	 *
	 * @return the number of conversations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CONVERSATION);

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
	 * Initializes the conversation persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.model.Conversation")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Conversation>> listenersList = new ArrayList<ModelListener<Conversation>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<Conversation>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ConversationImpl.class.getName());
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
	private static final String _SQL_SELECT_CONVERSATION = "SELECT conversation FROM Conversation conversation";
	private static final String _SQL_SELECT_CONVERSATION_WHERE = "SELECT conversation FROM Conversation conversation WHERE ";
	private static final String _SQL_COUNT_CONVERSATION = "SELECT COUNT(conversation) FROM Conversation conversation";
	private static final String _SQL_COUNT_CONVERSATION_WHERE = "SELECT COUNT(conversation) FROM Conversation conversation WHERE ";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_USERID_2 = "conversation.userId = ? AND ";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_1 =
		"conversation.conversationId IS NULL";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_2 =
		"conversation.conversationId = ?";
	private static final String _FINDER_COLUMN_USERID_CONVERSATIONID_CONVERSATIONID_3 =
		"(conversation.conversationId IS NULL OR conversation.conversationId = ?)";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "conversation.userId = ?";
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
			public Conversation toEntityModel() {
				return _nullConversation;
			}
		};
}