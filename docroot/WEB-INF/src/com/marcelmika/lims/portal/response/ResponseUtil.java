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

package com.marcelmika.lims.portal.response;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.portal.domain.ErrorMessage;
import com.marcelmika.lims.portal.http.HttpStatus;

import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:41 PM
 */
public class ResponseUtil {

    // Log
    private static Log log = LogFactoryUtil.getLog(ResponseUtil.class);

    /**
     * Sets status code to the response. Use for no-content responses.
     *
     * @param statusCode HTTP Status code
     * @param response   Resource response
     */
    public static void writeResponse(HttpStatus statusCode, ResourceResponse response) {
        // We will pass additional error message as a content to make the server API more readable
        String content = null;

        // 400
        if (statusCode == HttpStatus.BAD_REQUEST) {
            content = ErrorMessage.badRequest().serialize();
        }
        // 401
        else if (statusCode == HttpStatus.UNAUTHORIZED) {
            content = ErrorMessage.unauthorized().serialize();
        }
        // 403
        else if (statusCode == HttpStatus.FORBIDDEN) {
            content = ErrorMessage.forbidden().serialize();
        }
        // 404
        else if (statusCode == HttpStatus.NOT_FOUND) {
            content = ErrorMessage.notFound().serialize();
        }
        // 409
        else if (statusCode == HttpStatus.CONFLICT) {
            content = ErrorMessage.conflict().serialize();
        }
        // 417
        else if (statusCode == HttpStatus.REQUEST_ENTITY_TOO_LARGE) {
            content = ErrorMessage.requestEntityTooLarge().serialize();
        }
        // 500
        else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
            content = ErrorMessage.internalServerError().serialize();
        }

        // Write content to the response
        writeResponse(content, statusCode, response);
    }

    /**
     * Takes the response and writes a content given in parameter and sets status code.
     *
     * @param content    Which will be written to the response
     * @param statusCode HTTP Status code
     * @param response   Resource response
     */
    public static void writeResponse(String content, HttpStatus statusCode, ResourceResponse response) {

        // Write the content to the output stream
        if (content != null) {
            // Get the writer
            PrintWriter writer = getResponseWriter(response);
            // If it fails it returns null. So write the content only if we have the writer.
            if (writer != null) {
                writer.print(content);
            }
        }

        // Set status code
        response.setProperty(ResourceResponse.HTTP_STATUS_CODE, statusCode.toString());

        // Disable caching. It needs to be here because Internet Explorer aggressively caches
        // ajax requests.
        // @see http://www.dashbay.com/2011/05/internet-explorer-caches-ajax/
        response.addProperty("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.addProperty("Pragma", "no-cache"); // HTTP 1.0
        response.addProperty("Expires", "0"); // Proxies

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("RESPONSE STATUS CODE: %s", statusCode.toString()));
        }
    }

    /**
     * Returns writer from response, null on error
     *
     * @param response ResourceResponse
     * @return PrintWriter, null on error
     */
    public static PrintWriter getResponseWriter(ResourceResponse response) {
        PrintWriter writer = null;

        try {
            // Get the writer from response
            writer = response.getWriter();
        }
        // Failure
        catch (IOException e) {
            // Log
            if (log.isErrorEnabled()) {
                log.error(e);
            }
        }

        return writer;
    }

}
