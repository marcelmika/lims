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
 * This class is a wrapper for {@link Conversation}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Conversation
 * @generated
 */
public class ConversationWrapper implements Conversation,
	ModelWrapper<Conversation> {
	public ConversationWrapper(Conversation conversation) {
		_conversation = conversation;
	}

	public Class<?> getModelClass() {
		return Conversation.class;
	}

	public String getModelClassName() {
		return Conversation.class.getName();
	}

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

	/**
	* Returns the primary key of this conversation.
	*
	* @return the primary key of this conversation
	*/
	public long getPrimaryKey() {
		return _conversation.getPrimaryKey();
	}

	/**
	* Sets the primary key of this conversation.
	*
	* @param primaryKey the primary key of this conversation
	*/
	public void setPrimaryKey(long primaryKey) {
		_conversation.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the cid of this conversation.
	*
	* @return the cid of this conversation
	*/
	public long getCid() {
		return _conversation.getCid();
	}

	/**
	* Sets the cid of this conversation.
	*
	* @param cid the cid of this conversation
	*/
	public void setCid(long cid) {
		_conversation.setCid(cid);
	}

	/**
	* Returns the user ID of this conversation.
	*
	* @return the user ID of this conversation
	*/
	public long getUserId() {
		return _conversation.getUserId();
	}

	/**
	* Sets the user ID of this conversation.
	*
	* @param userId the user ID of this conversation
	*/
	public void setUserId(long userId) {
		_conversation.setUserId(userId);
	}

	/**
	* Returns the user uuid of this conversation.
	*
	* @return the user uuid of this conversation
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _conversation.getUserUuid();
	}

	/**
	* Sets the user uuid of this conversation.
	*
	* @param userUuid the user uuid of this conversation
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_conversation.setUserUuid(userUuid);
	}

	/**
	* Returns the conversation ID of this conversation.
	*
	* @return the conversation ID of this conversation
	*/
	public java.lang.String getConversationId() {
		return _conversation.getConversationId();
	}

	/**
	* Sets the conversation ID of this conversation.
	*
	* @param conversationId the conversation ID of this conversation
	*/
	public void setConversationId(java.lang.String conversationId) {
		_conversation.setConversationId(conversationId);
	}

	/**
	* Returns the conversation type of this conversation.
	*
	* @return the conversation type of this conversation
	*/
	public java.lang.String getConversationType() {
		return _conversation.getConversationType();
	}

	/**
	* Sets the conversation type of this conversation.
	*
	* @param conversationType the conversation type of this conversation
	*/
	public void setConversationType(java.lang.String conversationType) {
		_conversation.setConversationType(conversationType);
	}

	/**
	* Returns the conversation visibility of this conversation.
	*
	* @return the conversation visibility of this conversation
	*/
	public java.lang.String getConversationVisibility() {
		return _conversation.getConversationVisibility();
	}

	/**
	* Sets the conversation visibility of this conversation.
	*
	* @param conversationVisibility the conversation visibility of this conversation
	*/
	public void setConversationVisibility(
		java.lang.String conversationVisibility) {
		_conversation.setConversationVisibility(conversationVisibility);
	}

	/**
	* Returns the conversation name of this conversation.
	*
	* @return the conversation name of this conversation
	*/
	public java.lang.String getConversationName() {
		return _conversation.getConversationName();
	}

	/**
	* Sets the conversation name of this conversation.
	*
	* @param conversationName the conversation name of this conversation
	*/
	public void setConversationName(java.lang.String conversationName) {
		_conversation.setConversationName(conversationName);
	}

	/**
	* Returns the unread messages of this conversation.
	*
	* @return the unread messages of this conversation
	*/
	public int getUnreadMessages() {
		return _conversation.getUnreadMessages();
	}

	/**
	* Sets the unread messages of this conversation.
	*
	* @param unreadMessages the unread messages of this conversation
	*/
	public void setUnreadMessages(int unreadMessages) {
		_conversation.setUnreadMessages(unreadMessages);
	}

	public boolean isNew() {
		return _conversation.isNew();
	}

	public void setNew(boolean n) {
		_conversation.setNew(n);
	}

	public boolean isCachedModel() {
		return _conversation.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_conversation.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _conversation.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _conversation.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_conversation.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _conversation.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_conversation.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ConversationWrapper((Conversation)_conversation.clone());
	}

	public int compareTo(com.marcelmika.lims.model.Conversation conversation) {
		return _conversation.compareTo(conversation);
	}

	@Override
	public int hashCode() {
		return _conversation.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.model.Conversation> toCacheModel() {
		return _conversation.toCacheModel();
	}

	public com.marcelmika.lims.model.Conversation toEscapedModel() {
		return new ConversationWrapper(_conversation.toEscapedModel());
	}

	public com.marcelmika.lims.model.Conversation toUnescapedModel() {
		return new ConversationWrapper(_conversation.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _conversation.toString();
	}

	public java.lang.String toXmlString() {
		return _conversation.toXmlString();
	}

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
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Conversation getWrappedConversation() {
		return _conversation;
	}

	public Conversation getWrappedModel() {
		return _conversation;
	}

	public void resetOriginalValues() {
		_conversation.resetOriginalValues();
	}

	private Conversation _conversation;
}