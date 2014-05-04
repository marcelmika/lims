

package com.marcelmika.lims.jabber;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.marcelmika.lims.jabber.conversation.ConversationContainer;
import com.marcelmika.lims.jabber.conversation.ConversationKeys;
import com.marcelmika.lims.jabber.conversation.ConversationStore;
import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.jabber.domain.MUConversation;
import com.marcelmika.lims.jabber.domain.SUConversation;
import com.marcelmika.lims.jabber.form.JabberFormFactory;
import com.marcelmika.lims.jabber.listener.JabberMessageListener;
import com.marcelmika.lims.model.Buddy;
import com.marcelmika.lims.service.BuddyLocalServiceUtil;
import com.marcelmika.lims.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.util.PortletPropsValues;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Jabber server facade
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 * @deprecated
 */
public class JabberImpl implements Jabber {

    // ------------------------------------------------------------------------------
    //    Conversation
    // ------------------------------------------------------------------------------

    public void restartConversations(long userId) throws Exception {
        // @todo: Move to the conversation manager
        ConversationContainer container = ConversationStore.getInstance().getConversationContainer(userId);
        container.restartConversations();
    }

    @Override
    public Conversation createMUConversation(long userId,
                                             List<Buddy> participants,
                                             String message,
                                             Connection connection) throws Exception {
        // Get buddy
        Buddy buddy = BuddyLocalServiceUtil.getBuddyByUserId(userId);
        // Get new private room key
        String key = generatePrivateRoomKey();

        // Creates new room on the Jabber side
        MultiUserChat muc = new MultiUserChat(connection, getFullRoomId(key));
        // Set given user like an owner
        muc.create(getScreenName(connection.getUser()));
        // Set configuration form
        muc.sendConfigurationForm(JabberFormFactory.getMUCConfigurationForm());

        // Add to database
        ConversationLocalServiceUtil.addConversation(
                buddy.getUserId(),
                key,
                ConversationKeys.CONVERSATION_TYPE_MULTI_USER,
                "",
                key
        );
        // Create local conversation
        MUConversation conversation = new MUConversation(key, buddy, muc);
        conversation.addParticipants(participants);
        // Add it to container
        ConversationStore.getInstance().getConversationContainer(userId).addConversation(key, conversation);
        // Add participants to the conversation
        addParticipants(userId, conversation, participants, connection);
        // Send first message
        sendMessage(userId, conversation, message, connection);

        return conversation;
    }

    // @todo: Not implemented in v0.2
    @Override
    public Conversation createSUConversation(long userId,
                                             List<Buddy> participants,
                                             String message,
                                             Connection connection) throws Exception {

        // Get owner and participant
        Buddy owner = BuddyLocalServiceUtil.getBuddyByUserId(userId);
        Buddy participant = participants.get(0);
        if (participant == null) {
            throw new JabberException("Cannot create single user conversation when there is no participant");
        }

        // Get chat manager
        ChatManager chatManager = connection.getChatManager();
        // Create message listener
        JabberMessageListener messageListener = new JabberMessageListener();
        // Create chat
        Chat chat = chatManager.createChat(getJabberId(participant.getScreenName()), messageListener);

        chatManager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean b) {
//                chat.
            }
        });

        // Create conversation
        Conversation conversation = new SUConversation(owner, chat);
        // Add message listener to the conversation
        messageListener.setConversation(conversation);

        // Add to database
        ConversationLocalServiceUtil.addConversation(
                owner.getUserId(),
                participant.getScreenName(),
                ConversationKeys.CONVERSATION_TYPE_SINGLE_USER,
                "",
                participant.getFullName()
        );

        // Send the message
        sendMessage(userId, conversation, message, connection);

        return conversation;
    }

    /**
     * @deprecated This will be moved to conversation store
     */
    @Override
    public Conversation getConversation(long userId, String conversationId) throws Exception {
        // Find container that belongs to connected user
        ConversationContainer container = ConversationStore.getInstance().getConversationContainer(userId);

        // Return proper conversation based on the room key
        Conversation conversation = container.getConversation(conversationId);

        return conversation;
    }

    /**
     * @deprecated This will be moved to conversation store
     */
    @Override
    public List<Conversation> getAllConversations(long userId) throws Exception {
        // Find container that belongs to connected user
        ConversationContainer container = ConversationStore.getInstance().getConversationContainer(userId);

        // Return all conversations of the particular user
        return container.getAllConversations();
    }

    @Override
    public void changeStatus(long userId, String status, Connection connection) throws Exception {
        // Map status to jabber presence
        Presence presence = JabberMapper.mapStatusToPresence(status);
        // Send to jabber
        connection.sendPacket(presence);
    }

    @Override
    public void addParticipants(long userId,
                                Conversation conversation,
                                List<Buddy> participants,
                                Connection connection) throws Exception {
        // Single user conversation
        if (conversation.getConversationType().equals(ConversationKeys.CONVERSATION_TYPE_SINGLE_USER)) {
            // @todo: Not implemented in v0.2
//            System.out.println("Not implemented in v0.2");
            // Add just one participant => it's still a single user conversation
//            if (participants.size() == 1) {
//                throw new Exception("Not implemented yet.");
//            }
//            // Add more than one participant => create new multi user converastion
//            else if (participants.size() > 1) {
//                throw new Exception("Not implemented yet.");
//            }
        }


        // Multi user conversation
        if (conversation.getConversationType().equals(ConversationKeys.CONVERSATION_TYPE_MULTI_USER)) {
            // Room key
            String key = getFullRoomId(conversation.getConversationId());
            // Connect to room
            MultiUserChat muc = new MultiUserChat(connection, key);
            // Add buddies to the room
            for (Buddy participant : participants) {
                // Add buddy to room like an owner
                muc.grantOwnership(getJabberId(participant.getScreenName()));
                // Add locally
                ConversationLocalServiceUtil.addConversation(participant.getUserId(),
                        conversation.getConversationId(),
                        conversation.getConversationType(),
                        conversation.getConversationVisibility(),
                        conversation.getConversationName());

                // There is possibility that the participant may be connected. We need to
                // add conversation to his conversation container thus he can see
                // the newly created conversation immediately
//                Connection buddyConnection = connectionManager.getConnection(participant.getUserId());
//                if (buddyConnection != null) {
//                    // Create new multichat
//                    MultiUserChat buddyMuc = new MultiUserChat(buddyConnection, key);
//                    // Create new conversation
//                    MUConversation muConversation = new MUConversation(conversation.getConversationId(), participant, buddyMuc);
//                    muConversation.addParticipants(participants);
//                    // Add it to the user's conversation container
//                    ConversationContainer container = ConversationStore.getInstance().getConversationContainer(participant.getUserId());
//                    if (container != null) {
////                        System.out.println("[ADDING] " + getJabberId(participant.getScreenName()));
//                        container.addConversation(muConversation.getConversationId(), muConversation);
//                    }
//                }
            }
        }
    }

    @Override
    public void leaveConversation(long userId, String conversationId, Connection connection) throws Exception {
        // Room key
        String key = getFullRoomId(conversationId);
        // Connect to room
        MultiUserChat muc = new MultiUserChat(connection, key);
        // User key
        String user = getJabberId(getScreenName(connection.getUser()));
        // Change from ownership to membership
        muc.grantMembership(user);
        // Find container that belongs to connected user
        ConversationContainer container = ConversationStore.getInstance().getConversationContainer(userId);
        container.removeConversation(conversationId);
    }


//    public void sendMessage(long fromUserId, long toUserId, String content) {
//        System.out.println("SENDING MESSAGE");
//        try {
//            if (Validator.isNull(content)) {
//                return;
//            }
//
//            Connection connection = connectionManager.getConnection(fromUserId);
//
//            if (connection == null) {
//                System.out.println("User " + fromUserId + " is not connected to Jabber and cannot send messages");
//                return;
//            }
//
//            // Receiver
//            User toUser = UserLocalServiceUtil.getUser(toUserId);
//
//            Roster roster = connection.getRoster();
//            String jabberId = getJabberId(toUser.getScreenName());
//
//            if (!roster.contains(jabberId)) {
//                System.out.println("No roster :(");
//                return;
//            }
//
//            Iterator<Presence> presences = roster.getPresences(jabberId);
//            while (presences.hasNext()) {
//                Presence presence = presences.next();
//                System.out.println("PRESENCE: " + presence);
//
//                String from = presence.getFrom();
//
////                String resource = getResource(from);
////                if (resource.equalsIgnoreCase(
////                        PortletPropsValues.JABBER_RESOURCE)) {
////                    continue;
////                }
//
//                Buddy fromBuddy = BuddyLocalServiceUtil.getBuddyByUserId(fromUserId);
//
//
//                JabberMessageListener messageListener = new JabberMessageListener();
//                ChatManager chatManager = connection.getChatManager();
//
//                Chat chat = chatManager.createChat(from, messageListener);
//                Conversation conversation = new SUConversation(fromBuddy, chat);
//                messageListener.setConversation(conversation);
//
//                try {
//                    chat.sendMessage(content);
//                } catch (XMPPException xmppe) {
//                    System.out.println("User " + fromUserId + " could not send message" + xmppe);
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

    @Override
    public void sendMessage(long userId,
                            Conversation conversation,
                            String message,
                            Connection connection) throws Exception {
        // Single user conversation
        if (conversation.getConversationType().equals(ConversationKeys.CONVERSATION_TYPE_SINGLE_USER)) {
            // @todo: Not implemented in v0.2
            // sendSUMessage(userId, (SUConversation)conversation, message);
        }
        // Multi user conversation
        else if (conversation.getConversationType().equals(ConversationKeys.CONVERSATION_TYPE_MULTI_USER)) {
            sendMUMessage(userId, (MUConversation) conversation, message,connection);
        }
    }

    /**
     * Sends message to the multi user room
     *
     * @param userId       userId
     * @param conversation conversation
     * @param message      Message which will be sent
     * @throws Exception
     */
    protected void sendMUMessage(long userId,
                                 MUConversation conversation,
                                 String message,
                                 Connection connection) throws Exception {
        // No empty messages
        if (Validator.isNull(message)) {
            return;
        }
        // Room key
        String key = getFullRoomId(conversation.getConversationId());
        // Connect to room
        MultiUserChat muc = new MultiUserChat(connection, key);
        // Send message
        muc.sendMessage(message);
    }

    /**
     * Sends message to the single user room
     *
     * @param userId       userId
     * @param conversation Conversation
     * @param message      Message which will be sent
     * @throws Exception
     */
    protected void sendSUMessage(long userId,
                                 SUConversation conversation,
                                 String message,
                                 Connection connection) throws Exception {
        // No empty messages
        if (Validator.isNull(message)) {
            return;
        }

        Chat chat = conversation.getChat();
        chat.sendMessage(message);
    }

    // @todo: Remove from this class
    protected String generatePrivateRoomKey() {
//        String prefix = PortletPropsValues.JABBER_PP_PREFIX_PRIVATE; // User can create only private rooms
        String randomID = UUID.randomUUID().toString(); // Create random unique string
        String key = randomID;

        return key;
    }

    // ------------------------------------------------------------------------------
    //    Utils
    // ------------------------------------------------------------------------------

    protected String getFullJabberId(String screenName) {
        String jabberId = getJabberId(screenName);
        return jabberId.concat(StringPool.SLASH).concat(PortletPropsValues.JABBER_RESOURCE);
    }

    protected String getJabberId(String screenName) {
        return screenName.concat(StringPool.AT).concat(PortletPropsValues.JABBER_SERVICE_NAME);
    }

    public String getResource(String jabberId) {
        return StringUtil.extractLast(jabberId, StringPool.SLASH);
    }

    public String getScreenName(String jabberId) {
        return StringUtil.extractFirst(jabberId, StringPool.AT);
    }

    public String getFullRoomId(String roomJID) {
        return roomJID + StringPool.AT + PortletPropsValues.JABBER_SERVICE_MULTICHAT_NAME;
    }
}
