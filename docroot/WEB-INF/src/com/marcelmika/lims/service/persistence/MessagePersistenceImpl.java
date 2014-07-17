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

import com.marcelmika.lims.NoSuchMessageException;
import com.marcelmika.lims.model.Message;
import com.marcelmika.lims.model.impl.MessageImpl;
import com.marcelmika.lims.model.impl.MessageModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the message service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MessagePersistence
 * @see MessageUtil
 * @generated
 */
public class MessagePersistenceImpl extends BasePersistenceImpl<Message>
	implements MessagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MessageUtil} to access the message persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MessageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_CREATORID = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, MessageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByCreatorId",
			new String[] { Long.class.getName() },
			MessageModelImpl.CREATORID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CREATORID = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCreatorId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CID = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, MessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, MessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCid",
			new String[] { Long.class.getName() },
			MessageModelImpl.CID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CID = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCid",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, MessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, MessageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the message in the entity cache if it is enabled.
	 *
	 * @param message the message
	 */
	public void cacheResult(Message message) {
		EntityCacheUtil.putResult(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageImpl.class, message.getPrimaryKey(), message);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CREATORID,
			new Object[] { Long.valueOf(message.getCreatorId()) }, message);

		message.resetOriginalValues();
	}

	/**
	 * Caches the messages in the entity cache if it is enabled.
	 *
	 * @param messages the messages
	 */
	public void cacheResult(List<Message> messages) {
		for (Message message : messages) {
			if (EntityCacheUtil.getResult(
						MessageModelImpl.ENTITY_CACHE_ENABLED,
						MessageImpl.class, message.getPrimaryKey()) == null) {
				cacheResult(message);
			}
			else {
				message.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all messages.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MessageImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MessageImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Message message) {
		EntityCacheUtil.removeResult(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageImpl.class, message.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(message);
	}

	@Override
	public void clearCache(List<Message> messages) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Message message : messages) {
			EntityCacheUtil.removeResult(MessageModelImpl.ENTITY_CACHE_ENABLED,
				MessageImpl.class, message.getPrimaryKey());

			clearUniqueFindersCache(message);
		}
	}

	protected void cacheUniqueFindersCache(Message message) {
		if (message.isNew()) {
			Object[] args = new Object[] { Long.valueOf(message.getCreatorId()) };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CREATORID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CREATORID, args,
				message);
		}
		else {
			MessageModelImpl messageModelImpl = (MessageModelImpl)message;

			if ((messageModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CREATORID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(message.getCreatorId())
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CREATORID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CREATORID, args,
					message);
			}
		}
	}

	protected void clearUniqueFindersCache(Message message) {
		MessageModelImpl messageModelImpl = (MessageModelImpl)message;

		Object[] args = new Object[] { Long.valueOf(message.getCreatorId()) };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CREATORID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CREATORID, args);

		if ((messageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CREATORID.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(messageModelImpl.getOriginalCreatorId())
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CREATORID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CREATORID, args);
		}
	}

	/**
	 * Creates a new message with the primary key. Does not add the message to the database.
	 *
	 * @param mid the primary key for the new message
	 * @return the new message
	 */
	public Message create(long mid) {
		Message message = new MessageImpl();

		message.setNew(true);
		message.setPrimaryKey(mid);

		return message;
	}

	/**
	 * Removes the message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mid the primary key of the message
	 * @return the message that was removed
	 * @throws com.marcelmika.lims.NoSuchMessageException if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message remove(long mid)
		throws NoSuchMessageException, SystemException {
		return remove(Long.valueOf(mid));
	}

	/**
	 * Removes the message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message
	 * @return the message that was removed
	 * @throws com.marcelmika.lims.NoSuchMessageException if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Message remove(Serializable primaryKey)
		throws NoSuchMessageException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Message message = (Message)session.get(MessageImpl.class, primaryKey);

			if (message == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMessageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(message);
		}
		catch (NoSuchMessageException nsee) {
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
	protected Message removeImpl(Message message) throws SystemException {
		message = toUnwrappedModel(message);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, message);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(message);

		return message;
	}

	@Override
	public Message updateImpl(com.marcelmika.lims.model.Message message,
		boolean merge) throws SystemException {
		message = toUnwrappedModel(message);

		boolean isNew = message.isNew();

		MessageModelImpl messageModelImpl = (MessageModelImpl)message;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, message, merge);

			message.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MessageModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((messageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(messageModelImpl.getOriginalCid())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID,
					args);

				args = new Object[] { Long.valueOf(messageModelImpl.getCid()) };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CID,
					args);
			}
		}

		EntityCacheUtil.putResult(MessageModelImpl.ENTITY_CACHE_ENABLED,
			MessageImpl.class, message.getPrimaryKey(), message);

		clearUniqueFindersCache(message);
		cacheUniqueFindersCache(message);

		return message;
	}

	protected Message toUnwrappedModel(Message message) {
		if (message instanceof MessageImpl) {
			return message;
		}

		MessageImpl messageImpl = new MessageImpl();

		messageImpl.setNew(message.isNew());
		messageImpl.setPrimaryKey(message.getPrimaryKey());

		messageImpl.setMid(message.getMid());
		messageImpl.setCid(message.getCid());
		messageImpl.setCreatorId(message.getCreatorId());
		messageImpl.setCreatedAt(message.getCreatedAt());
		messageImpl.setMessageHash(message.getMessageHash());
		messageImpl.setBody(message.getBody());

		return messageImpl;
	}

	/**
	 * Returns the message with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message
	 * @return the message
	 * @throws com.liferay.portal.NoSuchModelException if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Message findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message with the primary key or throws a {@link com.marcelmika.lims.NoSuchMessageException} if it could not be found.
	 *
	 * @param mid the primary key of the message
	 * @return the message
	 * @throws com.marcelmika.lims.NoSuchMessageException if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message findByPrimaryKey(long mid)
		throws NoSuchMessageException, SystemException {
		Message message = fetchByPrimaryKey(mid);

		if (message == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + mid);
			}

			throw new NoSuchMessageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				mid);
		}

		return message;
	}

	/**
	 * Returns the message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message
	 * @return the message, or <code>null</code> if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Message fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the message with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mid the primary key of the message
	 * @return the message, or <code>null</code> if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message fetchByPrimaryKey(long mid) throws SystemException {
		Message message = (Message)EntityCacheUtil.getResult(MessageModelImpl.ENTITY_CACHE_ENABLED,
				MessageImpl.class, mid);

		if (message == _nullMessage) {
			return null;
		}

		if (message == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				message = (Message)session.get(MessageImpl.class,
						Long.valueOf(mid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (message != null) {
					cacheResult(message);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(MessageModelImpl.ENTITY_CACHE_ENABLED,
						MessageImpl.class, mid, _nullMessage);
				}

				closeSession(session);
			}
		}

		return message;
	}

	/**
	 * Returns the message where creatorId = &#63; or throws a {@link com.marcelmika.lims.NoSuchMessageException} if it could not be found.
	 *
	 * @param creatorId the creator ID
	 * @return the matching message
	 * @throws com.marcelmika.lims.NoSuchMessageException if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message findByCreatorId(long creatorId)
		throws NoSuchMessageException, SystemException {
		Message message = fetchByCreatorId(creatorId);

		if (message == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("creatorId=");
			msg.append(creatorId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMessageException(msg.toString());
		}

		return message;
	}

	/**
	 * Returns the message where creatorId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param creatorId the creator ID
	 * @return the matching message, or <code>null</code> if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message fetchByCreatorId(long creatorId) throws SystemException {
		return fetchByCreatorId(creatorId, true);
	}

	/**
	 * Returns the message where creatorId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param creatorId the creator ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching message, or <code>null</code> if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message fetchByCreatorId(long creatorId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { creatorId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CREATORID,
					finderArgs, this);
		}

		if (result instanceof Message) {
			Message message = (Message)result;

			if ((creatorId != message.getCreatorId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_MESSAGE_WHERE);

			query.append(_FINDER_COLUMN_CREATORID_CREATORID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorId);

				List<Message> list = q.list();

				result = list;

				Message message = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CREATORID,
						finderArgs, list);
				}
				else {
					message = list.get(0);

					cacheResult(message);

					if ((message.getCreatorId() != creatorId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CREATORID,
							finderArgs, message);
					}
				}

				return message;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CREATORID,
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
				return (Message)result;
			}
		}
	}

	/**
	 * Returns all the messages where cid = &#63;.
	 *
	 * @param cid the cid
	 * @return the matching messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<Message> findByCid(long cid) throws SystemException {
		return findByCid(cid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the messages where cid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param cid the cid
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @return the range of matching messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<Message> findByCid(long cid, int start, int end)
		throws SystemException {
		return findByCid(cid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the messages where cid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param cid the cid
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<Message> findByCid(long cid, int start, int end,
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

		List<Message> list = (List<Message>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Message message : list) {
				if ((cid != message.getCid())) {
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

			query.append(_SQL_SELECT_MESSAGE_WHERE);

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

				list = (List<Message>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first message in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message
	 * @throws com.marcelmika.lims.NoSuchMessageException if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message findByCid_First(long cid, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		Message message = fetchByCid_First(cid, orderByComparator);

		if (message != null) {
			return message;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("cid=");
		msg.append(cid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the first message in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message, or <code>null</code> if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message fetchByCid_First(long cid,
		OrderByComparator orderByComparator) throws SystemException {
		List<Message> list = findByCid(cid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message
	 * @throws com.marcelmika.lims.NoSuchMessageException if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message findByCid_Last(long cid, OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		Message message = fetchByCid_Last(cid, orderByComparator);

		if (message != null) {
			return message;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("cid=");
		msg.append(cid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchMessageException(msg.toString());
	}

	/**
	 * Returns the last message in the ordered set where cid = &#63;.
	 *
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message, or <code>null</code> if a matching message could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message fetchByCid_Last(long cid, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCid(cid);

		List<Message> list = findByCid(cid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the messages before and after the current message in the ordered set where cid = &#63;.
	 *
	 * @param mid the primary key of the current message
	 * @param cid the cid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message
	 * @throws com.marcelmika.lims.NoSuchMessageException if a message with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Message[] findByCid_PrevAndNext(long mid, long cid,
		OrderByComparator orderByComparator)
		throws NoSuchMessageException, SystemException {
		Message message = findByPrimaryKey(mid);

		Session session = null;

		try {
			session = openSession();

			Message[] array = new MessageImpl[3];

			array[0] = getByCid_PrevAndNext(session, message, cid,
					orderByComparator, true);

			array[1] = message;

			array[2] = getByCid_PrevAndNext(session, message, cid,
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

	protected Message getByCid_PrevAndNext(Session session, Message message,
		long cid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MESSAGE_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(message);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Message> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the messages.
	 *
	 * @return the messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<Message> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @return the range of messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<Message> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of messages
	 * @throws SystemException if a system exception occurred
	 */
	public List<Message> findAll(int start, int end,
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

		List<Message> list = (List<Message>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MESSAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MESSAGE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Message>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Message>)QueryUtil.list(q, getDialect(),
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
	 * Removes the message where creatorId = &#63; from the database.
	 *
	 * @param creatorId the creator ID
	 * @return the message that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Message removeByCreatorId(long creatorId)
		throws NoSuchMessageException, SystemException {
		Message message = findByCreatorId(creatorId);

		return remove(message);
	}

	/**
	 * Removes all the messages where cid = &#63; from the database.
	 *
	 * @param cid the cid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCid(long cid) throws SystemException {
		for (Message message : findByCid(cid)) {
			remove(message);
		}
	}

	/**
	 * Removes all the messages from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Message message : findAll()) {
			remove(message);
		}
	}

	/**
	 * Returns the number of messages where creatorId = &#63;.
	 *
	 * @param creatorId the creator ID
	 * @return the number of matching messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCreatorId(long creatorId) throws SystemException {
		Object[] finderArgs = new Object[] { creatorId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CREATORID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MESSAGE_WHERE);

			query.append(_FINDER_COLUMN_CREATORID_CREATORID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CREATORID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of messages where cid = &#63;.
	 *
	 * @param cid the cid
	 * @return the number of matching messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCid(long cid) throws SystemException {
		Object[] finderArgs = new Object[] { cid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MESSAGE_WHERE);

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
	 * Returns the number of messages.
	 *
	 * @return the number of messages
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MESSAGE);

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
	 * Initializes the message persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.model.Message")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Message>> listenersList = new ArrayList<ModelListener<Message>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<Message>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MessageImpl.class.getName());
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
	private static final String _SQL_SELECT_MESSAGE = "SELECT message FROM Message message";
	private static final String _SQL_SELECT_MESSAGE_WHERE = "SELECT message FROM Message message WHERE ";
	private static final String _SQL_COUNT_MESSAGE = "SELECT COUNT(message) FROM Message message";
	private static final String _SQL_COUNT_MESSAGE_WHERE = "SELECT COUNT(message) FROM Message message WHERE ";
	private static final String _FINDER_COLUMN_CREATORID_CREATORID_2 = "message.creatorId = ?";
	private static final String _FINDER_COLUMN_CID_CID_2 = "message.cid = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "message.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Message exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Message exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(MessagePersistenceImpl.class);
	private static Message _nullMessage = new MessageImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Message> toCacheModel() {
				return _nullMessageCacheModel;
			}
		};

	private static CacheModel<Message> _nullMessageCacheModel = new CacheModel<Message>() {
			public Message toEntityModel() {
				return _nullMessage;
			}
		};
}