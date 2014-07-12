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

import com.marcelmika.lims.NoSuchConversationException;
import com.marcelmika.lims.model.Conversation;
import com.marcelmika.lims.service.base.ConversationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * The implementation of the conversation local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.service.ConversationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.service.base.ConversationLocalServiceBaseImpl
 * @see com.marcelmika.lims.service.ConversationLocalServiceUtil
 */
public class ConversationLocalServiceImpl
	extends ConversationLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link ConversationLocalServiceUtil} to access the conversation local service.
	 */

    public Conversation addConversation(String conversationId, String conversationType) throws SystemException {
        // Fetch possible existing conversation
        Conversation conversationModel = conversationPersistence.fetchByConversationId(conversationId);

        if (conversationModel == null) {
            conversationModel = conversationPersistence.create(counterLocalService.increment());
            conversationModel.setConversationId(conversationId);
            conversationModel.setConversationType(conversationType);
            conversationModel = conversationPersistence.update(conversationModel, false);
        }

        return conversationModel;
    }

    public Conversation getConversation(String conversationId) throws SystemException {
        try {
            return conversationPersistence.findByConversationId(conversationId);
        } catch (SystemException e) {
            throw e;
        } catch (NoSuchConversationException e) {
            return null;
        }
    }

//    public List<Conversation> getAllConversations(long userId) throws SystemException {
//        return conversationPersistence.findByUserId(userId);
//    }
//
//    public Conversation getRoom(long userId, String conversationId) throws NoSuchConversationException, SystemException {
//        return conversationPersistence.findByUserId_conversationId(userId, conversationId);
//    }
//
//    public Conversation getConversation(long userId, String conversationId) {
//        Conversation conversation = null;
//        try {
//           conversation = conversationPersistence.fetchByUserId_conversationId(userId, conversationId);
//        } catch (Exception e) {
//
//        }
//        return conversation;
//    }
//
//    public void incrementUnreadMessages(long userId, String conversationId) {
//        try {
//            Conversation conversation = getConversation(userId, conversationId);
//            if (conversation != null) {
////                System.out.println("[INCREMENTING]");
//                int unreadMessages = conversation.getUnreadMessages();
//                conversation.setUnreadMessages(++unreadMessages);
//                conversation.persist();
//            }
//        } catch (Exception e) {
////            System.out.println("No such conversation " +"[" + userId + "] [" + conversationId + "]");
//        }
//    }
//
//    public void setUnreadMessages(long userId, String conversationId, int unreadMessages) {
//        Conversation conversation = getConversation(userId, conversationId);
//        if (conversation != null) {
//            conversation.setUnreadMessages(unreadMessages);
//            try {
//                conversationPersistence.clearCache();
//                conversation.persist();
//                conversationPersistence.clearCache();
//            } catch (SystemException e) {
////                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//    }
}