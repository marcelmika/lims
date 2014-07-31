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

package com.marcelmika.lims.persistence.generated.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ConversationSoap implements Serializable {
	public static ConversationSoap toSoapModel(Conversation model) {
		ConversationSoap soapModel = new ConversationSoap();

		soapModel.setCid(model.getCid());
		soapModel.setConversationId(model.getConversationId());
		soapModel.setConversationType(model.getConversationType());
		soapModel.setUpdatedAt(model.getUpdatedAt());

		return soapModel;
	}

	public static ConversationSoap[] toSoapModels(Conversation[] models) {
		ConversationSoap[] soapModels = new ConversationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ConversationSoap[][] toSoapModels(Conversation[][] models) {
		ConversationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ConversationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ConversationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ConversationSoap[] toSoapModels(List<Conversation> models) {
		List<ConversationSoap> soapModels = new ArrayList<ConversationSoap>(models.size());

		for (Conversation model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ConversationSoap[soapModels.size()]);
	}

	public ConversationSoap() {
	}

	public long getPrimaryKey() {
		return _cid;
	}

	public void setPrimaryKey(long pk) {
		setCid(pk);
	}

	public long getCid() {
		return _cid;
	}

	public void setCid(long cid) {
		_cid = cid;
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

	public Date getUpdatedAt() {
		return _updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		_updatedAt = updatedAt;
	}

	private long _cid;
	private String _conversationId;
	private String _conversationType;
	private Date _updatedAt;
}