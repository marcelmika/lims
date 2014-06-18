
AUI().use('aui-base', function(A) {
    Liferay.namespace('Chat.Menu');

    // ------------------------------------------------------------------------------
    //    Conversation
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Menu.AddToConversation = function(options) {
        var instance = this;
        if (!options.menuEl) {
            throw "Options must contain menuEl.";
        }
        // Private vars
        instance._options = options;
        instance._menuEl = options.menuEl;
        // Token input
        instance._tokenInputEl = $(options.panelId + " .users-token-input");        
        instance._tokenInputEl.tokenInput(Liferay.Chat.Globals.chatPortletURL, {
            theme: "facebook",
            preventDuplicates: true,
            dropdownContainer: options.panelId + ' .panel-window',

        });
        instance._usersListEl = $(options.panelId + ' .menu-add-to-conversation ul');
        instance._usersListEl.bind('input propertychange', function() {
            if(instance._usersListEl.hasClass('error')){
                instance._usersListEl.removeClass('error');
            }
        });
        instance._addButtonEl = instance._menuEl.one('.buttons .add');
        instance._cancelButtonEl = instance._menuEl.one('.buttons .cancel');

        // Events
        instance._addButtonEl.on('click', function() {
            var users = instance._tokenInputEl.val();
            // Cannot invite no users
            if (!users) {
                instance._usersListEl.addClass('error');
                return;
            }
            instance._menuEl.fire('addSelected', instance);
        }, instance);
        instance._cancelButtonEl.on('click', function() {
            instance._menuEl.fire('cancelSelected');
        }, instance);
    };

    Liferay.Chat.Menu.AddToConversation.prototype = {
        show: function() {
            var instance = this;
            instance._menuEl.show();
        },
        hide: function() {
            var instance = this;
            instance._menuEl.hide();
            instance._usersListEl.removeClass('error');
            instance._clearTokens();
        },
        userList: function() {
            var instance = this;
            return instance._tokenInputEl.val();
        },
        _clearTokens: function() {
            var instance = this;
            instance._tokenInputEl.tokenInput("clear");
        }
    };
});
