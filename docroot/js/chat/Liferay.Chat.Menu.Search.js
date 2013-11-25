
AUI().use('aui-base', 'aui-live-search', 'anim', function(A) {
    Liferay.namespace('Chat.Menu');

    // ------------------------------------------------------------------------------
    //    Conversation
    // ------------------------------------------------------------------------------  
    Liferay.Chat.Menu.Search = function(options) {
        var instance = this;

        if (!options.menuEl) {
            throw "Options must contain menuEl.";
        }

        // Private vars
        instance._options = options;
        instance._input = options.input;
        instance._menuEl = options.menuEl;
        instance._scrollArea = options.scrollArea;
        instance._searchNodes = [];
        instance._searchCurrentNodeIndex = 0;
        // Elements
        instance._nextButtonEl = instance._menuEl.one('.buttons .next');
        instance._prevButtonEl = instance._menuEl.one('.buttons .prev');
        instance._resultsCountEl = instance._menuEl.one('.results-count');
        // Live search
        instance._liveSearch = new A.LiveSearch({
            input: options.input,
            nodes: options.nodes,
            data: function(node) {
                return node.one('.text').text();
            },
            show: function(node) {
                var textEl = node.one('.text');
                var query = instance._liveSearch.query;

                var content = textEl.text();
                if (query) {
                    // Highlight queried text
                    content = instance._highlight(content, query);
                } else {
                    // Remove highlight if there is no query
                    content = instance._unhighlight(content);
                }
                textEl.html(content);
                // Add to nodes list
                instance._searchNodes.push(node);
            },
            hide: function(node) {
                // Remove highlight
                var textEl = node.one('.text');
                var content = textEl.text();
                content = instance._unhighlight(content);
                textEl.html(content);
            },
            after: {
                search: function(event) {                    
                    instance._refreshCounter();
                    // Scroll to node
                    if (instance._searchNodes.length > 0) {
                        var node = instance._searchNodes[instance._searchCurrentNodeIndex];
                        instance._scrollToNode(node);
                    }
                }
            }
        });

        // Events        
        instance._nextButtonEl.on('click', function() {
            instance._searchNext();
        }, instance);
        instance._prevButtonEl.on('click', function() {
            instance._searchPrev();
        }, instance);

        instance._liveSearch.on('search', function(e) {            
            instance._searchNodes = [];
            instance._searchCurrentNodeIndex = 0;
        });
    };

    Liferay.Chat.Menu.Search.prototype = {
        show: function() {
            var instance = this;
            instance._menuEl.show();
        },
        hide: function() {
            var instance = this;
            instance._menuEl.hide();
        },
        refreshIndex: function() {
            var instance = this;
            instance._liveSearch.refreshIndex();
        },
        clear: function() {
            var instance = this;
            // Unhighlight text
            for (var i in instance._searchNodes) {
                var searchNode = instance._searchNodes[i];
                var textEl = searchNode.one('.text');
                var content = textEl.text();
                content = instance._unhighlight(content);
                textEl.html(content);
            }
            // Reset nodes
            instance._searchNodes = [];
            instance._searchCurrentNodeIndex = 0;
            instance._refreshCounter();
        },
        _refreshCounter: function() {
            var instance = this;
            if (instance._searchNodes.length > 0 && instance._input.get('value')) {
                instance._resultsCountEl.html((instance._searchCurrentNodeIndex + 1) + "/" + instance._searchNodes.length);
            } else {
                instance._resultsCountEl.html(0);
            }
        },
        _highlight: function(content, query) {
            // Case insensitive and global occurence (all occurences in content) 
            var reg = new RegExp(query, "gi");
            // Wrap query text with highlight span
            content = content.replace(reg, function(matched) {
                // Return highlighted text, matched is used to prevent case
                return '<span class="highlight">' + matched + '</span>';
            });
            return content;
        },
        _unhighlight: function(content) {
            // Remove span wrapper
            content = content.replace('<span class="highlight">', content);
            content = content.replace('</span>', content);
            return content;
        },
        _scrollToNode: function(node) {
            var instance = this;
            if (!node) {
                return;
            }
            var top = node.get('offsetTop') - 80;

            var animation = new A.Anim({
                node: instance._scrollArea,
                duration: 0.2,
                to: {
                    scrollTop: top
                }
            });
            animation.run();
        },
        _searchNext: function() {
            var instance = this;
            if (instance._searchCurrentNodeIndex < instance._searchNodes.length - 1) {
                instance._searchCurrentNodeIndex++;
            } else {
                instance._searchCurrentNodeIndex = 0;
            }
            instance._refreshCounter();
            // Scroll to node
            var node = instance._searchNodes[instance._searchCurrentNodeIndex];
            instance._scrollToNode(node);
        },
        _searchPrev: function() {
            var instance = this;
            if (instance._searchCurrentNodeIndex > 0) {
                instance._searchCurrentNodeIndex--;
            } else {
                instance._searchCurrentNodeIndex = instance._searchNodes.length - 1;
            }
            instance._refreshCounter();
            // Scroll to node
            var node = instance._searchNodes[instance._searchCurrentNodeIndex];
            instance._scrollToNode(node);
        }
    };
});