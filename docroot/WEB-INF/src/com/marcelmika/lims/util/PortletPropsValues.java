

package com.marcelmika.lims.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PortletPropsValues {
    // Buddy
    public static final int BUDDY_LIST_MAX_BUDDIES = GetterUtil.getInteger(PortletProps.get(PortletPropsKeys.BUDDY_LIST_MAX_BUDDIES));
    public static final String BUDDY_LIST_STRATEGY = GetterUtil.getString(PortletProps.get(PortletPropsKeys.BUDDY_LIST_STRATEGY));
    // Jabber    
    public static final String JABBER_HOST = GetterUtil.getString(PortletProps.get(PortletPropsKeys.JABBER_HOST));    
    public static final int JABBER_PORT = GetterUtil.getInteger(PortletProps.get(PortletPropsKeys.JABBER_PORT));
    public static final String JABBER_RESOURCE = GetterUtil.getString(PortletProps.get(PortletPropsKeys.JABBER_RESOURCE));
    public static final String JABBER_SERVICE_NAME = GetterUtil.getString(PortletProps.get(PortletPropsKeys.JABBER_SERVICE_NAME));
    public static final String JABBER_SERVICE_MULTICHAT_NAME = GetterUtil.getString(PortletProps.get(PortletPropsKeys.JABBER_SERVICE_MULTICHAT_NAME));
    public static final boolean JABBER_SOCK5_PROXY_ENABLED = GetterUtil.getBoolean(PortletProps.get(PortletPropsKeys.JABBER_SOCK5_PROXY_ENABLED));
    public static final int JABBER_SOCK5_PROXY_PORT = GetterUtil.getInteger(PortletProps.get(PortletPropsKeys.JABBER_SOCK5_PROXY_PORT));

    // User Import
    public static final boolean JABBER_IMPORT_USER_ENABLED = GetterUtil.getBoolean(PortletProps.get(PortletPropsKeys.JABBER_IMPORT_USER_ENABLED));

    // SASL
    public static final boolean JABBER_SASL_PLAIN_ENABLED = GetterUtil.getBoolean(PortletProps.get(PortletPropsKeys.JABBER_SASL_PLAIN_ENABLED));
    public static final String JABBER_SASL_PLAIN_AUTHID = GetterUtil.getString(PortletProps.get(PortletPropsKeys.JABBER_SASL_PLAIN_AUTHID));
    public static final String JABBER_SASL_PLAIN_PASSWORD = GetterUtil.getString(PortletProps.get(PortletPropsKeys.JABBER_SASL_PLAIN_PASSWORD));

}