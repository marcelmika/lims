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

package com.marcelmika.lims.portal.portlet;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;

import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 01/10/14
 * Time: 09:00
 */
public class BrowserDetector {

    // Minimal supported version of Internet Explorer
    private static final float MIN_SUPPORTED_VERSION_IE = 8;

    // Log
    private static Log log = LogFactoryUtil.getLog(BrowserDetector.class);

    /**
     * Returns true if the browser that sent the request is supported
     *
     * @param request RenderRequest
     * @return true if the browser is supported
     */
    public static boolean isSupportedBrowser(RenderRequest request) {
        // Cast to http servlet request
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);

        return isSupportedBrowser(httpServletRequest);
    }

    /**
     * Returns true if the browser that sent the request is supported
     *
     * @param request HttpServletRequest
     * @return true if the browser is supported
     */
    public static boolean isSupportedBrowser(HttpServletRequest request) {
        // Check the availability of all browsers
        return validateInternetExplorer(request);
    }

    /**
     * Returns true if the browser that sent the request needs support for older
     * internet explorer browsers.
     *
     * @param request RenderRequest
     * @return true if the browser needs internet explorer support
     */
    public static boolean needsInternetExplorerSupport(RenderRequest request) {
        // Cast to http servlet request
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);

        // Check the availability of Internet Explorer
        boolean isIE = BrowserSnifferUtil.isIe(httpServletRequest);
        float majorVersion = BrowserSnifferUtil.getMajorVersion(httpServletRequest);

        return isIE && majorVersion <= 8;
    }

    /**
     * Returns false if the browser that sent the request is internet explorer in a version
     * that is not supported.
     *
     * @param request HttpServletRequest
     * @return true if the request is valid
     */
    private static boolean validateInternetExplorer(HttpServletRequest request) {

        // Check the availability of Internet Explorer
        boolean isIE = BrowserSnifferUtil.isIe(request);
        float majorVersion = BrowserSnifferUtil.getMajorVersion(request);

        // If it's not IE we stop validation
        return !isIE || majorVersion >= MIN_SUPPORTED_VERSION_IE;
    }
}
