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
            valueFn: function () {
                // Get root
                var rootNode = this.get('globals').getRootNode();
                // Return container
                return rootNode.one('.lims-sound');
            }
        },

        // Settings
        settings: {
            value: null // to be set
        },

        unreadMessagesCount: {
            value: 0
        },

        defaultPageTitle: {
            valueFn: function () {
                return Y.config.doc.title;
            }
        },

        // Global settings
        globals: {
            valueFn: function () {
                return new Y.LIMS.Core.Settings();
            }
        }
    }
});
