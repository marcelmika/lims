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

import com.marcelmika.lims.portal.domain.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 14/10/14
 * Time: 10:00
 */
public interface PropertiesManager {

    /**
     * Sets up all portlet properties. Decides which source of properties should be taken into account.
     * It doesn't matter how many times the method is called. The setup will be called just once.
     *
     * @param preferences PortletPreferences
     */
    public void setup(PortletPreferences preferences);

    /**
     * Updates portlet preferences based on the properties. Preferences are stored and updated
     * in current instance of Environment as well. Thanks to that the changes are visible immediately.
     * If the property in the properties object is null, nothing is updated.
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    public void updatePortletPreferences(PortletPreferences preferences, Properties properties) throws Exception;

}
