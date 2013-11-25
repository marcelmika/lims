

AUI().use('aui-base', function(A) {
    Liferay.namespace('Chat.Menu');

    // ------------------------------------------------------------------------------
    //    Conversation
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Menu.LeaveConversation = function(options) {
        var instance = this;

        if (!options.menuEl) {
            throw "Options must contain menuEl.";
        }

        // Private vars
        instance._options = options;
        instance._menuEl = options.menuEl;
        instance._leaveButtonEl = instance._menuEl.one('.buttons .leave');
        instance._cancelButtonEl = instance._menuEl.one('.buttons .cancel');

        // Events
        instance._leaveButtonEl.on('click', function() {
            instance._menuEl.fire('leaveConversationSelected');
        }, instance);
        instance._cancelButtonEl.on('click', function() {
            instance._menuEl.fire('cancelConversationSelected');
        }, instance);
    };


    Liferay.Chat.Menu.LeaveConversation.prototype = {
        show: function() {
            var instance = this;
            instance._menuEl.show();
        },
        hide: function() {
            var instance = this;
            instance._menuEl.hide();
        }
    };
});