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
 * Group View Item
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationItemView = Y.Base.create('conversationViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="conversation-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.MessageItemModel,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-item-template').get('innerHTML'),

    /**
     * Called on initialization of object
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Renders view
     *
     * @returns {ConversationItemView}
     */
    render: function () {
        // Vars
        var container = this.get('container'),      // Container that holds the view
            model = this.get('model'),              // Message model
            from = model.get('from'),               // Creator of the message
            formatter = this.get('dateFormatter');  // Prettify date formatter

        // Fill data from model to template and set it to container
        container.set('innerHTML', Y.Lang.sub(this.template, {
                createdPrettified: formatter.prettyDate(model.get('createdAt')),
                created: new Date(model.get('createdAt')),
                fullName: from.get('fullName'),
                content: model.get('body'),
                portrait: this._renderPortrait(from.get('screenName'))
            })
        );

        // Set date node
        this.set('dateNode', container.one('.conversation-item-date'));

        return this;
    },

    /**
     * Updates node that holds the creation time of message
     */
    updateTimestamp: function () {
        // Vars
        var dateNode = this.get('dateNode'),        // Node that holds date
            formatter = this.get('dateFormatter'),  // Prettify date formatter
            model = this.get('model');              // Message model

        // Update time
        dateNode.set('innerHTML', formatter.prettyDate(model.get('createdAt')));
    },

    /**
     * Attach listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model');

        // Local events
        model.after('messageSent', this.render, this);
        model.after('messageError', this.render, this);
    },

    /**
     * Renders portrait based on screenName and returns the rendered HTML
     *
     * @param screenName of the user whose portrait should be rendered
     * @returns HTML of the rendered portrait
     * @private
     */
    _renderPortrait: function (screenName) {
        // Vars
        var portraitView = new Y.LIMS.View.PortraitView({screenName: screenName});
        // Render
        portraitView.render();

        return portraitView.get('container').get('outerHTML');
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container that holds the message
         *
         * {node}
         */
        container: {
            valueFn: function () {
                // Create node from template on initialization
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Node that contains date
         *
         * {node}
         */
        dateNode: {
            value: null
        },

        /**
         * Instance of model attached to view
         *
         * {MessageItemModel}
         */
        model: {
            value: null // default value
        },

        /**
         * Instance of date formatter
         *
         * {DateFormatter}
         */
        dateFormatter: {
            valueFn: function () {
                return new Y.LIMS.Core.DateFormatter();
            }
        }
    }
});

