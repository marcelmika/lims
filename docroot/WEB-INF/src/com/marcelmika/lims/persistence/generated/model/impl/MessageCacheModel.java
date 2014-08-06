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

import com.marcelmika.lims.persistence.generated.model.Message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Message in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Message
 * @generated
 */
public class MessageCacheModel implements CacheModel<Message>, Externalizable {
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

	@Override
	public Message toEntityModel() {
		MessageImpl messageImpl = new MessageImpl();

		messageImpl.setMid(mid);
		messageImpl.setCid(cid);
		messageImpl.setCreatorId(creatorId);

		if (createdAt == Long.MIN_VALUE) {
			messageImpl.setCreatedAt(null);
		}
		else {
			messageImpl.setCreatedAt(new Date(createdAt));
		}

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

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mid = objectInput.readLong();
		cid = objectInput.readLong();
		creatorId = objectInput.readLong();
		createdAt = objectInput.readLong();
		messageHash = objectInput.readUTF();
		body = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mid);
		objectOutput.writeLong(cid);
		objectOutput.writeLong(creatorId);
		objectOutput.writeLong(createdAt);

		if (messageHash == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(messageHash);
		}

		if (body == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(body);
		}
	}

	public long mid;
	public long cid;
	public long creatorId;
	public long createdAt;
	public String messageHash;
	public String body;
}