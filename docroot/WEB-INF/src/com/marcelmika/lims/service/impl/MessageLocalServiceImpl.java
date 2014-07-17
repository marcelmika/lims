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

package com.marcelmika.lims.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.lims.model.Message;
import com.marcelmika.lims.service.base.MessageLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the message local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.service.MessageLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.service.base.MessageLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.MessageLocalServiceUtil
 */
public class MessageLocalServiceImpl extends MessageLocalServiceBaseImpl {
    /*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.service.MessageLocalServiceUtil} to access the message local service.
	 */

    public Message addMessage(long cid, long creatorId, String body, String messageHash) throws SystemException {
        // Fetch possible existing conversation
        Message messageModel = messagePersistence.create(counterLocalService.increment());

        // Map properties
        messageModel.setCid(cid);
        messageModel.setCreatorId(creatorId);
        messageModel.setBody(body);
        messageModel.setMessageHash(messageHash);

        // Time when the message was created
        Date now = new Date();
        messageModel.setCreatedAt(now.getTime());

        // Update model
        messageModel = messagePersistence.update(messageModel, false);

        return messageModel;
    }

    public List<Message> readMessages(long cid) throws SystemException {
        return messagePersistence.findByCid(cid);
    }

}