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

import com.marcelmika.lims.model.Buddy;

import java.io.Serializable;

/**
 * The cache model class for representing Buddy in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Buddy
 * @generated
 */
public class BuddyCacheModel implements CacheModel<Buddy>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{bid=");
		sb.append(bid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", portraitId=");
		sb.append(portraitId);
		sb.append(", fullName=");
		sb.append(fullName);
		sb.append(", screenName=");
		sb.append(screenName);
		sb.append(", statusMessage=");
		sb.append(statusMessage);
		sb.append(", isTyping=");
		sb.append(isTyping);
		sb.append(", awake=");
		sb.append(awake);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public Buddy toEntityModel() {
		BuddyImpl buddyImpl = new BuddyImpl();

		buddyImpl.setBid(bid);
		buddyImpl.setUserId(userId);
		buddyImpl.setCompanyId(companyId);
		buddyImpl.setPortraitId(portraitId);

		if (fullName == null) {
			buddyImpl.setFullName(StringPool.BLANK);
		}
		else {
			buddyImpl.setFullName(fullName);
		}

		if (screenName == null) {
			buddyImpl.setScreenName(StringPool.BLANK);
		}
		else {
			buddyImpl.setScreenName(screenName);
		}

		if (statusMessage == null) {
			buddyImpl.setStatusMessage(StringPool.BLANK);
		}
		else {
			buddyImpl.setStatusMessage(statusMessage);
		}

		buddyImpl.setIsTyping(isTyping);
		buddyImpl.setAwake(awake);

		if (status == null) {
			buddyImpl.setStatus(StringPool.BLANK);
		}
		else {
			buddyImpl.setStatus(status);
		}

		buddyImpl.resetOriginalValues();

		return buddyImpl;
	}

	public long bid;
	public long userId;
	public long companyId;
	public long portraitId;
	public String fullName;
	public String screenName;
	public String statusMessage;
	public boolean isTyping;
	public boolean awake;
	public String status;
}