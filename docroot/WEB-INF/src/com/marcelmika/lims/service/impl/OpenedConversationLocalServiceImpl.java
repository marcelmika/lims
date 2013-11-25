/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.marcelmika.lims.NoSuchOpenedConversationException;
import com.marcelmika.lims.model.OpenedConversation;
import com.marcelmika.lims.service.base.OpenedConversationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import java.util.List;

/**
 * The implementation of the opened conversation local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.marcelmika.lims.service.OpenedConversationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.service.base.OpenedConversationLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.OpenedConversationLocalServiceUtil
 */
public class OpenedConversationLocalServiceImpl
        extends OpenedConversationLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link OpenedConversationLocalServiceUtil} to access the opened conversation local service.
     */

    public OpenedConversation openConversation(long userId, String conversationId) throws SystemException {
        OpenedConversation oc = openedConversationPersistence.fetchByUserId_ConversationId(userId, conversationId);

        if (oc == null) {
            oc = openedConversationPersistence.create(counterLocalService.increment());
            oc.setUserId(userId);
            oc.setConversationId(conversationId);
            openedConversationPersistence.update(oc, false);
        }

        return oc;
    }

    public void closeConversation(long userId, String conversationId) throws SystemException {
        try {
            OpenedConversation oc = openedConversationPersistence.fetchByUserId_ConversationId(userId, conversationId);
            openedConversationPersistence.remove(oc.getOcid());
        } catch (NoSuchOpenedConversationException ex) {
        }
    }

    public boolean isConversationOpened(long userId, String conversationId) {
        OpenedConversation oc;
        try {
            oc = openedConversationPersistence.fetchByUserId_ConversationId(userId, conversationId);
        } catch (SystemException ex) {
            return false;
        }

        return (oc != null);

    }

    public List<OpenedConversation> getOpenedConversations(long userId) throws SystemException {
        return openedConversationPersistence.findByUserId(userId);
    }
}