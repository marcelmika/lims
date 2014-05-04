
package com.marcelmika.lims.portal.poller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * Poller dispatcher is responsible for calling the appropriate method
 * on poller processor based on the chunkId which is taken from the poller request.
 * ChunkId serves as an identifier of the action which is requested.
 * For example if the user attempts to create a new message the poller request with
 * poller.action.create.message is sent to the poller processor. Based on that the poller
 * dispatcher calls createMessage() method on the poller processor.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PollerDispatcher {

    // Actions
    // TODO: refactor (action names are slightly different than method names on poller processor), also in js
    private static final String POLLER_ACTION_CREATE_SINGLE_USER_CONVERSATION = "poller.action.create.singleUserConversation";
    private static final String POLLER_ACTION_CREATE_MESSAGE = "poller.action.create.message";
    private static final String POLLER_ACTION_OPEN_CONVERSATION = "poller.action.open.conversation";
    private static final String POLLER_ACTION_CLOSE_CONVERSATION = "poller.action.close.conversation";
    private static final String POLLER_ACTION_LEAVE_CONVERSATION = "poller.action.leave.conversation";
    private static final String POLLER_ACTION_ADD_TO_CONVERSATION = "poller.action.add.to.conversation";
    private static final String POLLER_ACTION_SEND_MESSAGE = "poller.action.send.message";
    private static final String POLLER_ACTION_SAVE_SETTINGS = "poller.action.save.settings";
    private static final String POLLER_ACTION_CHANGE_STATUS = "poller.action.change.status";
    private static final String POLLER_ACTION_CHANGE_ACTIVE_PANEL = "poller.action.change.active.panel";
    private static final String POLLER_ACTION_CHAT_ENABLED = "poller.action.chat.enabled";
    // Log
    private static Log log = LogFactoryUtil.getLog(PollerProcessor.class);

    /**
     * Calls appropriate method on PollerProcessor based on the chunkId taken from the poller request.
     *
     * @param pollerRequest   request from browser
     * @param pollerProcessor poller processor
     * @return ResponseEvent
     * @throws IllegalArgumentException if request does not contain chunkId
     */
    public static ResponseEvent dispatchSendRequest(PollerRequest pollerRequest,
                                                    PollerProcessor pollerProcessor)
            throws IllegalArgumentException {

        // Get the chunk id from the request.
        String chunkId = pollerRequest.getChunkId();
        if (chunkId == null) {
            throw new IllegalArgumentException("Chunk id cannot be null");
        }

        // Response event as s result of a request
        ResponseEvent responseEvent = null;

        // Create Single User conversation
        if(chunkId.equals(PollerDispatcher.POLLER_ACTION_CREATE_SINGLE_USER_CONVERSATION)) {
            responseEvent = pollerProcessor.createSingleUserConversation(pollerRequest);
        }// Create conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CREATE_MESSAGE)) {
            responseEvent = pollerProcessor.createConversation(pollerRequest);
        } // Open conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_OPEN_CONVERSATION)) {
            responseEvent = pollerProcessor.openConversation(pollerRequest);
        } // Close conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CLOSE_CONVERSATION)) {
            responseEvent = pollerProcessor.closeConversation(pollerRequest);
        } // Leave conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_LEAVE_CONVERSATION)) {
            responseEvent = pollerProcessor.leaveConversation(pollerRequest);
        } // Add to conversation
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_ADD_TO_CONVERSATION)) {
            responseEvent = pollerProcessor.addToConversation(pollerRequest);
        } // Send Message
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_SEND_MESSAGE)) {
            responseEvent = pollerProcessor.sendMessage(pollerRequest);
        } // Save settings
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_SAVE_SETTINGS)) {
            responseEvent = pollerProcessor.updateSettings(pollerRequest);
        } // Change status
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHANGE_STATUS)) {
            responseEvent = pollerProcessor.updateStatus(pollerRequest);
        } // Change active panel
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHANGE_ACTIVE_PANEL)) {
            responseEvent = pollerProcessor.updateActivePanel(pollerRequest);
        } // Enable/Disable chat
        else if (chunkId.equals(PollerDispatcher.POLLER_ACTION_CHAT_ENABLED)) {
            responseEvent = pollerProcessor.setChatEnabled(pollerRequest);
        }

        // Log
        log.info("Dispatcher: Send: " + chunkId);

        return responseEvent;
    }


    /**
     * Calls all appropriate methods on PollerProcessor that are scheduled for the receive request event.
     *
     * @param pollerRequest   request from browser
     * @param pollerResponse  response sent to browser
     * @param pollerProcessor poller processor
     */
    public static void dispatchReceiveRequest(PollerRequest pollerRequest,
                                              PollerResponse pollerResponse,
                                              PollerProcessor pollerProcessor) {

        // Run methods on poller processor
//        pollerProcessor.getBuddyList(pollerRequest, pollerResponse);

        // Groups are needed just on the initial request
        pollerProcessor.getGroupList(pollerRequest, pollerResponse);
        pollerProcessor.getAllConversations(pollerRequest, pollerResponse);
//        pollerProcessor.getOpenedConversations(pollerRequest, pollerResponse);



        // Log
        log.info("Dispatcher: Receive");
    }
}
