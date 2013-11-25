
AUI().use(
        'aui-base',
        'liferay-poller',
        function(A) {
            Liferay.namespace('Chat');

            // ------------------------------------------------------------------------------
            //    Poller Keys
            // ------------------------------------------------------------------------------   
            Liferay.Chat.PollerKeys = {
                // Action
                POLLER_ACTION_CREATE_MESSAGE: "poller.action.create.message",
                POLLER_ACTION_OPEN_CONVERSATION: "poller.action.open.conversation",
                POLLER_ACTION_CLOSE_CONVERSATION: "poller.action.close.conversation",
                POLLER_ACTION_LEAVE_CONVERSATION: "poller.action.leave.conversation",
                POLLER_ACTION_ADD_TO_CONVERSATION: "poller.action.add.to.conversation",
                POLLER_ACTION_SEND_MESSAGE: "poller.action.send.message",
                POLLER_ACTION_SAVE_SETTINGS: "poller.action.save.settings",
                POLLER_ACTION_CHANGE_STATUS: "poller.action.change.status",
                POLLER_ACTION_CHANGE_ACTIVE_PANEL: "poller.action.change.active.panel",
                POLLER_ACTION_CHANGE_ACTIVE_ROOM_TYPE: "poller.action.change.active.room.type",
                POLLER_ACTION_CHAT_ENABLED: "poller.action.chat.enabled",
                // Status
                JABBER_STATUS_ONLINE: "jabber.status.online",
                JABBER_STATUS_BUSY: "jabber.status.busy",
                JABBER_STATUS_UNAVAILABLE: "jabber.status.unavailable",
                JABBER_STATUS_INVISIBLE: "jabber.status.invisible",
                JABBER_STATUS_OFF: "jabber.status.off",
                // Room
                JABBER_ROOM_TYPE_PRIVATE: "jabber.room.type.private",
                JABBER_ROOM_TYPE_PUBLIC: "jabber.room.type.public"
            };

            // ------------------------------------------------------------------------------
            //    Poller
            // ------------------------------------------------------------------------------ 
            Liferay.Chat.Poller = {
                init: function() {
                    var instance = this;
                    // Vars
                    instance._portletId = Liferay.Chat.Globals.chatPortletId;
                    // Update listener
                    Liferay.Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);
                    // Session expired
                    Liferay.bind('sessionExpired', function(event) {
                        Liferay.Poller.removeListener(instance._portletId);
                        A.fire('pollerSessionExpired');
                    });
                },
                send: function(data, key) {
                    var instance = this;
                    Liferay.Poller.submitRequest(instance._portletId, data, key);
                },
                suspend: function() {
                    Liferay.Poller.suspend();
                },
                resume: function() {
                    Liferay.Poller.resume();
                },
                _onPollerUpdate: function(response, chunkId) {
                    A.fire('pollerUpdate', response);
                }
            };
        }
);