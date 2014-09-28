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

package com.marcelmika.lims.api.environment;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.portal.properties.PortletPropertiesValues;

import java.util.HashSet;
import java.util.Set;

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
        SITES_AND_SOCIAL,
        USER_GROUPS
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
        // Groups
        else if (value.equals("groups")) {
            return BuddyListStrategy.USER_GROUPS;
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
     * Enum for buddy list social relation
     */
    public enum BuddyListSocialRelation {

        TYPE_BI_UNKNOWN(0, "Unknown relation"),
        TYPE_BI_CONNECTION(12, "Connections"),
        TYPE_BI_COWORKER(1, "Coworkers"),
        TYPE_BI_FRIEND(2, "Friends"),
        TYPE_BI_ROMANTIC_PARTNER(3, "Romantic Partners"),
        TYPE_BI_SIBLING(4, "Siblings");

        // Integer code which uniquely describes relation
        private int code;
        // String description of relation type
        private String description;

        /**
         * Private constructor
         *
         * @param code        that uniquely represent relation type
         * @param description string localized description of relation
         */
        private BuddyListSocialRelation(final int code, final String description) {
            this.code = code;
            this.description = description;
        }

        /**
         * Factory method which creates buddy list social relation enum from given code
         *
         * @param code that uniquely represent relation type
         * @return BuddyListSocialRelation
         */
        public static BuddyListSocialRelation fromCode(int code) {
            // Connection
            if (code == TYPE_BI_CONNECTION.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_CONNECTION;
            }
            // Coworker
            else if (code == TYPE_BI_COWORKER.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_COWORKER;
            }
            // Friend
            else if (code == TYPE_BI_FRIEND.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_FRIEND;
            }
            // Romantic Partner
            else if (code == TYPE_BI_ROMANTIC_PARTNER.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_ROMANTIC_PARTNER;
            }
            // Sibling
            else if (code == TYPE_BI_SIBLING.getCode()) {
                return BuddyListSocialRelation.TYPE_BI_SIBLING;
            }
            // Unknown
            else {
                return BuddyListSocialRelation.TYPE_BI_UNKNOWN;
            }
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Returns an array of allowed social relation types enums
     *
     * @return BuddyListSocialRelation[]
     */
    public static BuddyListSocialRelation[] getBuddyListAllowedSocialRelationTypes() {
        // Relations types are stored in int values
        int[] relationsTypeCodes = PortletPropertiesValues.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES;

        // Create a set which will contain enums that represent relation types
        Set<BuddyListSocialRelation> relationTypeSet = new HashSet<BuddyListSocialRelation>();

        // Map integer values to relation types
        for (int value : relationsTypeCodes) {
            // Connection
            if (value == BuddyListSocialRelation.TYPE_BI_CONNECTION.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_CONNECTION);
            }
            // Coworker
            else if (value == BuddyListSocialRelation.TYPE_BI_COWORKER.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_COWORKER);
            }
            // Friend
            else if (value == BuddyListSocialRelation.TYPE_BI_FRIEND.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_FRIEND);
            }
            // Romantic partner
            else if (value == BuddyListSocialRelation.TYPE_BI_ROMANTIC_PARTNER.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_ROMANTIC_PARTNER);
            }
            // Sibling
            else if (value == BuddyListSocialRelation.TYPE_BI_SIBLING.getCode()) {
                relationTypeSet.add(BuddyListSocialRelation.TYPE_BI_SIBLING);
            }
            // Unknown value
            else {
                log.error(String.format("Unknown buddy list social relation type: %d. Valid values are \"12\", \"1\"," +
                        " \"2\", \"3\", \"4\". The value can be set in portlet-ext.properties file related to the " +
                        "LIMS portlet.", value));
            }
        }


        // Nothing was mapped at the end.
        // This means that no relation was selected or the relation code was wrong.
        if (relationTypeSet.size() == 0) {
            // Log error
            log.error("No buddy list social relation were mapped. This means that either no social relation was " +
                    "selected or it was wrong. Since the property is required \"12 - " +
                    "Connections\" was selected as default. The value can be set in portlet-ext.properties file " +
                    "related to the LIMS portlet.");
            // Connection type is default
            return new BuddyListSocialRelation[]{BuddyListSocialRelation.TYPE_BI_CONNECTION};
        }


        // Map set to array
        return relationTypeSet.toArray(
                new BuddyListSocialRelation[relationTypeSet.size()]
        );
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
     * Returns maximal number of serach result in buddy list
     *
     * @return int
     */
    public static int getBuddyListMaxSearch() {
        return PortletPropertiesValues.BUDDY_LIST_MAX_SEARCH;
    }

    /**
     * Returns an array of sites names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListSiteExcludes() {
        return PortletPropertiesValues.BUDDY_LIST_SITE_EXCLUDES;
    }

    /**
     * Returns an array of group names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListGroupExcludes() {
        return PortletPropertiesValues.BUDDY_LIST_GROUP_EXCLUDES;
    }

    /**
     * Returns true if the default user should be ignored. Default user is the user which has
     * the defaultUser flag in database set to true.
     *
     * @return boolean
     */
    public static boolean getBuddyListIgnoreDefaultUser() {
        return PortletPropertiesValues.BUDDY_LIST_IGNORE_DEFAULT_USER;
    }

    /**
     * Returns true if the deactivated user should be ignored. Deactivated user is the user which has
     * the status column set to 0 in database.
     *
     * @return boolean
     */
    public static boolean getBuddyListIgnoreDeactivatedUser() {
        return PortletPropertiesValues.BUDDY_LIST_IGNORE_DEACTIVATED_USER;
    }

    /**
     * Maximal count of messages in the conversation.
     *
     * @return int
     */
    public static int getConversationListMaxMessages() {
        return PortletPropertiesValues.CONVERSATION_LIST_MAX_MESSAGES;
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
     * Returns true if the Jabber Import User mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isJabberImportUserEnabled() {
        return PortletPropertiesValues.JABBER_IMPORT_USER_ENABLED;
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
