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

package com.marcelmika.lims.service.persistence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class ParticipantPK implements Comparable<ParticipantPK>, Serializable {
	public long pid;
	public long cid;

	public ParticipantPK() {
	}

	public ParticipantPK(long pid, long cid) {
		this.pid = pid;
		this.cid = cid;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public int compareTo(ParticipantPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (pid < pk.pid) {
			value = -1;
		}
		else if (pid > pk.pid) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (cid < pk.cid) {
			value = -1;
		}
		else if (cid > pk.cid) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ParticipantPK)) {
			return false;
		}

		ParticipantPK pk = (ParticipantPK)obj;

		if ((pid == pk.pid) && (cid == pk.cid)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (String.valueOf(pid) + String.valueOf(cid)).hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(10);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("pid");
		sb.append(StringPool.EQUAL);
		sb.append(pid);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("cid");
		sb.append(StringPool.EQUAL);
		sb.append(cid);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}