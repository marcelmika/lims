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

import com.marcelmika.lims.service.persistence.MessagePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class MessageSoap implements Serializable {
	public static MessageSoap toSoapModel(Message model) {
		MessageSoap soapModel = new MessageSoap();

		soapModel.setMid(model.getMid());
		soapModel.setCid(model.getCid());
		soapModel.setCreatorId(model.getCreatorId());
		soapModel.setCreatedAt(model.getCreatedAt());
		soapModel.setMessageHash(model.getMessageHash());
		soapModel.setText(model.getText());

		return soapModel;
	}

	public static MessageSoap[] toSoapModels(Message[] models) {
		MessageSoap[] soapModels = new MessageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MessageSoap[][] toSoapModels(Message[][] models) {
		MessageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MessageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MessageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MessageSoap[] toSoapModels(List<Message> models) {
		List<MessageSoap> soapModels = new ArrayList<MessageSoap>(models.size());

		for (Message model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MessageSoap[soapModels.size()]);
	}

	public MessageSoap() {
	}

	public MessagePK getPrimaryKey() {
		return new MessagePK(_mid, _cid);
	}

	public void setPrimaryKey(MessagePK pk) {
		setMid(pk.mid);
		setCid(pk.cid);
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
		_creatorId = creatorId;
	}

	public long getCreatedAt() {
		return _createdAt;
	}

	public void setCreatedAt(long createdAt) {
		_createdAt = createdAt;
	}

	public String getMessageHash() {
		return _messageHash;
	}

	public void setMessageHash(String messageHash) {
		_messageHash = messageHash;
	}

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;
	}

	private long _mid;
	private long _cid;
	private long _creatorId;
	private long _createdAt;
	private String _messageHash;
	private String _text;
}