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
import com.liferay.portal.model.CacheModel;

import com.marcelmika.lims.persistence.generated.model.Participant;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Participant in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Participant
 * @generated
 */
public class ParticipantCacheModel implements CacheModel<Participant>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{pid=");
		sb.append(pid);
		sb.append(", cid=");
		sb.append(cid);
		sb.append(", participantId=");
		sb.append(participantId);
		sb.append(", unreadMessagesCount=");
		sb.append(unreadMessagesCount);
		sb.append(", isOpened=");
		sb.append(isOpened);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Participant toEntityModel() {
		ParticipantImpl participantImpl = new ParticipantImpl();

		participantImpl.setPid(pid);
		participantImpl.setCid(cid);
		participantImpl.setParticipantId(participantId);
		participantImpl.setUnreadMessagesCount(unreadMessagesCount);
		participantImpl.setIsOpened(isOpened);

		participantImpl.resetOriginalValues();

		return participantImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		pid = objectInput.readLong();
		cid = objectInput.readLong();
		participantId = objectInput.readLong();
		unreadMessagesCount = objectInput.readInt();
		isOpened = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(pid);
		objectOutput.writeLong(cid);
		objectOutput.writeLong(participantId);
		objectOutput.writeInt(unreadMessagesCount);
		objectOutput.writeBoolean(isOpened);
	}

	public long pid;
	public long cid;
	public long participantId;
	public int unreadMessagesCount;
	public boolean isOpened;
}