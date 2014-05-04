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
import com.marcelmika.lims.service.PanelLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PanelClp extends BaseModelImpl<Panel> implements Panel {
	public PanelClp() {
	}

	public Class<?> getModelClass() {
		return Panel.class;
	}

	public String getModelClassName() {
		return Panel.class.getName();
	}

	public long getPrimaryKey() {
		return _pid;
	}

	public void setPrimaryKey(long primaryKey) {
		setPid(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_pid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("pid", getPid());
		attributes.put("userId", getUserId());
		attributes.put("activePanelId", getActivePanelId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pid = (Long)attributes.get("pid");

		if (pid != null) {
			setPid(pid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String activePanelId = (String)attributes.get("activePanelId");

		if (activePanelId != null) {
			setActivePanelId(activePanelId);
		}
	}

	public long getPid() {
		return _pid;
	}

	public void setPid(long pid) {
		_pid = pid;

		if (_panelRemoteModel != null) {
			try {
				Class<?> clazz = _panelRemoteModel.getClass();

				Method method = clazz.getMethod("setPid", long.class);

				method.invoke(_panelRemoteModel, pid);
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

		if (_panelRemoteModel != null) {
			try {
				Class<?> clazz = _panelRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_panelRemoteModel, userId);
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

	public String getActivePanelId() {
		return _activePanelId;
	}

	public void setActivePanelId(String activePanelId) {
		_activePanelId = activePanelId;

		if (_panelRemoteModel != null) {
			try {
				Class<?> clazz = _panelRemoteModel.getClass();

				Method method = clazz.getMethod("setActivePanelId", String.class);

				method.invoke(_panelRemoteModel, activePanelId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getPanelRemoteModel() {
		return _panelRemoteModel;
	}

	public void setPanelRemoteModel(BaseModel<?> panelRemoteModel) {
		_panelRemoteModel = panelRemoteModel;
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

		Class<?> remoteModelClass = _panelRemoteModel.getClass();

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

		Object returnValue = method.invoke(_panelRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			PanelLocalServiceUtil.addPanel(this);
		}
		else {
			PanelLocalServiceUtil.updatePanel(this);
		}
	}

	@Override
	public Panel toEscapedModel() {
		return (Panel)ProxyUtil.newProxyInstance(Panel.class.getClassLoader(),
			new Class[] { Panel.class }, new AutoEscapeBeanHandler(this));
	}

	public Panel toUnescapedModel() {
		return this;
	}

	@Override
	public Object clone() {
		PanelClp clone = new PanelClp();

		clone.setPid(getPid());
		clone.setUserId(getUserId());
		clone.setActivePanelId(getActivePanelId());

		return clone;
	}

	public int compareTo(Panel panel) {
		long primaryKey = panel.getPrimaryKey();

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

		if (!(obj instanceof PanelClp)) {
			return false;
		}

		PanelClp panel = (PanelClp)obj;

		long primaryKey = panel.getPrimaryKey();

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

		sb.append("{pid=");
		sb.append(getPid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", activePanelId=");
		sb.append(getActivePanelId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.Panel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>pid</column-name><column-value><![CDATA[");
		sb.append(getPid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>activePanelId</column-name><column-value><![CDATA[");
		sb.append(getActivePanelId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _pid;
	private long _userId;
	private String _userUuid;
	private String _activePanelId;
	private BaseModel<?> _panelRemoteModel;
}