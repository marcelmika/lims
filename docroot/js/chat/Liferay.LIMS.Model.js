/**
 * Created by marcelmika on 4/12/14.
 */


AUI().use('aui-base', 'io', function(A) {

        Liferay.namespace('LIMS');

        Liferay.LIMS.Model = {
            // GET Groups
            getGroups: function() {
                var url = Liferay.Chat.Globals.chatPortletURL;

                A.io(url, {
                    method: "GET",
                    data: {
                        query : "Marcel"
                    },
                    on: {
                        success: function(id, o) {
                            var jsonResponse = A.JSON.parse(o.response);
                            A.fire('Model:GroupsChanged', jsonResponse);
                        },
                        failure: function(x, o) {
                            console.log("groups not changed");
                        }
                    },
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
            }
        };
    }
);