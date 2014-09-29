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
        // Vars
        var ranges = this.get('localizedRanges'),   // Object with textual representation of time ranges
            date = new Date(time || ""),            // Date object created from time
            diff,                                   // Diff between now and date in seconds
            dayDiff;                                // Diff between now and date in days

        // Count the range from now to the time from parameter in seconds
        diff = (((new Date()).getTime() - date.getTime()) / 1000);
        // Count the range in days
        dayDiff = Math.floor(diff / 86400);

        // It is possible that the diff is in the past. This might happen when
        // there is e.g. latency on the network. If such inconsistency happens return
        // "just now"
        if (dayDiff < 0) {
            return ranges.justNow;
        }

        // Unknown value or value above possible ranges
        if (isNaN(dayDiff) || dayDiff >= 31) {
            return "";
        }

        // Within one day
        if (dayDiff === 0) {
            return this._lessThanDay(diff);
        }
        // Exactly one day
        else if (dayDiff === 1) {
            return this._yesterday();
        }
        // More than one day but less than a week
        else if (dayDiff < 7) {
            return this._lastThanWeek(dayDiff);
        }
        // More than a week
        else if (dayDiff < 31) {
            return this._moreThanWeek(dayDiff);
        }
        // Unknown value
        else {
            return '';
        }
    },

    /**
     * Returns a textual representation of a time range
     * which is in a range of a day
     *
     * @param seconds number of seconds
     * @returns {string}
     * @private
     */
    _lessThanDay: function (seconds) {

        // Vars
        var ranges = this.get('localizedRanges');

        // Now
        if (seconds < 60) {
            return ranges.justNow;
        }
        // A minute ago
        else if (seconds < 120) {
            return ranges.minuteAgo;
        }
        // Minutes ago
        else if (seconds < 3600) {
            return Y.Lang.sub(ranges.minutesAgo, {
                x: Math.floor(seconds / 60)
            });
        }
        // An hour ago
        else if (seconds < 7200) {
            return ranges.hourAgo;
        }
        // Hours ago
        else {
            return Y.Lang.sub(ranges.hoursAgo, {
                x: Math.floor(seconds / 3600)
            });
        }
    },

    /**
     * Returns a textual representation of a time range
     * which is in a range of the previous day
     *
     * @returns {string}
     * @private
     */
    _yesterday: function () {
        // Vars
        var ranges = this.get('localizedRanges');
        // Simply return yesterday time range, there is no need
        // to substitute e.g. number of days
        return ranges.yesterday;
    },

    /**
     * Returns a textual representation of a time range
     * which is in the less than a week range
     *
     * @param days
     * @returns {string}
     * @private
     */
    _lastThanWeek: function (days) {
        // Vars
        var ranges = this.get('localizedRanges');

        // Substitute number of days in a range
        return Y.Lang.sub(ranges.daysAgo, {
            x: days
        });
    },

    /**
     * Returns a textual representation of a time range
     * which is in the more than a week range
     *
     * @param days
     * @returns {string}
     * @private
     */
    _moreThanWeek: function (days) {
        // Vars
        var ranges = this.get('localizedRanges');

        // Substitute number of days in a range
        return Y.Lang.sub(ranges.weeksAgo, {
            x: Math.ceil(days / 7)
        });
    }


}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Returns an object with localized ranges
         *
         * {object}
         */
        localizedRanges: {
            valueFn: function () {
                return {
                    justNow: Y.LIMS.Core.i18n.values.timeRangeJustNow,
                    minuteAgo: Y.LIMS.Core.i18n.values.timeRangeMinuteAgo,
                    minutesAgo: Y.LIMS.Core.i18n.values.timeRangeMinutesAgo,
                    hourAgo: Y.LIMS.Core.i18n.values.timeRangeHourAgo,
                    hoursAgo: Y.LIMS.Core.i18n.values.timeRangeHoursAgo,
                    yesterday: Y.LIMS.Core.i18n.values.timeRangeYesterday,
                    daysAgo: Y.LIMS.Core.i18n.values.timeRangeDaysAgo,
                    weeksAgo: Y.LIMS.Core.i18n.values.timeRangeWeeksAgo
                };
            }
        }
    }
});
