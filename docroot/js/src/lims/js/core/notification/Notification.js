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
 * Notification
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Notification = Y.Base.create('notification', Y.View, [], {

    // Templates
    audioSoundTemplate: Y.one('#lims-notification-audio-template').get('innerHTML'),
    embedSoundTemplate: Y.one('#lims-notification-embed-template').get('innerHTML'),

    // Locations of the sound files
    mp3SoundFile: '/lims-portlet/audio/notification.mp3',
    wavSoundFile: '/lims-portlet/audio/notification.wav',

    /**
     * Notifies user about new messages by showing this in the title of the page.
     * If the sound is enabled it also plays a sound.
     *
     * @param messageCount number of newly received messages
     * @param silent set to true if no sound should appear on notification
     */
    notify: function (messageCount, silent) {
        // Vars
        var settings = this.get('settings');

        // Store new count
        this._increaseMessageCount(messageCount);

        // If sound is enabled
        if (!settings.isMute() && !silent) {
            this._playSound();
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
     * Plays notification sound
     *
     * @private
     */
    _playSound: function () {
        // Vars
        var container = this.get('container'),
            audioSoundPlayer = this.get('audioSoundPlayer'),
            embedSoundPlayer = this.get('embedSoundPlayer');

        // Play via HTML5 audio tag
        if (this._canPlayAudio()) {
            // Add to doc it it's not there yet
            if (!audioSoundPlayer.inDoc()) {
                container.append(audioSoundPlayer);
            }
            // Play
            audioSoundPlayer.getDOM().play();
        }
        // Play via embed player
        else {
            if (embedSoundPlayer.inDoc()) {
                embedSoundPlayer.remove();
            }
            container.set('innerHTML', embedSoundPlayer.get('innerHTML'));
        }
    },

    /**
     * Returns true if the browser is capable of playing the sound notification
     * via HTML5 audio tag
     *
     * @returns {boolean}
     * @see http://diveintohtml5.info/everything.html
     * @private
     */
    _canPlayAudio: function () {
        // Cars
        var audio = document.createElement('audio'),
            canPlay,
            canPlayWAV,
            canPlayMP3;

        canPlay = !!audio.canPlayType;
        canPlayWAV = canPlay && !!audio.canPlayType('audio/wav; codecs="1"').replace(/no/, '');
        canPlayMP3 = canPlay && !!audio.canPlayType('audio/mpeg;').replace(/no/, '');

        // Check if browser can play either WAV or MP3
        return (canPlay && (canPlayWAV || canPlayMP3));
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
            unreadMessagesCount = this.get('unreadMessagesCount'),
            properties = this.get('properties');

        // Chat is not enabled
        if (!properties.isChatEnabled()) {
            Y.config.doc.title = defaultPageTitle;
        }
        // If no unread message simply keep the default title
        else if (unreadMessagesCount === 0) {
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

        /**
         * Main container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Instance of settings
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        settings: {
            value: null // to be set
        },

        /**
         * Portlet properties
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        },

        /**
         * Node with an audio HTML5 tag that should play the notification sound
         *
         * {Node}
         */
        audioSoundPlayer: {
            valueFn: function () {
                return Y.Node.create(Y.Lang.sub(this.audioSoundTemplate, {
                    wav: this.wavSoundFile,
                    mp3: this.mp3SoundFile
                }));
            }
        },

        /**
         * Node with en embed tag that should play the notification sound
         *
         * {Node}
         */
        embedSoundPlayer: {
            valueFn: function () {
                return Y.Node.create(Y.Lang.sub(this.embedSoundTemplate, {
                    wav: this.wavSoundFile
                }));
            }
        },

        /**
         * Count of unread messages
         *
         * {integer}
         */
        unreadMessagesCount: {
            value: 0
        },

        /**
         * Cached page title
         *
         * {string}
         */
        defaultPageTitle: {
            valueFn: function () {
                return Y.config.doc.title;
            }
        }
    }
});
