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
import com.marcelmika.lims.portal.domain.Properties;
import com.marcelmika.lims.portal.http.HttpStatus;
import com.marcelmika.lims.portal.portlet.PermissionDetector;
import com.marcelmika.lims.portal.properties.PropertiesManager;
import com.marcelmika.lims.portal.request.RequestParameterKeys;
import com.marcelmika.lims.portal.response.ResponseUtil;

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

    // Dependencies
    PropertiesManager propertiesManager;

    /**
     * Constructor
     *
     * @param propertiesManager Preferences
     */
    public PropertiesController(final PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

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
            if (log.isErrorEnabled()) {
                log.error("Cannot find preferences in request. This shouldn't normally happen.");
            }
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
            propertiesManager.updatePortletPreferences(request.getPreferences(), properties);
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
}

