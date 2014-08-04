package com.marcelmika.lims.api.environment;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.portal.properties.PortletPropertiesValues;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/3/14
 * Time: 11:41 AM
 */
public class Environment {

    // Log
    private static Log log = LogFactoryUtil.getLog(Environment.class);

    /**
     * Enum for source of buddy list
     */
    public enum BuddyListSource {
        LIFERAY,
        JABBER
    }

    /**
     * Returns source of the buddy list property
     *
     * @return BuddyListSource
     */
    public static BuddyListSource getBuddyListSource() {
        String value = PortletPropertiesValues.BUDDY_LIST_SOURCE;

        // Liferay
        if (value.equals("liferay")) {
            return BuddyListSource.LIFERAY;
        }
        // Jabber
        else if (value.equals("jabber")) {
            return BuddyListSource.JABBER;
        }
        // Unknown value
        else {
            log.error(String.format(
                    "Unknown buddy list source: %s. Valid values are \"liferay\" or \"jabber\". Since no valid " +
                            "property was provided \"liferay\" was chosen as a default. The value can be " +
                            "set in portlet-ext.properties file related to the LIMS portlet.", value
            ));

            // Fallback to liferay option
            return BuddyListSource.LIFERAY;
        }
    }

    /**
     * Enum for buddy list strategy
     */
    public enum BuddyListStrategy {
        ALL,
        SITES,
        SOCIAL,
        SITES_AND_SOCIAL
    }

    /**
     * Returns strategy for buddy list generation
     *
     * @return BuddyListStrategy
     */
    public static BuddyListStrategy getBuddyListStrategy() {
        String value = PortletPropertiesValues.BUDDY_LIST_STRATEGY;

        // All buddies in list
        if (value.equals("all")) {
            return BuddyListStrategy.ALL;
        }
        // Sites
        else if (value.equals("sites")) {
            return BuddyListStrategy.SITES;
        }
        // Social
        else if (value.equals("social")) {
            return BuddyListStrategy.SOCIAL;
        }
        // Sites and Social
        else if (value.equals("sites,social")) {
            return BuddyListStrategy.SITES_AND_SOCIAL;
        }
        // Unknown value
        else {
            log.error(String.format(
                    "Unknown buddy list strategy: %s. Valid values are \"all\", \"sites\", \"social\", \"sites," +
                            "social\". Since no valid property provided \"all\" was chosen as a default. The value " +
                            "can be set in portlet-ext.properties file related to the LIMS portlet.", value
            ));

            return BuddyListStrategy.ALL;
        }
    }

    /**
     * Returns maximal buddies count in list
     *
     * @return int
     */
    public static int getBuddyListMaxBuddies() {
        return PortletPropertiesValues.BUDDY_LIST_MAX_BUDDIES;
    }

    /**
     * Returns an array of allowed social relation types
     *
     * @return int[]
     */
    public static int[] getBuddyListAllowedSocialRelationTypes() {
        return PortletPropertiesValues.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES;
    }

    /**
     * Returns an array of sites names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListExcludes() {
        return PortletPropertiesValues.BUDDY_LIST_SITE_EXCLUDES;
    }


    /**
     * Return true if communication via jabber is enabled
     *
     * @return boolean
     */
    public static boolean isJabberEnabled() {
        return PortletPropertiesValues.JABBER_ENABLED;
    }

    /**
     * Returns jabber server host property
     *
     * @return String jabber host
     */
    public static String getJabberHost() {
        return PortletPropertiesValues.JABBER_HOST;
    }

    /**
     * Returns jabber server port property
     *
     * @return int jabber port
     */
    public static int getJabberPort() {
        return PortletPropertiesValues.JABBER_PORT;
    }

    /**
     * Returns jabber server service name property
     *
     * @return String jabber service name
     */
    public static String getJabberServiceName() {
        return PortletPropertiesValues.JABBER_SERVICE_NAME;
    }

    /**
     * Returns jabber server resource property
     *
     * @return String jabber resource
     */
    public static String getJabberResource() {
        return PortletPropertiesValues.JABBER_RESOURCE;
    }

    /**
     * Returns true if jabber SOCK5 proxy mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isJabberSock5ProxyEnabled() {
        return PortletPropertiesValues.JABBER_SOCK5_PROXY_ENABLED;
    }

    /**
     * Returns jabber SOCK5 port
     *
     * @return int jabber SOCK5 port
     */
    public static int getJabberSock5ProxyPort() {
        return PortletPropertiesValues.JABBER_SOCK5_PROXY_PORT;
    }

    /**
     * Returns true if SASL authentication mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isSaslPlainEnabled() {
        return PortletPropertiesValues.JABBER_SASL_PLAIN_ENABLED;
    }

    /**
     * Returns SASL Authentication ID
     *
     * @return String auth ID
     */
    public static String getSaslPlainAuthId() {
        return PortletPropertiesValues.JABBER_SASL_PLAIN_AUTHID;
    }

    /**
     * Returns password for SASL authentication
     *
     * @return String password
     */
    public static String getSaslPlainPassword() {
        return PortletPropertiesValues.JABBER_SASL_PLAIN_PASSWORD;
    }

}
