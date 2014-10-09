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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PortletPropertiesValues {

    // Properties source
    public static final String PROPERTIES_SOURCE = getString(
            PortletPropertiesKeys.PROPERTIES_SOURCE
    );

    // Buddy List - Source
    public static final String BUDDY_LIST_SOURCE = getString(
            PortletPropertiesKeys.BUDDY_LIST_SOURCE
    );

    // Buddy List - Social Relation Types
    public static final int[] BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES = getIntegerValues(
            PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES
    );

    // Buddy List - Site Excludes
    public static final String[] BUDDY_LIST_SITE_EXCLUDES = getStringValues(
            PortletPropertiesKeys.BUDDY_LIST_SITE_EXCLUDES
    );

    // Buddy List - Group Excludes
    public static final String[] BUDDY_LIST_GROUP_EXCLUDES = getStringValues(
            PortletPropertiesKeys.BUDDY_LIST_GROUP_EXCLUDES
    );

    // Buddy List - Max buddy list count
    public static final Integer BUDDY_LIST_MAX_BUDDIES = getInteger(
            PortletPropertiesKeys.BUDDY_LIST_MAX_BUDDIES
    );

    // Buddy List - Max search count
    public static final Integer BUDDY_LIST_MAX_SEARCH = getInteger(
            PortletPropertiesKeys.BUDDY_LIST_MAX_SEARCH
    );

    // Buddy List - Strategy (All by default)
    public static final String BUDDY_LIST_STRATEGY = getString(
            PortletPropertiesKeys.BUDDY_LIST_STRATEGY
    );

    // Buddy List - Ignore default user
    public static final boolean BUDDY_LIST_IGNORE_DEFAULT_USER = getBoolean(
            PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEFAULT_USER
    );

    // Buddy List - Ignore deactivated user
    public static final boolean BUDDY_LIST_IGNORE_DEACTIVATED_USER = getBoolean(
            PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER
    );

    // Conversations - Max messages count
    public static final int CONVERSATION_LIST_MAX_MESSAGES = getInteger(
            PortletPropertiesKeys.CONVERSATION_LIST_MAX_MESSAGES
    );

    // Jabber (Disabled by default)
    public static final Boolean JABBER_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_ENABLED
    );

    // Jabber - Host
    public static final String JABBER_HOST = getString(
            PortletPropertiesKeys.JABBER_HOST
    );

    // Jabber - Port
    public static final int JABBER_PORT = getInteger(
            PortletPropertiesKeys.JABBER_PORT
    );

    // Jabber - Service name
    public static final String JABBER_SERVICE_NAME = getString(
            PortletPropertiesKeys.JABBER_SERVICE_NAME
    );

    // Jabber - Resource name
    public static final String JABBER_RESOURCE = getString(
            PortletPropertiesKeys.JABBER_RESOURCE
    );

    // Jabber - SOCK5 (Disabled by default)
    public static final boolean JABBER_SOCK5_PROXY_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_SOCK5_PROXY_ENABLED
    );

    // Jabber - SOCK5 - Port
    public static final int JABBER_SOCK5_PROXY_PORT = getInteger(
            PortletPropertiesKeys.JABBER_SOCK5_PROXY_PORT
    );

    // Jabber - User Import
    public static final boolean JABBER_IMPORT_USER_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_IMPORT_USER_ENABLED
    );

    // Jabber - SASL (Disabled by default)
    public static final Boolean JABBER_SASL_PLAIN_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_SASL_PLAIN_ENABLED
    );

    // Jabber - SASL - Authentication ID
    public static final String JABBER_SASL_PLAIN_AUTHID = getString(
            PortletPropertiesKeys.JABBER_SASL_PLAIN_AUTHID
    );

    // Jabber - SASL - Password
    public static final String JABBER_SASL_PLAIN_PASSWORD = getString(
            PortletPropertiesKeys.JABBER_SASL_PLAIN_PASSWORD
    );

    // Error Mode
    public static final Boolean ERROR_MODE_ENABLED = getBoolean(
            PortletPropertiesKeys.ERROR_MODE_ENABLED
    );

    /**
     * Returns string value from properties related to the key
     *
     * @param key of the string value
     * @return string representation of value related to the key
     */
    private static String getString(String key) {
        return GetterUtil.getString(PortletProps.get(key));
    }

    /**
     * Returns an array of string values from properties related to the key
     *
     * @param key of the string value
     * @return an array of string representation of values related to the key
     */
    private static String[] getStringValues(String key) {
        return PortletProps.getArray(key);
    }

    /**
     * Returns integer value from properties related to the key
     *
     * @param key of the integer value
     * @return integer representation of value related to the key
     */
    private static int getInteger(String key) {
        return GetterUtil.getInteger(PortletProps.get(key));
    }

    /**
     * Returns and array of int values from properties related to the key
     *
     * @param key of the integer values
     * @return an array of int representation of values related to the key
     */
    private static int[] getIntegerValues(String key) {
        return GetterUtil.getIntegerValues(PortletProps.getArray(key));
    }

    /**
     * Returns boolean value from properties related to the key
     *
     * @param key of the boolean value
     * @return boolean representation of value related to the key
     */
    private static boolean getBoolean(String key) {
        return GetterUtil.getBoolean(PortletProps.get(key));
    }

}