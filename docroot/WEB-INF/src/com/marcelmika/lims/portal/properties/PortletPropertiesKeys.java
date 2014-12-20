/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.portal.properties;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public interface PortletPropertiesKeys {

    // Version
    public static final String VERSION = "version";

    // Properties source
    public static final String PROPERTIES_SOURCE = "properties.source";

    // Excluded
    public static final String EXCLUDED_SITES = "excluded.sites";

    // Buddy list
    public static final String BUDDY_LIST_SOURCE = "buddy.list.source";
    public static final String BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES = "buddy.list.allowed.social.relation.types";
    public static final String BUDDY_LIST_MAX_BUDDIES = "buddy.list.max.buddies";
    public static final String BUDDY_LIST_MAX_SEARCH = "buddy.list.max.search";
    public static final String BUDDY_LIST_SITE_EXCLUDES = "buddy.list.site.excludes";
    public static final String BUDDY_LIST_GROUP_EXCLUDES = "buddy.list.group.excludes";
    public static final String BUDDY_LIST_STRATEGY = "buddy.list.strategy";
    public static final String BUDDY_LIST_IGNORE_DEFAULT_USER = "buddy.list.ignore.default.user";
    public static final String BUDDY_LIST_IGNORE_DEACTIVATED_USER = "buddy.list.ignore.deactivated.user";

    // Conversation
    public static final String CONVERSATION_LIST_MAX_MESSAGES = "conversation.list.max.messages";

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

    // URLs
    public static final String URL_HELP = "url.help";
    public static final String URL_UNSUPPORTED_BROWSER = "url.unsupported.browser";

    // Error mode
    public static final String ERROR_MODE_ENABLED = "error.mode.enabled";
}