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

package com.marcelmika.lims.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.environment.Environment.BuddyListSocialRelation;
import com.marcelmika.lims.portal.domain.Properties;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.portlet.PermissionDetector;
import com.marcelmika.lims.portal.properties.PortletPropertiesKeys;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.response.ResponseUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/10/14
 * Time: 12:16
 */
public class PropertiesController {

    // Log
    private static Log log = LogFactoryUtil.getLog(PropertiesController.class);

    /**
     * Patches properties
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void patchProperties(ResourceRequest request, ResourceResponse response) {

        // Check if the user is an admin
        if (!PermissionDetector.isAdmin(request)) {
            ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            return;
        }

        // Check preferences
        if (request.getPreferences() == null) {
            ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            return;
        }


        Properties properties;

        // Deserialize
        try {
            properties = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT), Properties.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }


        // Update preferences
        try {
            updatePortletPreferences(request.getPreferences(), properties);
        }
        // Failure
        catch (Exception exception) {
            // This is a server fault
            ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            // Log
            if (log.isErrorEnabled()) {
                log.error(exception);
            }
        }
    }

    /**
     * Updates portlet preferences based on the properties. If the property is null, nothing is updated
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    private void updatePortletPreferences(PortletPreferences preferences, Properties properties) throws Exception {

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
        BuddyListSocialRelation[] relations = properties.getBuddyListSocialRelations();
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

}

