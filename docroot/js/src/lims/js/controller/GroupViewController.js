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
     * Called whenever the groups model is updated
     *
     * @private
     */
    _groupsUpdated: function () {
        this._animateGroups();
        this.get('activityIndicator').hide();
    },

    _animateGroups: function () {
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


        container.setStyle('opacity', 0);

        animation.run();
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
