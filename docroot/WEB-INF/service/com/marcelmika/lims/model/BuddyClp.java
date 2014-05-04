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

package com.marcelmika.lims.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.marcelmika.lims.service.BuddyLocalServiceUtil;
import com.marcelmika.lims.service.ClpSerializer;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class BuddyClp extends BaseModelImpl<Buddy> implements Buddy {
	public BuddyClp() {
	}

	public Class<?> getModelClass() {
		return Buddy.class;
	}

	public String getModelClassName() {
		return Buddy.class.getName();
	}

	public long getPrimaryKey() {
		return _bid;
	}

	public void setPrimaryKey(long primaryKey) {
		setBid(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_bid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("bid", getBid());
		attributes.put("userId", getUserId());
		attributes.put("companyId", getCompanyId());
		attributes.put("portraitId", getPortraitId());
		attributes.put("fullName", getFullName());
		attributes.put("screenName", getScreenName());
		attributes.put("statusMessage", getStatusMessage());
		attributes.put("isTyping", getIsTyping());
		attributes.put("awake", getAwake());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long bid = (Long)attributes.get("bid");

		if (bid != null) {
			setBid(bid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long portraitId = (Long)attributes.get("portraitId");

		if (portraitId != null) {
			setPortraitId(portraitId);
		}

		String fullName = (String)attributes.get("fullName");

		if (fullName != null) {
			setFullName(fullName);
		}

		String screenName = (String)attributes.get("screenName");

		if (screenName != null) {
			setScreenName(screenName);
		}

		String statusMessage = (String)attributes.get("statusMessage");

		if (statusMessage != null) {
			setStatusMessage(statusMessage);
		}

		Boolean isTyping = (Boolean)attributes.get("isTyping");

		if (isTyping != null) {
			setIsTyping(isTyping);
		}

		Boolean awake = (Boolean)attributes.get("awake");

		if (awake != null) {
			setAwake(awake);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	public long getBid() {
		return _bid;
	}

	public void setBid(long bid) {
		_bid = bid;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setBid", long.class);

				method.invoke(_buddyRemoteModel, bid);
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

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_buddyRemoteModel, userId);
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

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setCompanyId", long.class);

				method.invoke(_buddyRemoteModel, companyId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setPortraitId", long.class);

				method.invoke(_buddyRemoteModel, portraitId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getFullName() {
		return _fullName;
	}

	public void setFullName(String fullName) {
		_fullName = fullName;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setFullName", String.class);

				method.invoke(_buddyRemoteModel, fullName);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setScreenName", String.class);

				method.invoke(_buddyRemoteModel, screenName);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getStatusMessage() {
		return _statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setStatusMessage", String.class);

				method.invoke(_buddyRemoteModel, statusMessage);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public boolean getIsTyping() {
		return _isTyping;
	}

	public boolean isIsTyping() {
		return _isTyping;
	}

	public void setIsTyping(boolean isTyping) {
		_isTyping = isTyping;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setIsTyping", boolean.class);

				method.invoke(_buddyRemoteModel, isTyping);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public boolean getAwake() {
		return _awake;
	}

	public boolean isAwake() {
		return _awake;
	}

	public void setAwake(boolean awake) {
		_awake = awake;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setAwake", boolean.class);

				method.invoke(_buddyRemoteModel, awake);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;

		if (_buddyRemoteModel != null) {
			try {
				Class<?> clazz = _buddyRemoteModel.getClass();

				Method method = clazz.getMethod("setStatus", String.class);

				method.invoke(_buddyRemoteModel, status);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public com.liferay.portal.kernel.json.JSONObject toJSON() {
		try {
			String methodName = "toJSON";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			com.liferay.portal.kernel.json.JSONObject returnObj = (com.liferay.portal.kernel.json.JSONObject)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public BaseModel<?> getBuddyRemoteModel() {
		return _buddyRemoteModel;
	}

	public void setBuddyRemoteModel(BaseModel<?> buddyRemoteModel) {
		_buddyRemoteModel = buddyRemoteModel;
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

		Class<?> remoteModelClass = _buddyRemoteModel.getClass();

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

		Object returnValue = method.invoke(_buddyRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			BuddyLocalServiceUtil.addBuddy(this);
		}
		else {
			BuddyLocalServiceUtil.updateBuddy(this);
		}
	}

	@Override
	public Buddy toEscapedModel() {
		return (Buddy)ProxyUtil.newProxyInstance(Buddy.class.getClassLoader(),
			new Class[] { Buddy.class }, new AutoEscapeBeanHandler(this));
	}

	public Buddy toUnescapedModel() {
		return this;
	}

	@Override
	public Object clone() {
		BuddyClp clone = new BuddyClp();

		clone.setBid(getBid());
		clone.setUserId(getUserId());
		clone.setCompanyId(getCompanyId());
		clone.setPortraitId(getPortraitId());
		clone.setFullName(getFullName());
		clone.setScreenName(getScreenName());
		clone.setStatusMessage(getStatusMessage());
		clone.setIsTyping(getIsTyping());
		clone.setAwake(getAwake());
		clone.setStatus(getStatus());

		return clone;
	}

	public int compareTo(Buddy buddy) {
		long primaryKey = buddy.getPrimaryKey();

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

		if (!(obj instanceof BuddyClp)) {
			return false;
		}

		BuddyClp buddy = (BuddyClp)obj;

		long primaryKey = buddy.getPrimaryKey();

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
		StringBundler sb = new StringBundler(21);

		sb.append("{bid=");
		sb.append(getBid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", portraitId=");
		sb.append(getPortraitId());
		sb.append(", fullName=");
		sb.append(getFullName());
		sb.append(", screenName=");
		sb.append(getScreenName());
		sb.append(", statusMessage=");
		sb.append(getStatusMessage());
		sb.append(", isTyping=");
		sb.append(getIsTyping());
		sb.append(", awake=");
		sb.append(getAwake());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.Buddy");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>bid</column-name><column-value><![CDATA[");
		sb.append(getBid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portraitId</column-name><column-value><![CDATA[");
		sb.append(getPortraitId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fullName</column-name><column-value><![CDATA[");
		sb.append(getFullName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>screenName</column-name><column-value><![CDATA[");
		sb.append(getScreenName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusMessage</column-name><column-value><![CDATA[");
		sb.append(getStatusMessage());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>isTyping</column-name><column-value><![CDATA[");
		sb.append(getIsTyping());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>awake</column-name><column-value><![CDATA[");
		sb.append(getAwake());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _bid;
	private long _userId;
	private String _userUuid;
	private long _companyId;
	private long _portraitId;
	private String _fullName;
	private String _screenName;
	private String _statusMessage;
	private boolean _isTyping;
	private boolean _awake;
	private String _status;
	private BaseModel<?> _buddyRemoteModel;
}