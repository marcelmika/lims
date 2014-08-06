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

import com.marcelmika.lims.persistence.generated.model.Conversation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Conversation in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Conversation
 * @generated
 */
public class ConversationCacheModel implements CacheModel<Conversation>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{cid=");
		sb.append(cid);
		sb.append(", conversationId=");
		sb.append(conversationId);
		sb.append(", conversationType=");
		sb.append(conversationType);
		sb.append(", updatedAt=");
		sb.append(updatedAt);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Conversation toEntityModel() {
		ConversationImpl conversationImpl = new ConversationImpl();

		conversationImpl.setCid(cid);

		if (conversationId == null) {
			conversationImpl.setConversationId(StringPool.BLANK);
		}
		else {
			conversationImpl.setConversationId(conversationId);
		}

		if (conversationType == null) {
			conversationImpl.setConversationType(StringPool.BLANK);
		}
		else {
			conversationImpl.setConversationType(conversationType);
		}

		if (updatedAt == Long.MIN_VALUE) {
			conversationImpl.setUpdatedAt(null);
		}
		else {
			conversationImpl.setUpdatedAt(new Date(updatedAt));
		}

		conversationImpl.resetOriginalValues();

		return conversationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		cid = objectInput.readLong();
		conversationId = objectInput.readUTF();
		conversationType = objectInput.readUTF();
		updatedAt = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(cid);

		if (conversationId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(conversationId);
		}

		if (conversationType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(conversationType);
		}

		objectOutput.writeLong(updatedAt);
	}

	public long cid;
	public String conversationId;
	public String conversationType;
	public long updatedAt;
}