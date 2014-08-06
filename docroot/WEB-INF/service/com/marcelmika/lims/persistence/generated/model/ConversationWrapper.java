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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Conversation}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Conversation
 * @generated
 */
public class ConversationWrapper implements Conversation,
	ModelWrapper<Conversation> {
	public ConversationWrapper(Conversation conversation) {
		_conversation = conversation;
	}

	@Override
	public Class<?> getModelClass() {
		return Conversation.class;
	}

	@Override
	public String getModelClassName() {
		return Conversation.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cid", getCid());
		attributes.put("conversationId", getConversationId());
		attributes.put("conversationType", getConversationType());
		attributes.put("updatedAt", getUpdatedAt());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		String conversationId = (String)attributes.get("conversationId");

		if (conversationId != null) {
			setConversationId(conversationId);
		}

		String conversationType = (String)attributes.get("conversationType");

		if (conversationType != null) {
			setConversationType(conversationType);
		}

		Date updatedAt = (Date)attributes.get("updatedAt");

		if (updatedAt != null) {
			setUpdatedAt(updatedAt);
		}
	}

	/**
	* Returns the primary key of this conversation.
	*
	* @return the primary key of this conversation
	*/
	@Override
	public long getPrimaryKey() {
		return _conversation.getPrimaryKey();
	}

	/**
	* Sets the primary key of this conversation.
	*
	* @param primaryKey the primary key of this conversation
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_conversation.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the cid of this conversation.
	*
	* @return the cid of this conversation
	*/
	@Override
	public long getCid() {
		return _conversation.getCid();
	}

	/**
	* Sets the cid of this conversation.
	*
	* @param cid the cid of this conversation
	*/
	@Override
	public void setCid(long cid) {
		_conversation.setCid(cid);
	}

	/**
	* Returns the conversation ID of this conversation.
	*
	* @return the conversation ID of this conversation
	*/
	@Override
	public java.lang.String getConversationId() {
		return _conversation.getConversationId();
	}

	/**
	* Sets the conversation ID of this conversation.
	*
	* @param conversationId the conversation ID of this conversation
	*/
	@Override
	public void setConversationId(java.lang.String conversationId) {
		_conversation.setConversationId(conversationId);
	}

	/**
	* Returns the conversation type of this conversation.
	*
	* @return the conversation type of this conversation
	*/
	@Override
	public java.lang.String getConversationType() {
		return _conversation.getConversationType();
	}

	/**
	* Sets the conversation type of this conversation.
	*
	* @param conversationType the conversation type of this conversation
	*/
	@Override
	public void setConversationType(java.lang.String conversationType) {
		_conversation.setConversationType(conversationType);
	}

	/**
	* Returns the updated at of this conversation.
	*
	* @return the updated at of this conversation
	*/
	@Override
	public java.util.Date getUpdatedAt() {
		return _conversation.getUpdatedAt();
	}

	/**
	* Sets the updated at of this conversation.
	*
	* @param updatedAt the updated at of this conversation
	*/
	@Override
	public void setUpdatedAt(java.util.Date updatedAt) {
		_conversation.setUpdatedAt(updatedAt);
	}

	@Override
	public boolean isNew() {
		return _conversation.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_conversation.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _conversation.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_conversation.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _conversation.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _conversation.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_conversation.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _conversation.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_conversation.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_conversation.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_conversation.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ConversationWrapper((Conversation)_conversation.clone());
	}

	@Override
	public int compareTo(
		com.marcelmika.lims.persistence.generated.model.Conversation conversation) {
		return _conversation.compareTo(conversation);
	}

	@Override
	public int hashCode() {
		return _conversation.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.persistence.generated.model.Conversation> toCacheModel() {
		return _conversation.toCacheModel();
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Conversation toEscapedModel() {
		return new ConversationWrapper(_conversation.toEscapedModel());
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Conversation toUnescapedModel() {
		return new ConversationWrapper(_conversation.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _conversation.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _conversation.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_conversation.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ConversationWrapper)) {
			return false;
		}

		ConversationWrapper conversationWrapper = (ConversationWrapper)obj;

		if (Validator.equals(_conversation, conversationWrapper._conversation)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Conversation getWrappedConversation() {
		return _conversation;
	}

	@Override
	public Conversation getWrappedModel() {
		return _conversation;
	}

	@Override
	public void resetOriginalValues() {
		_conversation.resetOriginalValues();
	}

	private Conversation _conversation;
}