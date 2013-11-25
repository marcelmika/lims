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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class BuddySoap implements Serializable {
	public static BuddySoap toSoapModel(Buddy model) {
		BuddySoap soapModel = new BuddySoap();

		soapModel.setBid(model.getBid());
		soapModel.setUserId(model.getUserId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setPortraitId(model.getPortraitId());
		soapModel.setFullName(model.getFullName());
		soapModel.setScreenName(model.getScreenName());
		soapModel.setStatusMessage(model.getStatusMessage());
		soapModel.setIsTyping(model.getIsTyping());
		soapModel.setAwake(model.getAwake());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static BuddySoap[] toSoapModels(Buddy[] models) {
		BuddySoap[] soapModels = new BuddySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BuddySoap[][] toSoapModels(Buddy[][] models) {
		BuddySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new BuddySoap[models.length][models[0].length];
		}
		else {
			soapModels = new BuddySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BuddySoap[] toSoapModels(List<Buddy> models) {
		List<BuddySoap> soapModels = new ArrayList<BuddySoap>(models.size());

		for (Buddy model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BuddySoap[soapModels.size()]);
	}

	public BuddySoap() {
	}

	public long getPrimaryKey() {
		return _bid;
	}

	public void setPrimaryKey(long pk) {
		setBid(pk);
	}

	public long getBid() {
		return _bid;
	}

	public void setBid(long bid) {
		_bid = bid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;
	}

	public String getFullName() {
		return _fullName;
	}

	public void setFullName(String fullName) {
		_fullName = fullName;
	}

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public String getStatusMessage() {
		return _statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;
	}

	public boolean getIsTyping() {
		return _isTyping;
	}

	public boolean isIsTyping() {
		return _isTyping;
	}

	public void setIsTyping(boolean isTyping) {
		_isTyping = isTyping;
	}

	public boolean getAwake() {
		return _awake;
	}

	public boolean isAwake() {
		return _awake;
	}

	public void setAwake(boolean awake) {
		_awake = awake;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	private long _bid;
	private long _userId;
	private long _companyId;
	private long _portraitId;
	private String _fullName;
	private String _screenName;
	private String _statusMessage;
	private boolean _isTyping;
	private boolean _awake;
	private String _status;
}