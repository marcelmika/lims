

AUI().use(
        'aui-base',
        'stylesheet',
        function(A) {
            Liferay.namespace('Chat');

            // ------------------------------------------------------------------------------
            //    Panel
            // ------------------------------------------------------------------------------  
            Liferay.Chat.Panel = function(options) {
                var instance = this;

                if (!options.container) {
                    instance._tabsContainer = Liferay.Chat.Globals.tabsContainerEl;
                }
                else {
                    instance._tabsContainer = A.one(options.container);
                }

                instance._chatProperties = {};                
                instance._panelId = options.panelId;
                instance._panelTitle = options.panelTitle;
                instance._panelIcon = options.panelIcon;

                var panelHTML = instance._setPanelHTML(options.panelHTML);
                instance.set('panelHTML', panelHTML);
                instance._createPanel(options.fromMarkup);


                if (options.panelTitle) {
                    instance.setTitle(options.panelTitle);
                }



                instance._popupTrigger.unselectable();
            };


            // ------------------------------------------------------------------------------
            //    Panel Prototype
            // ------------------------------------------------------------------------------  
            Liferay.Chat.Panel.prototype = {
                show: function() {
                    var instance = this;

                    instance.set('selected', true);
                    instance._panel.addClass('selected');
                    instance.isOpened = true;

                    A.fire('showPanel', instance);
                },
                hide: function() {
                    var instance = this;
                    instance.isOpened = false;
                    instance.set('selected', false);
                    instance._panel.removeClass('selected');

                    A.fire('hidePanel', instance);
                },
                close: function() {
                    var instance = this;
                    instance.isOpened = false;
                    instance._panel.remove();


                    A.fire('closePanel', instance);
                },
                toggle: function() {
                    var instance = this;

                    if (instance.get('selected')) {
                        instance.hide();
                    }
                    else {
                        instance.show();
                    }
                },
                getPanel: function() {
                    var instance = this;

                    return instance._panel;
                },
                getTitle: function() {
                    var instance = this;

                    return instance._popupTitle.text();
                },
                setInvisible: function(invisible) {
                    var instance = this;
                    if (invisible) {
                        instance._panel.hide();
                    } else {
                        instance._panel.show();
                    }
                },
                setTitle: function(value) {
                    var instance = this;

                    instance._popupTrigger.one('.trigger-name').text(value);
                    instance._popupTitle.text(value);
                },
                _createPanel: function(fromMarkup) {
                    var instance = this;

                    var panel;
                    var markupEl = A.one(fromMarkup);

                    if (fromMarkup && markupEl) {
                        panel = A.one(fromMarkup);
                    }
                    else {
                        panel = A.Node.create(instance.get('panelHTML'));
                    }

                    if (panel.hasClass('selected')) {
                        instance.isOpened = panel.hasClass('selected');
                    }
                    instance._popup = panel.one('.chat-panel');
                    instance._popupTitle = panel.one('.panel-title');
                    instance._textBox = panel.one('textarea');
                    instance._popupTrigger = panel.one('.panel-trigger');
                    instance._popupTrigger.on('click', instance.toggle, instance);

                    panel.all('.panel-button').on(
                            'click',
                            function(event) {
                                var target = event.currentTarget;

                                if (target.hasClass('minimize')) {
                                    instance.hide();
                                }
                                else if (target.hasClass('close')) {
                                    instance.close();
                                }
                            }
                    );

                    instance._panel = panel;

                    instance._tabsContainer.append(panel);
                },
                _setPanelHTML: function(html) {
                    var instance = this;

                    if (!html) {
                        html = '<li class="panel">' +
                                '<div class="panel-trigger"><span class="trigger-name"></span></div>' +
                                '<div class="chat-panel">' +
                                '<div class="panel-window">' +
                                '<div class="panel-button minimize"></div>' +
                                '<div class="panel-title"></div>' +
                                '<div class="search-buddies"><input class="search-buddies-field" type="text" /></div>' +
                                '<div class="panel-content"></div>' +
                                '</div>' +
                                '</div>' +
                                '</li>';
                    }

                    return html;
                }
            };


            A.augment(Liferay.Chat.Panel, A.Attribute);

        }
);