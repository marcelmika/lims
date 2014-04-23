/**
 * Group View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupViewController = Y.Base.create('groupViewController', Y.View, [], {

    /**
     *  The initializer runs when a Group View Controller instance is created, and gives
     *  us an opportunity to set up the view.
     */
    initializer: function () {
        var container = this.get('container');
        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: container,
            panelId: "groups"
        }));

        // Initializations
        this._initGroups();
        // Events
        this._attachEvents();
    },

    /**
     * Initializes group view list
     *
     * @private
     */
    _initGroups: function () {
        var container, model, view;
        // Container
        container = this.get('groupListContainer');
        // Model
        model = new Y.LIMS.Model.GroupModelList();
        model.after('groupsModified', this._groupsUpdated, this);
        // View
        view = new Y.LIMS.View.GroupViewList({
            container: container,
            model: model
        });

        // Store view
        this.groupViewList = view;
    },

    /**
     * Maps all events on Y object to private internal functions
     *
     * @private
     */
    _attachEvents: function () {
        Y.on('buddySelected', this._onBuddySelected, this);
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
    },

    /**
     * Called whenever the groups model is updated
     *
     * @private
     */
    _groupsUpdated: function () {
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
        var container = this.get('groupListContainer'),
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
     * Panel shown event handler. Closes own panel if some other panel was shown.
     * Thanks to that only one panel can be open at one time.
     *
     * @param panel
     * @private
     */
    _onPanelShown: function (panel) {
        // Don't close own panel
        if (panel !== this.get('panel')) {
            this.get('panel').hide();
        }
    },

    /**
     * Panel hidden event handler. Closes own panel if some other panel was shown.
     * Thanks to that only one panel can be open at one time.
     *
     * @param panel
     * @private
     */
    _onPanelHidden: function () {
        // Todo: send active panel ajax
    },

    /**
     * Buddy selected event. Called whenever the user selects one of the buddies from
     * the group list
     *
     * @private
     */
    _onBuddySelected: function () {
        this.get('panel').hide();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Main container
        container: {
            valueFn: function () {
                return Y.one('#chatBar .buddy-list');
            }
        },

        // Container for group list
        groupListContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-content .group-list');
            }
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        },

        // Container for activity indicator
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one(".panel-content .preloader");
            }
        },

        groupViewList: {
            value: null // default value
        }
    }
});
