/**
 * Created by marcelmika on 4/12/14.
 */

Y.namespace('LIMS');

Y.LIMS.Notification = function() {
    var instance = this;

    // Private values
//    instance._chatContainerEl = Liferay.Chat.Globals.chatContainerEl;
//    instance._sound = new SWFObject('/liferay-lims-portlet/swf/alert.swf', 'alertsound', '0', '0', '8');
//    instance._soundContainer = instance._chatContainerEl.one('.chat-sound');
    instance._mute = false;
};

Y.LIMS.Notification.prototype = {
    setMute: function(mute) {
        var instance = this;
        instance._mute = mute;
    },
    getMute: function() {
        var instance = this;
        return instance._mute;
    },
    triggerSound: function() {
        var instance = this;
        // Play only if sound isn't mute
        if (!instance._mute) {
            instance._sound.write(instance._soundContainer.getDOM());
        }
    }
};
