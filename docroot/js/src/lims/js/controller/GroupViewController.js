/**
 * Group View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupViewController = Y.Base.create('groupViewController', Y.View, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {
        // Initializations
        this._initGroups();
    },

    // Initializes group view list
    _initGroups: function() {
        var container, model, view;

        // Container
        container = this.get('groupListContainer');
        // Model
        model = new Y.LIMS.Model.GroupModelList();
        model.after('add', this._groupsUpdated, this);
        // View
        view = new Y.LIMS.View.GroupViewList({
            container: container,
            model: model
        });

        // Store view
        this.groupViewList = view;
    },

    // Called whenever the groups model is updated
    _groupsUpdated: function() {
        this.get('activityIndicator').hide();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        groupListContainer: {
            valueFn: function () {
                return Y.one("#chatBar .buddy-list .panel-content .group-list");
            }
        },

        activityIndicator: {
            valueFn: function () {
                 return Y.one("#chatBar .buddy-list .panel-content .preloader");
            }
        },

        groupViewList: {
            value: null // default value
        }
    }
});
