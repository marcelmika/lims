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

import com.marcelmika.lims.NoSuchBuddyException;
import com.marcelmika.lims.model.Buddy;
import com.marcelmika.lims.model.impl.BuddyImpl;
import com.marcelmika.lims.model.impl.BuddyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the buddy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BuddyPersistence
 * @see BuddyUtil
 * @generated
 */
public class BuddyPersistenceImpl extends BasePersistenceImpl<Buddy>
	implements BuddyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BuddyUtil} to access the buddy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BuddyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BuddyModelImpl.ENTITY_CACHE_ENABLED,
			BuddyModelImpl.FINDER_CACHE_ENABLED, BuddyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BuddyModelImpl.ENTITY_CACHE_ENABLED,
			BuddyModelImpl.FINDER_CACHE_ENABLED, BuddyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BuddyModelImpl.ENTITY_CACHE_ENABLED,
			BuddyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the buddy in the entity cache if it is enabled.
	 *
	 * @param buddy the buddy
	 */
	public void cacheResult(Buddy buddy) {
		EntityCacheUtil.putResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
			BuddyImpl.class, buddy.getPrimaryKey(), buddy);

		buddy.resetOriginalValues();
	}

	/**
	 * Caches the buddies in the entity cache if it is enabled.
	 *
	 * @param buddies the buddies
	 */
	public void cacheResult(List<Buddy> buddies) {
		for (Buddy buddy : buddies) {
			if (EntityCacheUtil.getResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
						BuddyImpl.class, buddy.getPrimaryKey()) == null) {
				cacheResult(buddy);
			}
			else {
				buddy.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all buddies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BuddyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BuddyImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the buddy.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Buddy buddy) {
		EntityCacheUtil.removeResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
			BuddyImpl.class, buddy.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Buddy> buddies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Buddy buddy : buddies) {
			EntityCacheUtil.removeResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
				BuddyImpl.class, buddy.getPrimaryKey());
		}
	}

	/**
	 * Creates a new buddy with the primary key. Does not add the buddy to the database.
	 *
	 * @param bid the primary key for the new buddy
	 * @return the new buddy
	 */
	public Buddy create(long bid) {
		Buddy buddy = new BuddyImpl();

		buddy.setNew(true);
		buddy.setPrimaryKey(bid);

		return buddy;
	}

	/**
	 * Removes the buddy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param bid the primary key of the buddy
	 * @return the buddy that was removed
	 * @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Buddy remove(long bid) throws NoSuchBuddyException, SystemException {
		return remove(Long.valueOf(bid));
	}

	/**
	 * Removes the buddy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the buddy
	 * @return the buddy that was removed
	 * @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Buddy remove(Serializable primaryKey)
		throws NoSuchBuddyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Buddy buddy = (Buddy)session.get(BuddyImpl.class, primaryKey);

			if (buddy == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBuddyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(buddy);
		}
		catch (NoSuchBuddyException nsee) {
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
	protected Buddy removeImpl(Buddy buddy) throws SystemException {
		buddy = toUnwrappedModel(buddy);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, buddy);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(buddy);

		return buddy;
	}

	@Override
	public Buddy updateImpl(com.marcelmika.lims.model.Buddy buddy, boolean merge)
		throws SystemException {
		buddy = toUnwrappedModel(buddy);

		boolean isNew = buddy.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, buddy, merge);

			buddy.setNew(false);
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

		EntityCacheUtil.putResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
			BuddyImpl.class, buddy.getPrimaryKey(), buddy);

		return buddy;
	}

	protected Buddy toUnwrappedModel(Buddy buddy) {
		if (buddy instanceof BuddyImpl) {
			return buddy;
		}

		BuddyImpl buddyImpl = new BuddyImpl();

		buddyImpl.setNew(buddy.isNew());
		buddyImpl.setPrimaryKey(buddy.getPrimaryKey());

		buddyImpl.setBid(buddy.getBid());
		buddyImpl.setUserId(buddy.getUserId());
		buddyImpl.setCompanyId(buddy.getCompanyId());
		buddyImpl.setPortraitId(buddy.getPortraitId());
		buddyImpl.setFullName(buddy.getFullName());
		buddyImpl.setScreenName(buddy.getScreenName());
		buddyImpl.setStatusMessage(buddy.getStatusMessage());
		buddyImpl.setIsTyping(buddy.isIsTyping());
		buddyImpl.setAwake(buddy.isAwake());
		buddyImpl.setStatus(buddy.getStatus());

		return buddyImpl;
	}

	/**
	 * Returns the buddy with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the buddy
	 * @return the buddy
	 * @throws com.liferay.portal.NoSuchModelException if a buddy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Buddy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the buddy with the primary key or throws a {@link com.marcelmika.lims.NoSuchBuddyException} if it could not be found.
	 *
	 * @param bid the primary key of the buddy
	 * @return the buddy
	 * @throws com.marcelmika.lims.NoSuchBuddyException if a buddy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Buddy findByPrimaryKey(long bid)
		throws NoSuchBuddyException, SystemException {
		Buddy buddy = fetchByPrimaryKey(bid);

		if (buddy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + bid);
			}

			throw new NoSuchBuddyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				bid);
		}

		return buddy;
	}

	/**
	 * Returns the buddy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the buddy
	 * @return the buddy, or <code>null</code> if a buddy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Buddy fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the buddy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param bid the primary key of the buddy
	 * @return the buddy, or <code>null</code> if a buddy with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Buddy fetchByPrimaryKey(long bid) throws SystemException {
		Buddy buddy = (Buddy)EntityCacheUtil.getResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
				BuddyImpl.class, bid);

		if (buddy == _nullBuddy) {
			return null;
		}

		if (buddy == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				buddy = (Buddy)session.get(BuddyImpl.class, Long.valueOf(bid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (buddy != null) {
					cacheResult(buddy);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(BuddyModelImpl.ENTITY_CACHE_ENABLED,
						BuddyImpl.class, bid, _nullBuddy);
				}

				closeSession(session);
			}
		}

		return buddy;
	}

	/**
	 * Returns all the buddies.
	 *
	 * @return the buddies
	 * @throws SystemException if a system exception occurred
	 */
	public List<Buddy> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<Buddy> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the buddies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of buddies
	 * @param end the upper bound of the range of buddies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of buddies
	 * @throws SystemException if a system exception occurred
	 */
	public List<Buddy> findAll(int start, int end,
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

		List<Buddy> list = (List<Buddy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BUDDY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BUDDY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Buddy>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Buddy>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the buddies from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Buddy buddy : findAll()) {
			remove(buddy);
		}
	}

	/**
	 * Returns the number of buddies.
	 *
	 * @return the number of buddies
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BUDDY);

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
	 * Initializes the buddy persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.model.Buddy")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Buddy>> listenersList = new ArrayList<ModelListener<Buddy>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<Buddy>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(BuddyImpl.class.getName());
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
	private static final String _SQL_SELECT_BUDDY = "SELECT buddy FROM Buddy buddy";
	private static final String _SQL_COUNT_BUDDY = "SELECT COUNT(buddy) FROM Buddy buddy";
	private static final String _ORDER_BY_ENTITY_ALIAS = "buddy.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Buddy exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(BuddyPersistenceImpl.class);
	private static Buddy _nullBuddy = new BuddyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Buddy> toCacheModel() {
				return _nullBuddyCacheModel;
			}
		};

	private static CacheModel<Buddy> _nullBuddyCacheModel = new CacheModel<Buddy>() {
			public Buddy toEntityModel() {
				return _nullBuddy;
			}
		};
}