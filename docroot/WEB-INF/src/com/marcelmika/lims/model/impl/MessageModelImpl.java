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

package com.marcelmika.lims.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.marcelmika.lims.model.Message;
import com.marcelmika.lims.model.MessageModel;
import com.marcelmika.lims.service.persistence.MessagePK;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the Message service. Represents a row in the &quot;LiferayLIMS_Message&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.marcelmika.lims.model.MessageModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MessageImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MessageImpl
 * @see com.marcelmika.lims.model.Message
 * @see com.marcelmika.lims.model.MessageModel
 * @generated
 */
public class MessageModelImpl extends BaseModelImpl<Message>
	implements MessageModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message model instance should use the {@link com.marcelmika.lims.model.Message} interface instead.
	 */
	public static final String TABLE_NAME = "LiferayLIMS_Message";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mid", Types.BIGINT },
			{ "cid", Types.BIGINT },
			{ "creatorId", Types.BIGINT },
			{ "createdAt", Types.BIGINT },
			{ "messageHash", Types.VARCHAR },
			{ "text_", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table LiferayLIMS_Message (mid LONG not null,cid LONG not null,creatorId LONG,createdAt LONG,messageHash VARCHAR(75) null,text_ VARCHAR(75) null,primary key (mid, cid))";
	public static final String TABLE_SQL_DROP = "drop table LiferayLIMS_Message";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.marcelmika.lims.model.Message"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.marcelmika.lims.model.Message"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.marcelmika.lims.model.Message"),
			true);
	public static long CREATORID_COLUMN_BITMASK = 1L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.marcelmika.lims.model.Message"));

	public MessageModelImpl() {
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

	public Class<?> getModelClass() {
		return Message.class;
	}

	public String getModelClassName() {
		return Message.class.getName();
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
	}

	public long getCid() {
		return _cid;
	}

	public void setCid(long cid) {
		_cid = cid;
	}

	public long getCreatorId() {
		return _creatorId;
	}

	public void setCreatorId(long creatorId) {
		_columnBitmask |= CREATORID_COLUMN_BITMASK;

		if (!_setOriginalCreatorId) {
			_setOriginalCreatorId = true;

			_originalCreatorId = _creatorId;
		}

		_creatorId = creatorId;
	}

	public long getOriginalCreatorId() {
		return _originalCreatorId;
	}

	public long getCreatedAt() {
		return _createdAt;
	}

	public void setCreatedAt(long createdAt) {
		_createdAt = createdAt;
	}

	public String getMessageHash() {
		if (_messageHash == null) {
			return StringPool.BLANK;
		}
		else {
			return _messageHash;
		}
	}

	public void setMessageHash(String messageHash) {
		_messageHash = messageHash;
	}

	public String getText() {
		if (_text == null) {
			return StringPool.BLANK;
		}
		else {
			return _text;
		}
	}

	public void setText(String text) {
		_text = text;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public Message toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Message)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	public Message toUnescapedModel() {
		return (Message)this;
	}

	@Override
	public Object clone() {
		MessageImpl messageImpl = new MessageImpl();

		messageImpl.setMid(getMid());
		messageImpl.setCid(getCid());
		messageImpl.setCreatorId(getCreatorId());
		messageImpl.setCreatedAt(getCreatedAt());
		messageImpl.setMessageHash(getMessageHash());
		messageImpl.setText(getText());

		messageImpl.resetOriginalValues();

		return messageImpl;
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

		if (!(obj instanceof Message)) {
			return false;
		}

		Message message = (Message)obj;

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
	public void resetOriginalValues() {
		MessageModelImpl messageModelImpl = this;

		messageModelImpl._originalCreatorId = messageModelImpl._creatorId;

		messageModelImpl._setOriginalCreatorId = false;

		messageModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Message> toCacheModel() {
		MessageCacheModel messageCacheModel = new MessageCacheModel();

		messageCacheModel.mid = getMid();

		messageCacheModel.cid = getCid();

		messageCacheModel.creatorId = getCreatorId();

		messageCacheModel.createdAt = getCreatedAt();

		messageCacheModel.messageHash = getMessageHash();

		String messageHash = messageCacheModel.messageHash;

		if ((messageHash != null) && (messageHash.length() == 0)) {
			messageCacheModel.messageHash = null;
		}

		messageCacheModel.text = getText();

		String text = messageCacheModel.text;

		if ((text != null) && (text.length() == 0)) {
			messageCacheModel.text = null;
		}

		return messageCacheModel;
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

	private static ClassLoader _classLoader = Message.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			Message.class
		};
	private long _mid;
	private long _cid;
	private long _creatorId;
	private long _originalCreatorId;
	private boolean _setOriginalCreatorId;
	private long _createdAt;
	private String _messageHash;
	private String _text;
	private long _columnBitmask;
	private Message _escapedModel;
}