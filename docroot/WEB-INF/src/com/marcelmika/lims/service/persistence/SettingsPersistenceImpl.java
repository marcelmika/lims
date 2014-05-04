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

import com.marcelmika.lims.NoSuchSettingsException;
import com.marcelmika.lims.model.Settings;
import com.marcelmika.lims.model.impl.SettingsImpl;
import com.marcelmika.lims.model.impl.SettingsModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the settings service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SettingsPersistence
 * @see SettingsUtil
 * @generated
 */
public class SettingsPersistenceImpl extends BasePersistenceImpl<Settings>
	implements SettingsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SettingsUtil} to access the settings persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SettingsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, SettingsImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] { Long.class.getName() },
			SettingsModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STATUS = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, SettingsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStatus",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS =
		new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, SettingsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStatus",
			new String[] { String.class.getName() },
			SettingsModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STATUS = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStatus",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, SettingsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, SettingsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the settings in the entity cache if it is enabled.
	 *
	 * @param settings the settings
	 */
	public void cacheResult(Settings settings) {
		EntityCacheUtil.putResult(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsImpl.class, settings.getPrimaryKey(), settings);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { Long.valueOf(settings.getUserId()) }, settings);

		settings.resetOriginalValues();
	}

	/**
	 * Caches the settingses in the entity cache if it is enabled.
	 *
	 * @param settingses the settingses
	 */
	public void cacheResult(List<Settings> settingses) {
		for (Settings settings : settingses) {
			if (EntityCacheUtil.getResult(
						SettingsModelImpl.ENTITY_CACHE_ENABLED,
						SettingsImpl.class, settings.getPrimaryKey()) == null) {
				cacheResult(settings);
			}
			else {
				settings.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all settingses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SettingsImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SettingsImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the settings.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Settings settings) {
		EntityCacheUtil.removeResult(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsImpl.class, settings.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(settings);
	}

	@Override
	public void clearCache(List<Settings> settingses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Settings settings : settingses) {
			EntityCacheUtil.removeResult(SettingsModelImpl.ENTITY_CACHE_ENABLED,
				SettingsImpl.class, settings.getPrimaryKey());

			clearUniqueFindersCache(settings);
		}
	}

	protected void cacheUniqueFindersCache(Settings settings) {
		if (settings.isNew()) {
			Object[] args = new Object[] { Long.valueOf(settings.getUserId()) };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID, args,
				settings);
		}
		else {
			SettingsModelImpl settingsModelImpl = (SettingsModelImpl)settings;

			if ((settingsModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { Long.valueOf(settings.getUserId()) };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID, args,
					settings);
			}
		}
	}

	protected void clearUniqueFindersCache(Settings settings) {
		SettingsModelImpl settingsModelImpl = (SettingsModelImpl)settings;

		Object[] args = new Object[] { Long.valueOf(settings.getUserId()) };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID, args);

		if ((settingsModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_USERID.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(settingsModelImpl.getOriginalUserId())
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID, args);
		}
	}

	/**
	 * Creates a new settings with the primary key. Does not add the settings to the database.
	 *
	 * @param sid the primary key for the new settings
	 * @return the new settings
	 */
	public Settings create(long sid) {
		Settings settings = new SettingsImpl();

		settings.setNew(true);
		settings.setPrimaryKey(sid);

		return settings;
	}

	/**
	 * Removes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sid the primary key of the settings
	 * @return the settings that was removed
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings remove(long sid)
		throws NoSuchSettingsException, SystemException {
		return remove(Long.valueOf(sid));
	}

	/**
	 * Removes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the settings
	 * @return the settings that was removed
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Settings remove(Serializable primaryKey)
		throws NoSuchSettingsException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Settings settings = (Settings)session.get(SettingsImpl.class,
					primaryKey);

			if (settings == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSettingsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(settings);
		}
		catch (NoSuchSettingsException nsee) {
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
	protected Settings removeImpl(Settings settings) throws SystemException {
		settings = toUnwrappedModel(settings);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, settings);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(settings);

		return settings;
	}

	@Override
	public Settings updateImpl(com.marcelmika.lims.model.Settings settings,
		boolean merge) throws SystemException {
		settings = toUnwrappedModel(settings);

		boolean isNew = settings.isNew();

		SettingsModelImpl settingsModelImpl = (SettingsModelImpl)settings;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, settings, merge);

			settings.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SettingsModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((settingsModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						settingsModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STATUS, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS,
					args);

				args = new Object[] { settingsModelImpl.getStatus() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STATUS, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS,
					args);
			}
		}

		EntityCacheUtil.putResult(SettingsModelImpl.ENTITY_CACHE_ENABLED,
			SettingsImpl.class, settings.getPrimaryKey(), settings);

		clearUniqueFindersCache(settings);
		cacheUniqueFindersCache(settings);

		return settings;
	}

	protected Settings toUnwrappedModel(Settings settings) {
		if (settings instanceof SettingsImpl) {
			return settings;
		}

		SettingsImpl settingsImpl = new SettingsImpl();

		settingsImpl.setNew(settings.isNew());
		settingsImpl.setPrimaryKey(settings.getPrimaryKey());

		settingsImpl.setSid(settings.getSid());
		settingsImpl.setUserId(settings.getUserId());
		settingsImpl.setStatus(settings.getStatus());
		settingsImpl.setMute(settings.isMute());
		settingsImpl.setChatEnabled(settings.isChatEnabled());

		return settingsImpl;
	}

	/**
	 * Returns the settings with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the settings
	 * @return the settings
	 * @throws com.liferay.portal.NoSuchModelException if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Settings findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the settings with the primary key or throws a {@link com.marcelmika.lims.NoSuchSettingsException} if it could not be found.
	 *
	 * @param sid the primary key of the settings
	 * @return the settings
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings findByPrimaryKey(long sid)
		throws NoSuchSettingsException, SystemException {
		Settings settings = fetchByPrimaryKey(sid);

		if (settings == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + sid);
			}

			throw new NoSuchSettingsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				sid);
		}

		return settings;
	}

	/**
	 * Returns the settings with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the settings
	 * @return the settings, or <code>null</code> if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Settings fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the settings with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sid the primary key of the settings
	 * @return the settings, or <code>null</code> if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings fetchByPrimaryKey(long sid) throws SystemException {
		Settings settings = (Settings)EntityCacheUtil.getResult(SettingsModelImpl.ENTITY_CACHE_ENABLED,
				SettingsImpl.class, sid);

		if (settings == _nullSettings) {
			return null;
		}

		if (settings == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				settings = (Settings)session.get(SettingsImpl.class,
						Long.valueOf(sid));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (settings != null) {
					cacheResult(settings);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SettingsModelImpl.ENTITY_CACHE_ENABLED,
						SettingsImpl.class, sid, _nullSettings);
				}

				closeSession(session);
			}
		}

		return settings;
	}

	/**
	 * Returns the settings where userId = &#63; or throws a {@link com.marcelmika.lims.NoSuchSettingsException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching settings
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings findByUserId(long userId)
		throws NoSuchSettingsException, SystemException {
		Settings settings = fetchByUserId(userId);

		if (settings == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchSettingsException(msg.toString());
		}

		return settings;
	}

	/**
	 * Returns the settings where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching settings, or <code>null</code> if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings fetchByUserId(long userId) throws SystemException {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the settings where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching settings, or <code>null</code> if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings fetchByUserId(long userId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs, this);
		}

		if (result instanceof Settings) {
			Settings settings = (Settings)result;

			if ((userId != settings.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_SETTINGS_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<Settings> list = q.list();

				result = list;

				Settings settings = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, list);
				}
				else {
					settings = list.get(0);

					cacheResult(settings);

					if ((settings.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
							finderArgs, settings);
					}
				}

				return settings;
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
				return (Settings)result;
			}
		}
	}

	/**
	 * Returns all the settingses where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching settingses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Settings> findByStatus(String status) throws SystemException {
		return findByStatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the settingses where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of settingses
	 * @param end the upper bound of the range of settingses (not inclusive)
	 * @return the range of matching settingses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Settings> findByStatus(String status, int start, int end)
		throws SystemException {
		return findByStatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the settingses where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of settingses
	 * @param end the upper bound of the range of settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching settingses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Settings> findByStatus(String status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STATUS;
			finderArgs = new Object[] { status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STATUS;
			finderArgs = new Object[] { status, start, end, orderByComparator };
		}

		List<Settings> list = (List<Settings>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Settings settings : list) {
				if (!Validator.equals(status, settings.getStatus())) {
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

			query.append(_SQL_SELECT_SETTINGS_WHERE);

			if (status == null) {
				query.append(_FINDER_COLUMN_STATUS_STATUS_1);
			}
			else {
				if (status.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_STATUS_STATUS_3);
				}
				else {
					query.append(_FINDER_COLUMN_STATUS_STATUS_2);
				}
			}

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

				if (status != null) {
					qPos.add(status);
				}

				list = (List<Settings>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first settings in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching settings
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings findByStatus_First(String status,
		OrderByComparator orderByComparator)
		throws NoSuchSettingsException, SystemException {
		Settings settings = fetchByStatus_First(status, orderByComparator);

		if (settings != null) {
			return settings;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSettingsException(msg.toString());
	}

	/**
	 * Returns the first settings in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching settings, or <code>null</code> if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings fetchByStatus_First(String status,
		OrderByComparator orderByComparator) throws SystemException {
		List<Settings> list = findByStatus(status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last settings in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching settings
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings findByStatus_Last(String status,
		OrderByComparator orderByComparator)
		throws NoSuchSettingsException, SystemException {
		Settings settings = fetchByStatus_Last(status, orderByComparator);

		if (settings != null) {
			return settings;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSettingsException(msg.toString());
	}

	/**
	 * Returns the last settings in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching settings, or <code>null</code> if a matching settings could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings fetchByStatus_Last(String status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByStatus(status);

		List<Settings> list = findByStatus(status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the settingses before and after the current settings in the ordered set where status = &#63;.
	 *
	 * @param sid the primary key of the current settings
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next settings
	 * @throws com.marcelmika.lims.NoSuchSettingsException if a settings with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Settings[] findByStatus_PrevAndNext(long sid, String status,
		OrderByComparator orderByComparator)
		throws NoSuchSettingsException, SystemException {
		Settings settings = findByPrimaryKey(sid);

		Session session = null;

		try {
			session = openSession();

			Settings[] array = new SettingsImpl[3];

			array[0] = getByStatus_PrevAndNext(session, settings, status,
					orderByComparator, true);

			array[1] = settings;

			array[2] = getByStatus_PrevAndNext(session, settings, status,
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

	protected Settings getByStatus_PrevAndNext(Session session,
		Settings settings, String status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SETTINGS_WHERE);

		if (status == null) {
			query.append(_FINDER_COLUMN_STATUS_STATUS_1);
		}
		else {
			if (status.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STATUS_STATUS_3);
			}
			else {
				query.append(_FINDER_COLUMN_STATUS_STATUS_2);
			}
		}

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

		if (status != null) {
			qPos.add(status);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(settings);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Settings> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the settingses.
	 *
	 * @return the settingses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Settings> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of settingses
	 * @param end the upper bound of the range of settingses (not inclusive)
	 * @return the range of settingses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Settings> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of settingses
	 * @param end the upper bound of the range of settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of settingses
	 * @throws SystemException if a system exception occurred
	 */
	public List<Settings> findAll(int start, int end,
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

		List<Settings> list = (List<Settings>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SETTINGS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SETTINGS;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Settings>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Settings>)QueryUtil.list(q, getDialect(),
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
	 * Removes the settings where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the settings that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Settings removeByUserId(long userId)
		throws NoSuchSettingsException, SystemException {
		Settings settings = findByUserId(userId);

		return remove(settings);
	}

	/**
	 * Removes all the settingses where status = &#63; from the database.
	 *
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStatus(String status) throws SystemException {
		for (Settings settings : findByStatus(status)) {
			remove(settings);
		}
	}

	/**
	 * Removes all the settingses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Settings settings : findAll()) {
			remove(settings);
		}
	}

	/**
	 * Returns the number of settingses where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching settingses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SETTINGS_WHERE);

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
	 * Returns the number of settingses where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching settingses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStatus(String status) throws SystemException {
		Object[] finderArgs = new Object[] { status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_STATUS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SETTINGS_WHERE);

			if (status == null) {
				query.append(_FINDER_COLUMN_STATUS_STATUS_1);
			}
			else {
				if (status.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_STATUS_STATUS_3);
				}
				else {
					query.append(_FINDER_COLUMN_STATUS_STATUS_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (status != null) {
					qPos.add(status);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_STATUS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of settingses.
	 *
	 * @return the number of settingses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SETTINGS);

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
	 * Initializes the settings persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.model.Settings")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Settings>> listenersList = new ArrayList<ModelListener<Settings>>();

				for (String listenerClassName : listenerClassNames) {
					Class<?> clazz = getClass();

					listenersList.add((ModelListener<Settings>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SettingsImpl.class.getName());
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
	private static final String _SQL_SELECT_SETTINGS = "SELECT settings FROM Settings settings";
	private static final String _SQL_SELECT_SETTINGS_WHERE = "SELECT settings FROM Settings settings WHERE ";
	private static final String _SQL_COUNT_SETTINGS = "SELECT COUNT(settings) FROM Settings settings";
	private static final String _SQL_COUNT_SETTINGS_WHERE = "SELECT COUNT(settings) FROM Settings settings WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "settings.userId = ?";
	private static final String _FINDER_COLUMN_STATUS_STATUS_1 = "settings.status IS NULL";
	private static final String _FINDER_COLUMN_STATUS_STATUS_2 = "settings.status = ?";
	private static final String _FINDER_COLUMN_STATUS_STATUS_3 = "(settings.status IS NULL OR settings.status = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "settings.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Settings exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Settings exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(SettingsPersistenceImpl.class);
	private static Settings _nullSettings = new SettingsImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Settings> toCacheModel() {
				return _nullSettingsCacheModel;
			}
		};

	private static CacheModel<Settings> _nullSettingsCacheModel = new CacheModel<Settings>() {
			public Settings toEntityModel() {
				return _nullSettings;
			}
		};
}