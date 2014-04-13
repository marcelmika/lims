/**
 * Group View Item
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewItem = Y.Base.create('groupViewItem', Y.View, [], {

    // Override the default container attribute.
    container: Y.Node.create('<li/>'),

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="todo-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.GroupModelItem,

    // Provide an optional template that will be used to render the view. The
    // template can be anything we want, but in this case we'll use a string
    // that will be processed with Y.Lang.sub().
//    template: '{slices} slice(s) of {type} pie remaining.'

    render: function () {
        var container, model;
        // This is a 3.4.x workaround, check http://yuilibrary.com/yui/docs/view/#upgrading-from-yui-34x
        container = this.get('container');
        model = this.getModel(this);

        container.set('innerHTML', '<div class="name">' + model.get('name') + "</div>");


//        container.setHTML(Y.Lang.sub(this.template, {
//            checked: done ? 'checked' : '',
//            text   : model.getAsHTML('text')
//        }));

//        container[done ? 'addClass' : 'removeClass']('todo-done');
//        this.set('inputNode', container.one('.todo-input'));

        return this;
    },

    getModel: function (view) {
        return view.get('model') !== undefined ? view.get('model') : view.model;
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.
        container: {
            valueFn: function () {
                return Y.Node.create('<li/>');
            }
        }
    }
});

