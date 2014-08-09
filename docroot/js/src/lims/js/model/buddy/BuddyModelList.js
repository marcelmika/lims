/**
 * Buddy Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * BuddyModelItem instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelList = Y.Base.create('buddyModelList', Y.ModelList, [], {

    // This tells the list that it will hold instances of the BuddyModelItem class.
    model: Y.LIMS.Model.BuddyModelItem



}, {});
