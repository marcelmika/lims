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

import com.marcelmika.lims.service.ClpSerializer;
import com.marcelmika.lims.service.OpenedConversationLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class OpenedConversationClp extends BaseModelImpl<OpenedConversation>
	implements OpenedConversation {
	public OpenedConversationClp() {
	}

	public Class<?> getModelClass() {
		return OpenedConversation.class;
	}

	public String getModelClassName() {
		return OpenedConversation.class.getName();
	}

	public long getPrimaryKey() {
		return _ocid;
	}

	public void setPrimaryKey(long primaryKey) {
		setOcid(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_ocid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("ocid", getOcid());
		attributes.put("userId", getUserId());
		attributes.put("conversationId", getConversationId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long ocid = (Long)attributes.get("ocid");

		if (ocid != null) {
			setOcid(ocid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String conversationId = (String)attributes.get("conversationId");

		if (conversationId != null) {
			setConversationId(conversationId);
		}
	}

	public long getOcid() {
		return _ocid;
	}

	public void setOcid(long ocid) {
		_ocid = ocid;

		if (_openedConversationRemoteModel != null) {
			try {
				Class<?> clazz = _openedConversationRemoteModel.getClass();

				Method method = clazz.getMethod("setOcid", long.class);

				method.invoke(_openedConversationRemoteModel, ocid);
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

		if (_openedConversationRemoteModel != null) {
			try {
				Class<?> clazz = _openedConversationRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_openedConversationRemoteModel, userId);
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

	public String getConversationId() {
		return _conversationId;
	}

	public void setConversationId(String conversationId) {
		_conversationId = conversationId;

		if (_openedConversationRemoteModel != null) {
			try {
				Class<?> clazz = _openedConversationRemoteModel.getClass();

				Method method = clazz.getMethod("setConversationId",
						String.class);

				method.invoke(_openedConversationRemoteModel, conversationId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getOpenedConversationRemoteModel() {
		return _openedConversationRemoteModel;
	}

	public void setOpenedConversationRemoteModel(
		BaseModel<?> openedConversationRemoteModel) {
		_openedConversationRemoteModel = openedConversationRemoteModel;
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

		Class<?> remoteModelClass = _openedConversationRemoteModel.getClass();

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

		Object returnValue = method.invoke(_openedConversationRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			OpenedConversationLocalServiceUtil.addOpenedConversation(this);
		}
		else {
			OpenedConversationLocalServiceUtil.updateOpenedConversation(this);
		}
	}

	@Override
	public OpenedConversation toEscapedModel() {
		return (OpenedConversation)ProxyUtil.newProxyInstance(OpenedConversation.class.getClassLoader(),
			new Class[] { OpenedConversation.class },
			new AutoEscapeBeanHandler(this));
	}

	public OpenedConversation toUnescapedModel() {
		return this;
	}

	@Override
	public Object clone() {
		OpenedConversationClp clone = new OpenedConversationClp();

		clone.setOcid(getOcid());
		clone.setUserId(getUserId());
		clone.setConversationId(getConversationId());

		return clone;
	}

	public int compareTo(OpenedConversation openedConversation) {
		long primaryKey = openedConversation.getPrimaryKey();

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

		if (!(obj instanceof OpenedConversationClp)) {
			return false;
		}

		OpenedConversationClp openedConversation = (OpenedConversationClp)obj;

		long primaryKey = openedConversation.getPrimaryKey();

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
		StringBundler sb = new StringBundler(7);

		sb.append("{ocid=");
		sb.append(getOcid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", conversationId=");
		sb.append(getConversationId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.OpenedConversation");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>ocid</column-name><column-value><![CDATA[");
		sb.append(getOcid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationId</column-name><column-value><![CDATA[");
		sb.append(getConversationId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _ocid;
	private long _userId;
	private String _userUuid;
	private String _conversationId;
	private BaseModel<?> _openedConversationRemoteModel;
}