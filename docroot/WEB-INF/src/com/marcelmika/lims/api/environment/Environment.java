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

/**
 * Environment contains all settings and properties related to the portlet
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/3/14
 * Time: 11:41 AM
 */
public class Environment {

    // Environment properties
    private static PropertiesSource propertiesSource = PropertiesSource.PREFERENCES;
    private static String[] excludedSites = new String[]{};
    private static BuddyListSource buddyListSource = BuddyListSource.LIFERAY;
    private static BuddyListStrategy buddyListStrategy = BuddyListStrategy.ALL;
    private static BuddyListSocialRelation[] buddyListSocialRelations = new BuddyListSocialRelation[]{};
    private static Boolean buddyListIgnoreDefaultUser = false;
    private static Boolean buddyListIgnoreDeactivatedUser = false;
    private static Integer buddyListMaxBuddies = 0;
    private static Integer buddyListMaxSearch = 0;
    private static Integer conversationListMaxMessages = 0;
    private static String[] buddyListSiteExcludes = new String[]{};
    private static String[] buddyListGroupExcludes = new String[]{};
    private static Boolean jabberEnabled = false;
    private static String jabberHost = "";
    private static Integer jabberPort = 0;
    private static String jabberServiceName = "";
    private static String jabberResource = "";
    private static Boolean jabberSock5ProxyEnabled = false;
    private static Integer jabberSock5ProxyPort = 0;
    private static Boolean jabberImportUserEnabled = false;
    private static Boolean saslPlainEnabled = false;
    private static String saslPlainAuthId = "";
    private static String saslPlainPassword = "";
    private static String urlHelp = "";
    private static String urlUnsupportedBrowser = "";
    private static Boolean errorModeEnabled = false;

    /**
     * Enum for properties source
     */
    public enum PropertiesSource {

        /**
         * Properties are loaded from the PortletPreferences
         */
        PREFERENCES,
        /**
         * Properties are loaded from the portal.properties file
         */
        PROPERTIES
    }

    /**
     * Returns source of properties
     *
     * @return PropertiesSource
     */
    public static PropertiesSource getPropertiesSource() {
        return propertiesSource;
    }

    /**
     * Set properties source
     *
     * @param propertiesSource PropertiesSource
     */
    public static void setPropertiesSource(PropertiesSource propertiesSource) {
        Environment.propertiesSource = propertiesSource;
    }

    /**
     * Returns an array of sites names where the portlet shouldn't be displayed
     *
     * @return String[]
     */
    public static String[] getExcludedSites() {
        return excludedSites;
    }

    /**
     * Sets the excluded sites property
     *
     * @param excludedSites String[]
     */
    public static void setExcludedSites(String[] excludedSites) {
        Environment.excludedSites = excludedSites;
    }

    /**
     * Enum for source of buddy list
     */
    public enum BuddyListSource {

        /**
         * Buddies are loaded from the Liferay database
         */
        LIFERAY,
        /**
         * Buddies are loaded from the Jabber server
         */
        JABBER
    }

    /**
     * Returns source of the buddy list property
     *
     * @return BuddyListSource
     */
    public static BuddyListSource getBuddyListSource() {
        return buddyListSource;
    }

    /**
     * Sets buddy list source
     *
     * @param buddyListSource BuddyListSource
     */
    public static void setBuddyListSource(BuddyListSource buddyListSource) {
        Environment.buddyListSource = buddyListSource;
    }

    /**
     * Enum for buddy list strategy
     */
    public enum BuddyListStrategy {
        /**
         * All buddies in the system
         */
        ALL("all"),

        /**
         * Buddies related to the sites where the users participates
         */
        SITES("sites"),

        /**
         * Buddies listed based on the social relations
         */
        SOCIAL("social"),

        /**
         * Merge of the sites and social list strategies
         */
        SITES_AND_SOCIAL("sites,social"),

        /**
         * Buddies shown based on the user groups where the user belongs
         */
        USER_GROUPS("groups");


        // String description of relation type
        private String description;

        /**
         * Private constructor
         *
         * @param description string description of the list strategy
         */
        private BuddyListStrategy(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Returns strategy for buddy list generation
     *
     * @return BuddyListStrategy
     */
    public static BuddyListStrategy getBuddyListStrategy() {
        return buddyListStrategy;
    }

    /**
     * Sets the buddy list strategy property
     *
     * @param buddyListStrategy BuddyListStrategy
     */
    public static void setBuddyListStrategy(BuddyListStrategy buddyListStrategy) {
        Environment.buddyListStrategy = buddyListStrategy;
    }

    /**
     * Enum for buddy list social relation
     */
    public enum BuddyListSocialRelation {

        /**
         * Unknown type of relation
         */
        TYPE_BI_UNKNOWN(0, "Unknown relation"),

        /**
         * Bi-directional connection
         */
        TYPE_BI_CONNECTION(12, "Connections"),

        /**
         * Coworker relation
         */
        TYPE_BI_COWORKER(1, "Coworkers"),

        /**
         * Friend relation
         */
        TYPE_BI_FRIEND(2, "Friends"),

        /**
         * Romantic partner relations
         */
        TYPE_BI_ROMANTIC_PARTNER(3, "Romantic Partners"),

        /**
         * Sibling relation
         */
        TYPE_BI_SIBLING(4, "Siblings");


        // Integer code which uniquely describes relation
        private int code;
        // String description of relation type
        private String description;

        /**
         * Private constructor
         *
         * @param code        that uniquely represents relation type
         * @param description string description of relation
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
    public static BuddyListSocialRelation[] getBuddyListSocialRelations() {
        return buddyListSocialRelations;
    }

    /**
     * Sets the buddy list allowed social relations
     *
     * @param relations BuddyListSocialRelation[]
     */
    public static void setBuddyListSocialRelations(BuddyListSocialRelation[] relations) {
        Environment.buddyListSocialRelations = relations;
    }

    /**
     * Returns maximal buddies count in list
     *
     * @return int
     */
    public static int getBuddyListMaxBuddies() {
        return buddyListMaxBuddies;
    }

    /**
     * Sets the buddy list max buddies property
     *
     * @param buddyListMaxBuddies Integer
     */
    public static void setBuddyListMaxBuddies(Integer buddyListMaxBuddies) {
        Environment.buddyListMaxBuddies = buddyListMaxBuddies;
    }

    /**
     * Returns maximal number of search result in buddy list
     *
     * @return int
     */
    public static int getBuddyListMaxSearch() {
        return buddyListMaxSearch;
    }

    /**
     * Sets the buddy list max search property
     *
     * @param buddyListMaxSearch Integer
     */
    public static void setBuddyListMaxSearch(Integer buddyListMaxSearch) {
        Environment.buddyListMaxSearch = buddyListMaxSearch;
    }

    /**
     * Returns an array of sites names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListSiteExcludes() {
        return buddyListSiteExcludes;
    }

    /**
     * Sets the buddy list site excludes property
     *
     * @param buddyListSiteExcludes String[]
     */
    public static void setBuddyListSiteExcludes(String[] buddyListSiteExcludes) {
        Environment.buddyListSiteExcludes = buddyListSiteExcludes;
    }

    /**
     * Returns an array of group names which should be excluded from
     * the buddy list
     *
     * @return String[]
     */
    public static String[] getBuddyListGroupExcludes() {
        return buddyListGroupExcludes;
    }

    /**
     * Sets the buddy list group excludes property
     *
     * @param buddyListGroupExcludes String[]
     */
    public static void setBuddyListGroupExcludes(String[] buddyListGroupExcludes) {
        Environment.buddyListGroupExcludes = buddyListGroupExcludes;
    }

    /**
     * Returns true if the default user should be ignored. Default user is the user which has
     * the defaultUser flag in database set to true.
     *
     * @return boolean
     */
    public static boolean getBuddyListIgnoreDefaultUser() {
        return buddyListIgnoreDefaultUser;
    }

    /**
     * Sets the buddy list ignore default user property
     *
     * @param buddyListIgnoreDefaultUser Boolean
     */
    public static void setBuddyListIgnoreDefaultUser(Boolean buddyListIgnoreDefaultUser) {
        Environment.buddyListIgnoreDefaultUser = buddyListIgnoreDefaultUser;
    }

    /**
     * Returns true if the deactivated user should be ignored. Deactivated user is the user which has
     * the status column set to 0 in database.
     *
     * @return boolean
     */
    public static boolean getBuddyListIgnoreDeactivatedUser() {
        return buddyListIgnoreDeactivatedUser;
    }

    /**
     * Sets the buddy list ignore deactivated user property
     *
     * @param buddyListIgnoreDeactivatedUser Boolean
     */
    public static void setBuddyListIgnoreDeactivatedUser(Boolean buddyListIgnoreDeactivatedUser) {
        Environment.buddyListIgnoreDeactivatedUser = buddyListIgnoreDeactivatedUser;
    }

    /**
     * Maximal count of messages in the conversation.
     *
     * @return int
     */
    public static int getConversationListMaxMessages() {
        return conversationListMaxMessages;
    }

    /**
     * Sets the conversation list max messages property
     *
     * @param conversationListMaxMessages Integer
     */
    public static void setConversationListMaxMessages(Integer conversationListMaxMessages) {
        Environment.conversationListMaxMessages = conversationListMaxMessages;
    }

    /**
     * Return true if communication via jabber is enabled
     *
     * @return boolean
     */
    public static boolean isJabberEnabled() {
        return jabberEnabled;
    }

    /**
     * Sets jabber enabled property
     *
     * @param jabberEnabled Boolean
     */
    public static void setJabberEnabled(Boolean jabberEnabled) {
        Environment.jabberEnabled = jabberEnabled;
    }

    /**
     * Returns jabber server host property
     *
     * @return String jabber host
     */
    public static String getJabberHost() {
        return jabberHost;
    }

    /**
     * Sets jabber host property
     *
     * @param jabberHost String
     */
    public static void setJabberHost(String jabberHost) {
        Environment.jabberHost = jabberHost;
    }

    /**
     * Returns jabber server port property
     *
     * @return int jabber port
     */
    public static int getJabberPort() {
        return jabberPort;
    }

    /**
     * Sets jabber port property
     *
     * @param jabberPort Integer
     */
    public static void setJabberPort(Integer jabberPort) {
        Environment.jabberPort = jabberPort;
    }

    /**
     * Returns jabber server service name property
     *
     * @return String jabber service name
     */
    public static String getJabberServiceName() {
        return jabberServiceName;
    }

    /**
     * Sets jabber service name property
     *
     * @param jabberServiceName String
     */
    public static void setJabberServiceName(String jabberServiceName) {
        Environment.jabberServiceName = jabberServiceName;
    }

    /**
     * Returns jabber server resource property
     *
     * @return String jabber resource
     */
    public static String getJabberResource() {
        return jabberResource;
    }

    /**
     * Sets jabber resource property
     *
     * @param jabberResource String
     */
    public static void setJabberResource(String jabberResource) {
        Environment.jabberResource = jabberResource;
    }

    /**
     * Returns true if jabber SOCK5 proxy mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isJabberSock5ProxyEnabled() {
        return jabberSock5ProxyEnabled;
    }

    /**
     * Sets jabber SOCK5 proxy enabled property
     *
     * @param jabberSock5ProxyEnabled Integer
     */
    public static void setJabberSock5ProxyEnabled(Boolean jabberSock5ProxyEnabled) {
        Environment.jabberSock5ProxyEnabled = jabberSock5ProxyEnabled;
    }

    /**
     * Returns jabber SOCK5 port
     *
     * @return int jabber SOCK5 port
     */
    public static int getJabberSock5ProxyPort() {
        return jabberSock5ProxyPort;
    }

    /**
     * Sets jabber SOCK5 proxy port property
     *
     * @param jabberSock5ProxyPort Integer
     */
    public static void setJabberSock5ProxyPort(Integer jabberSock5ProxyPort) {
        Environment.jabberSock5ProxyPort = jabberSock5ProxyPort;
    }

    /**
     * Returns true if the Jabber Import User mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isJabberImportUserEnabled() {
        return jabberImportUserEnabled;
    }

    /**
     * Sets jabber import user enabled property
     *
     * @param jabberImportUserEnabled Boolean
     */
    public static void setJabberImportUserEnabled(Boolean jabberImportUserEnabled) {
        Environment.jabberImportUserEnabled = jabberImportUserEnabled;
    }

    /**
     * Returns true if SASL authentication mechanism is enabled
     *
     * @return boolean
     */
    public static boolean isSaslPlainEnabled() {
        return saslPlainEnabled;
    }

    /**
     * Sets SASL plain enabled property
     *
     * @param saslPlainEnabled Boolean
     */
    public static void setSaslPlainEnabled(Boolean saslPlainEnabled) {
        Environment.saslPlainEnabled = saslPlainEnabled;
    }

    /**
     * Returns SASL Authentication ID
     *
     * @return String auth ID
     */
    public static String getSaslPlainAuthId() {
        return saslPlainAuthId;
    }

    /**
     * Sets SASL plain auth id property
     *
     * @param saslPlainAuthId String
     */
    public static void setSaslPlainAuthId(String saslPlainAuthId) {
        Environment.saslPlainAuthId = saslPlainAuthId;
    }

    /**
     * Returns password for SASL authentication property
     *
     * @return String password
     */
    public static String getSaslPlainPassword() {
        return saslPlainPassword;
    }

    /**
     * Sets SASL plain password property
     *
     * @param saslPlainPassword String
     */
    public static void setSaslPlainPassword(String saslPlainPassword) {
        Environment.saslPlainPassword = saslPlainPassword;
    }

    /**
     * Returns url string for the unsupported browser message
     *
     * @return String
     */
    public static String getUrlUnsupportedBrowser() {
        return urlUnsupportedBrowser;
    }

    /**
     * Sets url string for the unsupported browser
     *
     * @param urlUnsupportedBrowser String
     */
    public static void setUrlUnsupportedBrowser(String urlUnsupportedBrowser) {
        Environment.urlUnsupportedBrowser = urlUnsupportedBrowser;
    }

    /**
     * Returns url string for the help message
     *
     * @return String
     */
    public static String getUrlHelp() {
        return urlHelp;
    }

    /**
     * Set the url string for the help message
     *
     * @param urlHelp String
     */
    public static void setUrlHelp(String urlHelp) {
        Environment.urlHelp = urlHelp;
    }

    /**
     * Returns true if the error mode is enabled
     *
     * @return boolean
     */
    public static Boolean isErrorModeEnabled() {
        return errorModeEnabled;
    }

    /**
     * Sets error mode enabled property
     *
     * @param errorModeEnabled Boolean
     */
    public static void setErrorModeEnabled(Boolean errorModeEnabled) {
        Environment.errorModeEnabled = errorModeEnabled;
    }
}
