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

import com.marcelmika.lims.model.Panel;

import java.io.Serializable;

/**
 * The cache model class for representing Panel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Panel
 * @generated
 */
public class PanelCacheModel implements CacheModel<Panel>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{pid=");
		sb.append(pid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", activePanelId=");
		sb.append(activePanelId);
		sb.append("}");

		return sb.toString();
	}

	public Panel toEntityModel() {
		PanelImpl panelImpl = new PanelImpl();

		panelImpl.setPid(pid);
		panelImpl.setUserId(userId);

		if (activePanelId == null) {
			panelImpl.setActivePanelId(StringPool.BLANK);
		}
		else {
			panelImpl.setActivePanelId(activePanelId);
		}

		panelImpl.resetOriginalValues();

		return panelImpl;
	}

	public long pid;
	public long userId;
	public String activePanelId;
}