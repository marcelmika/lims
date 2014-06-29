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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Message}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Message
 * @generated
 */
public class MessageWrapper implements Message, ModelWrapper<Message> {
	public MessageWrapper(Message message) {
		_message = message;
	}

	public Class<?> getModelClass() {
		return Message.class;
	}

	public String getModelClassName() {
		return Message.class.getName();
	}

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

	/**
	* Returns the primary key of this message.
	*
	* @return the primary key of this message
	*/
	public com.marcelmika.lims.service.persistence.MessagePK getPrimaryKey() {
		return _message.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message.
	*
	* @param primaryKey the primary key of this message
	*/
	public void setPrimaryKey(
		com.marcelmika.lims.service.persistence.MessagePK primaryKey) {
		_message.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mid of this message.
	*
	* @return the mid of this message
	*/
	public long getMid() {
		return _message.getMid();
	}

	/**
	* Sets the mid of this message.
	*
	* @param mid the mid of this message
	*/
	public void setMid(long mid) {
		_message.setMid(mid);
	}

	/**
	* Returns the cid of this message.
	*
	* @return the cid of this message
	*/
	public long getCid() {
		return _message.getCid();
	}

	/**
	* Sets the cid of this message.
	*
	* @param cid the cid of this message
	*/
	public void setCid(long cid) {
		_message.setCid(cid);
	}

	/**
	* Returns the creator ID of this message.
	*
	* @return the creator ID of this message
	*/
	public long getCreatorId() {
		return _message.getCreatorId();
	}

	/**
	* Sets the creator ID of this message.
	*
	* @param creatorId the creator ID of this message
	*/
	public void setCreatorId(long creatorId) {
		_message.setCreatorId(creatorId);
	}

	/**
	* Returns the created at of this message.
	*
	* @return the created at of this message
	*/
	public long getCreatedAt() {
		return _message.getCreatedAt();
	}

	/**
	* Sets the created at of this message.
	*
	* @param createdAt the created at of this message
	*/
	public void setCreatedAt(long createdAt) {
		_message.setCreatedAt(createdAt);
	}

	/**
	* Returns the message hash of this message.
	*
	* @return the message hash of this message
	*/
	public java.lang.String getMessageHash() {
		return _message.getMessageHash();
	}

	/**
	* Sets the message hash of this message.
	*
	* @param messageHash the message hash of this message
	*/
	public void setMessageHash(java.lang.String messageHash) {
		_message.setMessageHash(messageHash);
	}

	/**
	* Returns the text of this message.
	*
	* @return the text of this message
	*/
	public java.lang.String getText() {
		return _message.getText();
	}

	/**
	* Sets the text of this message.
	*
	* @param text the text of this message
	*/
	public void setText(java.lang.String text) {
		_message.setText(text);
	}

	public boolean isNew() {
		return _message.isNew();
	}

	public void setNew(boolean n) {
		_message.setNew(n);
	}

	public boolean isCachedModel() {
		return _message.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_message.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _message.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _message.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_message.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _message.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_message.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MessageWrapper((Message)_message.clone());
	}

	public int compareTo(Message message) {
		return _message.compareTo(message);
	}

	@Override
	public int hashCode() {
		return _message.hashCode();
	}

	public com.liferay.portal.model.CacheModel<Message> toCacheModel() {
		return _message.toCacheModel();
	}

	public Message toEscapedModel() {
		return new MessageWrapper(_message.toEscapedModel());
	}

	public Message toUnescapedModel() {
		return new MessageWrapper(_message.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _message.toString();
	}

	public java.lang.String toXmlString() {
		return _message.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_message.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MessageWrapper)) {
			return false;
		}

		MessageWrapper messageWrapper = (MessageWrapper)obj;

		if (Validator.equals(_message, messageWrapper._message)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Message getWrappedMessage() {
		return _message;
	}

	public Message getWrappedModel() {
		return _message;
	}

	public void resetOriginalValues() {
		_message.resetOriginalValues();
	}

	private Message _message;
}