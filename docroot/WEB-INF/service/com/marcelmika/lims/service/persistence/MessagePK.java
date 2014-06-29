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
public class MessagePK implements Comparable<MessagePK>, Serializable {
	public long mid;
	public long cid;

	public MessagePK() {
	}

	public MessagePK(long mid, long cid) {
		this.mid = mid;
		this.cid = cid;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public int compareTo(MessagePK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (mid < pk.mid) {
			value = -1;
		}
		else if (mid > pk.mid) {
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

		if (!(obj instanceof MessagePK)) {
			return false;
		}

		MessagePK pk = (MessagePK)obj;

		if ((mid == pk.mid) && (cid == pk.cid)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (String.valueOf(mid) + String.valueOf(cid)).hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(10);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("mid");
		sb.append(StringPool.EQUAL);
		sb.append(mid);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("cid");
		sb.append(StringPool.EQUAL);
		sb.append(cid);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}