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
 * Group View List
 *
 * The class extends Y.View. It represent a view for a list of groups
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewList = Y.Base.create('groupViewList', Y.View, [], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.GroupModelList,

    // The initializer runs when the instance is created, and gives
    // us an opportunity to set up the view.
    initializer: function () {
        // Init model
        this._initModel();
    },

    // Init model attached to the view
    _initModel: function () {
        var groupModelList;
        // Init group list
        groupModelList = this.get('model');
        // Update the display when a new item is added to the list, or when the
        // entire list is reset.
        groupModelList.after('add', this._onGroupAdd, this);
        groupModelList.after('reset', this._onGroupReset, this);
    },


    /**
     * Called whenever the group model is reset
     *
     * @private
     */
    _onGroupReset: function() {
        // Empty container
        this.get('container').set('innerHTML', '');
    },

    // Creates a new GroupView instance and renders it into the list whenever a
    // Group item is added to the list.
    _onGroupAdd: function (e) {
        var groupView;
        // Create new Group View Item
        groupView = new Y.LIMS.View.GroupViewItem({model: e.model});
        // Render it
        groupView.render();
        // Append to list
        this.get('container').append(groupView.get('container'));
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {

        container: {
            value: null // default value
        },

        model: {
            value: null // default value
        }
    }
});
