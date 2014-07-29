/**
 * Message List Model
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * MessageItemModel instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.MessageListModel = Y.Base.create('messageListModel', Y.ModelList, [], {

    // This tells the list that it will hold instances of the GroupModelItem class.
    model: Y.LIMS.Model.MessageItemModel

}, {

    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.
    }
});
