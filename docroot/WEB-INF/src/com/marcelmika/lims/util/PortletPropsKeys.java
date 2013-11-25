
package com.marcelmika.lims.util;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public interface PortletPropsKeys {

    // Buddy
    public static final String BUDDY_LIST_MAX_BUDDIES = "buddy.list.max.buddies";
    public static final String BUDDY_LIST_STRATEGY = "buddy.list.strategy";

    // Jabber
    public static final String JABBER_HOST = "jabber.host";    
    public static final String JABBER_PORT = "jabber.port";
    public static final String JABBER_RESOURCE = "jabber.resource";
    public static final String JABBER_SERVICE_NAME = "jabber.service.name";
    public static final String JABBER_SERVICE_MULTICHAT_NAME = "jabber.service.multichat.name";

    // Sock5
    public static final String JABBER_SOCK5_PROXY_ENABLED = "jabber.sock5.proxy.enabled";
    public static final String JABBER_SOCK5_PROXY_PORT = "jabber.sock5.proxy.port";

    //  Public/Private rooms
    public static final String JABBER_PP_ENABLED = "jabber.pp.enabled";
    public static final String JABBER_PP_PREFIX_PRIVATE = "jabber.pp.prefix.private";
    public static final String JABBER_PP_PREFIX_PUBLIC = "jabber.pp.prefix.public";

    // User import
    public static final String JABBER_IMPORT_USER_ENABLED = "jabber.import.user.enabled";

    // SASL
    public static final String JABBER_SASL_PLAIN_ENABLED = "jabber.sasl.plain.enabled";
    public static final String JABBER_SASL_PLAIN_AUTHID = "jabber.sasl.plain.authId";
    public static final String JABBER_SASL_PLAIN_PASSWORD = "jabber.sasl.plain.password";
}