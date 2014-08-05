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

package com.marcelmika.lims.persistence.generated.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Settings}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Settings
 * @generated
 */
public class SettingsWrapper implements Settings, ModelWrapper<Settings> {
	public SettingsWrapper(Settings settings) {
		_settings = settings;
	}

	public Class<?> getModelClass() {
		return Settings.class;
	}

	public String getModelClassName() {
		return Settings.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("sid", getSid());
		attributes.put("userId", getUserId());
		attributes.put("presence", getPresence());
		attributes.put("mute", getMute());
		attributes.put("chatEnabled", getChatEnabled());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long sid = (Long)attributes.get("sid");

		if (sid != null) {
			setSid(sid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String presence = (String)attributes.get("presence");

		if (presence != null) {
			setPresence(presence);
		}

		Boolean mute = (Boolean)attributes.get("mute");

		if (mute != null) {
			setMute(mute);
		}

		Boolean chatEnabled = (Boolean)attributes.get("chatEnabled");

		if (chatEnabled != null) {
			setChatEnabled(chatEnabled);
		}
	}

	/**
	* Returns the primary key of this settings.
	*
	* @return the primary key of this settings
	*/
	public long getPrimaryKey() {
		return _settings.getPrimaryKey();
	}

	/**
	* Sets the primary key of this settings.
	*
	* @param primaryKey the primary key of this settings
	*/
	public void setPrimaryKey(long primaryKey) {
		_settings.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the sid of this settings.
	*
	* @return the sid of this settings
	*/
	public long getSid() {
		return _settings.getSid();
	}

	/**
	* Sets the sid of this settings.
	*
	* @param sid the sid of this settings
	*/
	public void setSid(long sid) {
		_settings.setSid(sid);
	}

	/**
	* Returns the user ID of this settings.
	*
	* @return the user ID of this settings
	*/
	public long getUserId() {
		return _settings.getUserId();
	}

	/**
	* Sets the user ID of this settings.
	*
	* @param userId the user ID of this settings
	*/
	public void setUserId(long userId) {
		_settings.setUserId(userId);
	}

	/**
	* Returns the user uuid of this settings.
	*
	* @return the user uuid of this settings
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settings.getUserUuid();
	}

	/**
	* Sets the user uuid of this settings.
	*
	* @param userUuid the user uuid of this settings
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_settings.setUserUuid(userUuid);
	}

	/**
	* Returns the presence of this settings.
	*
	* @return the presence of this settings
	*/
	public java.lang.String getPresence() {
		return _settings.getPresence();
	}

	/**
	* Sets the presence of this settings.
	*
	* @param presence the presence of this settings
	*/
	public void setPresence(java.lang.String presence) {
		_settings.setPresence(presence);
	}

	/**
	* Returns the mute of this settings.
	*
	* @return the mute of this settings
	*/
	public boolean getMute() {
		return _settings.getMute();
	}

	/**
	* Returns <code>true</code> if this settings is mute.
	*
	* @return <code>true</code> if this settings is mute; <code>false</code> otherwise
	*/
	public boolean isMute() {
		return _settings.isMute();
	}

	/**
	* Sets whether this settings is mute.
	*
	* @param mute the mute of this settings
	*/
	public void setMute(boolean mute) {
		_settings.setMute(mute);
	}

	/**
	* Returns the chat enabled of this settings.
	*
	* @return the chat enabled of this settings
	*/
	public boolean getChatEnabled() {
		return _settings.getChatEnabled();
	}

	/**
	* Returns <code>true</code> if this settings is chat enabled.
	*
	* @return <code>true</code> if this settings is chat enabled; <code>false</code> otherwise
	*/
	public boolean isChatEnabled() {
		return _settings.isChatEnabled();
	}

	/**
	* Sets whether this settings is chat enabled.
	*
	* @param chatEnabled the chat enabled of this settings
	*/
	public void setChatEnabled(boolean chatEnabled) {
		_settings.setChatEnabled(chatEnabled);
	}

	public boolean isNew() {
		return _settings.isNew();
	}

	public void setNew(boolean n) {
		_settings.setNew(n);
	}

	public boolean isCachedModel() {
		return _settings.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_settings.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _settings.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _settings.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_settings.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _settings.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_settings.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SettingsWrapper((Settings)_settings.clone());
	}

	public int compareTo(
		com.marcelmika.lims.persistence.generated.model.Settings settings) {
		return _settings.compareTo(settings);
	}

	@Override
	public int hashCode() {
		return _settings.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.persistence.generated.model.Settings> toCacheModel() {
		return _settings.toCacheModel();
	}

	public com.marcelmika.lims.persistence.generated.model.Settings toEscapedModel() {
		return new SettingsWrapper(_settings.toEscapedModel());
	}

	public com.marcelmika.lims.persistence.generated.model.Settings toUnescapedModel() {
		return new SettingsWrapper(_settings.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _settings.toString();
	}

	public java.lang.String toXmlString() {
		return _settings.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_settings.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SettingsWrapper)) {
			return false;
		}

		SettingsWrapper settingsWrapper = (SettingsWrapper)obj;

		if (Validator.equals(_settings, settingsWrapper._settings)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Settings getWrappedSettings() {
		return _settings;
	}

	public Settings getWrappedModel() {
		return _settings;
	}

	public void resetOriginalValues() {
		_settings.resetOriginalValues();
	}

	private Settings _settings;
}