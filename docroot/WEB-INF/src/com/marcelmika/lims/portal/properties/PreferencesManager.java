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

import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.portal.domain.Properties;

import javax.portlet.PortletPreferences;

/**
 * Updates portlet preferences based on the properties
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 13/10/14
 * Time: 15:25
 */
public class PreferencesManager {

    /**
     * Updates portlet preferences based on the properties. If the property is null, nothing is updated
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    public void updatePortletPreferences(PortletPreferences preferences, Properties properties) throws Exception {

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
            updateBuddyListDefaultUser(preferences, properties);
        }

        // Buddy list ignore deactivated user
        if (properties.getBuddyListIgnoreDeactivatedUser() != null) {
            updateBuddyListDeactivatedUser(preferences, properties);
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
        Environment.setBuddyListStrategy(preferences);
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
        Environment.setBuddyListSocialRelations(preferences);
    }

    /**
     * Updates the buddy list default user property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListDefaultUser(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEFAULT_USER,
                String.valueOf(properties.getBuddyListIgnoreDefaultUser())
        );
        // Persist
        preferences.store();

        // Save in Environment
        Environment.setBuddyListIgnoreDefaultUser(preferences);
    }

    /**
     * Updates the buddy list deactivated user property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListDeactivatedUser(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER,
                String.valueOf(properties.getBuddyListIgnoreDeactivatedUser())
        );
        // Persist
        preferences.store();

        // Save in Environment
        Environment.setBuddyListIgnoreDeactivatedUser(preferences);
    }

    /**
     * Updates the buddy list max buddies property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListMaxBuddies(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_MAX_BUDDIES,
                String.valueOf(properties.getBuddyListMaxBuddies())
        );

        // Persists
        preferences.store();

        // Save in Environment
        Environment.setBuddyListMaxBuddies(preferences);
    }

    /**
     * Updates the buddy list max search property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateBuddyListMaxSearch(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.BUDDY_LIST_MAX_SEARCH,
                String.valueOf(properties.getBuddyListMaxSearch())
        );

        // Persist
        preferences.store();

        // Save in Environment
        Environment.setBuddyListMaxSearch(preferences);
    }

    /**
     * Updates conversation list max messages property
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updateConversationListMaxMessages(PortletPreferences preferences, Properties properties) throws Exception {

        // Set the value in portlet preferences
        preferences.setValue(
                PortletPropertiesKeys.CONVERSATION_LIST_MAX_MESSAGES,
                String.valueOf(properties.getConversationListMaxMessages())
        );

        // Persist
        preferences.store();

        // Save in Environment
        Environment.setConversationListMaxMessages(preferences);
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
        Environment.setBuddyListSiteExcludes(preferences);
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
        Environment.setBuddyListGroupExcludes(preferences);
    }
}
