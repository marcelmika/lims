<%-- Variables --%>
<%--@elvariable id="isEnabled" type="boolean"--%>
<%--@elvariable id="screenName" type="String"--%>
<%--@elvariable id="fullName" type="String"--%>

<%-- Taglib --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%-- Define Objects --%>
<liferay-theme:defineObjects/>
<portlet:defineObjects/>


<c:if test="${isEnabled}">

    <%-- Resource URL --%>
    <portlet:resourceURL var="limsPortletURL" id="view.jsp" escapeXml="false"/>

    <%-- LIMS bar --%>
    <div id="lims-container">
        <div class="lims-bar">
            <div class="lims-sound"></div>
            <div class="lims-tabs-container">
                <ul class="lims-tabs">
                    <%@ include file="/WEB-INF/jspf/status-panel.jspf" %>
                    <%@ include file="/WEB-INF/jspf/settings-panel.jspf" %>
                    <%@ include file="/WEB-INF/jspf/group-list-panel.jspf" %>
                    <%@ include file="/WEB-INF/jspf/conversations.jspf" %>
                </ul>
            </div>
        </div>

            <%-- Javascript Templates --%>
        <%@ include file="/WEB-INF/jspf/templates.jspf" %>

            <%-- Portlet Enabled Flag --%>
        <aui:input type="hidden"
                   id="limsPortletEnabled"
                   name="limsPortletEnabled"
                   useNamespace="false"
                   value="${settings.chatEnabled}"/>

            <%-- Portlet URL --%>
        <aui:input type="hidden"
                   id="limsPortletURL"
                   name="limsPortletURL"
                   useNamespace="false"
                   value="<%= renderResponse.encodeURL(limsPortletURL.toString()) %>"/>

            <%-- Server Time --%>
        <aui:input type="hidden"
                   id="limsCurrentServerTime"
                   name="limsCurrentServerTime"
                   useNamespace="false"
                   value="<%= System.currentTimeMillis() %>"/>

            <%-- Logged user screen name --%>
        <aui:input type="hidden"
                   id="limsCurrentUserScreenName"
                   name="limsCurrentUserScreenName"
                   useNamespace="false"
                   value="${screenName}"/>

            <%-- Logged user full name --%>
        <aui:input type="hidden"
                   id="limsCurrentUserFullName"
                   name="limsCurrentUserFullName"
                   useNamespace="false"
                   value="${fullName}"/>
    </div>

    <%-- Preloaded Images --%>
    <%@ include file="/WEB-INF/jspf/preloaded-images.jspf" %>

</c:if>