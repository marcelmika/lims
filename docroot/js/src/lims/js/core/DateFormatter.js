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
