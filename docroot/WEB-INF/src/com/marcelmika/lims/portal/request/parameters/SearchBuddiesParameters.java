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

package com.marcelmika.lims.portal.request.parameters;

import com.marcelmika.lims.portal.properties.InputLimits;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/14
 * Time: 20:02
 */
public class SearchBuddiesParameters {

    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        // Limit the maximal search query size
        if (searchQuery.length() > InputLimits.SEARCH_QUERY_MAX_SIZE) {
            searchQuery = searchQuery.substring(0, InputLimits.SEARCH_QUERY_MAX_SIZE);
        }

        this.searchQuery = searchQuery;
    }

    @Override
    public String toString() {
        return "SearchBuddiesParameters{" +
                "searchQuery='" + searchQuery + '\'' +
                '}';
    }
}
