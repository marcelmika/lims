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

import com.marcelmika.lims.service.ClpSerializer;
import com.marcelmika.lims.service.ParticipantLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ParticipantClp extends BaseModelImpl<Participant>
	implements Participant {
	public ParticipantClp() {
	}

	public Class<?> getModelClass() {
		return Participant.class;
	}

	public String getModelClassName() {
		return Participant.class.getName();
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
		attributes.put("cid", getCid());
		attributes.put("participantId", getParticipantId());
		attributes.put("unreadMessagesCount", getUnreadMessagesCount());
		attributes.put("isOpened", getIsOpened());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pid = (Long)attributes.get("pid");

		if (pid != null) {
			setPid(pid);
		}

		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		Long participantId = (Long)attributes.get("participantId");

		if (participantId != null) {
			setParticipantId(participantId);
		}

		Integer unreadMessagesCount = (Integer)attributes.get(
				"unreadMessagesCount");

		if (unreadMessagesCount != null) {
			setUnreadMessagesCount(unreadMessagesCount);
		}

		Boolean isOpened = (Boolean)attributes.get("isOpened");

		if (isOpened != null) {
			setIsOpened(isOpened);
		}
	}

	public long getPid() {
		return _pid;
	}

	public void setPid(long pid) {
		_pid = pid;

		if (_participantRemoteModel != null) {
			try {
				Class<?> clazz = _participantRemoteModel.getClass();

				Method method = clazz.getMethod("setPid", long.class);

				method.invoke(_participantRemoteModel, pid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public long getCid() {
		return _cid;
	}

	public void setCid(long cid) {
		_cid = cid;

		if (_participantRemoteModel != null) {
			try {
				Class<?> clazz = _participantRemoteModel.getClass();

				Method method = clazz.getMethod("setCid", long.class);

				method.invoke(_participantRemoteModel, cid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public long getParticipantId() {
		return _participantId;
	}

	public void setParticipantId(long participantId) {
		_participantId = participantId;

		if (_participantRemoteModel != null) {
			try {
				Class<?> clazz = _participantRemoteModel.getClass();

				Method method = clazz.getMethod("setParticipantId", long.class);

				method.invoke(_participantRemoteModel, participantId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public int getUnreadMessagesCount() {
		return _unreadMessagesCount;
	}

	public void setUnreadMessagesCount(int unreadMessagesCount) {
		_unreadMessagesCount = unreadMessagesCount;

		if (_participantRemoteModel != null) {
			try {
				Class<?> clazz = _participantRemoteModel.getClass();

				Method method = clazz.getMethod("setUnreadMessagesCount",
						int.class);

				method.invoke(_participantRemoteModel, unreadMessagesCount);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public boolean getIsOpened() {
		return _isOpened;
	}

	public boolean isIsOpened() {
		return _isOpened;
	}

	public void setIsOpened(boolean isOpened) {
		_isOpened = isOpened;

		if (_participantRemoteModel != null) {
			try {
				Class<?> clazz = _participantRemoteModel.getClass();

				Method method = clazz.getMethod("setIsOpened", boolean.class);

				method.invoke(_participantRemoteModel, isOpened);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getParticipantRemoteModel() {
		return _participantRemoteModel;
	}

	public void setParticipantRemoteModel(BaseModel<?> participantRemoteModel) {
		_participantRemoteModel = participantRemoteModel;
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

		Class<?> remoteModelClass = _participantRemoteModel.getClass();

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

		Object returnValue = method.invoke(_participantRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			ParticipantLocalServiceUtil.addParticipant(this);
		}
		else {
			ParticipantLocalServiceUtil.updateParticipant(this);
		}
	}

	@Override
	public Participant toEscapedModel() {
		return (Participant)ProxyUtil.newProxyInstance(Participant.class.getClassLoader(),
			new Class[] { Participant.class }, new AutoEscapeBeanHandler(this));
	}

	public Participant toUnescapedModel() {
		return this;
	}

	@Override
	public Object clone() {
		ParticipantClp clone = new ParticipantClp();

		clone.setPid(getPid());
		clone.setCid(getCid());
		clone.setParticipantId(getParticipantId());
		clone.setUnreadMessagesCount(getUnreadMessagesCount());
		clone.setIsOpened(getIsOpened());

		return clone;
	}

	public int compareTo(Participant participant) {
		long primaryKey = participant.getPrimaryKey();

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

		if (!(obj instanceof ParticipantClp)) {
			return false;
		}

		ParticipantClp participant = (ParticipantClp)obj;

		long primaryKey = participant.getPrimaryKey();

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

		sb.append("{pid=");
		sb.append(getPid());
		sb.append(", cid=");
		sb.append(getCid());
		sb.append(", participantId=");
		sb.append(getParticipantId());
		sb.append(", unreadMessagesCount=");
		sb.append(getUnreadMessagesCount());
		sb.append(", isOpened=");
		sb.append(getIsOpened());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.Participant");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>pid</column-name><column-value><![CDATA[");
		sb.append(getPid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>cid</column-name><column-value><![CDATA[");
		sb.append(getCid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participantId</column-name><column-value><![CDATA[");
		sb.append(getParticipantId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>unreadMessagesCount</column-name><column-value><![CDATA[");
		sb.append(getUnreadMessagesCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>isOpened</column-name><column-value><![CDATA[");
		sb.append(getIsOpened());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _pid;
	private long _cid;
	private long _participantId;
	private int _unreadMessagesCount;
	private boolean _isOpened;
	private BaseModel<?> _participantRemoteModel;
}