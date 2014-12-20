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

Y.LIMS.View.GroupViewItem = Y.Base.create('groupViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.GroupModelItem,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-group-item-template').get('innerHTML'),

    /**
     * Renders the view
     *
     * @returns {Y.LIMS.View.GroupViewItem}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            socialRelation = model.get('socialRelation'),
            buddiesView,
            name;

        // If the group contains social relation we need to localize
        // the social relation name.
        if (socialRelation) {
            name = socialRelation.getLocalizedName();
        }
        // If not, use the name from model
        else {
            name = model.get('name');
        }


        // Render Group:
        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: name
            })
        );

        // Hide group name if nothing was set
        if (!model.get('name')) {
            Y.LIMS.Core.Util.hide(container.one('.group-name'));
        }

        // Render Buddies
        buddiesView = new Y.LIMS.View.GroupBuddyViewList({model: model.get('buddies')});
        buddiesView.render();
        container.append(buddiesView.get("container"));

        return this;
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.

        /**
         * Container node
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Model
         *
         * {Y.LIMS.Model.GroupModelItem}
         */
        model: {
            value: null // to be set
        }
    }

});

