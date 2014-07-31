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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.marcelmika.lims.persistence.generated.service.ClpSerializer;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class SettingsClp extends BaseModelImpl<Settings> implements Settings {
	public SettingsClp() {
	}

	public Class<?> getModelClass() {
		return Settings.class;
	}

	public String getModelClassName() {
		return Settings.class.getName();
	}

	public long getPrimaryKey() {
		return _sid;
	}

	public void setPrimaryKey(long primaryKey) {
		setSid(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_sid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("sid", getSid());
		attributes.put("userId", getUserId());
		attributes.put("status", getStatus());
		attributes.put("mute", getMute());
		attributes.put("chatEnabled", getChatEnabled());

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

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
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

	public long getSid() {
		return _sid;
	}

	public void setSid(long sid) {
		_sid = sid;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setSid", long.class);

				method.invoke(_settingsRemoteModel, sid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_settingsRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setStatus", String.class);

				method.invoke(_settingsRemoteModel, status);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public boolean getMute() {
		return _mute;
	}

	public boolean isMute() {
		return _mute;
	}

	public void setMute(boolean mute) {
		_mute = mute;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setMute", boolean.class);

				method.invoke(_settingsRemoteModel, mute);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public boolean getChatEnabled() {
		return _chatEnabled;
	}

	public boolean isChatEnabled() {
		return _chatEnabled;
	}

	public void setChatEnabled(boolean chatEnabled) {
		_chatEnabled = chatEnabled;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setChatEnabled", boolean.class);

				method.invoke(_settingsRemoteModel, chatEnabled);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getSettingsRemoteModel() {
		return _settingsRemoteModel;
	}

	public void setSettingsRemoteModel(BaseModel<?> settingsRemoteModel) {
		_settingsRemoteModel = settingsRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _settingsRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_settingsRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			SettingsLocalServiceUtil.addSettings(this);
		}
		else {
			SettingsLocalServiceUtil.updateSettings(this);
		}
	}

	@Override
	public Settings toEscapedModel() {
		return (Settings)ProxyUtil.newProxyInstance(Settings.class.getClassLoader(),
			new Class[] { Settings.class }, new AutoEscapeBeanHandler(this));
	}

	public Settings toUnescapedModel() {
		return this;
	}

	@Override
	public Object clone() {
		SettingsClp clone = new SettingsClp();

		clone.setSid(getSid());
		clone.setUserId(getUserId());
		clone.setStatus(getStatus());
		clone.setMute(getMute());
		clone.setChatEnabled(getChatEnabled());

		return clone;
	}

	public int compareTo(Settings settings) {
		long primaryKey = settings.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SettingsClp)) {
			return false;
		}

		SettingsClp settings = (SettingsClp)obj;

		long primaryKey = settings.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{sid=");
		sb.append(getSid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", mute=");
		sb.append(getMute());
		sb.append(", chatEnabled=");
		sb.append(getChatEnabled());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.persistence.generated.model.Settings");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>sid</column-name><column-value><![CDATA[");
		sb.append(getSid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mute</column-name><column-value><![CDATA[");
		sb.append(getMute());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>chatEnabled</column-name><column-value><![CDATA[");
		sb.append(getChatEnabled());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _sid;
	private long _userId;
	private String _userUuid;
	private String _status;
	private boolean _mute;
	private boolean _chatEnabled;
	private BaseModel<?> _settingsRemoteModel;
}