package com.marcelmika.lims.core.service;


/**
 * Utility class that holds an instance of ConversationCoreService.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:04 PM
 */
public class ConversationCoreServiceUtil {

    private static ConversationCoreService conversationCoreService;

    /**
     * Returns ConversationCoreService implementation
     *
     * @return ConversationCoreService
     */
    public static ConversationCoreService getConversationCoreService() {
        return conversationCoreService;
    }

    /**
     * Injects proper ConversationCoreService via Dependency Injection
     *
     * @param conversationCoreService ConversationCoreService
     */
    public void setConversationCoreService(ConversationCoreService conversationCoreService) {
        ConversationCoreServiceUtil.conversationCoreService = conversationCoreService;
    }


}
