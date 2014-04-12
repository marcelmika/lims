/**
 * GroupModel
 * Model class which represents a group
 *
 * Created by marcelmika on 4/12/14.
 */
var GroupModel = Y.Base.create('groupModel', Y.Model, [], {

    // Add prototype methods for your Model here if desired. These methods will be
    // available to all instances of your Model.

    // Returns true if all the slices of the pie have been eaten.
    allGone: function () {
        return this.get('slices') === 0;
    },

    // Consumes a slice of pie, or fires an `error` event if there are no slices
    // left.
    eatSlice: function () {
        if (this.allGone()) {
            this.fire('error', {
                type: 'eat',
                error: "Oh snap! There isn't any pie left."
            });
        } else {
            this.set('slices', this.get('slices') - 1);
            Y.log('You just ate a slice of delicious ' + this.get('type') + ' pie!');
        }
    }
}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        slices: {
            value: 6 // default value
        },

        type: {
            value: 'apple'
        }
    }
});

Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModel = GroupModel;
