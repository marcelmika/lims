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
 * Group Buddy View List
 *
 * The class extends Y.View. It represent a view for a list of buddies within a single group
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupBuddyViewList = Y.Base.create('groupBuddyViewList', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<ul class="group-buddy-list"/>',

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.BuddyModelList,

    render: function () {
        // Vars
        var container, buddies, buddyIndex, buddy, buddyView;
        // Container and model
        container = this.getContainer();
        buddies = this.getModel();

        for (buddyIndex = 0; buddyIndex < buddies.size(); buddyIndex++) {
            // Get buddy from model list
            buddy = buddies.item(buddyIndex);
            // Build view from buddy
            buddyView = new Y.LIMS.View.GroupBuddyViewItem({model: buddy});
            // Render view
            buddyView.render();
            // Append to container
            container.append(buddyView.get('container'));
        }

        return this;
    },

    // ----------------------------------------------------------------------------------------------------------------
    // Getters/Setters
    // ----------------------------------------------------------------------------------------------------------------

    getContainer: function () {
        return this.get('container');
    },

    getModel: function () {
        return this.get('model');
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {

        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        model: {
            value: null // default value
        }
    }
});
