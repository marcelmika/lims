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

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OpenedConversation}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OpenedConversation
 * @generated
 */
public class OpenedConversationWrapper implements OpenedConversation,
	ModelWrapper<OpenedConversation> {
	public OpenedConversationWrapper(OpenedConversation openedConversation) {
		_openedConversation = openedConversation;
	}

	public Class<?> getModelClass() {
		return OpenedConversation.class;
	}

	public String getModelClassName() {
		return OpenedConversation.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("ocid", getOcid());
		attributes.put("userId", getUserId());
		attributes.put("conversationId", getConversationId());

		return attributes;
	}

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

	/**
	* Returns the primary key of this opened conversation.
	*
	* @return the primary key of this opened conversation
	*/
	public long getPrimaryKey() {
		return _openedConversation.getPrimaryKey();
	}

	/**
	* Sets the primary key of this opened conversation.
	*
	* @param primaryKey the primary key of this opened conversation
	*/
	public void setPrimaryKey(long primaryKey) {
		_openedConversation.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the ocid of this opened conversation.
	*
	* @return the ocid of this opened conversation
	*/
	public long getOcid() {
		return _openedConversation.getOcid();
	}

	/**
	* Sets the ocid of this opened conversation.
	*
	* @param ocid the ocid of this opened conversation
	*/
	public void setOcid(long ocid) {
		_openedConversation.setOcid(ocid);
	}

	/**
	* Returns the user ID of this opened conversation.
	*
	* @return the user ID of this opened conversation
	*/
	public long getUserId() {
		return _openedConversation.getUserId();
	}

	/**
	* Sets the user ID of this opened conversation.
	*
	* @param userId the user ID of this opened conversation
	*/
	public void setUserId(long userId) {
		_openedConversation.setUserId(userId);
	}

	/**
	* Returns the user uuid of this opened conversation.
	*
	* @return the user uuid of this opened conversation
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _openedConversation.getUserUuid();
	}

	/**
	* Sets the user uuid of this opened conversation.
	*
	* @param userUuid the user uuid of this opened conversation
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_openedConversation.setUserUuid(userUuid);
	}

	/**
	* Returns the conversation ID of this opened conversation.
	*
	* @return the conversation ID of this opened conversation
	*/
	public java.lang.String getConversationId() {
		return _openedConversation.getConversationId();
	}

	/**
	* Sets the conversation ID of this opened conversation.
	*
	* @param conversationId the conversation ID of this opened conversation
	*/
	public void setConversationId(java.lang.String conversationId) {
		_openedConversation.setConversationId(conversationId);
	}

	public boolean isNew() {
		return _openedConversation.isNew();
	}

	public void setNew(boolean n) {
		_openedConversation.setNew(n);
	}

	public boolean isCachedModel() {
		return _openedConversation.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_openedConversation.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _openedConversation.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _openedConversation.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_openedConversation.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _openedConversation.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_openedConversation.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new OpenedConversationWrapper((OpenedConversation)_openedConversation.clone());
	}

	public int compareTo(
		com.marcelmika.lims.model.OpenedConversation openedConversation) {
		return _openedConversation.compareTo(openedConversation);
	}

	@Override
	public int hashCode() {
		return _openedConversation.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.model.OpenedConversation> toCacheModel() {
		return _openedConversation.toCacheModel();
	}

	public com.marcelmika.lims.model.OpenedConversation toEscapedModel() {
		return new OpenedConversationWrapper(_openedConversation.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _openedConversation.toString();
	}

	public java.lang.String toXmlString() {
		return _openedConversation.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_openedConversation.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public OpenedConversation getWrappedOpenedConversation() {
		return _openedConversation;
	}

	public OpenedConversation getWrappedModel() {
		return _openedConversation;
	}

	public void resetOriginalValues() {
		_openedConversation.resetOriginalValues();
	}

	private OpenedConversation _openedConversation;
}