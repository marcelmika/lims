
AUI().use('aui-base', function(A) {

    Liferay.namespace('Chat');

    /**
     * Stores elements that are accessible from all over the app
     */
    Liferay.Chat.Globals = {
        // Main container element where all elements are stored
        chatContainerEl: A.one('#chatBar'),
        // Container element that stores all tabs
        tabsContainerEl: A.one('#chatBar .chat-tabs'),
        // Portlet information
        chatPortletId: A.one('#chatPortletId').get('value'),
        chatPortletEnabled: A.one('#chatPortletEnabled').get('value')  === "true" ? true : false,
        chatPortletURL: A.one('#chatPortletURL').get('value'),
        // Screen name
        currentUserScreenName: A.one('#currentChatUserScreenName').get('value')
    }; 

});