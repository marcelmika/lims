/**
 * Panel View
 *
 * The class extends Y.View. It contains all necessary methods to handle panels with content
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PanelView = Y.Base.create('panelView', Y.View, [], {

    /**
     * Constructor
     *
     * @param options {container, panelId} are required
     */
    initializer: function (options) {
        this.set("container", options.container);
        this.set("panelId", options.panelId);

        // Handle already opened panel
        if (options.container.hasClass('selected')) {
            this.set('isOpened', true);
        }

        // Attach events to DOM elements from container
        this._attachEvents();
    },

    /**
     * Shows the panel
     */
    show: function () {
        var container = this.get('container');
        // Show container
        container.addClass('selected');
        // Set flag
        this.set('isOpened', true);
        // Fire event
        Y.fire('panelShown', this);
    },

    /**
     * Hides panel
     */
    hide: function () {
        var container = this.get('container');
        // Hide container
        container.removeClass('selected');
        // Set flag
        this.set('isOpened', false);
        // Fire event
        Y.fire('panelHidden', this);
    },

    /**
     * Toggles panel. If it's shown it hides it, if it's hidden it shows it.
     */
    toggle: function () {
        var isOpened = this.get('isOpened');
        if (isOpened) {
            this.hide();
        } else {
            this.show();
        }
    },

    /**
     * Completely removes panel from tabs
     */
    close: function () {
        var container = this.get('container');
        // Remove container
        container.remove();
        // Set flag
        this.set('isOpened', false);
        // Fire event
        Y.fire('panelClosed', this);
    },

    /**
     * Shows search panel
     */
    showSearch: function() {
        var searchContainer = this.get('searchContainer');
        if(searchContainer !== undefined) {
            searchContainer.removeClass('hidden');
        }
    },

    /**
     * Hides search panel
     */
    hideSearch: function() {
        var searchContainer = this.get('searchContainer');
        if(searchContainer !== undefined) {
            searchContainer.addClass('hidden');
        }
    },

    /**
     * Attaches events to all necessary elements from the container
     *
     * @private
     */
    _attachEvents: function () {
        // Attach trigger event
        this.get('trigger').on('click', this._onTriggerClick, this);
        // Attach panel button event
        this.get('panelButtons').on('click', this._onPanelButtonsClick, this);
    },

    /**
     * Trigger click handler
     *
     * @private
     */
    _onTriggerClick: function () {
        // Toggle panel while clicked on trigger
        this.toggle();
    },

    /**
     * Panel buttons click handler
     *
     * @param event
     * @private
     */
    _onPanelButtonsClick: function (event) {
        var target = event.currentTarget;
        // Minimize button
        if (target.hasClass('minimize')) {
            this.hide();
        }
        // Close button
        else if (target.hasClass('close')) {
            this.close();
        }
        // Search button
        else if (target.hasClass('search')) {
            this.showSearch();
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Panel container
        container: {
            value: null // To be set in initializer
        },

        // Id of the panel
        panelId: {
            value: null // To be set in initializer
        },

        // Indicates if the panel is opened
        isOpened: {
            value: false // default value
        },

        // Tab bar item which shows/hides panel
        trigger: {
            valueFn: function () {
                return this.get('container').one('.panel-trigger');
            }
        },

        // All buttons related to panel like e.g. minimize or close button
        panelButtons: {
            valueFn: function () {
                return this.get('container').all('.panel-button');
            }
        },

        // Search container
        searchContainer: {
            valueFn: function() {
                return this.get('container').one('.panel-search');
            }
        }
    }

});

