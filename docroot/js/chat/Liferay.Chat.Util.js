
AUI().use(
        'aui-base',
        function(A) {
            var Lang = A.Lang;
            var now = Lang.now;

            Liferay.namespace('Chat');

            Liferay.Chat.Util = {
                formatTime: function(time) {
                    var instance = this;
                    time = Number(time);
                    time = new Date(time);
                    var meridian = 'am';
                    var hour = time.getHours();
                    var minute = time.getMinutes();
                    if (hour >= 11) {
                        meridian = 'pm';
                    }

                    if (hour > 12) {
                        hour -= 12;
                    }

                    if (hour === 0) {
                        hour += 12;
                    }

                    if (minute < 10) {
                        minute = '0' + minute;
                    }

                    return hour + ':' + minute + ' ' + meridian;
                },
                /*
                 * JavaScript Pretty Date
                 * Copyright (c) 2011 John Resig (ejohn.org)
                 * Licensed under the MIT and GPL licenses.
                 */
                // Takes an ISO time and returns a string representing how
                // long ago the date represents.
                prettyDate: function(date) {
                    var diff = (((new Date()).getTime() - date.getTime()) / 1000);
                    var day_diff = Math.floor(diff / 86400);

                    if (isNaN(day_diff) || day_diff < 0 || day_diff >= 31)
                        return;

                    return day_diff === 0 && (
                            diff < 60 && date.toLocaleTimeString() ||
                            diff < 120 && "1 min ago" ||
                            diff < 3600 && Math.floor(diff / 60) + " mins ago" ||
                            diff < 7200 && "1 hour ago" ||
                            diff < 86400 && Math.floor(diff / 3600) + " hours ago") ||
                            day_diff === 1 && "Yesterday" ||
                            day_diff < 7 && day_diff + " days ago" ||
                            day_diff < 31 && Math.ceil(day_diff / 7) + " weeks ago";
                },
                getCurrentTimestamp: function() {
                    var instance = this;
                    var now = Lang.now;
                    return now() - instance._getOffset();
                },
                getUserImagePath: function(userId) {
                    return themeDisplay.getPathImage() + '/user_portrait?img_id=' + userId;
                },
                getUserImagePathScreenName: function(screenName, companyId) {
                    return themeDisplay.getPathImage() + '/user_portrait?screenName=' +
                            screenName + '&' + 'companyId=' + companyId;
                },
                getMultipleBuddiesTitle: function(buddies) {
                    var maxBuddies = 3;
                    var i = 0;
                    var title = '';
                    for (i = 0; i < buddies.length; i++) {
                        title += buddies[i].fullName + ", ";
                    }

                    if (i <= maxBuddies) {
                        return title.substring(0, title.length - 2);
                    } else {
                        return title + " and" + (buddies.length - maxBuddies) + " others";
                    }
                },
                _getOffset: function() {
                    var instance = this;
                    var offset = instance._offset;
                    if (Lang.isUndefined(offset)) {
                        var currentChatServerTime = A.one('#currentChatServerTime').val() || 0;
                        offset = now() - currentChatServerTime;
                        instance._offset = offset;
                    }

                    return offset;
                },
                getStatusCssClass: function(status) {
                    switch (status) {
                        case Liferay.Chat.PollerKeys.JABBER_STATUS_ONLINE:
                            return "online";
                        case Liferay.Chat.PollerKeys.JABBER_STATUS_BUSY:
                            return "busy";
                        case Liferay.Chat.PollerKeys.JABBER_STATUS_UNAVAILABLE:
                            return "unavailable";
                        case Liferay.Chat.PollerKeys.JABBER_STATUS_INVISIBLE:
                            return "invisible";
                        case Liferay.Chat.PollerKeys.JABBER_STATUS_OFF:
                            return "off";
                    }
                }, contains: function(obj, array) {
                    for (var i = 0; i < array.length; i++) {
                        if (array[i] === obj) {
                            return true;
                        }
                    }
                    return false;
                },
                TIMESTAMP_24: (24 * 60 * 60 * 1000)
            };
        }
);