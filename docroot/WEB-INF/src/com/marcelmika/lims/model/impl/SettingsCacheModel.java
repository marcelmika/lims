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

package com.marcelmika.lims.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.marcelmika.lims.model.Settings;

import java.io.Serializable;

/**
 * The cache model class for representing Settings in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Settings
 * @generated
 */
public class SettingsCacheModel implements CacheModel<Settings>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{sid=");
		sb.append(sid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", activeRoomType=");
		sb.append(activeRoomType);
		sb.append(", activePanelId=");
		sb.append(activePanelId);
		sb.append(", mute=");
		sb.append(mute);
		sb.append(", chatEnabled=");
		sb.append(chatEnabled);
		sb.append("}");

		return sb.toString();
	}

	public Settings toEntityModel() {
		SettingsImpl settingsImpl = new SettingsImpl();

		settingsImpl.setSid(sid);
		settingsImpl.setUserId(userId);

		if (status == null) {
			settingsImpl.setStatus(StringPool.BLANK);
		}
		else {
			settingsImpl.setStatus(status);
		}

		if (activeRoomType == null) {
			settingsImpl.setActiveRoomType(StringPool.BLANK);
		}
		else {
			settingsImpl.setActiveRoomType(activeRoomType);
		}

		if (activePanelId == null) {
			settingsImpl.setActivePanelId(StringPool.BLANK);
		}
		else {
			settingsImpl.setActivePanelId(activePanelId);
		}

		settingsImpl.setMute(mute);
		settingsImpl.setChatEnabled(chatEnabled);

		settingsImpl.resetOriginalValues();

		return settingsImpl;
	}

	public long sid;
	public long userId;
	public String status;
	public String activeRoomType;
	public String activePanelId;
	public boolean mute;
	public boolean chatEnabled;
}