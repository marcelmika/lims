
AUI().use('aui-base', function(A) {

    Liferay.namespace('Chat.Container');

    // ------------------------------------------------------------------------------
    //    Status Container
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Container.Status = function() {
        var instance = this;

        // Panel
        instance.panel = new Liferay.Chat.Panel({
            fromMarkup: '.chat-tabs > .status-panel',
            panelId: 'status'
        });
        // Instance Vars                
        var panel = instance.panel.getPanel();
        instance.node = panel.one('.status');
        instance._statusIndicator = panel.one('.status-indicator');

        // Select status action
        instance.node.delegate('click', function(event) {
            var target = event.currentTarget;
            if (target.ancestor('li')) {
                var status = target.getAttribute('data-status');
                A.fire('statusChanged', status);
            }
        }, 'li');

        // Events
        A.on('statusChanged', instance._onStatusChanged, instance);
    };

    Liferay.Chat.Container.Status.prototype = {
        _onStatusChanged: function(status) {
            var instance = this;
            instance._statusIndicator.setAttribute('class', 'status-indicator ' + Liferay.Chat.Util.getStatusCssClass(status));
            instance.panel.hide();
        },
        hidePanels: function(panel) {
            var instance = this;
            if (instance.panel !== panel && instance.panel.isOpened) {
                instance.panel.hide();
            }
        },
        invisible: function(invisible) {
            var instance = this;
            instance.panel.setInvisible(invisible);
        }
    };
});