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

package com.marcelmika.lims.portal.processor;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * Portlet processor contains business logic which decides what controller
 * should be called based on the query received in request parameter.
 * This is an important part of the system. Every time you introduce new controller
 * or add a method to the existing one you need to add mapping to the processRequest() method.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 6:01 PM
 */
public interface PortletProcessor {

    /**
     * Adds request further to the system and writes data to response if needed.
     * Contains logic that decides which resource should be accessed.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void processRequest(ResourceRequest request, ResourceResponse response);

}
