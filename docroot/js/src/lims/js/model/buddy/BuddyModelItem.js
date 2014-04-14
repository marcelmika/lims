/**
 * Buddy Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single Buddy.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelItem = Y.Base.create('buddyModelItem', Y.Model, [], {



}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        screenName: {
            value: "" // default value
        },

        fullName: {
            value: "" // default value
        },

        presence: {
            value: null // default value
        }
    }
});
