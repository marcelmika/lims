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
import com.liferay.portal.kernel.util.StringUtil;
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
	}

	@Override
	public void clearCache(List<Conversation> conversations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Conversation conversation : conversations) {
			EntityCacheUtil.removeResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
				ConversationImpl.class, conversation.getPrimaryKey());
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

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(ConversationModelImpl.ENTITY_CACHE_ENABLED,
			ConversationImpl.class, conversation.getPrimaryKey(), conversation);

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
	private static final String _SQL_SELECT_CONVERSATION = "SELECT conversation FROM Conversation conversation";
	private static final String _SQL_COUNT_CONVERSATION = "SELECT COUNT(conversation) FROM Conversation conversation";
	private static final String _ORDER_BY_ENTITY_ALIAS = "conversation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Conversation exists with the primary key ";
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