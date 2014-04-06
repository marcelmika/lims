package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.conversation.GetConversationsRequestEvent;
import com.marcelmika.lims.api.events.conversation.GetConversationsResponseEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.conversation.manager.multi.MultiUserConversationManager;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.ConversationType;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 8:08 PM
 */
public class ConversationJabberServiceImpl implements ConversationJabberService {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationJabberServiceImpl.class);

    // Dependencies
    private UserSessionStore userSessionStore;

    // todo: DEBUG
    private String threadId;

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public ConversationJabberServiceImpl(UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
//        Message message = Message.fromMessageDetails(event.getMessageDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return SendMessageResponseEvent.sendMessageFailure(
                    new JabberException("There is no user session. Message cannot be sent.")
            );
        }

        // Decide where to go based on the conversation type
//        ConversationType conversationType = ConversationType.fromConversationTypeDetails(
//                event.getConversationDetails().getConversationType()
//        );


        // Single user conversation
//        if (conversationType == ConversationType.SINGLE_USER) {
            // todo: Map from conversation details
            SingleUserConversation conversation = new SingleUserConversation();
            conversation.setConversationId(threadId);
        Message message = new Message();
        Buddy b  = new Buddy();
        b.setScreenName("marcel.mika@test02.rclick.cz");
            message.setTo(b);
        message.setBody("Ahoj");

            // Send message via single user conversation manager taken from user session
            SingleUserConversationManager manager = userSession.getSingleUserConversationManager();
            try {
                conversation = manager.sendMessage(conversation, message);
                log.info("Saving conversation: " + conversation.getConversationId());
                threadId = conversation.getConversationId();
            } catch (JabberException e) {
                return SendMessageResponseEvent.sendMessageFailure(e);
            }

            return SendMessageResponseEvent.sendMessageSuccess("Message successfully sent");
        }
        // Multi user conversation
//        else if (conversationType == ConversationType.MULTI_USER) {
//            // todo: Map from conversation details
////            MultiUserConversation conversation = MultiUserConversation.from
//            // todo: Send message via single user conversation manager taken from user session
//            MultiUserConversationManager manager = userSession.getMultiUserConversationManager();
//
//
//            return SendMessageResponseEvent.sendMessageSuccess("Message successfully send");
//
//        }
//        // Unknown type
//        else {
//            return SendMessageResponseEvent.sendMessageFailure(
//                    new JabberException("Unknown type of conversation: " + conversationType)
//            );
//        }
//    }
}
