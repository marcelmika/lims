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

package com.marcelmika.lims.persistence.generated.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.marcelmika.lims.persistence.generated.model.Settings;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Settings in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Settings
 * @generated
 */
public class SettingsCacheModel implements CacheModel<Settings>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{sid=");
		sb.append(sid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", presence=");
		sb.append(presence);
		sb.append(", mute=");
		sb.append(mute);
		sb.append(", chatEnabled=");
		sb.append(chatEnabled);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Settings toEntityModel() {
		SettingsImpl settingsImpl = new SettingsImpl();

		settingsImpl.setSid(sid);
		settingsImpl.setUserId(userId);

		if (presence == null) {
			settingsImpl.setPresence(StringPool.BLANK);
		}
		else {
			settingsImpl.setPresence(presence);
		}

		settingsImpl.setMute(mute);
		settingsImpl.setChatEnabled(chatEnabled);

		settingsImpl.resetOriginalValues();

		return settingsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		sid = objectInput.readLong();
		userId = objectInput.readLong();
		presence = objectInput.readUTF();
		mute = objectInput.readBoolean();
		chatEnabled = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(sid);
		objectOutput.writeLong(userId);

		if (presence == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(presence);
		}

		objectOutput.writeBoolean(mute);
		objectOutput.writeBoolean(chatEnabled);
	}

	public long sid;
	public long userId;
	public String presence;
	public boolean mute;
	public boolean chatEnabled;
}