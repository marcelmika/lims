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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ParticipantSoap implements Serializable {
	public static ParticipantSoap toSoapModel(Participant model) {
		ParticipantSoap soapModel = new ParticipantSoap();

		soapModel.setPid(model.getPid());
		soapModel.setCid(model.getCid());
		soapModel.setParticipantId(model.getParticipantId());
		soapModel.setUnreadMessagesCount(model.getUnreadMessagesCount());
		soapModel.setIsOpened(model.getIsOpened());
		soapModel.setOpenedAt(model.getOpenedAt());

		return soapModel;
	}

	public static ParticipantSoap[] toSoapModels(Participant[] models) {
		ParticipantSoap[] soapModels = new ParticipantSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ParticipantSoap[][] toSoapModels(Participant[][] models) {
		ParticipantSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ParticipantSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ParticipantSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ParticipantSoap[] toSoapModels(List<Participant> models) {
		List<ParticipantSoap> soapModels = new ArrayList<ParticipantSoap>(models.size());

		for (Participant model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ParticipantSoap[soapModels.size()]);
	}

	public ParticipantSoap() {
	}

	public long getPrimaryKey() {
		return _pid;
	}

	public void setPrimaryKey(long pk) {
		setPid(pk);
	}

	public long getPid() {
		return _pid;
	}

	public void setPid(long pid) {
		_pid = pid;
	}

	public long getCid() {
		return _cid;
	}

	public void setCid(long cid) {
		_cid = cid;
	}

	public long getParticipantId() {
		return _participantId;
	}

	public void setParticipantId(long participantId) {
		_participantId = participantId;
	}

	public int getUnreadMessagesCount() {
		return _unreadMessagesCount;
	}

	public void setUnreadMessagesCount(int unreadMessagesCount) {
		_unreadMessagesCount = unreadMessagesCount;
	}

	public boolean getIsOpened() {
		return _isOpened;
	}

	public boolean isIsOpened() {
		return _isOpened;
	}

	public void setIsOpened(boolean isOpened) {
		_isOpened = isOpened;
	}

	public long getOpenedAt() {
		return _openedAt;
	}

	public void setOpenedAt(long openedAt) {
		_openedAt = openedAt;
	}

	private long _pid;
	private long _cid;
	private long _participantId;
	private int _unreadMessagesCount;
	private boolean _isOpened;
	private long _openedAt;
}