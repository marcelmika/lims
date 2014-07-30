/**
 * View Controller
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.ViewController = Y.Base.create('viewController', Y.View, [], {

    /**
     * This method needs to be called in initializer of subclass
     *
     * @param container Node (required)
     * @param controllerId String (required)
     */
    setup: function (container, controllerId) {
        // Set required params
        this.set('container', container);
        this.set('controllerId', controllerId);
        // Set panel
        this.set('panel', new Y.LIMS.View.PanelView({
            container: container,
            panelId: controllerId
        }));

        // Call that view was loaded
        this.onPanelDidLoad();

        // Attach events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
        Y.on('panelClosed', this._onPanelClosed, this);
        Y.on('chatEnabled', this._onChatEnabled, this);
        Y.on('chatDisabled', this._onChatDisabled, this);

        // Check if panel is already opened. If so we should call onPanelDidAppear.
        // This usually happens when panel was already rendered and opened. Hence it wasn't
        // created by javascript.
        if (this.get('panel').get('isOpened') === true) {
            this.onPanelDidAppear();
        }
    },

    /**
     * Panel Did Load is called when the panel is attached to the controller
     */
    onPanelDidLoad: function () {
        // Override the function
    },

    /**
     * Panel Did Appear is called when the panel is currently active. Only one panel
     * can be active at one time.
     */
    onPanelDidAppear: function () {
        // Override the function
    },

    /**
     * Panel Did Disappear is called when the panel is not active anymore. In other words it was active
     * but the user recently opened some other panel and since only one panel can be active at one time
     * given panel needs to disappear.
     */
    onPanelDidDisappear: function () {
        // Override the function
    },

    /**
     * Panel Did Unload is called whenever the panel completely disappears from the screen. This happens when
     * the user closes the whole panel.
     */
    onPanelDidUnload: function () {
        // Override the function
    },

    /**
     * Shows view controller. Panel is visible.
     */
    showViewController: function () {
        this.get('container').show();
    },

    /**
     * Hides the controller's panel. Nothing is visible.
     */
    hideViewController: function () {
        this.get('container').hide();
    },

    /**
     * Presents view controller. Panel is opened.
     */
    presentViewController: function () {
        this.get('panel').show();
    },

    /**
     * Dismisses view controller.
     * The controller panel will still be visible, only minimized.
     */
    dismissViewController: function () {
        // Simply hide the panel
        this.get('panel').hide();
    },

    /**
     * Returns controller's panel
     *
     * @returns PanelView
     */
    getPanel: function () {
        return this.get('panel');
    },

    /**
     * Returns Id of the controller
     *
     * @returns String
     */
    getControllerId: function () {
        return this.get('controllerId');
    },

    /**
     * Returns controller's container
     *
     * @returns Node
     */
    getContainer: function () {
        return this.get('container');
    },

    /**
     * Panel shown event handler. Closes own panel if some other panel was shown.
     * Thanks to that only one panel can be open at one time.
     *
     * @param panel
     * @private
     */
    _onPanelShown: function (panel) {
        // Our panel was shown
        if (panel === this.get('panel')) {
            // Call that panel did appear
            this.onPanelDidAppear();
            // Fire event
            this.fire('panelDidAppear', this);
        }
        // Some other panel was shown
        else {
            // Only one controller can be visible. So dismiss our controller
            // if it was opened.
            if (this.get('panel').get('isOpened') === true) {
                this.dismissViewController();
                // Fire event
                this.fire('panelDidDisappear', this);
            }
        }
    },

    /**
     * Panel hidden event handler.
     *
     * @param panel
     * @private
     */
    _onPanelHidden: function (panel) {
        if (panel === this.get('panel')) {
            this.onPanelDidDisappear();
            // Fire event
            this.fire('panelDidAppear', this);
        }
    },

    /**
     * Panel closed event handler.
     *
     * @param panel
     * @private
     */
    _onPanelClosed: function (panel) {
        if (panel === this.get('panel')) {
            this.onPanelDidUnload();
            // Fire event
            this.fire('panelDidUnload', this);
        }
    },

    /**
     * Called whenever the chat is enabled
     *
     * @private
     */
    _onChatEnabled: function () {
        // Show controller
        this.showViewController();
    },

    /**
     * Called whenever the chat is disabled
     *
     * @private
     */
    _onChatDisabled: function () {
        // Hide controller
        this.hideViewController();
    }


}, {
    // Attributes of the view controller
    ATTRS: {

        // Id of the controller
        controllerId: {
            value: null // to be set in initializer
        },

        // Container node
        container: {
            value: null // to be set in initializer
        },

        // Panel view related to the controller
        panel: {
            value: null // to be set in initializer
        }
    }
});
