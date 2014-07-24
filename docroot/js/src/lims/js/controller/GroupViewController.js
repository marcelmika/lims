/**
 * Group View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupViewController = Y.Base.create('groupViewController', Y.LIMS.Core.ViewController, [], {

    /**
     *  The initializer runs when a Group View Controller instance is created, and gives
     *  us an opportunity to set up the view.
     */
    initializer: function () {
        // This needs to be called in each view controller
        this.setup(this.get('container'), this.get('controllerId'));
    },

    /**
     * Panel Did Load is called when the panel is attached to the controller
     */
    onPanelDidLoad: function () {
        // Events
        this._attachEvents();
    },

    /**
     * Panel Did Appear is called when the panel did appear on the screen
     */
    onPanelDidAppear: function () {
        this._startTimer();
    },

    /**
     * Panel Did Disappear is called when the panel disappeared from the screen
     */
    onPanelDidDisappear: function () {
        this._pauseTimer();
    },

    /**
     * Starts timer which periodically refreshes group list
     *
     * @private
     */
    _startTimer: function () {
        console.log('starting timer');
        // Vars
        var settings = this.get('settings'),
            model = this.get('model'),
            timer;

        // Start only if the chat is enabled
        if (settings.isChatEnabled()) {
            // Load model
            model.load();
            // Start periodical update
            timer = setInterval(function () {
                model.load();
            }, 10000);

            this.set('timer', timer);
        }
    },

    /**
     * Pauses timer which periodically refreshes group list
     *
     * @private
     */
    _pauseTimer: function () {
        console.log('pausing timer');
        // Vars
        var timer = this.get('timer');
        // Pause
        clearTimeout(timer);
    },

    /**
     * Maps all events on Y object to private internal functions
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model');

        // Local events
        model.after('groupsLoaded', this._groupsLoaded, this);

        // Global events
        Y.on('buddySelected', this._onBuddySelected, this);
        Y.on('chatEnabled', this._onChatEnabled, this);
        Y.on('chatDisabled', this._onChatDisabled, this);

    },

    /**
     * Called whenever the groups model is updated
     *
     * @private
     */
    _groupsLoaded: function () {
        // Do the animation of groups
        this._fadeInGroups();
        // Hide indicator
        this.get('activityIndicator').hide();
    },

    /**
     * Runs fade in effect on groups
     *
     * @private
     */
    _fadeInGroups: function () {
        // Container
        var container = this.get('groupViewListContainer'),
            animation = new Y.Anim({
                node: container,
                duration: 0.5,
                from: {
                    opacity: 0
                },
                to: {
                    opacity: 1
                }
            });

        // Opacity needs to be set to zero otherwise there will
        // be a weird blink effect
        container.setStyle('opacity', 0);

        // Run the effect animation
        animation.run();
    },

    /**
     * Buddy selected event. Called whenever the user selects one of the buddies from
     * the group list
     *
     * @private
     */
    _onBuddySelected: function () {
        this.dismissViewController();
    },

    /**
     * Called whenever the chat is enabled
     *
     * @private
     */
    _onChatEnabled: function () {
        // Show container
        this.get('container').show();
        // Load model
        this.get('model').load();
    },

    /**
     * Called whenever the chat is disabled
     *
     * @private
     */
    _onChatDisabled: function () {
        // Hide container
        // TODO: Add to hide view controller
        this.get('container').hide();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Id of the controller
        controllerId: {
            value: "groups"
        },

        // Container Node
        container: {
            valueFn: function () {
                return Y.one('#chatBar .buddy-list');
            }
        },

        // Container for activity indicator
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one(".panel-content .preloader");
            }
        },

        // Container Node for group list
        groupViewListContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-content .group-list');
            }
        },

        groupViewList: {
            valueFn: function() {
                var container = this.get('groupViewListContainer'),
                    model = this.get('model');
                // View
                return new Y.LIMS.View.GroupViewList({
                    container: container,
                    model: model
                });
            }
        },

        // Group model list
        model: {
            valueFn: function () {
                return new Y.LIMS.Model.GroupModelList();
            }
        },

        // Timer used to set async calls to server
        timer: {
            value: null // to be set
        },

        // Global settings
        settings: {
            valueFn: function () {
                return new Y.LIMS.Core.Settings();
            }
        }
    }
});
