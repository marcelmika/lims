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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Panel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Panel
 * @generated
 */
public class PanelWrapper implements Panel, ModelWrapper<Panel> {
	public PanelWrapper(Panel panel) {
		_panel = panel;
	}

	@Override
	public Class<?> getModelClass() {
		return Panel.class;
	}

	@Override
	public String getModelClassName() {
		return Panel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("pid", getPid());
		attributes.put("userId", getUserId());
		attributes.put("activePanelId", getActivePanelId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pid = (Long)attributes.get("pid");

		if (pid != null) {
			setPid(pid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String activePanelId = (String)attributes.get("activePanelId");

		if (activePanelId != null) {
			setActivePanelId(activePanelId);
		}
	}

	/**
	* Returns the primary key of this panel.
	*
	* @return the primary key of this panel
	*/
	@Override
	public long getPrimaryKey() {
		return _panel.getPrimaryKey();
	}

	/**
	* Sets the primary key of this panel.
	*
	* @param primaryKey the primary key of this panel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_panel.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the pid of this panel.
	*
	* @return the pid of this panel
	*/
	@Override
	public long getPid() {
		return _panel.getPid();
	}

	/**
	* Sets the pid of this panel.
	*
	* @param pid the pid of this panel
	*/
	@Override
	public void setPid(long pid) {
		_panel.setPid(pid);
	}

	/**
	* Returns the user ID of this panel.
	*
	* @return the user ID of this panel
	*/
	@Override
	public long getUserId() {
		return _panel.getUserId();
	}

	/**
	* Sets the user ID of this panel.
	*
	* @param userId the user ID of this panel
	*/
	@Override
	public void setUserId(long userId) {
		_panel.setUserId(userId);
	}

	/**
	* Returns the user uuid of this panel.
	*
	* @return the user uuid of this panel
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _panel.getUserUuid();
	}

	/**
	* Sets the user uuid of this panel.
	*
	* @param userUuid the user uuid of this panel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_panel.setUserUuid(userUuid);
	}

	/**
	* Returns the active panel ID of this panel.
	*
	* @return the active panel ID of this panel
	*/
	@Override
	public java.lang.String getActivePanelId() {
		return _panel.getActivePanelId();
	}

	/**
	* Sets the active panel ID of this panel.
	*
	* @param activePanelId the active panel ID of this panel
	*/
	@Override
	public void setActivePanelId(java.lang.String activePanelId) {
		_panel.setActivePanelId(activePanelId);
	}

	@Override
	public boolean isNew() {
		return _panel.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_panel.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _panel.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_panel.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _panel.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _panel.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_panel.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _panel.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_panel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_panel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_panel.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PanelWrapper((Panel)_panel.clone());
	}

	@Override
	public int compareTo(
		com.marcelmika.lims.persistence.generated.model.Panel panel) {
		return _panel.compareTo(panel);
	}

	@Override
	public int hashCode() {
		return _panel.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.persistence.generated.model.Panel> toCacheModel() {
		return _panel.toCacheModel();
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Panel toEscapedModel() {
		return new PanelWrapper(_panel.toEscapedModel());
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Panel toUnescapedModel() {
		return new PanelWrapper(_panel.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _panel.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _panel.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_panel.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PanelWrapper)) {
			return false;
		}

		PanelWrapper panelWrapper = (PanelWrapper)obj;

		if (Validator.equals(_panel, panelWrapper._panel)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Panel getWrappedPanel() {
		return _panel;
	}

	@Override
	public Panel getWrappedModel() {
		return _panel;
	}

	@Override
	public void resetOriginalValues() {
		_panel.resetOriginalValues();
	}

	private Panel _panel;
}