

AUI().use('aui-base', function(A) {
    Liferay.namespace('Chat.Menu');

    // ------------------------------------------------------------------------------
    //    Conversation
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Menu.Conversation = function(options) {
        var instance = this;

        if (!options.menuEl) {
            throw "Options must contain menuEl.";
        }

        // Private vars
        instance._options = options;
        instance._menuEl = options.menuEl;
        instance._addButtonEl = instance._menuEl.one('.menu-buttons .add');
        instance._buddiesInButtonEl = instance._menuEl.one('.menu-buttons .buddies-in');
        instance._leaveButtonEl = instance._menuEl.one('.menu-buttons .leave');

        // Events
        instance._addButtonEl.on('click', function() {
            instance._menuEl.fire('addToConversationSelected');
        }, instance);
        instance._buddiesInButtonEl.on('click', function() {
            instance._menuEl.fire('peopleInConversationSelected');
        }, instance);
        instance._leaveButtonEl.on('click', function() {
            instance._menuEl.fire('leaveConversationSelected');
        }, instance);
    };


    Liferay.Chat.Menu.Conversation.prototype = {
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