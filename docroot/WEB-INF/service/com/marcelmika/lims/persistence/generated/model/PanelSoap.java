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
public class PanelSoap implements Serializable {
	public static PanelSoap toSoapModel(Panel model) {
		PanelSoap soapModel = new PanelSoap();

		soapModel.setPid(model.getPid());
		soapModel.setUserId(model.getUserId());
		soapModel.setActivePanelId(model.getActivePanelId());

		return soapModel;
	}

	public static PanelSoap[] toSoapModels(Panel[] models) {
		PanelSoap[] soapModels = new PanelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PanelSoap[][] toSoapModels(Panel[][] models) {
		PanelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PanelSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PanelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PanelSoap[] toSoapModels(List<Panel> models) {
		List<PanelSoap> soapModels = new ArrayList<PanelSoap>(models.size());

		for (Panel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PanelSoap[soapModels.size()]);
	}

	public PanelSoap() {
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getActivePanelId() {
		return _activePanelId;
	}

	public void setActivePanelId(String activePanelId) {
		_activePanelId = activePanelId;
	}

	private long _pid;
	private long _userId;
	private String _activePanelId;
}