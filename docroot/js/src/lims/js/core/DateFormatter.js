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

/**
 * Date Formatter
 *
 * Contains function that handle formatting of date
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.DateFormatter = Y.Base.create('dateFormatter', Y.Base, [], {

    /**
     * Returns human readable relative representation of time given in parameter
     *
     * @param time
     * @returns {string}
     */
    prettyDate: function (time) {
        var date = new Date(time || ""),
            diff = (((new Date()).getTime() - date.getTime()) / 1000),
            day_diff = Math.floor(diff / 86400);

        if (isNaN(day_diff) || day_diff < 0 || day_diff >= 31) {
            return "";
        }

        return day_diff === 0 && (
            diff < 60 && "just now" ||
            diff < 120 && "a minute ago" ||
            diff < 3600 && Math.floor(diff / 60) + " minutes ago" ||
            diff < 7200 && "an hour ago" ||
            diff < 86400 && Math.floor(diff / 3600) + " hours ago") ||
            day_diff === 1 && "Yesterday" ||
            day_diff < 7 && day_diff + " days ago" ||
            day_diff < 31 && Math.ceil(day_diff / 7) + " weeks ago";
    }

});
