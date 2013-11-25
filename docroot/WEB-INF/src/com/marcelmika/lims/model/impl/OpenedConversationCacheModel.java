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

import com.marcelmika.lims.model.OpenedConversation;

import java.io.Serializable;

/**
 * The cache model class for representing OpenedConversation in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OpenedConversation
 * @generated
 */
public class OpenedConversationCacheModel implements CacheModel<OpenedConversation>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{ocid=");
		sb.append(ocid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", conversationId=");
		sb.append(conversationId);
		sb.append("}");

		return sb.toString();
	}

	public OpenedConversation toEntityModel() {
		OpenedConversationImpl openedConversationImpl = new OpenedConversationImpl();

		openedConversationImpl.setOcid(ocid);
		openedConversationImpl.setUserId(userId);

		if (conversationId == null) {
			openedConversationImpl.setConversationId(StringPool.BLANK);
		}
		else {
			openedConversationImpl.setConversationId(conversationId);
		}

		openedConversationImpl.resetOriginalValues();

		return openedConversationImpl;
	}

	public long ocid;
	public long userId;
	public String conversationId;
}