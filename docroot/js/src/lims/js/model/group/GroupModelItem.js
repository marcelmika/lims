/**
 * Group Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single group item.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModelItem = Y.Base.create('groupModelItem', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        var data;

        switch (action) {
            case 'create':
                data = this.toJSON();
                return;
            case 'read':
                return;
            case 'update':
                return;
            case 'delete':
                return;
            default:
                callback('Invalid action');
        }
    }

}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        name: {
            value: "" // default value
        },

        buddies: {
            value: [] // default value
        }
    }
});
