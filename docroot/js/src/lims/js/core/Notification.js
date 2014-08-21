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

Y.LIMS.Core.Notification = Y.Base.create('notification', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    soundTemplate: Y.one('#lims-notification-template').get('innerHTML'),

    /**
     * Notifies user about new messages by showing this in the title of the page.
     * If the sound is enabled it also plays a sound.
     *
     * @param messageCount number of newly received messages
     * @param silent set to true if no sound should appear on notification
     */
    notify: function (messageCount, silent) {
        // Vars
        var container = this.get('container'),
            settings = this.get('settings');

        // Store new count
        this._increaseMessageCount(messageCount);

        // If sound is enabled
        if (!settings.isMute() && !silent) {
            // Fill data from model to template and set it to container.
            // This will play the sound since alert.swf contains correct sound
            container.set('innerHTML', Y.Lang.sub(this.soundTemplate, {
                    url: '/lims-portlet/swf/alert.swf'
                })
            );
        }

        // Update title
        this._updatePageTitleMessage();
    },

    /**
     * Call this method whenever the amount of read messages has decreased.
     * For example if the user has opened conversation so he has already read
     * some amount of messages.
     *
     * @param messageCount
     */
    suppress: function (messageCount) {
        // Store new count
        this._decreaseMessageCount(messageCount);
        // Update title
        this._updatePageTitleMessage();
    },

    /**
     * Increments message count
     *
     * @param count
     * @private
     */
    _increaseMessageCount: function (count) {
        // Vars
        var unreadMessagesCount = this.get('unreadMessagesCount');

        // We need to be sure that count is a number
        count = parseInt(count, 10);
        // Increase counter
        unreadMessagesCount = count + unreadMessagesCount;
        // Set the value
        this.set('unreadMessagesCount', unreadMessagesCount);
    },

    /**
     * Decrements message count
     *
     * @param count
     * @private
     */
    _decreaseMessageCount: function (count) {
        // Vars
        var unreadMessagesCount = this.get('unreadMessagesCount');

        // We need to be sure that count is a number
        count = parseInt(count, 10);
        // Increase counter
        unreadMessagesCount = unreadMessagesCount - count;

        // Just to be sure that message count cannot be less than 0
        if (unreadMessagesCount < 0) {
            unreadMessagesCount = 0;
        }

        // Set the value
        this.set('unreadMessagesCount', unreadMessagesCount);
    },

    /**
     * Takes amount of unread messages and updates the page title
     *
     * @private
     */
    _updatePageTitleMessage: function () {
        // Vars
        var defaultPageTitle = this.get('defaultPageTitle'),
            unreadMessagesCount = this.get('unreadMessagesCount');

        // If no unread message simply keep the default title
        if (unreadMessagesCount === 0) {
            Y.config.doc.title = defaultPageTitle;
        }
        // Append message count to title
        else {
            Y.config.doc.title = "(" + unreadMessagesCount + ") " + defaultPageTitle;
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Container Node
        container: {
            value: null // to be set
        },

        // Settings
        settings: {
            value: null // to be set
        },

        // Portlet properties
        properties: {
            value: null // to be set
        },

        unreadMessagesCount: {
            value: 0
        },

        defaultPageTitle: {
            valueFn: function () {
                return Y.config.doc.title;
            }
        }
    }
});
