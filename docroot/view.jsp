

<%-- Taglib --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%-- Define Objects --%>
<liferay-theme:defineObjects />
<portlet:defineObjects />

<c:if test="${show}">
    <%-- Resource URL --%>
    <portlet:resourceURL var="chatPortletURL" id="view.jsp" escapeXml="false" />    

    <%-- Chat bar --%>
    <div class="portlet-chat" id="chatBar">
        <div class="errorNotification"></div>
        <div class="chat-bar">
            <div class="chat-sound"></div>            
            <div class="chat-tabs-container">                
                <ul class="chat-tabs">                              
                    <%@ include file="/WEB-INF/jspf/status-panel.jspf" %>
                    <%@ include file="/WEB-INF/jspf/settings-panel.jspf" %>
                    <%@ include file="/WEB-INF/jspf/buddy-list-panel.jspf" %>
                    <%@ include file="/WEB-INF/jspf/conversation-panel.jspf" %>                                                 
                    <%@ include file="/WEB-INF/jspf/conversation-sessions.jspf" %> 
                </ul>
            </div>
        </div>

        <%-- Javascript Templates --%>
        <%@ include file="/WEB-INF/jspf/templates.jspf" %>

        <%-- Hidden inputs --%>
        <aui:input type="hidden" id="chatPortletId" name="chatPortletId" useNamespace="false" value="${portletDisplay.id}" />        
        <aui:input type="hidden" id="chatPortletEnabled" name="chatPortletEnabled" useNamespace="false" value="${settings.chatEnabled}" />
        <aui:input type="hidden" id="chatPortletURL" name="portletURL" useNamespace="false" value="<%= renderResponse.encodeURL(chatPortletURL.toString()) %>" />  
        <aui:input type="hidden" id="currentChatServerTime" name="currentChatServerTime" useNamespace="false" value="<%= System.currentTimeMillis() %>" />
        <aui:input type="hidden" id="currentChatUserScreenName" name="currentChatUserPortraitId" useNamespace="false" value="${screenName}" />        
    </div>   
</c:if>