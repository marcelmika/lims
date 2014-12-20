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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.environment.Environment.BuddyListSocialRelation;
import com.marcelmika.lims.api.environment.Environment.BuddyListSource;
import com.marcelmika.lims.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.lims.api.environment.Environment.PropertiesSource;
import com.marcelmika.lims.portal.domain.Properties;

import javax.portlet.PortletPreferences;
import java.util.HashSet;
import java.util.Set;

/**
 * Updates portlet preferences based on the properties
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 13/10/14
 * Time: 15:25
 */
public class PropertiesManagerImpl implements PropertiesManager {

    // Log
    private static Log log = LogFactoryUtil.getLog(PropertiesManagerImpl.class);

    // Constants
    private static final int BUDDY_LIST_MAX_BUDDIES_DEFAULT = 200;
    private static final int BUDDY_LIST_MAX_BUDDIES_MIN = 10;
    private static final int BUDDY_LIST_MAX_BUDDIES_MAX = 500;

    private static final int BUDDY_LIST_MAX_SEARCH_DEFAULT = 10;
    private static final int BUDDY_LIST_MAX_SEARCH_MIN = 5;
    private static final int BUDDY_LIST_MAX_SEARCH_MAX = 50;

    private static final int CONVERSATION_LIST_MAX_MESSAGES_DEFAULT = 100;
    private static final int CONVERSATION_LIST_MAX_MESSAGES_MIN = 10;
    private static final int CONVERSATION_LIST_MAX_MESSAGES_MAX = 200;

    // Set to true if the environment was already set up
    private boolean isSetup = false;

    /**
     * Sets up all portlet properties. Decides which source of properties should be taken into account.
     * It doesn't matter how many times the method is called. The setup will be called just once.
     *
     * @param preferences PortletPreferences
     */
    @Override
    public void setup(PortletPreferences preferences) {

        // Preferences cannot be null
        if (preferences == null) {
            // Log
            if (log.isErrorEnabled()) {
                log.error("Cannot load preferences");
            }
            // End here
            return;
        }

        // Setup just once, there is no need to set it on every call of the setup method
        if (!isSetup) {

            // Setup can be done just once at the beginning
            isSetup = true;

            // Log
            if (log.isDebugEnabled()) {
                log.debug("Settings the environment");
            }

            // Set properties source
            setupPropertiesSource();

            // Set the whole environment
            setupBuddyListSource();
            setupExcludedSites(preferences);
            setupBuddyListStrategy(preferences);
            setupBuddyListSocialRelations(preferences);
            setupBuddyListIgnoreDefaultUser(preferences);
            setupBuddyListIgnoreDeactivatedUser(preferences);
            setupBuddyListMaxBuddies(preferences);
            setupBuddyListMaxSearch(preferences);
            setupConversationListMaxMessages(preferences);
            setupBuddyListSiteExcludes(preferences);
            setupBuddyListGroupExcludes(preferences);
            setupErrorMode();

            // Set url properties
            setUrlProperties();

            // Set jabber related properties
            setupJabberProperties();
        }
    }

    /**
     * Updates portlet preferences based on the properties. Preferences are stored and updated
     * in current instance of Environment as well. Thanks to that the changes are visible immediately.
     * If the property in the properties object is null, nothing is updated.
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    @Override
    public void updatePortletPreferences(PortletPreferences preferences, Properties properties) throws Exception {

        // Excluded sites
        if (properties.getExcludedSites() != null) {
            updateExcludedSites(preferences, properties);
        }

        // Buddy list strategy
        if (properties.getBuddyListStrategy() != null) {
            updateBuddyListStrategy(preferences, properties);
        }

        // Buddy list social relations
        if (properties.getBuddyListSocialRelations() != null) {
            updateBuddyListSocialRelations(preferences, properties);
        }

        // Buddy list ignore default user
        if (properties.getBuddyListIgnoreDefaultUser() != null) {
            updateBuddyListIgnoreDefaultUser(preferences, properties);
        }

        // Buddy list ignore deactivated user
        if (properties.getBuddyListIgnoreDeactivatedUser() != null) {
            updateBuddyListIgnoreDeactivatedUser(preferences, properties);
        }

        // Buddy list max buddies
        if (properties.getBuddyListMaxBuddies() != null) {
            updateBuddyListMaxBuddies(preferences, properties);
        }

        // Buddy list max search
        if (properties.getBuddyListMaxSearch() != null) {
            updateBuddyListMaxSearch(preferences, properties);
        }

        // Conversation list max messages
        if (properties.getConversationListMaxMessages() != null) {
            updateConversationListMaxMessages(preferences, properties);
        }

        // Buddy list site excludes
        if (properties.getBuddyListSiteExcludes() != null) {
            updateBuddyListSiteExcludes(preferences, properties);
        }

        // Buddy list group excludes
        if (properties.getBuddyListGroupExcludes() != null) {
            updateBuddyListGroupExcludes(preferences, properties);
        }
    }

    /**
     * Sets properties source
     */
    private void setupPropertiesSource() {
        String value = PortletPropertiesValues.PROPERTIES_SOURCE;

        PropertiesSource propertiesSource;

        // Preferences
        if (value.equals("preferences")) {
            propertiesSource = PropertiesSource.PREFERENCES;
        }
        // Properties
        else if (value.equals("properties")) {
            propertiesSource = PropertiesSource.PROPERTIES;
        }
        // Unknown value
        else {
            log.error(String.format(
                    "Unknown properties source %s. Valid values are \"preferences\" or \"properties\". Since no valid " +
                            "property was provided \"preferences\" was chosen as a default. The value can be " +
                            "set in portlet-ext.properties file related to the LIMS portlet.", value
            ));
            // Fallback to default
            propertiesSource = PropertiesSource.PREFERENCES;
        }

        // Save to Environment
        Environment.setPropertiesSource(propertiesSource);
    }

    /**
     * Sets the excluded sites property
     *
     * @param preferences PortletPreferences
     */
    private void setupExcludedSites(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String[] excludedSites;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            excludedSites = preferences.getValues(
                    PortletPropertiesKeys.EXCLUDED_SITES,
                    PortletPropertiesValues.EXCLUDED_SITES
            );
        }
        // Properties
        else {
            excludedSites = PortletPropertiesValues.EXCLUDED_SITES;
        }

        // Save in Environment
        Environment.setExcludedSites(excludedSites);
    }

    /**
     * Updates excluded sites property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateExcludedSites(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.EXCLUDED_SITES,
                properties.getExcludedSites()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupExcludedSites(preferences);
    }

    /**
     * Sets buddy list source
     */
    private void setupBuddyListSource() {
        String value = PortletPropertiesValues.BUDDY_LIST_SOURCE;

        BuddyListSource buddyListSource;

        // Liferay
        if (value.equals("liferay")) {
            buddyListSource = BuddyListSource.LIFERAY;
        }
        // Jabber
        else if (value.equals("jabber")) {
            buddyListSource = BuddyListSource.JABBER;
        }
        // Unknown value
        else {
            log.error(String.format(
                    "Unknown buddy list source: %s. Valid values are \"liferay\" or \"jabber\". Since no valid " +
                            "property was provided \"liferay\" was chosen as a default. The value can be " +
                            "set in portlet-ext.properties file related to the LIMS portlet.", value
            ));

            // Fallback to default
            buddyListSource = BuddyListSource.LIFERAY;
        }

        // Save to Environment
        Environment.setBuddyListSource(buddyListSource);
    }

    /**
     * Updates buddy list strategy preferences
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListStrategy(PortletPreferences preferences, Properties properties) throws Exception {

        // Reset previous preferences
        preferences.reset(PortletPropertiesKeys.BUDDY_LIST_STRATEGY);

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_STRATEGY,
                properties.getBuddyListStrategy().getDescription()
        );

        // Persist
        preferences.store();

        // Save in environment
        setupBuddyListStrategy(preferences);
    }

    /**
     * Sets the buddy list strategy
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListStrategy(PortletPreferences preferences) {
        // Get the property source
        PropertiesSource source = Environment.getPropertiesSource();

        String value;
        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preference. Value from properties is a default.
            value = preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_STRATEGY,
                    PortletPropertiesValues.BUDDY_LIST_STRATEGY
            );
        }
        // Properties
        else {
            // Take the value from properties
            value = PortletPropertiesValues.BUDDY_LIST_STRATEGY;
        }

        BuddyListStrategy buddyListStrategy;

        // All buddies in list
        if (value.equals(BuddyListStrategy.ALL.getDescription())) {
            buddyListStrategy = BuddyListStrategy.ALL;
        }
        // Sites
        else if (value.equals(BuddyListStrategy.SITES.getDescription())) {
            buddyListStrategy = BuddyListStrategy.SITES;
        }
        // Social
        else if (value.equals(BuddyListStrategy.SOCIAL.getDescription())) {
            buddyListStrategy = BuddyListStrategy.SOCIAL;
        }
        // Sites and Social
        else if (value.equals(BuddyListStrategy.SITES_AND_SOCIAL.getDescription())) {
            buddyListStrategy = BuddyListStrategy.SITES_AND_SOCIAL;
        }
        // Groups
        else if (value.equals(BuddyListStrategy.USER_GROUPS.getDescription())) {
            buddyListStrategy = BuddyListStrategy.USER_GROUPS;
        }
        // Unknown value
        else {
            log.error(String.format(
                    "Unknown buddy list strategy: %s. Valid values are \"all\", \"sites\", \"social\", \"sites," +
                            "social\". Since no valid property provided \"all\" was chosen as a default. The value " +
                            "can be set in portlet-ext.properties file related to the LIMS portlet.", value
            ));

            buddyListStrategy = BuddyListStrategy.ALL;
        }

        // Save in environment
        Environment.setBuddyListStrategy(buddyListStrategy);
    }

    /**
     * Updates buddy list social relations preferences
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListSocialRelations(PortletPreferences preferences, Properties properties) throws Exception {

        // Social relations needs to be mapped to the string alternatives so they can
        // be moved to the preferences
        Environment.BuddyListSocialRelation[] relations = properties.getBuddyListSocialRelations();
        String[] relationStrings = new String[relations.length];

        for (int i = 0; i < relationStrings.length; i++) {
            relationStrings[i] = String.valueOf(relations[i].getCode());
        }

        // Reset previous preferences
        preferences.reset(PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES);

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES,
                relationStrings
        );

        // Persist
        preferences.store();

        // Save in environment
        setupBuddyListSocialRelations(preferences);
    }

    /**
     * Sets the buddy list allowed social relations
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListSocialRelations(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        int[] relationsTypeCodes;
        // Preferences
        if (source == PropertiesSource.PREFERENCES) {

            // Take the value from preferences
            String[] values = preferences.getValues(
                    PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES, null
            );

            // There is already some values set in preferences
            if (values != null) {
                // However, the values are stored as string values so we first need to parse them.
                // Create new array of integers with the same size as the values gotten from preferences.
                relationsTypeCodes = new int[values.length];

                // Iterate over relation codes
                for (int i = 0; i < relationsTypeCodes.length; i++) {
                    // And parse each code from string to integer
                    relationsTypeCodes[i] = Integer.parseInt(values[i]);
                }
            }
            // Nothing was set in preferences so take it from properties
            else {
                relationsTypeCodes = PortletPropertiesValues.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES;
            }

        }
        // Properties
        else {
            relationsTypeCodes = PortletPropertiesValues.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES;
        }

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

        BuddyListSocialRelation[] buddyListSocialRelations;

        // Nothing was mapped at the end.
        // This means that no relation was selected or the relation code was wrong.
        if (relationTypeSet.size() == 0) {
            // Log error
            log.error("No buddy list social relation were mapped. This means that either no social relation was " +
                    "selected or it was wrong. Since the property is required \"12 - " +
                    "Connections\" was selected as default. The value can be set in portlet-ext.properties file " +
                    "related to the LIMS portlet.");

            // Connection type is default
            buddyListSocialRelations = new BuddyListSocialRelation[]{BuddyListSocialRelation.TYPE_BI_CONNECTION};
        } else {

            // Map set to array
            buddyListSocialRelations = relationTypeSet.toArray(new BuddyListSocialRelation[relationTypeSet.size()]);
        }

        // Save to environment
        Environment.setBuddyListSocialRelations(buddyListSocialRelations);
    }

    /**
     * Updates the buddy list default user property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListIgnoreDefaultUser(PortletPreferences preferences,
                                                  Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEFAULT_USER,
                String.valueOf(properties.getBuddyListIgnoreDefaultUser())
        );
        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListIgnoreDefaultUser(preferences);
    }

    /**
     * Sets the buddy list ignore default user property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListIgnoreDefaultUser(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean buddyListIgnoreDefaultUser;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListIgnoreDefaultUser = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEFAULT_USER,
                    String.valueOf(PortletPropertiesValues.BUDDY_LIST_IGNORE_DEFAULT_USER)
            ));
        }
        // Properties
        else {
            buddyListIgnoreDefaultUser = PortletPropertiesValues.BUDDY_LIST_IGNORE_DEFAULT_USER;
        }

        // Save in environment
        Environment.setBuddyListIgnoreDefaultUser(buddyListIgnoreDefaultUser);
    }

    /**
     * Updates the buddy list deactivated user property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListIgnoreDeactivatedUser(PortletPreferences preferences,
                                                      Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER,
                String.valueOf(properties.getBuddyListIgnoreDeactivatedUser())
        );
        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListIgnoreDeactivatedUser(preferences);
    }

    /**
     * Sets the buddy list ignore deactivated user property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListIgnoreDeactivatedUser(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        Boolean buddyListIgnoreDeactivatedUser;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListIgnoreDeactivatedUser = Boolean.parseBoolean(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER,
                    String.valueOf(PortletPropertiesValues.BUDDY_LIST_IGNORE_DEACTIVATED_USER)
            ));
        }
        // Properties
        else {
            buddyListIgnoreDeactivatedUser = PortletPropertiesValues.BUDDY_LIST_IGNORE_DEACTIVATED_USER;
        }

        // Save in Environment
        Environment.setBuddyListIgnoreDeactivatedUser(buddyListIgnoreDeactivatedUser);
    }

    /**
     * Updates the buddy list max buddies property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListMaxBuddies(PortletPreferences preferences, Properties properties) throws Exception {

        // Get the value from properties
        Integer value = validateValueScope(
                properties.getBuddyListMaxBuddies(),
                "buddy list max buddies",
                BUDDY_LIST_MAX_BUDDIES_MIN,
                BUDDY_LIST_MAX_BUDDIES_MAX,
                BUDDY_LIST_MAX_BUDDIES_DEFAULT
        );

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_MAX_BUDDIES,
                String.valueOf(value)
        );

        // Persists
        preferences.store();

        // Save in Environment
        setupBuddyListMaxBuddies(preferences);
    }

    /**
     * Sets the buddy list max buddies property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListMaxBuddies(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.BUDDY_LIST_MAX_BUDDIES,
                "buddy list max buddies",
                BUDDY_LIST_MAX_BUDDIES_MIN,
                BUDDY_LIST_MAX_BUDDIES_MAX,
                BUDDY_LIST_MAX_BUDDIES_DEFAULT
        );

        // Prepare the value that will be returned
        Integer buddyListMaxBuddies;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListMaxBuddies = Integer.parseInt(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_MAX_BUDDIES,
                    String.valueOf(value)
            ));
        }
        // Properties
        else {
            buddyListMaxBuddies = value;
        }

        // Save in Environment
        Environment.setBuddyListMaxBuddies(buddyListMaxBuddies);
    }

    /**
     * Updates the buddy list max search property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListMaxSearch(PortletPreferences preferences, Properties properties) throws Exception {

        // Get the value from properties
        Integer value = validateValueScope(
                properties.getBuddyListMaxSearch(),
                "buddy list max search",
                BUDDY_LIST_MAX_SEARCH_MIN,
                BUDDY_LIST_MAX_SEARCH_MAX,
                BUDDY_LIST_MAX_SEARCH_DEFAULT
        );

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_MAX_SEARCH,
                String.valueOf(value)
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListMaxSearch(preferences);
    }

    /**
     * Sets the buddy list max search property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListMaxSearch(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.BUDDY_LIST_MAX_SEARCH,
                "buddy list max search",
                BUDDY_LIST_MAX_SEARCH_MIN,
                BUDDY_LIST_MAX_SEARCH_MAX,
                BUDDY_LIST_MAX_SEARCH_DEFAULT
        );

        // Prepare the value that will be returned
        Integer buddyListMaxSearch;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListMaxSearch = Integer.parseInt(preferences.getValue(
                    PortletPropertiesKeys.BUDDY_LIST_MAX_SEARCH,
                    String.valueOf(value)
            ));
        }
        // Properties
        else {
            buddyListMaxSearch = value;
        }

        // Save in Environment
        Environment.setBuddyListMaxSearch(buddyListMaxSearch);
    }


    /**
     * Updates conversation list max messages property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateConversationListMaxMessages(PortletPreferences preferences, Properties properties) throws Exception {

        // Get the value from properties
        Integer value = validateValueScope(
                properties.getConversationListMaxMessages(),
                "conversation list max messages",
                CONVERSATION_LIST_MAX_MESSAGES_MIN,
                CONVERSATION_LIST_MAX_MESSAGES_MAX,
                CONVERSATION_LIST_MAX_MESSAGES_DEFAULT
        );

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.CONVERSATION_LIST_MAX_MESSAGES,
                String.valueOf(value)
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupConversationListMaxMessages(preferences);
    }

    /**
     * Sets the conversation list max messages property
     *
     * @param preferences PortletPreferences
     */
    private void setupConversationListMaxMessages(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        // Get the value from properties
        Integer value = validateValueScope(
                PortletPropertiesValues.CONVERSATION_LIST_MAX_MESSAGES,
                "conversation list max messages",
                CONVERSATION_LIST_MAX_MESSAGES_MIN,
                CONVERSATION_LIST_MAX_MESSAGES_MAX,
                CONVERSATION_LIST_MAX_MESSAGES_DEFAULT
        );

        // Prepare the value that will be returned
        Integer conversationListMaxMessages;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            conversationListMaxMessages = Integer.parseInt(preferences.getValue(
                    PortletPropertiesKeys.CONVERSATION_LIST_MAX_MESSAGES,
                    String.valueOf(value)
            ));
        }
        // Properties
        else {
            conversationListMaxMessages = value;
        }

        // Save in Environment
        Environment.setConversationListMaxMessages(conversationListMaxMessages);
    }

    /**
     * Updates buddy list site excludes property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListSiteExcludes(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.BUDDY_LIST_SITE_EXCLUDES,
                properties.getBuddyListSiteExcludes()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListSiteExcludes(preferences);
    }

    /**
     * Sets the buddy list site excludes property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListSiteExcludes(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String[] buddyListSiteExcludes;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListSiteExcludes = preferences.getValues(
                    PortletPropertiesKeys.BUDDY_LIST_SITE_EXCLUDES,
                    PortletPropertiesValues.BUDDY_LIST_SITE_EXCLUDES
            );
        }
        // Properties
        else {
            buddyListSiteExcludes = PortletPropertiesValues.BUDDY_LIST_SITE_EXCLUDES;
        }

        // Save in Environment
        Environment.setBuddyListSiteExcludes(buddyListSiteExcludes);
    }

    /**
     * Updates buddy list group excludes property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListGroupExcludes(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValues(
                PortletPropertiesKeys.BUDDY_LIST_GROUP_EXCLUDES,
                properties.getBuddyListGroupExcludes()
        );

        // Persist
        preferences.store();

        // Save in Environment
        setupBuddyListGroupExcludes(preferences);
    }

    /**
     * Sets the buddy list group excludes property
     *
     * @param preferences PortletPreferences
     */
    private void setupBuddyListGroupExcludes(PortletPreferences preferences) {
        // Get the properties source
        PropertiesSource source = Environment.getPropertiesSource();

        String[] buddyListGroupExcludes;

        // Preferences
        if (source == PropertiesSource.PREFERENCES) {
            // Take the value from preferences
            buddyListGroupExcludes = preferences.getValues(
                    PortletPropertiesKeys.BUDDY_LIST_GROUP_EXCLUDES,
                    PortletPropertiesValues.BUDDY_LIST_GROUP_EXCLUDES
            );
        }
        // Properties
        else {
            buddyListGroupExcludes = PortletPropertiesValues.BUDDY_LIST_GROUP_EXCLUDES;
        }

        // Save in Environment
        Environment.setBuddyListGroupExcludes(buddyListGroupExcludes);
    }

    /**
     * Sets jabber related properties
     */
    private void setupJabberProperties() {

        // Set jabber properties to Environment
        // TODO: Uncomment when implemented
//        Environment.setJabberEnabled(PortletPropertiesValues.JABBER_ENABLED);
        Environment.setJabberEnabled(false);
        Environment.setJabberHost(PortletPropertiesValues.JABBER_HOST);
        Environment.setJabberPort(PortletPropertiesValues.JABBER_PORT);
        Environment.setJabberServiceName(PortletPropertiesValues.JABBER_SERVICE_NAME);
        Environment.setJabberResource(PortletPropertiesValues.JABBER_RESOURCE);
        Environment.setJabberSock5ProxyEnabled(PortletPropertiesValues.JABBER_SOCK5_PROXY_ENABLED);
        Environment.setJabberSock5ProxyPort(PortletPropertiesValues.JABBER_SOCK5_PROXY_PORT);
        Environment.setJabberImportUserEnabled(PortletPropertiesValues.JABBER_IMPORT_USER_ENABLED);
        Environment.setSaslPlainEnabled(PortletPropertiesValues.JABBER_SASL_PLAIN_ENABLED);
        Environment.setSaslPlainAuthId(PortletPropertiesValues.JABBER_SASL_PLAIN_AUTHID);
        Environment.setSaslPlainPassword(PortletPropertiesValues.JABBER_SASL_PLAIN_PASSWORD);

    }

    /**
     * Setups the error mode
     */
    private void setupErrorMode() {
        Environment.setErrorModeEnabled(PortletPropertiesValues.ERROR_MODE_ENABLED);
    }

    /**
     * Sets url related properties
     */
    private void setUrlProperties() {

        // Set url properties
        Environment.setUrlHelp(PortletPropertiesValues.URL_HELP);
        Environment.setUrlUnsupportedBrowser(PortletPropertiesValues.URL_UNSUPPORTED_BROWSER);

    }

    /**
     * Validates the value if it's in a proper scope. Returns the default
     * value if the provided value is not in the scope
     *
     * @param value        that should be examined
     * @param name         of the property that is represented by the value
     * @param min          scope minimum
     * @param max          scope maximum
     * @param defaultValue which is returned if the value is out of scope
     * @return validated value
     */
    private Integer validateValueScope(Integer value, String name, Integer min, Integer max, Integer defaultValue) {
        // Check the value scope
        if (value < min || value > max) {
            // Log
            if (log.isInfoEnabled()) {
                log.info(String.format(
                        "The value of %s property is out of scope: %d. Value should be between %d - %d. Setting" +
                                " the %s value to default: %d. To get rid of the message check the" +
                                " portlet.properties file and update the value.",
                        name, value, min, max, name, defaultValue));
            }

            // Return default
            return defaultValue;
        }

        // Value is ok
        return value;
    }
}
