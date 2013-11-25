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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.marcelmika.lims.service.ConversationLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ConversationClp extends BaseModelImpl<Conversation>
	implements Conversation {
	public ConversationClp() {
	}

	public Class<?> getModelClass() {
		return Conversation.class;
	}

	public String getModelClassName() {
		return Conversation.class.getName();
	}

	public long getPrimaryKey() {
		return _cid;
	}

	public void setPrimaryKey(long primaryKey) {
		setCid(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_cid);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cid", getCid());
		attributes.put("userId", getUserId());
		attributes.put("conversationId", getConversationId());
		attributes.put("conversationType", getConversationType());
		attributes.put("conversationVisibility", getConversationVisibility());
		attributes.put("conversationName", getConversationName());
		attributes.put("unreadMessages", getUnreadMessages());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String conversationId = (String)attributes.get("conversationId");

		if (conversationId != null) {
			setConversationId(conversationId);
		}

		String conversationType = (String)attributes.get("conversationType");

		if (conversationType != null) {
			setConversationType(conversationType);
		}

		String conversationVisibility = (String)attributes.get(
				"conversationVisibility");

		if (conversationVisibility != null) {
			setConversationVisibility(conversationVisibility);
		}

		String conversationName = (String)attributes.get("conversationName");

		if (conversationName != null) {
			setConversationName(conversationName);
		}

		Integer unreadMessages = (Integer)attributes.get("unreadMessages");

		if (unreadMessages != null) {
			setUnreadMessages(unreadMessages);
		}
	}

	public long getCid() {
		return _cid;
	}

	public void setCid(long cid) {
		_cid = cid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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
	}

	public String getConversationType() {
		return _conversationType;
	}

	public void setConversationType(String conversationType) {
		_conversationType = conversationType;
	}

	public String getConversationVisibility() {
		return _conversationVisibility;
	}

	public void setConversationVisibility(String conversationVisibility) {
		_conversationVisibility = conversationVisibility;
	}

	public String getConversationName() {
		return _conversationName;
	}

	public void setConversationName(String conversationName) {
		_conversationName = conversationName;
	}

	public int getUnreadMessages() {
		return _unreadMessages;
	}

	public void setUnreadMessages(int unreadMessages) {
		_unreadMessages = unreadMessages;
	}

	public BaseModel<?> getConversationRemoteModel() {
		return _conversationRemoteModel;
	}

	public void setConversationRemoteModel(BaseModel<?> conversationRemoteModel) {
		_conversationRemoteModel = conversationRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			ConversationLocalServiceUtil.addConversation(this);
		}
		else {
			ConversationLocalServiceUtil.updateConversation(this);
		}
	}

	@Override
	public Conversation toEscapedModel() {
		return (Conversation)Proxy.newProxyInstance(Conversation.class.getClassLoader(),
			new Class[] { Conversation.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		ConversationClp clone = new ConversationClp();

		clone.setCid(getCid());
		clone.setUserId(getUserId());
		clone.setConversationId(getConversationId());
		clone.setConversationType(getConversationType());
		clone.setConversationVisibility(getConversationVisibility());
		clone.setConversationName(getConversationName());
		clone.setUnreadMessages(getUnreadMessages());

		return clone;
	}

	public int compareTo(Conversation conversation) {
		long primaryKey = conversation.getPrimaryKey();

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
		if (obj == null) {
			return false;
		}

		ConversationClp conversation = null;

		try {
			conversation = (ConversationClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = conversation.getPrimaryKey();

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
		StringBundler sb = new StringBundler(15);

		sb.append("{cid=");
		sb.append(getCid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", conversationId=");
		sb.append(getConversationId());
		sb.append(", conversationType=");
		sb.append(getConversationType());
		sb.append(", conversationVisibility=");
		sb.append(getConversationVisibility());
		sb.append(", conversationName=");
		sb.append(getConversationName());
		sb.append(", unreadMessages=");
		sb.append(getUnreadMessages());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.lims.model.Conversation");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>cid</column-name><column-value><![CDATA[");
		sb.append(getCid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationId</column-name><column-value><![CDATA[");
		sb.append(getConversationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationType</column-name><column-value><![CDATA[");
		sb.append(getConversationType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationVisibility</column-name><column-value><![CDATA[");
		sb.append(getConversationVisibility());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationName</column-name><column-value><![CDATA[");
		sb.append(getConversationName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>unreadMessages</column-name><column-value><![CDATA[");
		sb.append(getUnreadMessages());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _cid;
	private long _userId;
	private String _userUuid;
	private String _conversationId;
	private String _conversationType;
	private String _conversationVisibility;
	private String _conversationName;
	private int _unreadMessages;
	private BaseModel<?> _conversationRemoteModel;
}