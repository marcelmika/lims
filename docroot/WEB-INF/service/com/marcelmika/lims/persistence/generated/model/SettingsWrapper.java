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
 * @author Brian Wing Shun Chan
 * @see Settings
 * @generated
 */
public class SettingsWrapper implements Settings, ModelWrapper<Settings> {
	public SettingsWrapper(Settings settings) {
		_settings = settings;
	}

	@Override
	public Class<?> getModelClass() {
		return Settings.class;
	}

	@Override
	public String getModelClassName() {
		return Settings.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("sid", getSid());
		attributes.put("userId", getUserId());
		attributes.put("presence", getPresence());
		attributes.put("presenceUpdatedAt", getPresenceUpdatedAt());
		attributes.put("mute", getMute());
		attributes.put("chatEnabled", getChatEnabled());
		attributes.put("adminAreaOpened", getAdminAreaOpened());

		return attributes;
	}

	@Override
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

		Long presenceUpdatedAt = (Long)attributes.get("presenceUpdatedAt");

		if (presenceUpdatedAt != null) {
			setPresenceUpdatedAt(presenceUpdatedAt);
		}

		Boolean mute = (Boolean)attributes.get("mute");

		if (mute != null) {
			setMute(mute);
		}

		Boolean chatEnabled = (Boolean)attributes.get("chatEnabled");

		if (chatEnabled != null) {
			setChatEnabled(chatEnabled);
		}

		Boolean adminAreaOpened = (Boolean)attributes.get("adminAreaOpened");

		if (adminAreaOpened != null) {
			setAdminAreaOpened(adminAreaOpened);
		}
	}

	/**
	* Returns the primary key of this settings.
	*
	* @return the primary key of this settings
	*/
	@Override
	public long getPrimaryKey() {
		return _settings.getPrimaryKey();
	}

	/**
	* Sets the primary key of this settings.
	*
	* @param primaryKey the primary key of this settings
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_settings.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the sid of this settings.
	*
	* @return the sid of this settings
	*/
	@Override
	public long getSid() {
		return _settings.getSid();
	}

	/**
	* Sets the sid of this settings.
	*
	* @param sid the sid of this settings
	*/
	@Override
	public void setSid(long sid) {
		_settings.setSid(sid);
	}

	/**
	* Returns the user ID of this settings.
	*
	* @return the user ID of this settings
	*/
	@Override
	public long getUserId() {
		return _settings.getUserId();
	}

	/**
	* Sets the user ID of this settings.
	*
	* @param userId the user ID of this settings
	*/
	@Override
	public void setUserId(long userId) {
		_settings.setUserId(userId);
	}

	/**
	* Returns the user uuid of this settings.
	*
	* @return the user uuid of this settings
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settings.getUserUuid();
	}

	/**
	* Sets the user uuid of this settings.
	*
	* @param userUuid the user uuid of this settings
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_settings.setUserUuid(userUuid);
	}

	/**
	* Returns the presence of this settings.
	*
	* @return the presence of this settings
	*/
	@Override
	public java.lang.String getPresence() {
		return _settings.getPresence();
	}

	/**
	* Sets the presence of this settings.
	*
	* @param presence the presence of this settings
	*/
	@Override
	public void setPresence(java.lang.String presence) {
		_settings.setPresence(presence);
	}

	/**
	* Returns the presence updated at of this settings.
	*
	* @return the presence updated at of this settings
	*/
	@Override
	public long getPresenceUpdatedAt() {
		return _settings.getPresenceUpdatedAt();
	}

	/**
	* Sets the presence updated at of this settings.
	*
	* @param presenceUpdatedAt the presence updated at of this settings
	*/
	@Override
	public void setPresenceUpdatedAt(long presenceUpdatedAt) {
		_settings.setPresenceUpdatedAt(presenceUpdatedAt);
	}

	/**
	* Returns the mute of this settings.
	*
	* @return the mute of this settings
	*/
	@Override
	public boolean getMute() {
		return _settings.getMute();
	}

	/**
	* Returns <code>true</code> if this settings is mute.
	*
	* @return <code>true</code> if this settings is mute; <code>false</code> otherwise
	*/
	@Override
	public boolean isMute() {
		return _settings.isMute();
	}

	/**
	* Sets whether this settings is mute.
	*
	* @param mute the mute of this settings
	*/
	@Override
	public void setMute(boolean mute) {
		_settings.setMute(mute);
	}

	/**
	* Returns the chat enabled of this settings.
	*
	* @return the chat enabled of this settings
	*/
	@Override
	public boolean getChatEnabled() {
		return _settings.getChatEnabled();
	}

	/**
	* Returns <code>true</code> if this settings is chat enabled.
	*
	* @return <code>true</code> if this settings is chat enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isChatEnabled() {
		return _settings.isChatEnabled();
	}

	/**
	* Sets whether this settings is chat enabled.
	*
	* @param chatEnabled the chat enabled of this settings
	*/
	@Override
	public void setChatEnabled(boolean chatEnabled) {
		_settings.setChatEnabled(chatEnabled);
	}

	/**
	* Returns the admin area opened of this settings.
	*
	* @return the admin area opened of this settings
	*/
	@Override
	public boolean getAdminAreaOpened() {
		return _settings.getAdminAreaOpened();
	}

	/**
	* Returns <code>true</code> if this settings is admin area opened.
	*
	* @return <code>true</code> if this settings is admin area opened; <code>false</code> otherwise
	*/
	@Override
	public boolean isAdminAreaOpened() {
		return _settings.isAdminAreaOpened();
	}

	/**
	* Sets whether this settings is admin area opened.
	*
	* @param adminAreaOpened the admin area opened of this settings
	*/
	@Override
	public void setAdminAreaOpened(boolean adminAreaOpened) {
		_settings.setAdminAreaOpened(adminAreaOpened);
	}

	@Override
	public boolean isNew() {
		return _settings.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_settings.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _settings.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_settings.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _settings.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _settings.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_settings.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _settings.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_settings.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_settings.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_settings.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SettingsWrapper((Settings)_settings.clone());
	}

	@Override
	public int compareTo(
		com.marcelmika.lims.persistence.generated.model.Settings settings) {
		return _settings.compareTo(settings);
	}

	@Override
	public int hashCode() {
		return _settings.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.persistence.generated.model.Settings> toCacheModel() {
		return _settings.toCacheModel();
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Settings toEscapedModel() {
		return new SettingsWrapper(_settings.toEscapedModel());
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Settings toUnescapedModel() {
		return new SettingsWrapper(_settings.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _settings.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _settings.toXmlString();
	}

	@Override
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
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Settings getWrappedSettings() {
		return _settings;
	}

	@Override
	public Settings getWrappedModel() {
		return _settings;
	}

	@Override
	public void resetOriginalValues() {
		_settings.resetOriginalValues();
	}

	private Settings _settings;
}