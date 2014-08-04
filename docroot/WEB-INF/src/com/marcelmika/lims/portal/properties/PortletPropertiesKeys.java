package com.marcelmika.lims.portal.properties;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public interface PortletPropertiesKeys {

    // Buddy list
    public static final String BUDDY_LIST_SOURCE = "buddy.list.source";
    public static final String BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES = "buddy.list.allowed.social.relation.types";
    public static final String BUDDY_LIST_MAX_BUDDIES = "buddy.list.max.buddies";
    public static final String BUDDY_LIST_SITE_EXCLUDES = "buddy.list.site.excludes";
    public static final String BUDDY_LIST_STRATEGY = "buddy.list.strategy";
    public static final String BUDDY_LIST_IGNORE_DEFAULT_USER = "buddy.list.ignore.default.user";

    // Jabber
    public static final String JABBER_ENABLED = "jabber.enabled";
    public static final String JABBER_HOST = "jabber.host";
    public static final String JABBER_PORT = "jabber.port";
    public static final String JABBER_RESOURCE = "jabber.resource";
    public static final String JABBER_SERVICE_NAME = "jabber.service.name";

    // Sock5
    public static final String JABBER_SOCK5_PROXY_ENABLED = "jabber.sock5.proxy.enabled";
    public static final String JABBER_SOCK5_PROXY_PORT = "jabber.sock5.proxy.port";

    // User import
    public static final String JABBER_IMPORT_USER_ENABLED = "jabber.import.user.enabled";

    // SASL
    public static final String JABBER_SASL_PLAIN_ENABLED = "jabber.sasl.plain.enabled";
    public static final String JABBER_SASL_PLAIN_AUTHID = "jabber.sasl.plain.authId";
    public static final String JABBER_SASL_PLAIN_PASSWORD = "jabber.sasl.plain.password";
}