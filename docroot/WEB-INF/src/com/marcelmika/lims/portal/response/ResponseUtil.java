package com.marcelmika.lims.portal.response;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
        writeResponse(null, statusCode, response);
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
            writer = response.getWriter();
        } catch (IOException e) {
            log.error(e);
        }

        return writer;
    }

}
