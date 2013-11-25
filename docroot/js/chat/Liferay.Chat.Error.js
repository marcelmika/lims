
AUI().use(
        'aui-base',
        'anim',
        function(A) {
            Liferay.namespace('Chat');


            Liferay.Chat.Error = function() {
                var instance = this;

                instance._errorEl = Liferay.Chat.Globals.chatContainerEl.one('.errorNotification');                
                instance._isVisible = false;                
                instance._positionFromBottomOn = 40;
                instance._positionFromBottomOff = -5;
                instance._animation = new A.Anim({
                    node: instance._errorEl,
                    duration: 0.8,
                    easing: A.Easing.elasticIn
                });
            };

            Liferay.Chat.Error.prototype = {
                setMessage: function(message) {
                    var instance = this;
                    instance._errorEl.html("Chat error: " + message);
         
                },
                show: function() {
                    var instance = this;
                    // Show only if it's not already shown
                    if (!instance._isVisible) {
                        // Get current xy of error element
                        var xy = instance._errorEl.getXY();
                        var x = xy[0];
                        var y = xy[1];
                        // Set final position
                        instance._animation.set('to', {
                            xy: [x, y - instance._positionFromBottomOn],
                            opacity: 1
                        });
                        // Switch top to bottom (we can't have bottom = 0 and start moving with top because this
                        // will increase element height)
                        instance._errorEl.setStyle("top", y);
                        instance._errorEl.setStyle("bottom", "auto");
                        // Run animation
                        instance._animation.run();                        
                        instance._animation.on('end', function() {
                            instance._isVisible = true;
                            // Switch bottom to top (thanks to that we can still change the window size and
                            // and element will be moving properly)
                            instance._errorEl.setStyle("top", "auto");
                            instance._errorEl.setStyle("left", "auto");
                            instance._errorEl.setStyle("bottom", instance._positionFromBottomOn);
                        });
                    }
                },
                hide: function() {
                    var instance = this;
                    // Hide only if it's shown
                    if (instance._isVisible) {
                       // Get current xy of error element
                        var xy = instance._errorEl.getXY();
                        var x = xy[0];
                        var y = xy[1];         
                        // Set final position
                        instance._animation.set('to', {
                            xy: [x, y + instance._positionFromBottomOn + instance._positionFromBottomOff],
                            opacity: 0
                        });
                        // Switch top to bottom (we can't have bottom = 0 and start moving with top because this
                        // will increase element height)
                        instance._errorEl.setStyle("top", y);
                        instance._errorEl.setStyle("bottom", "auto");
                        // Run animation
                        instance._animation.run();                        
                        instance._animation.on('end', function() {
                            instance._isVisible = false;
                            // Switch bottom to top (thanks to that we can still change the window size and
                            // and element will be moving properly)
                            instance._errorEl.setStyle("top", "auto");
                            instance._errorEl.setStyle("left", "auto");
                            instance._errorEl.setStyle("bottom", instance._positionFromBottomOn + instance._positionFromBottomOff);
                        });
                    }
                }
            };
        }
);