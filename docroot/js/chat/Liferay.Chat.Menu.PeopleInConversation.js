
AUI().use('aui-base', function(A) {
    Liferay.namespace('Chat.Menu');

    // ------------------------------------------------------------------------------
    //    Conversation
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Menu.PeopleInConversation = function(options) {
        var instance = this;

        if (!options.menuEl) {
            throw "Options must contain menuEl.";
        }

        // Private vars
        instance._options = options;
        instance._menuEl = options.menuEl;
    };

    Liferay.Chat.Menu.PeopleInConversation.prototype = {
        show: function() {
            var instance = this;
            instance._menuEl.show();
        },
        hide: function() {
            var instance = this;
            instance._menuEl.hide();
        },
        update: function(buddies) {
            var instance = this;
            var buffer = [''];
            for (var key in buddies) {
                var buddy = buddies[key];
                var html = instance._getBuddyHTML(buddy);
                buffer.push(html);                
            }
            instance._menuEl.html(buffer.join(''));
        },
        _getBuddyHTML: function(buddy) {            
            var userImagePath = Liferay.Chat.Util.getUserImagePath(buddy.portraitId);
            var status = Liferay.Chat.Util.getStatusCssClass(buddy.status);

            // Create node
            var html =
                    '<li class="user active" userId="' + buddy.userId + '">' +
                    '<img alt="" src="' + userImagePath + '" />' +
                    '<div class="name">' + buddy.fullName + '</div>' +
                    '<div class="status ' + status + '"></div>';
            return html;
        }
    };
});