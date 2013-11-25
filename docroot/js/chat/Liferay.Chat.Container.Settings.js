
AUI().use('aui-base', function(A) {

    Liferay.namespace('Chat.Container');

    // ------------------------------------------------------------------------------
    //    Settings Container
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Container.Settings = function() {
        var instance = this;

        // Panel
        instance.panel = new Liferay.Chat.Panel({
            fromMarkup: '.chat-tabs > .chat-settings',
            panelId: 'settings'
        });

        // Instance Vars
        var panel = instance.panel.getPanel();
        // Objects                                
        instance._playSoundObj = panel.one('#playSound');
        // Events
        instance._playSoundObj.on('change', instance._onSettingsChanged, instance);
    };

    Liferay.Chat.Container.Settings.prototype = {
        getMute: function() {
            var instance = this;
            return instance._playSoundObj.get('checked') ? false : true;
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
        },
        // ------------------------------------------------------------------------------
        //    Events
        // ------------------------------------------------------------------------------
        _onSettingsChanged: function() {
            var instance = this;
            // Send notification that settings has been changed                    
            A.fire('settingsChanged', {mute: instance.getMute()});
        }
    };
});