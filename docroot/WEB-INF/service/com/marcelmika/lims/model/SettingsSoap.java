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
public class SettingsSoap implements Serializable {
	public static SettingsSoap toSoapModel(Settings model) {
		SettingsSoap soapModel = new SettingsSoap();

		soapModel.setSid(model.getSid());
		soapModel.setUserId(model.getUserId());
		soapModel.setStatus(model.getStatus());
		soapModel.setActiveRoomType(model.getActiveRoomType());
		soapModel.setActivePanelId(model.getActivePanelId());
		soapModel.setMute(model.getMute());
		soapModel.setChatEnabled(model.getChatEnabled());

		return soapModel;
	}

	public static SettingsSoap[] toSoapModels(Settings[] models) {
		SettingsSoap[] soapModels = new SettingsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SettingsSoap[][] toSoapModels(Settings[][] models) {
		SettingsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SettingsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SettingsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SettingsSoap[] toSoapModels(List<Settings> models) {
		List<SettingsSoap> soapModels = new ArrayList<SettingsSoap>(models.size());

		for (Settings model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SettingsSoap[soapModels.size()]);
	}

	public SettingsSoap() {
	}

	public long getPrimaryKey() {
		return _sid;
	}

	public void setPrimaryKey(long pk) {
		setSid(pk);
	}

	public long getSid() {
		return _sid;
	}

	public void setSid(long sid) {
		_sid = sid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public String getActiveRoomType() {
		return _activeRoomType;
	}

	public void setActiveRoomType(String activeRoomType) {
		_activeRoomType = activeRoomType;
	}

	public String getActivePanelId() {
		return _activePanelId;
	}

	public void setActivePanelId(String activePanelId) {
		_activePanelId = activePanelId;
	}

	public boolean getMute() {
		return _mute;
	}

	public boolean isMute() {
		return _mute;
	}

	public void setMute(boolean mute) {
		_mute = mute;
	}

	public boolean getChatEnabled() {
		return _chatEnabled;
	}

	public boolean isChatEnabled() {
		return _chatEnabled;
	}

	public void setChatEnabled(boolean chatEnabled) {
		_chatEnabled = chatEnabled;
	}

	private long _sid;
	private long _userId;
	private String _status;
	private String _activeRoomType;
	private String _activePanelId;
	private boolean _mute;
	private boolean _chatEnabled;
}