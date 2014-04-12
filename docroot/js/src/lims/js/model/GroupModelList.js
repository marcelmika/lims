/**
 * Group Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * GroupModel instances, and to provide some convenience methods for getting
 * information about the Group items in the list.
 *
 * Created by marcelmika on 4/12/14.
 */

Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModelList = Y.Base.create('groupModelList', Y.ModelList, [], {

    // This tells the list that it will hold instances of the GroupModel class.
    model: GroupModel

    // This tells the list to use a localStorage sync provider (which we'll
    // create below) to load the list of todo items.
//    sync: LocalStorageSync('todo'),

    // Returns an array of all models in this list with the `done` attribute
    // set to `true`.
//    done: function () {
//        return this.filter(function (model) {
//            return model.get('type') === 'apple';
//        });
//    },

    // Returns an array of all models in this list with the `done` attribute
    // set to `false`.
//    remaining: function () {
//        return this.filter(function (model) {
//            return !model.get('done');
//        });
//    }

}, {});


//Y.LIMS.Model.GroupModelList = GroupModelList;