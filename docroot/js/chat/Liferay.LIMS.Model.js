/**
 * Created by marcelmika on 4/12/14.
 */


var Model = Y.Base.create('model', Y.Base, [], {

    // GET Groups
    getGroups: function () {

        var url = "abc";

        Y.io(url, {
            method: "GET",
            data: {
                query: "Marcel"
            },
            on: {
                success: function (id, o) {
                    var jsonResponse = Y.JSON.parse(o.response);
//                            Y.fire('Model:GroupsChanged', jsonResponse);
                    console.log(jsonResponse);
                },
                failure: function (x, o) {
                    console.log("groups not changed");
                    console.log(x);
                    console.log(o);
                }
            },
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

}, {});

Y.namespace('LIMS');

Y.LIMS.Model = Model;
