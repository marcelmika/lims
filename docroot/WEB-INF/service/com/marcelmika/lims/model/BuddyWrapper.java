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
 * This class is a wrapper for {@link Buddy}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Buddy
 * @generated
 */
public class BuddyWrapper implements Buddy, ModelWrapper<Buddy> {
	public BuddyWrapper(Buddy buddy) {
		_buddy = buddy;
	}

	public Class<?> getModelClass() {
		return Buddy.class;
	}

	public String getModelClassName() {
		return Buddy.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("bid", getBid());
		attributes.put("userId", getUserId());
		attributes.put("companyId", getCompanyId());
		attributes.put("portraitId", getPortraitId());
		attributes.put("fullName", getFullName());
		attributes.put("screenName", getScreenName());
		attributes.put("statusMessage", getStatusMessage());
		attributes.put("isTyping", getIsTyping());
		attributes.put("awake", getAwake());
		attributes.put("status", getStatus());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long bid = (Long)attributes.get("bid");

		if (bid != null) {
			setBid(bid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long portraitId = (Long)attributes.get("portraitId");

		if (portraitId != null) {
			setPortraitId(portraitId);
		}

		String fullName = (String)attributes.get("fullName");

		if (fullName != null) {
			setFullName(fullName);
		}

		String screenName = (String)attributes.get("screenName");

		if (screenName != null) {
			setScreenName(screenName);
		}

		String statusMessage = (String)attributes.get("statusMessage");

		if (statusMessage != null) {
			setStatusMessage(statusMessage);
		}

		Boolean isTyping = (Boolean)attributes.get("isTyping");

		if (isTyping != null) {
			setIsTyping(isTyping);
		}

		Boolean awake = (Boolean)attributes.get("awake");

		if (awake != null) {
			setAwake(awake);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	* Returns the primary key of this buddy.
	*
	* @return the primary key of this buddy
	*/
	public long getPrimaryKey() {
		return _buddy.getPrimaryKey();
	}

	/**
	* Sets the primary key of this buddy.
	*
	* @param primaryKey the primary key of this buddy
	*/
	public void setPrimaryKey(long primaryKey) {
		_buddy.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the bid of this buddy.
	*
	* @return the bid of this buddy
	*/
	public long getBid() {
		return _buddy.getBid();
	}

	/**
	* Sets the bid of this buddy.
	*
	* @param bid the bid of this buddy
	*/
	public void setBid(long bid) {
		_buddy.setBid(bid);
	}

	/**
	* Returns the user ID of this buddy.
	*
	* @return the user ID of this buddy
	*/
	public long getUserId() {
		return _buddy.getUserId();
	}

	/**
	* Sets the user ID of this buddy.
	*
	* @param userId the user ID of this buddy
	*/
	public void setUserId(long userId) {
		_buddy.setUserId(userId);
	}

	/**
	* Returns the user uuid of this buddy.
	*
	* @return the user uuid of this buddy
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _buddy.getUserUuid();
	}

	/**
	* Sets the user uuid of this buddy.
	*
	* @param userUuid the user uuid of this buddy
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_buddy.setUserUuid(userUuid);
	}

	/**
	* Returns the company ID of this buddy.
	*
	* @return the company ID of this buddy
	*/
	public long getCompanyId() {
		return _buddy.getCompanyId();
	}

	/**
	* Sets the company ID of this buddy.
	*
	* @param companyId the company ID of this buddy
	*/
	public void setCompanyId(long companyId) {
		_buddy.setCompanyId(companyId);
	}

	/**
	* Returns the portrait ID of this buddy.
	*
	* @return the portrait ID of this buddy
	*/
	public long getPortraitId() {
		return _buddy.getPortraitId();
	}

	/**
	* Sets the portrait ID of this buddy.
	*
	* @param portraitId the portrait ID of this buddy
	*/
	public void setPortraitId(long portraitId) {
		_buddy.setPortraitId(portraitId);
	}

	/**
	* Returns the full name of this buddy.
	*
	* @return the full name of this buddy
	*/
	public java.lang.String getFullName() {
		return _buddy.getFullName();
	}

	/**
	* Sets the full name of this buddy.
	*
	* @param fullName the full name of this buddy
	*/
	public void setFullName(java.lang.String fullName) {
		_buddy.setFullName(fullName);
	}

	/**
	* Returns the screen name of this buddy.
	*
	* @return the screen name of this buddy
	*/
	public java.lang.String getScreenName() {
		return _buddy.getScreenName();
	}

	/**
	* Sets the screen name of this buddy.
	*
	* @param screenName the screen name of this buddy
	*/
	public void setScreenName(java.lang.String screenName) {
		_buddy.setScreenName(screenName);
	}

	/**
	* Returns the status message of this buddy.
	*
	* @return the status message of this buddy
	*/
	public java.lang.String getStatusMessage() {
		return _buddy.getStatusMessage();
	}

	/**
	* Sets the status message of this buddy.
	*
	* @param statusMessage the status message of this buddy
	*/
	public void setStatusMessage(java.lang.String statusMessage) {
		_buddy.setStatusMessage(statusMessage);
	}

	/**
	* Returns the is typing of this buddy.
	*
	* @return the is typing of this buddy
	*/
	public boolean getIsTyping() {
		return _buddy.getIsTyping();
	}

	/**
	* Returns <code>true</code> if this buddy is is typing.
	*
	* @return <code>true</code> if this buddy is is typing; <code>false</code> otherwise
	*/
	public boolean isIsTyping() {
		return _buddy.isIsTyping();
	}

	/**
	* Sets whether this buddy is is typing.
	*
	* @param isTyping the is typing of this buddy
	*/
	public void setIsTyping(boolean isTyping) {
		_buddy.setIsTyping(isTyping);
	}

	/**
	* Returns the awake of this buddy.
	*
	* @return the awake of this buddy
	*/
	public boolean getAwake() {
		return _buddy.getAwake();
	}

	/**
	* Returns <code>true</code> if this buddy is awake.
	*
	* @return <code>true</code> if this buddy is awake; <code>false</code> otherwise
	*/
	public boolean isAwake() {
		return _buddy.isAwake();
	}

	/**
	* Sets whether this buddy is awake.
	*
	* @param awake the awake of this buddy
	*/
	public void setAwake(boolean awake) {
		_buddy.setAwake(awake);
	}

	/**
	* Returns the status of this buddy.
	*
	* @return the status of this buddy
	*/
	public java.lang.String getStatus() {
		return _buddy.getStatus();
	}

	/**
	* Sets the status of this buddy.
	*
	* @param status the status of this buddy
	*/
	public void setStatus(java.lang.String status) {
		_buddy.setStatus(status);
	}

	public boolean isNew() {
		return _buddy.isNew();
	}

	public void setNew(boolean n) {
		_buddy.setNew(n);
	}

	public boolean isCachedModel() {
		return _buddy.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_buddy.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _buddy.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _buddy.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_buddy.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _buddy.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_buddy.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BuddyWrapper((Buddy)_buddy.clone());
	}

	public int compareTo(com.marcelmika.lims.model.Buddy buddy) {
		return _buddy.compareTo(buddy);
	}

	@Override
	public int hashCode() {
		return _buddy.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.model.Buddy> toCacheModel() {
		return _buddy.toCacheModel();
	}

	public com.marcelmika.lims.model.Buddy toEscapedModel() {
		return new BuddyWrapper(_buddy.toEscapedModel());
	}

	public com.marcelmika.lims.model.Buddy toUnescapedModel() {
		return new BuddyWrapper(_buddy.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _buddy.toString();
	}

	public java.lang.String toXmlString() {
		return _buddy.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_buddy.persist();
	}

	public com.liferay.portal.kernel.json.JSONObject toJSON() {
		return _buddy.toJSON();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BuddyWrapper)) {
			return false;
		}

		BuddyWrapper buddyWrapper = (BuddyWrapper)obj;

		if (Validator.equals(_buddy, buddyWrapper._buddy)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Buddy getWrappedBuddy() {
		return _buddy;
	}

	public Buddy getWrappedModel() {
		return _buddy;
	}

	public void resetOriginalValues() {
		_buddy.resetOriginalValues();
	}

	private Buddy _buddy;
}