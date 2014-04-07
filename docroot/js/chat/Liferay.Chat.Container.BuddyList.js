AUI().use('aui-base', 'aui-live-search', function (A) {

    Liferay.namespace('Chat.Container');

    // ------------------------------------------------------------------------------
    //    Buddy List Container
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Container.BuddyList = function () {
        var instance = this;

        // Panel
        instance.panel = new Liferay.Chat.Panel({
            fromMarkup: '.chat-tabs > .buddy-list',
            panelId: 'buddylist'
        });

        // Instance Vars                
        var panel = instance.panel.getPanel();
        instance.node = panel.one('.online-users');
        instance.onlineBuddiesCount = 0;
        instance.buddies = [];

        instance._searchBuddiesFieldObj = panel.one('.search-buddies-field');
        instance._liveSearch = new A.LiveSearch({
            input: instance._searchBuddiesFieldObj,
            nodes: '#chatBar .buddy-list .online-users li',
            data: function (node) {
                return node.one('.name').text();
            }
        });

        // Events
        instance._searchBuddiesFieldObj.on('focus', instance._liveSearch.refreshIndex, instance._liveSearch);
        instance.panel.on('show', function (event) {
            if (instance._searchBuddiesFieldObj.val()) {
                instance._searchBuddiesFieldObj.selectText();
            }
        });

        if (instance.node) {
            instance.node.delegate('click', function (event) {
                var target = event.currentTarget;

                if (target.ancestor('li')) {
                    var userId = Number(target.getAttribute('userId'));
                    if (!isNaN(userId)) {
                        A.fire('selectedBuddy', userId);
                    }
                    return;
                }

            }, 'li');
        }
    };

    Liferay.Chat.Container.BuddyList.prototype = {
        update: function (buddies) {
            var instance = this;
            buddies = buddies || [];

            // Count number of buddies
            instance.onlineBuddiesCount = buddies.length;
            // Update list of all buddies
            instance._updateNode(buddies);
            // Update count of buddies on the panel's title
            instance._updateTitle();
            // Update search index
            instance._updateSearch();

            // Send notification that settings has been changed
            A.fire('buddyListUpdated');
        },
        hidePanels: function (panel) {
            var instance = this;
            if (instance.panel !== panel && instance.panel.isOpened) {
                instance.panel.hide();
            }
        },
        invisible: function (invisible) {
            var instance = this;
            instance.panel.setInvisible(invisible);
        },
        _updateNode: function (groups) {
            var instance = this;
            var buffer = [''];

            for (var i = 0; i < groups.length; i++) {

                var group = groups[i];
                var buddies = group.buddies;

                buffer.push(
                    '<li>' +
                        '<div class="group-name">' + group.name + '</div>' +
                        '<ul>'
                );

                for (var j = 0; j < buddies.length; j++) {

                    var buddy = buddies[j];

                    var userImagePath = Liferay.Chat.Util.getUserImagePathScreenName(buddy.screenName, Liferay.ThemeDisplay.getCompanyId());
                    var status = Liferay.Chat.Util.getStatusCssClass(buddy.status);

                    // Add buddy to instance
                    instance.buddies[buddy.userId] = buddy;

                    // Create node
                    buffer.push(
                        '<li class="user active" userId="' + buddy.screenName + '">' +
                            '<img alt="" src="' + userImagePath + '" />' +
                            '<div class="name">' + buddy.fullName + '</div>' +
                            '<div class="status ' + status + '"></div>');

                    // Close node
                    buffer.push('</div>' + '</li>');
                }

                buffer.push('</ul></li>');
            }

            // Add to HTML
            instance.node.html(buffer.join(''));
        },
        _updateTitle: function () {
            var instance = this;
            var title = instance.panel.getTitle();
            // Change title
            title = title.replace(/\(\d+\)/, '(' + instance.onlineBuddiesCount + ')');
            // Save
            instance.panel._popupTitle.text(title);
        },
        _updateSearch: function () {
            var instance = this;
            var searchBuddiesField = instance._searchBuddiesFieldObj;
            var searchText = searchBuddiesField.val().toLowerCase();
            var isVisible = searchBuddiesField.test(':visible');
            var isActive = searchBuddiesField.compareTo(document.activeElement);
            var isNotEmtpy = searchText.length > 2;
            // Refresh index
            if (isVisible && (isActive || isNotEmtpy)) {
                instance._liveSearch.refreshIndex();
                instance._liveSearch.fire('search', {liveSearch: {}});
            }
        }
    };
});