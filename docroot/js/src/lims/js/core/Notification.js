/**
 * Date Formatter
 *
 * Contains function that handle formatting of date
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Notification = Y.Base.create('notification', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    soundTemplate: Y.one('#lims-notification-template').get('innerHTML'),


    notify: function () {
       // Vars
          var container = this.get('container');

        // Fill data from model to template and set it to container.
        // This will play the sound since alert.swf contains correct sound
        container.set('innerHTML', Y.Lang.sub(this.soundTemplate, {
                url: '/liferay-lims-portlet/swf/alert.swf'
            })
        );
    }


}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Container Node
        container: {
            valueFn: function () {
                return Y.one('#chatBar .chat-sound');
            }
        }
    }

});
