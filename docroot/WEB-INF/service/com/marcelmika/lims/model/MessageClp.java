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
import com.marcelmika.lims.service.MessageLocalServiceUtil;
import com.marcelmika.lims.service.persistence.MessagePK;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageClp extends BaseModelImpl<Message> implements Message {
	public MessageClp() {
	}

	public Class<?> getModelClass() {
		return Message.class;
	}

	public String getModelClassName() {
		return Message.class.getName();
	}

	public MessagePK getPrimaryKey() {
		return new MessagePK(_mid, _cid);
	}

	public void setPrimaryKey(MessagePK primaryKey) {
		setMid(primaryKey.mid);
		setCid(primaryKey.cid);
	}

	public Serializable getPrimaryKeyObj() {
		return new MessagePK(_mid, _cid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((MessagePK)primaryKeyObj);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mid", getMid());
		attributes.put("cid", getCid());
		attributes.put("creatorId", getCreatorId());
		attributes.put("createdAt", getCreatedAt());
		attributes.put("messageHash", getMessageHash());
		attributes.put("text", getText());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mid = (Long)attributes.get("mid");

		if (mid != null) {
			setMid(mid);
		}

		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		Long creatorId = (Long)attributes.get("creatorId");

		if (creatorId != null) {
			setCreatorId(creatorId);
		}

		Long createdAt = (Long)attributes.get("createdAt");

		if (createdAt != null) {
			setCreatedAt(createdAt);
		}

		String messageHash = (String)attributes.get("messageHash");

		if (messageHash != null) {
			setMessageHash(messageHash);
		}

		String text = (String)attributes.get("text");

		if (text != null) {
			setText(text);
		}
	}

	public long getMid() {
		return _mid;
	}

	public void setMid(long mid) {
		_mid = mid;

		if (_messageRemoteModel != null) {
			try {
				Class<?> clazz = _messageRemoteModel.getClass();

				Method method = clazz.getMethod("setMid", long.class);

				method.invoke(_messageRemoteModel, mid);
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

		if (_messageRemoteModel != null) {
			try {
				Class<?> clazz = _messageRemoteModel.getClass();

				Method method = clazz.getMethod("setCid", long.class);

				method.invoke(_messageRemoteModel, cid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public long getCreatorId() {
		return _creatorId;
	}

	public void setCreatorId(long creatorId) {
		_creatorId = creatorId;

		if (_messageRemoteModel != null) {
			try {
				Class<?> clazz = _messageRemoteModel.getClass();

				Method method = clazz.getMethod("setCreatorId", long.class);

				method.invoke(_messageRemoteModel, creatorId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public long getCreatedAt() {
		return _createdAt;
	}

	public void setCreatedAt(long createdAt) {
		_createdAt = createdAt;

		if (_messageRemoteModel != null) {
			try {
				Class<?> clazz = _messageRemoteModel.getClass();

				Method method = clazz.getMethod("setCreatedAt", long.class);

				method.invoke(_messageRemoteModel, createdAt);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getMessageHash() {
		return _messageHash;
	}

	public void setMessageHash(String messageHash) {
		_messageHash = messageHash;

		if (_messageRemoteModel != null) {
			try {
				Class<?> clazz = _messageRemoteModel.getClass();

				Method method = clazz.getMethod("setMessageHash", String.class);

				method.invoke(_messageRemoteModel, messageHash);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;

		if (_messageRemoteModel != null) {
			try {
				Class<?> clazz = _messageRemoteModel.getClass();

				Method method = clazz.getMethod("setText", String.class);

				method.invoke(_messageRemoteModel, text);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getMessageRemoteModel() {
		return _messageRemoteModel;
	}

	public void setMessageRemoteModel(BaseModel<?> messageRemoteModel) {
		_messageRemoteModel = messageRemoteModel;
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

		Class<?> remoteModelClass = _messageRemoteModel.getClass();

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

		Object returnValue = method.invoke(_messageRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			MessageLocalServiceUtil.addMessage(this);
		}
		else {
			MessageLocalServiceUtil.updateMessage(this);
		}
	}

	@Override
	public Message toEscapedModel() {
		return (Message)ProxyUtil.newProxyInstance(Message.class.getClassLoader(),
			new Class[] { Message.class }, new AutoEscapeBeanHandler(this));
	}

	public Message toUnescapedModel() {
		return this;
	}

	@Override
	public Object clone() {
		MessageClp clone = new MessageClp();

		clone.setMid(getMid());
		clone.setCid(getCid());
		clone.setCreatorId(getCreatorId());
		clone.setCreatedAt(getCreatedAt());
		clone.setMessageHash(getMessageHash());
		clone.setText(getText());

		return clone;
	}

	public int compareTo(Message message) {
		MessagePK primaryKey = message.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MessageClp)) {
			return false;
		}

		MessageClp message = (MessageClp)obj;

		MessagePK primaryKey = message.getPrimaryKey();

		if (getPrimaryKey().equals(primaryKey)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mid=");
		sb.append(getMid());
		sb.append(", cid=");
		sb.append(getCid());
		sb.append(", creatorId=");
		sb.append(getCreatorId());
		sb.append(", createdAt=");
		sb.append(getCreatedAt());
		sb.append(", messageHash=");
		sb.append(getMessageHash());
		sb.append(", text=");
		sb.append(getText());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.Message");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mid</column-name><column-value><![CDATA[");
		sb.append(getMid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>cid</column-name><column-value><![CDATA[");
		sb.append(getCid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>creatorId</column-name><column-value><![CDATA[");
		sb.append(getCreatorId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createdAt</column-name><column-value><![CDATA[");
		sb.append(getCreatedAt());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageHash</column-name><column-value><![CDATA[");
		sb.append(getMessageHash());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>text</column-name><column-value><![CDATA[");
		sb.append(getText());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _mid;
	private long _cid;
	private long _creatorId;
	private long _createdAt;
	private String _messageHash;
	private String _text;
	private BaseModel<?> _messageRemoteModel;
}