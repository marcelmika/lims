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

package com.marcelmika.lims.portal.serialization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/02/15
 * Time: 11:38
 */
public class SerializationUtil {

    /**
     * This method removes all the key:value pairs from the Json String for which the value equals null
     *
     * @param json with null properties
     * @return json without null properties
     */
    public static String excludeNullProperties(String json) {

        // Validate input
        if (json == null || json.length() == 0) {
            return json;
        }

        // Patter used to find the null properties
        Pattern pattern = Pattern.compile("([,]?\"[^\"]*\":null[,]?)+");
        // Matcher finds the pattern in json
        Matcher matcher = pattern.matcher(json);

        // Prepare string buffer used to build the output string
        StringBuffer newString = new StringBuffer(json.length());

        // Go over the whole string
        while (matcher.find()) {
            if (matcher.group().startsWith(",") & matcher.group().endsWith(",")) {
                matcher.appendReplacement(newString, ",");
            } else {
                matcher.appendReplacement(newString, "");
            }
        }
        // Append the tail to matcher
        matcher.appendTail(newString);

        // Return json without null properties
        return newString.toString();
    }
}
