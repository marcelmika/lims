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

import com.marcelmika.lims.persistence.generated.NoSuchPanelException;
import com.marcelmika.lims.persistence.generated.model.Panel;
import com.marcelmika.lims.persistence.generated.model.impl.PanelImpl;
import com.marcelmika.lims.persistence.generated.model.impl.PanelModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the panel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PanelPersistence
 * @see PanelUtil
 * @generated
 */
public class PanelPersistenceImpl extends BasePersistenceImpl<Panel>
	implements PanelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PanelUtil} to access the panel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PanelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID = new FinderPath(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelModelImpl.FINDER_CACHE_ENABLED, PanelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] { Long.class.getName() },
			PanelModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelModelImpl.FINDER_CACHE_ENABLED, PanelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelModelImpl.FINDER_CACHE_ENABLED, PanelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the panel in the entity cache if it is enabled.
	 *
	 * @param panel the panel
	 */
	public void cacheResult(Panel panel) {
		EntityCacheUtil.putResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelImpl.class, panel.getPrimaryKey(), panel);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { Long.valueOf(panel.getUserId()) }, panel);

		panel.resetOriginalValues();
	}

	/**
	 * Caches the panels in the entity cache if it is enabled.
	 *
	 * @param panels the panels
	 */
	public void cacheResult(List<Panel> panels) {
		for (Panel panel : panels) {
			if (EntityCacheUtil.getResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
						PanelImpl.class, panel.getPrimaryKey()) == null) {
				cacheResult(panel);
			}
			else {
				panel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all panels.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PanelImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PanelImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the panel.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Panel panel) {
		EntityCacheUtil.removeResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelImpl.class, panel.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(panel);
	}

	@Override
	public void clearCache(List<Panel> panels) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Panel panel : panels) {
			EntityCacheUtil.removeResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
				PanelImpl.class, panel.getPrimaryKey());

			clearUniqueFindersCache(panel);
		}
	}

	protected void cacheUniqueFindersCache(Panel panel) {
		if (panel.isNew()) {
			Object[] args = new Object[] { Long.valueOf(panel.getUserId()) };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID, args, panel);
		}
		else {
			PanelModelImpl panelModelImpl = (PanelModelImpl)panel;

			if ((panelModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { Long.valueOf(panel.getUserId()) };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID, args,
					panel);
			}
		}
	}

	protected void clearUniqueFindersCache(Panel panel) {
		PanelModelImpl panelModelImpl = (PanelModelImpl)panel;

		Object[] args = new Object[] { Long.valueOf(panel.getUserId()) };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID, args);

		if ((panelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_USERID.getColumnBitmask()) != 0) {
			args = new Object[] { Long.valueOf(panelModelImpl.getOriginalUserId()) };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID, args);
		}
	}

	/**
	 * Creates a new panel with the primary key. Does not add the panel to the database.
	 *
	 * @param pid the primary key for the new panel
	 * @return the new panel
	 */
	public Panel create(long pid) {
		Panel panel = new PanelImpl();

		panel.setNew(true);
		panel.setPrimaryKey(pid);

		return panel;
	}

	/**
	 * Removes the panel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pid the primary key of the panel
	 * @return the panel that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a panel with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Panel remove(long pid) throws NoSuchPanelException, SystemException {
		return remove(Long.valueOf(pid));
	}

	/**
	 * Removes the panel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the panel
	 * @return the panel that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a panel with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Panel remove(Serializable primaryKey)
		throws NoSuchPanelException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Panel panel = (Panel)session.get(PanelImpl.class, primaryKey);

			if (panel == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPanelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(panel);
		}
		catch (NoSuchPanelException nsee) {
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
	protected Panel removeImpl(Panel panel) throws SystemException {
		panel = toUnwrappedModel(panel);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, panel);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(panel);

		return panel;
	}

	@Override
	public Panel updateImpl(
		com.marcelmika.lims.persistence.generated.model.Panel panel,
		boolean merge) throws SystemException {
		panel = toUnwrappedModel(panel);

		boolean isNew = panel.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, panel, merge);

			panel.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PanelModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
			PanelImpl.class, panel.getPrimaryKey(), panel);

		clearUniqueFindersCache(panel);
		cacheUniqueFindersCache(panel);

		return panel;
	}

	protected Panel toUnwrappedModel(Panel panel) {
		if (panel instanceof PanelImpl) {
			return panel;
		}

		PanelImpl panelImpl = new PanelImpl();

		panelImpl.setNew(panel.isNew());
		panelImpl.setPrimaryKey(panel.getPrimaryKey());

		panelImpl.setPid(panel.getPid());
		panelImpl.setUserId(panel.getUserId());
		panelImpl.setActivePanelId(panel.getActivePanelId());

		return panelImpl;
	}

	/**
	 * Returns the panel with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the panel
	 * @return the panel
	 * @throws com.liferay.portal.NoSuchModelException if a panel with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Panel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the panel with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchPanelException} if it could not be found.
	 *
	 * @param pid the primary key of the panel
	 * @return the panel
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a panel with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Panel findByPrimaryKey(long pid)
		throws NoSuchPanelException, SystemException {
		Panel panel = fetchByPrimaryKey(pid);

		if (panel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + pid);
			}

			throw new NoSuchPanelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				pid);
		}

		return panel;
	}

	/**
	 * Returns the panel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the panel
	 * @return the panel, or <code>null</code> if a panel with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Panel fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the panel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pid the primary key of the panel
	 * @return the panel, or <code>null</code> if a panel with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Panel fetchByPrimaryKey(long pid) throws SystemException {
		Panel panel = (Panel)EntityCacheUtil.getResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
				PanelImpl.class, pid);

		if (panel == _nullPanel) {
			return null;
		}

		if (panel == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				panel = (Panel)session.get(PanelImpl.class, Long.valueOf(pid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (panel != null) {
					cacheResult(panel);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PanelModelImpl.ENTITY_CACHE_ENABLED,
						PanelImpl.class, pid, _nullPanel);
				}

				closeSession(session);
			}
		}

		return panel;
	}

	/**
	 * Returns the panel where userId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchPanelException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching panel
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchPanelException if a matching panel could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Panel findByUserId(long userId)
		throws NoSuchPanelException, SystemException {
		Panel panel = fetchByUserId(userId);

		if (panel == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPanelException(msg.toString());
		}

		return panel;
	}

	/**
	 * Returns the panel where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching panel, or <code>null</code> if a matching panel could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Panel fetchByUserId(long userId) throws SystemException {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the panel where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching panel, or <code>null</code> if a matching panel could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Panel fetchByUserId(long userId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs, this);
		}

		if (result instanceof Panel) {
			Panel panel = (Panel)result;

			if ((userId != panel.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_PANEL_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<Panel> list = q.list();

				result = list;

				Panel panel = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, list);
				}
				else {
					panel = list.get(0);

					cacheResult(panel);

					if ((panel.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
							finderArgs, panel);
					}
				}

				return panel;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
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
				return (Panel)result;
			}
		}
	}

	/**
	 * Returns all the panels.
	 *
	 * @return the panels
	 * @throws SystemException if a system exception occurred
	 */
	public List<Panel> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the panels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of panels
	 * @param end the upper bound of the range of panels (not inclusive)
	 * @return the range of panels
	 * @throws SystemException if a system exception occurred
	 */
	public List<Panel> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the panels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of panels
	 * @param end the upper bound of the range of panels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of panels
	 * @throws SystemException if a system exception occurred
	 */
	public List<Panel> findAll(int start, int end,
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

		List<Panel> list = (List<Panel>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PANEL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PANEL;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Panel>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Panel>)QueryUtil.list(q, getDialect(), start,
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
	 * Removes the panel where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the panel that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Panel removeByUserId(long userId)
		throws NoSuchPanelException, SystemException {
		Panel panel = findByUserId(userId);

		return remove(panel);
	}

	/**
	 * Removes all the panels from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Panel panel : findAll()) {
			remove(panel);
		}
	}

	/**
	 * Returns the number of panels where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching panels
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PANEL_WHERE);

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
	 * Returns the number of panels.
	 *
	 * @return the number of panels
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PANEL);

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
	 * Initializes the panel persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.persistence.generated.model.Panel")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Panel>> listenersList = new ArrayList<ModelListener<Panel>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<Panel>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PanelImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ConversationPersistence.class)
	protected ConversationPersistence conversationPersistence;
	@BeanReference(type = MessagePersistence.class)
	protected MessagePersistence messagePersistence;
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
	private static final String _SQL_SELECT_PANEL = "SELECT panel FROM Panel panel";
	private static final String _SQL_SELECT_PANEL_WHERE = "SELECT panel FROM Panel panel WHERE ";
	private static final String _SQL_COUNT_PANEL = "SELECT COUNT(panel) FROM Panel panel";
	private static final String _SQL_COUNT_PANEL_WHERE = "SELECT COUNT(panel) FROM Panel panel WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "panel.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "panel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Panel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Panel exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PanelPersistenceImpl.class);
	private static Panel _nullPanel = new PanelImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Panel> toCacheModel() {
				return _nullPanelCacheModel;
			}
		};

	private static CacheModel<Panel> _nullPanelCacheModel = new CacheModel<Panel>() {
			public Panel toEntityModel() {
				return _nullPanel;
			}
		};
}