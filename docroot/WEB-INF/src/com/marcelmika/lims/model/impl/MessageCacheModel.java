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

import com.marcelmika.lims.model.Message;

import java.io.Serializable;

/**
 * The cache model class for representing Message in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Message
 * @generated
 */
public class MessageCacheModel implements CacheModel<Message>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mid=");
		sb.append(mid);
		sb.append(", cid=");
		sb.append(cid);
		sb.append(", creatorId=");
		sb.append(creatorId);
		sb.append(", createdAt=");
		sb.append(createdAt);
		sb.append(", messageHash=");
		sb.append(messageHash);
		sb.append(", body=");
		sb.append(body);
		sb.append("}");

		return sb.toString();
	}

	public Message toEntityModel() {
		MessageImpl messageImpl = new MessageImpl();

		messageImpl.setMid(mid);
		messageImpl.setCid(cid);
		messageImpl.setCreatorId(creatorId);
		messageImpl.setCreatedAt(createdAt);

		if (messageHash == null) {
			messageImpl.setMessageHash(StringPool.BLANK);
		}
		else {
			messageImpl.setMessageHash(messageHash);
		}

		if (body == null) {
			messageImpl.setBody(StringPool.BLANK);
		}
		else {
			messageImpl.setBody(body);
		}

		messageImpl.resetOriginalValues();

		return messageImpl;
	}

	public long mid;
	public long cid;
	public long creatorId;
	public long createdAt;
	public String messageHash;
	public String body;
}