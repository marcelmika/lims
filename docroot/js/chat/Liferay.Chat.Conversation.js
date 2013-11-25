

AUI().use(
        'aui-base',
        'anim',
        function(A) {
            Liferay.namespace('Chat');

            // ------------------------------------------------------------------------------
            //    Conversation
            // ------------------------------------------------------------------------------
            Liferay.Chat.Conversation = function(options) {
                var instance = this;

                Liferay.Chat.Conversation.superclass.constructor.call(instance, options);

                instance._conversation = {};
                instance._chatInput = instance._panel.one('.panel-input textarea');
                instance._chatOutput = instance._panel.one('.panel-output');
                instance._preloader = instance._panel.one('.preloader');
                instance._portrait = instance._panel.one('.panel-icon');
                instance._title = instance._panel.one('.panel-title');
                instance._searchInputEl = instance._panel.one('.panel-search-input');
                instance._searchClearEl = instance._panel.one('.panel-serach-clear-button');
                instance._unreadMessagesContainer = instance._popupTrigger.one('.unread');
                instance._triggerTitle = instance._popupTrigger.one('.trigger-name');
                instance._unreadMessages = 0;
                instance._buddiesCount = 0;
                instance._originalPageTitle = document.title;
                instance._heightMonitor = A.Node.create('<pre class="chat-height-monitor" />');
                instance._heightMonitor.appendTo(document.body);
                if (!instance._unreadMessagesContainer) {
                    instance._unreadMessagesContainer = A.Node.create('<div class="unread" />');
                    instance._unreadMessagesContainer.hide();
                    instance._popupTrigger.append(instance._unreadMessagesContainer);
                }
                // Menu containers
                instance._primaryMenu = instance._panel.one('.panel-primary-menu');
                instance._secondaryMenu = instance._panel.one('.panel-secondary-menu');
                instance._tertiaryMenu = instance._panel.one('.panel-tertiary-menu');

                // Menus
                instance._menuButton = instance._panel.one('.panel-button.menu');
                instance._conversationMenuEl = instance._panel.one('.menu-conversation');
                instance._conversationMenu = new Liferay.Chat.Menu.Conversation({menuEl: instance._conversationMenuEl});
                instance._addToConversationMenuEl = instance._panel.one('.menu-add-to-conversation');

                instance._addToConversationMenu = new Liferay.Chat.Menu.AddToConversation({
                    menuEl: instance._addToConversationMenuEl,
                    panelId: "#conversation_" + instance._panelId
                });
                instance._leaveConversationMenuEl = instance._panel.one('.menu-leave-conversation');
                instance._leaveConversationMenu = new Liferay.Chat.Menu.LeaveConversation({menuEl: instance._leaveConversationMenuEl});
                instance._peopleInConversationMenuEl = instance._panel.one('.menu-people-in-conversation');
                instance._peopleInConversationMenu = new Liferay.Chat.Menu.PeopleInConversation({menuEl: instance._peopleInConversationMenuEl});
                instance._searchMenuEl = instance._panel.one('.menu-search');
                instance._searchMenu = new Liferay.Chat.Menu.Search({
                    menuEl: instance._searchMenuEl,
                    input: instance._searchInputEl,
                    nodes: '#conversation_' + instance._panelId + " .blurb",
                    scrollArea: instance._chatOutput
                });

                instance._secondaryMenuElements = [
                    instance._addToConversationMenu,
                    instance._leaveConversationMenu,
                    instance._peopleInConversationMenu
                ];
                instance._tertiaryMenuElements = [
                    instance._searchMenuEl
                ];

                // ------------------------------------------------------------------------------
                //    Events
                // ------------------------------------------------------------------------------
                // Chat Input
                instance._chatInput.on('keyup', instance._onKeystroke, instance);
                instance._chatInput.on('focus', instance._onKeystroke, instance);

                // Menu button
                instance._menuButton.on('click', instance._onPrimaryMenuOpen, instance);

                // Conversation menu
                instance._conversationMenuEl.on('addToConversationSelected', function() {
                    instance._onSecondaryMenuOpen(instance._addToConversationMenu);
                }, instance);
                instance._conversationMenuEl.on('leaveConversationSelected', function() {
                    instance._onSecondaryMenuOpen(instance._leaveConversationMenu);
                }, instance);
                instance._conversationMenuEl.on('peopleInConversationSelected', function() {
                    instance._onSecondaryMenuOpen(instance._peopleInConversationMenu);
                }, instance);

                // Leave conversation menu
                instance._leaveConversationMenuEl.on('cancelConversationSelected', function() {
                    instance.closeMenu(instance._secondaryMenu, true);
                }, instance);
                instance._leaveConversationMenuEl.on('leaveConversationSelected', function() {
                    A.fire('leaveConversationSelected', instance._panelId);
                }, instance);

                // Add to conversation menu
                instance._addToConversationMenuEl.on('addSelected', function(menu) {
                    A.fire('addToConversationSelected', {
                        roomJID: instance._panelId,
                        users: menu.userList()
                    });
                    instance.closeMenu(instance._secondaryMenu, true);
                    instance._addToConversationMenu.hide();
                }, instance);
                instance._addToConversationMenuEl.on('cancelSelected', function() {
                    instance.closeMenu(instance._secondaryMenu, true);
                    instance._addToConversationMenu.hide();
                }, instance);


                // Search input
                instance._searchInputEl.on({
                    focus: function() {
                        if (!instance._searchInputEl.hasClass('selected')) {
                            instance._onSearchOpen();
                        }
                        instance._searchMenu.refreshIndex();
                    },
                    blur: function() {
                        instance._onSearchClose();
                    }
                }, instance);

                instance._searchClearEl.on('click', function() {
                    instance._searchInputEl.set('value', '');
                    instance._onSearchClose();
                }, instance);

                // Preloader
                instance.startPreloader();
            };
            // ------------------------------------------------------------------------------
            //    Conversation ----> Panel
            // ------------------------------------------------------------------------------
            A.extend(Liferay.Chat.Conversation, Liferay.Chat.Panel, {
                // ------------------------------------------------------------------------------
                //    Public
                // ------------------------------------------------------------------------------
                update: function(conversation) {
                    var instance = this;
                    instance.conversation = conversation;
                    var messages = conversation.messages;

                    // Stop preloader
                    instance.stopPreloader();

                    if (messages.length > 0) {
                        // Remove all outgoing nodes
                        instance._removeOutgoingNodes();
                        // Scroll window to the last message
                        instance._scrollToBottom();
                        // Increase number of unread messages
                        instance._unreadMessages = conversation.unreadMessages;
                        if (instance._unreadMessages > 0) {
                            instance.setAsUnread();
                        } else {
                            instance.setAsRead();
                        }
                        // Update panel title and image
                        instance._panelTitle = conversation.title;
                        instance._buddiesCount = conversation.buddies.length - 1;
                        instance._panelIcon = Liferay.Chat.Util.getUserImagePath(conversation.lastMessage.from.portraitId);
                        instance._updateTitle();
                        // Update window
                        instance._updateMessageWindow(messages);
                        // Update search
                        instance._updateSearch();
                    }

                    // Update buddies
                    instance._peopleInConversationMenu.update(conversation.buddies);
                },
                show: function() {
                    var instance = this;

                    Liferay.Chat.Panel.prototype.show.call(instance);
                    instance.setAsRead();

                    var outputEl = instance._chatOutput.getDOM();

                    outputEl.scrollTop = outputEl.scrollHeight;
                },
                setAsRead: function() {
                    var instance = this;

                    instance._panel.removeClass('waiting');
                    instance._unreadMessagesContainer.hide();

                    instance._unreadMessages = 0;

                    instance.set('lastReadTime', Liferay.Chat.Util.getCurrentTimestamp());
                    document.title = instance._originalPageTitle;
                },
                setAsUnread: function() {
                    var instance = this;

                    if (!instance.get('selected')) {
                        var panel = instance._panel;

                        panel.addClass('waiting');

                        if (instance._unreadMessages > 0) {
                            Liferay.Chat.Manager.notificator.triggerSound();
                            instance._unreadMessagesContainer.text(instance._unreadMessages);
                            instance._unreadMessagesContainer.show();
                        }
                        else {
                            instance._unreadMessagesContainer.hide();
                        }

                        document.title = "(" + instance._unreadMessages + ") " + instance._originalPageTitle;
                    }
                },
                startPreloader: function() {
                    var instance = this;
                    instance._preloader.show();
                },
                stopPreloader: function() {
                    var instance = this;
                    instance._preloader.hide();
                },
                openMenu: function(menu, height, onClose) {
                    var instance = this;
                    // Defaults
                    height = height || 85;
                    // Animation properties
                    var animation = new A.Anim({
                        node: menu,
                        duration: 0.2,
                        to: {
                            height: height
                        }
                    });
                    // Reset height
                    menu.setStyle("overflow", "hidden");
                    menu.setStyle("height", "0");
                    // Run animation
                    try {
                        animation.run();
                    } catch (e) {
                    }

                    animation.on('end', function() {
                        // Custom onClose function
                        if (onClose) {
                            onClose();
                        } else {
                            menu.on('clickoutside', function() {
                                instance.closeMenu(menu, true);
                            }, instance);
                        }
                        // Add opened class to prevent posibility to open menu twice
                        menu.addClass('opened');
                        // If menu is opened and content is refreshed size is
                        // automatically updated too
                        menu.setStyle('height', 'auto');
                        menu.setStyle('overflow', 'auto');
                    });
                },
                closeMenu: function(menu, anim) {
                    var duration = anim ? 0.2 : 0;

                    var animation = new A.Anim({
                        node: menu,
                        duration: duration,
                        to: {
                            height: 0
                        }
                    });
                    // Hide scrollbar during animation
                    menu.setStyle("overflow", "hidden");
                    // Run animation
                    try {
                        animation.run();
                    } catch (e) {
                    }
                    animation.on('end', function() {
                        menu.removeClass('opened');
                        menu.setStyle("overflow", "hidden");
                        menu.setStyle("height", "0");
                        menu.detach('clickoutside');
                    });
                },
                openSearch: function() {
                    var instance = this;
                    var animation = new A.Anim({
                        node: instance._searchInputEl,
                        duration: 0.2,
                        to: {
                            width: 131
                        }
                    });
                    animation.on('end', function() {
                        instance._searchClearEl.show();
                    });
                    animation.run();
                },
                closeSearch: function() {
                    var instance = this;
                    var animation = new A.Anim({
                        node: instance._searchInputEl,
                        duration: 0.3,
                        to: {
                            width: 110
                        }
                    });
                    animation.on('start', function() {
                        instance._searchClearEl.hide();
                    });
                    animation.run();
                },
                // ------------------------------------------------------------------------------
                //    Private
                // ------------------------------------------------------------------------------
                _setPanelHTML: function(html) {
                    var instance = this;
                    if (!html) {

                        html = '<li class="conversation"  id="conversation_' + instance._panelId + '" panelId="' + instance._panelId + '">' +
                                '<div class="panel-trigger">' +
                                '<span class="trigger-name"></span>' +
                                '</div>' +
                                '<div class="chat-panel">' +
                                '<div class="panel-window">' +
                                '<div class="panel-button minimize"></div>' +
                                '<div class="panel-button close"></div>' +
                                '<img alt="" class="panel-icon" src="' + instance._panelIcon + '" />' +
                                '<div class="panel-title">' + instance._panelTitle + '</div>' +
                                // Panel profile
                                '<div class="panel-profile">' +
                                // Panel search
                                '<div class="panel-search">' +
                                '<input class="panel-search-input" type="text"/>' +
                                '<div class="panel-serach-clear-button aui-helper-hidden"></div>' +
                                '</div>' +
                                // [1] Primary Panel menu
                                '<div class="panel-button menu"></div>' +
                                '<div class="panel-primary-menu">' +
                                '<div class="menu-conversation">' +
                                '<ul class="menu-buttons">' +
                                '<li class="add">' + Liferay.Language.get('add-to-conversation') + '</li>' +
                                '<li class="buddies-in">' + Liferay.Language.get('buddies-in') + '</li>' +
                                '<li class="leave">' + Liferay.Language.get('leave-conversation') + '</li>' +
                                '</ul>' +
                                '</div>' +
                                '</div>' +
                                // [2] Secondary menu
                                '<div class="panel-secondary-menu">' +
                                // Add to conversation
                                '<div class="menu-add-to-conversation">' +
                                '<input type="text" class="users-token-input"/>' +
                                '<div class="buttons">' +
                                '<div class="cancel">'+ Liferay.Language.get('cancel') +'</div>' +
                                '<div class="add">'+ Liferay.Language.get('add') +'</div>' +
                                '</div>' +
                                '</div>' +
                                // Leave conversation
                                '<div class="menu-leave-conversation">' +
                                '<p>' + Liferay.Language.get('leave-conversation-message') + '</p>' +
                                '<div class="buttons">' +
                                '<div class="cancel">' + Liferay.Language.get('cancel') + '</div>' +
                                '<div class="leave">' + Liferay.Language.get('leave') + '</div>' +
                                '</div>' +
                                '</div>' +
                                // People in conversation
                                '<ul class="menu-people-in-conversation"></ul>' +
                                '</div>' +
                                // [3] Tertiary menu
                                '<div class="panel-tertiary-menu">' +
                                '<div class="menu-search">' +
                                '<p>' + Liferay.Language.get('results') + ': <span class="results-count">0</span></p>' +
                                '<div class="buttons">' +
                                '<div class="prev"></div>' +
                                '<div class="next"></div>' +
                                '</div>' +
                                '<div class="clear"></div>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                // Output
                                '<div class="panel-output">' +
                                '<div class="preloader"><img alt="" src="/liferay-lims-portlet/images/preloader.gif" /></div>' +
                                '</div>' +
                                '<div class="panel-input">' +
                                '<textarea></textarea>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '</li>';
                    }

                    return html;
                },
                _autoSize: function() {
                    var instance = this;

                    var heightMonitorEl = instance._heightMonitor.getDOM();

                    if (!instance._chatInputWidth) {
                        instance._chatInputWidth = instance._chatInput.get('offsetWidth');

                        instance._heightMonitor.setStyle('width', instance._chatInputWidth);
                    }

                    var chatInputEl = instance._chatInput.getDOM();

                    var content = Liferay.Util.escapeHTML(chatInputEl.value);
                    var textNode = document.createTextNode(content);

                    heightMonitorEl.innerHTML = '';

                    heightMonitorEl.appendChild(textNode);

                    content = heightMonitorEl.innerHTML;

                    if (!content.length) {
                        content = '&nbsp;&nbsp;';
                    }

                    if (Liferay.Browser.isIe()) {
                        content = content.replace(/\n/g, '<br />');
                    }

                    heightMonitorEl.innerHTML = content;

                    var height = Math.max(heightMonitorEl.offsetHeight, 45);

                    height = Math.min(height, 64);
                    chatInputEl.style.overflowY = 'auto';

                    if (height !== instance._lastHeight) {
                        instance._lastHeight = height;

                        chatInputEl.style.height = height + 'px';
                        chatInputEl.style.overflowY = (height === 64) ? 'scroll' : 'hidden';

                        chatInputEl.parentNode.style.height = (height + 10) + 'px';
                    }
                },
                _sendMessage: function(content) {
                    var instance = this;
                    // Get content
                    var roomJID = instance._panelId;
                    var message = Liferay.Util.escapeHTML(content);

                    // Fire an event
                    A.fire('messageSend', {roomJID: roomJID, message: message});

                    // Update message window before we get a ping from server
                    instance._addMessageNode({
                        content: message,
                        created: new Date(),
                        outgoing: true,
                        from: {
                            fullName: themeDisplay.getUserName(),
                            userId: themeDisplay.getUserId(),
                            companyId: themeDisplay.getCompanyId(),
                            screenName: Liferay.Chat.Globals.currentUserScreenName
                        }
                    });
                    // Update title
                    if (instance.conversation.roomVisibility === Liferay.Chat.PollerKeys.JABBER_ROOM_TYPE_PRIVATE) {
                        instance._panelTitle = themeDisplay.getUserName() + " (+" + instance._buddiesCount + ")";
                    }
                    instance._panelIcon = Liferay.Chat.Util.getUserImagePathScreenName(
                            Liferay.Chat.Globals.currentUserScreenName,
                            themeDisplay.getCompanyId());
                    instance._updateTitle();

                    // Scroll to bottom
                    instance._scrollToBottom();
                },
                _updateTitle: function() {
                    var instance = this;
                    // Title
                    instance._title.html(instance._panelTitle);
                    instance._triggerTitle.html(instance._panelTitle);
                    // Panel icon
                    instance._portrait.set('src', instance._panelIcon);
                },
                _updateMessageWindow: function(messages) {
                    var instance = this;
                    for (var index in messages) {
                        instance._addMessageNode(messages[index]);
                    }
                },
                _updateSearch: function() {
                    var instance = this;
                    instance._searchMenu.refreshIndex();
                },
                _addMessageNode: function(message) {
                    var instance = this;

                    if (!message.from) {
                        return;
                    }
                    // Date of creation
                    var createdPrettified = '';
                    var created = '';
                    if (message.created) {
                        var date = new Date(message.created);
                        createdPrettified = Liferay.Chat.Util.prettyDate(date);
                        created = date.toLocaleTimeString();
                    }
                    var outputEl = instance._chatOutput;
                    var cssClass = message.outgoing ? 'outgoing' : '';
                    var content = message.content || "";
                    content = content.replace(/\n/g, '<br />');
                    var userName = message.from.fullName || "";

                    // User image path
                    var userImagePath = message.from.portraitId ?
                            Liferay.Chat.Util.getUserImagePath(message.from.portraitId) :
                            Liferay.Chat.Util.getUserImagePathScreenName(message.from.screenName, message.from.companyId);

                    var node = '<div class="blurb ' + cssClass + '">' +
                            '<img class="buddy-image" alt="" src="' + userImagePath + '" />' +
                            '<div class="message">' +
                            '<span class="name">' + userName + '</span>' +
                            '<span class="date" title="' + created + '">' + createdPrettified + '</span>' +
                            '<span class="text">' + content + '</span>' +
                            '<div class="clear"></div>' +
                            '</div>' +
                            '<div class="clear"></div>' +
                            '</div>';

                    // Append
                    outputEl.append(node);
                },
                _removeOutgoingNodes: function() {
                    A.all(".blurb.outgoing").each(function(outgoing) {
                        outgoing.remove();
                    });
                },
                _scrollToBottom: function() {
                    var instance = this;
                    setTimeout(function() {
                        instance._chatOutput.getDOM().scrollTop =
                                instance._chatOutput.getDOM().scrollHeight -
                                instance._chatOutput.getDOM().clientHeight;
                    }, 1);
                },
                // ------------------------------------------------------------------------------
                //    Events
                // ------------------------------------------------------------------------------
                _onKeystroke: function(event) {
                    var instance = this;

                    var chatInput = instance._chatInput;
                    var chatInputEl = chatInput.getDOM();
                    var content = chatInputEl.value.replace(/\n|\r/gim, '');

                    // Send message on enter
                    if (event.keyCode === 13 && !event.shiftKey && content.length) {
                        instance._sendMessage(chatInputEl.value);
                        chatInputEl.value = '';
                    }

                    instance._autoSize();
                },
                _onPrimaryMenuOpen: function() {
                    var instance = this;
                    if (instance._primaryMenu.hasClass('opened')) {
                        instance.closeMenu(instance._primaryMenu);
                    } else {
                        instance.openMenu(instance._primaryMenu, 90);
                    }
                },
                _onSecondaryMenuOpen: function(menu) {
                    var instance = this;

                    // Hide other menus
                    for (var key in  instance._secondaryMenuElements) {
                        var el = instance._secondaryMenuElements[key];
                        if (el !== menu) {
                            el.hide();
                        } else {
                            el.show();
                        }
                    }

                    // Get the maximum height of menu
                    var height = menu._menuEl.get('clientHeight') < 140 ? menu._menuEl.get('clientHeight') : 140;

                    // Close primary menu if opened
                    if (instance._primaryMenu.hasClass('opened')) {
                        instance.closeMenu(instance._primaryMenu, false);
                    }
                    // Open secondary menu if closed
                    if (!instance._secondaryMenu.hasClass('opened')) {
                        instance.openMenu(instance._secondaryMenu, height);
                    }
                },
                _onTertiaryMenuOpen: function(element) {
                    var instance = this;

                    // Get the maximum height of menu
                    var height = element.get('clientHeight') < 140 ? element.get('clientHeight') : 140;

                    // Close primary menu if opened
                    if (instance._primaryMenu.hasClass('opened')) {
                        instance.closeMenu(instance._primaryMenu, false);
                    }
                    // Close secondary menu if opened
                    if (instance._secondaryMenu.hasClass('opened')) {
                        instance.closeMenu(instance._secondaryMenu, false);
                    }
                    if (!instance._tertiaryMenu.hasClass('opened')) {
                        instance.openMenu(instance._tertiaryMenu, height, function() {
                            instance._tertiaryMenu.on('clickoutside', function() {
                                if (!instance._searchInputEl.hasClass('selected') &&
                                        instance._searchInputEl.get('value').length === 0) {
                                    instance.closeMenu(instance._tertiaryMenu, true);
                                }
                            }, instance);
                        });
                    }
                },
                _onSearchOpen: function() {
                    var instance = this;
                    instance._onTertiaryMenuOpen(instance._searchMenuEl);
                    instance._searchInputEl.addClass('selected');
                    instance.openSearch();
                },
                _onSearchClose: function() {
                    var instance = this;
                    if (!instance._searchInputEl.get('value')) {
                        instance._searchInputEl.removeClass('selected');
                        instance.closeSearch();
                        instance.closeMenu(instance._tertiaryMenu, true);
                        instance._searchMenu.clear();
                    }
                }
            });
        });