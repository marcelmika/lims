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
public class OpenedConversationSoap implements Serializable {
	public static OpenedConversationSoap toSoapModel(OpenedConversation model) {
		OpenedConversationSoap soapModel = new OpenedConversationSoap();

		soapModel.setOcid(model.getOcid());
		soapModel.setUserId(model.getUserId());
		soapModel.setConversationId(model.getConversationId());

		return soapModel;
	}

	public static OpenedConversationSoap[] toSoapModels(
		OpenedConversation[] models) {
		OpenedConversationSoap[] soapModels = new OpenedConversationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OpenedConversationSoap[][] toSoapModels(
		OpenedConversation[][] models) {
		OpenedConversationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OpenedConversationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OpenedConversationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OpenedConversationSoap[] toSoapModels(
		List<OpenedConversation> models) {
		List<OpenedConversationSoap> soapModels = new ArrayList<OpenedConversationSoap>(models.size());

		for (OpenedConversation model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OpenedConversationSoap[soapModels.size()]);
	}

	public OpenedConversationSoap() {
	}

	public long getPrimaryKey() {
		return _ocid;
	}

	public void setPrimaryKey(long pk) {
		setOcid(pk);
	}

	public long getOcid() {
		return _ocid;
	}

	public void setOcid(long ocid) {
		_ocid = ocid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getConversationId() {
		return _conversationId;
	}

	public void setConversationId(String conversationId) {
		_conversationId = conversationId;
	}

	private long _ocid;
	private long _userId;
	private String _conversationId;
}