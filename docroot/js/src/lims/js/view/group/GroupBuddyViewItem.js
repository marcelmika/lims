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
 * Group Buddy View Item
 *
 * The class extends Y.View. It represents the view for a single group buddy item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupBuddyViewItem = Y.Base.create('groupBuddyViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-buddy-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.BuddyModelItem,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-group-buddy-item-template').get('innerHTML'),

    // Renders view
    render: function () {
        // Vars
        var container, model;
        // Container and model
        container = this.get("container");
        model = this.get("model");

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: model.printableName(),
                portrait: this._getPortrait(model),
                presence: this._getPresence(model.get('presence'))
            })
        );

        // Attach events to newly created container
        this._attachEvents();

        return this;
    },

    /**
     * Attach events to container content
     * @private
     */
    _attachEvents: function () {
        var model = this.get('model'),
            container = this.get('container');
        // Attach click on panel's item
        container.on('click', function (event) {
            event.preventDefault();
                // Fire event, add current model (buddy)
                Y.fire('buddySelected', model);
        });
    },

    // Returns user portrait URL
    _getPortrait: function (buddy) {
        var portraitView = new Y.LIMS.View.PortraitView({buddy: buddy});
        portraitView.render();
        return portraitView.get('container').get('outerHTML');
    },

    // Returns presence view
    _getPresence: function (presenceType) {
        var presenceView = new Y.LIMS.View.PresenceView({presenceType: presenceType});
        presenceView.render();
        return presenceView.get('container').get('outerHTML');
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },
        // Instance of model attached to view
        model: {
            value: null
        }
    }

});

